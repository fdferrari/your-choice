package com.yourchoice.clases;

import java.io.Serializable;

/**
 * @author federico
 */
public class Caracteristica implements Serializable{
    private String nombre;
    private Double peso;
    private Integer importancia;

    public Caracteristica() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getImportancia() {
        return importancia;
    }

    public void setImportancia(Integer importancia) {
        this.importancia = importancia;
    }


}
