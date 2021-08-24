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

    public ArrayList<Musculacao> getMusculacoes() {
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
    
     public void pesquisarTreino(){
        pesquisar = true;
        cadastrar = false;
    }
   
   public void reset(){
       musculacao = new Musculacao();
       cadastrar = true;
       pesquisar = false;
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
}
