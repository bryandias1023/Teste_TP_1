package com.example.teste_tp_1.entities;

import android.os.Parcel;

import java.io.Serializable;

public class Person implements Serializable {
    private Integer id;
    private String nome;
    private String morada;
    private String apelido;
    private String genero;
    private String profissao;
    private Integer telemovel;
    private Integer idade;
    private String codigoPostal;
    private Integer user_id;
    private Integer pais_id;

    public Person(Integer id, String nome, String apelido, String morada, String profissao, String genero,String codigopostal, Integer idade, Integer telemovel, Integer user_id,Integer pais_id){
        this.id = id;
        this.nome=nome;
        this.apelido=apelido;
        this.morada=morada;
        this.profissao=profissao;
        this.genero=genero;
        this.codigoPostal=codigopostal;
        this.idade=idade;
        this.telemovel=telemovel;
        this.user_id=user_id;
        this.pais_id=pais_id;

    }



    public Integer getId() {
        return id;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getApelido() {
        return apelido;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public Integer getTelemovel() {
        return telemovel;
    }

    public String getGenero() {
        return genero;
    }

    public String getMorada() {
        return morada;
    }

    public String getNome() {
        return nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(Integer numero) {
        this.telemovel = numero;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }


}
