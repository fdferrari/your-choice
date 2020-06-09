package com.yourchoice.clases.lsp;

import java.math.BigDecimal;

/**
 * @author federico ferrari - 64710
 */
public class PreferenciaElemental {

    private String descripcion;
    private BigDecimal valor;
    private BigDecimal peso;

    public PreferenciaElemental(String descripcion, BigDecimal valor, BigDecimal peso) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.peso = peso;
    }


    public BigDecimal getPeso() {
        return peso;
    }


    public BigDecimal getValor() {
        return valor;
    }

}
