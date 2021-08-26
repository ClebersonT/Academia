/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entidades.Musculacao;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author CLEBERSON DELL
 */

@Named(value = "AcademiaDBBean")
@RequestScoped
public class AcademiaDBBean {
    @Resource(lookup = "java:/academiaDBDS")
     private DataSource academiaDataSource;
    
    
    private ArrayList<Musculacao> musculacoes;
    private Musculacao musculacao;
    private String diaSelecionado;
    private String nivelSelecionado;

    private boolean cadastrar;
    private boolean pesquisar;
    private ArrayList<String> mensagensErro;
    
    
    @PostConstruct
    public void init(){
        musculacoes = new ArrayList();
        musculacao = new Musculacao();
        mensagensErro = new ArrayList();
        cadastrar = false;
        pesquisar = false;
    }

    public ArrayList<Musculacao> getMusculacoes() throws SQLException{
         getTreinosDB();
        return musculacoes;
    }

    public void setMusculacoes(ArrayList<Musculacao> musculacoes) {
        this.musculacoes = musculacoes;
    }

    public Musculacao getMusculacao() {
        return musculacao;
    }

    public void setMusculacao(Musculacao musculacao) {
        this.musculacao = musculacao;
    }

    public boolean isCadastrar() {
        return cadastrar;
    }

    public void setCadastrar(boolean cadastrar) {
        this.cadastrar = cadastrar;
    }

    public boolean isPesquisar() {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar) {
        this.pesquisar = pesquisar;
    }

    public ArrayList<String> getMensagensErro() {
        return mensagensErro;
    }

    public void setMensagensErro(ArrayList<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }
    
    public String cadastrarMusculacao() throws SQLException{
        saveTreinoDB();
        reset();
        return "index";
    }
    
     public void pesquisarTreino(){
        pesquisar = true;
        cadastrar = false;
    }
   
    public void reset(){
       musculacao = new Musculacao();
       cadastrar = true;
       pesquisar = false;
    }
    
    public String getDiaSelecionado() {
        return diaSelecionado;
    }

    public void setDiaSelecionado(String diaSelecionado) {
        this.diaSelecionado = diaSelecionado;
    }
    
    public String getNivelSelecionado() {
        return nivelSelecionado;
    }

    public void setNivelSelecionado(String nivelSelecionado) {
        this.nivelSelecionado = nivelSelecionado;
    }
    
     public Connection getConnectionDB(){
       Connection conexão = null;
       if(academiaDataSource == null){
           mensagensErro.add("DataSource não acessível");
           return null;
       }
       try{ conexão = academiaDataSource.getConnection(); }
       catch (SQLException exception) {mensagensErro.add(exception.getMessage());}
       return conexão;
   }
     
     public String getTreinosDB() throws SQLException{
       String próxima_página = "";
       ArrayList<Musculacao> treinos = new ArrayList();
       Connection conexão = getConnectionDB();
       if (conexão == null) return "";
       PreparedStatement comando = null;
       ResultSet consultas = null;
       try{
           comando = conexão.prepareStatement("SELECT NomeTreino, Objetivo, DescricaoTreino, TipoTreino, DiasDaSemana FROM Musculacao ORDER BY NomeTreino Desc");
           consultas = comando.executeQuery();
           while(consultas.next()){
               Musculacao musculacao = new Musculacao(consultas.getString("NomeTreino"),
                       consultas.getString("Objetivo"), 
                       consultas.getString("DescricaoTreino"),
                       consultas.getString("TipoTreino"), 
                       consultas.getString("DiasDaSemana"));
               treinos.add(musculacao);
           }
           consultas.close();
           comando.close();
           próxima_página = "index";
       }catch(SQLException exception){
           if(consultas != null) consultas.close();
           if(comando != null) comando.close();
           mensagensErro.add(exception.getMessage());
       }
       conexão.close();
       this.musculacoes = treinos;
       return próxima_página;
    }
     
      public void saveTreinoDB() throws SQLException{
       Connection conexão = getConnectionDB();
       if (conexão == null) return;
       PreparedStatement comando = null;
       try{
           comando = conexão.prepareStatement("INSERT INTO Musculacao(NomeTreino, Objetivo, DescricaoTreino, TipoTreino, DiasDaSemana) "
                   + " VALUES (?, ?, ?, ?, ?)");
           comando.setString(1, musculacao.getNomeTreino());
           comando.setString(2, musculacao.getObjetivo());
           comando.setString(3, musculacao.getDescricaoTreino());
           comando.setString(4, musculacao.getTipoTreino());
           comando.setString(5, musculacao.getDiasDaSemana());
           comando.executeUpdate();
           comando.close();
       }catch(SQLException exception){
           if(comando != null) comando.close();
           mensagensErro.add(exception.getMessage());
       }
       conexão.close();
       return;
   }
      
   public ArrayList<String> getInfoMusculacaoFiltros() throws SQLException{
       getFiltroPesquisaDB();
       ArrayList<String> info_musculacoes_filtros = new ArrayList();
       for(Musculacao musculacao : musculacoes){
           String nivel = musculacao.getTipoTreino();
           String dia = musculacao.getDiasDaSemana();
           
           if(nivel.equals(nivelSelecionado) && dia.equals(diaSelecionado)){
               boolean inserido = false;
               if(!inserido) info_musculacoes_filtros.add(musculacao.toString(true));
           }
       }
       return info_musculacoes_filtros;
   }

   public void getFiltroPesquisaDB() throws SQLException{
        ArrayList<Musculacao> treinos = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        try{
            comando = conexão.prepareStatement("SELECT * FROM Musculacao WHERE TipoTreino = ? && DiasDaSemana = ? ORDER BY TipoTreino DESC");
            comando.setString(1, nivelSelecionado);
            comando.setString(2, diaSelecionado);
            consultas = comando.executeQuery();
            while(consultas.next()){
                Musculacao musculacao = new Musculacao(consultas.getString("NomeTreino"),
                       consultas.getString("Objetivo"), 
                       consultas.getString("DescricaoTreino"),
                       consultas.getString("TipoTreino"), 
                       consultas.getString("DiasDaSemana"));
                treinos.add(musculacao);
            }
            consultas.close();
            comando.close();
            this.musculacoes = treinos;
        }catch(SQLException exception){
            if(consultas != null) consultas.close();
            if(comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        return;
    }   
}
