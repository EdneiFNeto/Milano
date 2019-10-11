package com.example.milano.model;

public class Impressora {

    private String nome;
    private String address;

    public Impressora(String nome, String address) {
        this.nome = nome;
        this.address = address;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
