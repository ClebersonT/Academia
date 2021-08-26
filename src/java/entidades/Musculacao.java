/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author CLEBERSON DELL
 */
public class Musculacao {
    private String nomeTreino;
    private String objetivo;
    private String descricaoTreino;
    private String tipoTreino;
    private String diasDaSemana;

    
    public Musculacao(){
        
    }
    
    public Musculacao(String nomeTreino, String objetivo, String descricaoTreino, String tipoTreino, String diasDaSemana){
        this.nomeTreino = nomeTreino;
        this.objetivo = objetivo;
        this.descricaoTreino = descricaoTreino;
        this.tipoTreino = tipoTreino;
        this.diasDaSemana = diasDaSemana;
    }

    public String toString(boolean sem_filtro) {
        return "Musculacao - " + "nome = " + nomeTreino + " - objetivo = " + objetivo + ", Nível do Treino = " + tipoTreino;
    }
    
    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDescricaoTreino() {
        return descricaoTreino;
    }

    public void setDescricaoTreino(String descricaoTreino) {
        this.descricaoTreino = descricaoTreino;
    }

    public String getTipoTreino() {
        return tipoTreino;
    }

    public void setTipoTreino(String tipoTreino) {
        this.tipoTreino = tipoTreino;
    }

    public String getDiasDaSemana() {
        return diasDaSemana;
    }

    public void setDiasDaSemana(String diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }
    
}
