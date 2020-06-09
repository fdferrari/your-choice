package com.yourchoice.clases;

import java.io.Serializable;

/**
 * @author federico
 */
public class CaracteristicaValor implements Serializable{

    private Integer puntaje;
    private Caracteristica caracteristica;


    public CaracteristicaValor(Integer puntaje, Caracteristica caracteristica) {
        this.puntaje = puntaje;
        this.setCaracteristica(caracteristica);
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        if (puntaje != null) {
            this.puntaje = puntaje;
        }
    }


    private Double redondear(Double numero) {
        return Math.rint(numero * 100) / 100;
    }


    //Convierte el puntaje a valores entre 0 y 1
    public Double normalizar() {
        Double valor = Double.valueOf(String.valueOf(puntaje));
        Double min = 0.0;//para que cuando valor sea 1 y no se haga 0.0 se arregla con min = 0.0
        Double max = 10.0;
        return this.redondear(Math.abs(Math.rint(((valor - min) / (max - min)) * 100) / 100));
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }
}
