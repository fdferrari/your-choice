package com.yourchoice.clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by federico on 14/10/2015.
 */
public class ModeloSistema implements Serializable {
    private String nombreModelo;
    private List<Caracteristica> listaCaract = new ArrayList<>();
    private List<Alternativa> listaAlternativa = new ArrayList<>();
    private Integer polaridad;

    public ModeloSistema() {
        this.limpiar();
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public List<Caracteristica> getListaCaract() {
        return listaCaract;
    }

    public List<Alternativa> getListaAlternativa() {
        return listaAlternativa;
    }

    public Integer getPolaridad() {
        return polaridad;
    }

    public void setPolaridad(Integer polaridad) {
        this.polaridad = polaridad;
    }

    public void limpiar() {
        this.nombreModelo = "";
        listaCaract.clear();
        listaAlternativa.clear();
        polaridad = 2;
    }
}
