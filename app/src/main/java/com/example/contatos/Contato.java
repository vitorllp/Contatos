package com.example.contatos;

public class Contato {

    private String nome;
    private String email;
    private boolean verificaNumero;
    private String telefone;
    private String celular;
    private String site;

    public Contato() {

    }

    public Contato(String nome, String email, String telefone, boolean verificaNumero, String celular, String site) {
        this.nome = nome;
        this.email = email;
        this.verificaNumero = verificaNumero;
        this.telefone = telefone;
        this.celular = celular;
        this.site = site;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerificaNumero() {
        return verificaNumero;
    }

    public void setVerificaNumero(boolean verificaNumero) {
        this.verificaNumero = verificaNumero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", verificaNumero=" + verificaNumero +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
