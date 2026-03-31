package br.edu.fatecpg.consumoapi.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private int    id;
    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String logradouro;
    private List<Socio> qsa;

    public Empresa(String cnpj, String razao_social,
                   String nome_fantasia, String logradouro) {
        this.cnpj          = cnpj;
        this.razao_social  = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.logradouro    = logradouro;
        this.qsa           = new ArrayList<>();
    }


    public int    getId()           { return id; }
    public String getCnpj()         { return cnpj; }
    public String getRazao_social() { return razao_social; }
    public String getNome_fantasia(){ return nome_fantasia; }
    public String getLogradouro()   { return logradouro; }
    public List<Socio> getQsa()     { return qsa; }


    public void setId(int id)                       { this.id = id; }
    public void setCnpj(String cnpj)               { this.cnpj = cnpj; }
    public void setRazao_social(String razao_social){ this.razao_social = razao_social; }
    public void setNome_fantasia(String nome_fantasia){ this.nome_fantasia = nome_fantasia; }
    public void setLogradouro(String logradouro)   { this.logradouro = logradouro; }
    public void setQsa(List<Socio> qsa)            { this.qsa = qsa; }

    @Override
    public String toString() {
        return String.format(
                "%-14s | %-60s | %-30s | %s",
                cnpj, razao_social, nome_fantasia, logradouro
        );
    }
}