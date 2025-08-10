package com.lucaspoltronieri.controlealimentar;

public class Refeicao {
    private int tipoRefeicao;
    private String data;
    private int apetite;
    private boolean foraHorario;

    public Refeicao(int tipoRefeicao, String data, int apetite, boolean foraHorario) {
        this.tipoRefeicao = tipoRefeicao;
        this.data = data;
        this.apetite = apetite;
        this.foraHorario = foraHorario;
    }

    public int getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(int tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getApetite() {
        return apetite;
    }

    public void setApetite(int apetite) {
        this.apetite = apetite;
    }

    public boolean isForaHorario() {
        return foraHorario;
    }

    public void setForaHorario(boolean foraHorario) {
        this.foraHorario = foraHorario;
    }
}
