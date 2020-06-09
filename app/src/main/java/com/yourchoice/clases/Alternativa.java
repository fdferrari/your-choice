package com.yourchoice.clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author federico
 */
public class Alternativa implements Serializable{

    private String nombre;
    private Double IG;
    private List<CaracteristicaValor> listaValor;

    public Alternativa(String nombre, List<Caracteristica> listaCaract) {
        this.nombre = nombre;
        this.listaValor = new ArrayList<>();
        for (Caracteristica caract : listaCaract) {
            this.listaValor.add(new CaracteristicaValor(0, caract));
        }
        this.IG = 0.0;
    }

    public List<CaracteristicaValor> getListaValor() {
        return listaValor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getIG() {
        return IG * 100.0;
    }

    public void setIG(Double IG) {
        this.IG = IG;
    }
}
