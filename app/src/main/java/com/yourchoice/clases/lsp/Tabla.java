package com.yourchoice.clases.lsp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author federico ferrari - 64710
 */
public class Tabla {

    private List<OperadorLSP> listOperadorLSP;

    public Tabla() {
        this.listOperadorLSP = new ArrayList<>();
        this.init();
    }

    private void init() {
        this.listOperadorLSP.add(new OperadorLSP("CC Fuerte (+)", "C++",
                new BigDecimal("-9.060"), new BigDecimal("-7.639"),
                new BigDecimal("-6.689"), new BigDecimal("-6.013")));
        this.listOperadorLSP.add(new OperadorLSP("CC Media", "CA",
                new BigDecimal("-0.720"), new BigDecimal("-0.732"),
                new BigDecimal("-0.721"), new BigDecimal("-0.707")));
        this.listOperadorLSP.add(new OperadorLSP("Media Aritmética", "A",
                new BigDecimal("1.0"), new BigDecimal("1.0"),
                new BigDecimal("1.0"), new BigDecimal("1.0")));
        this.listOperadorLSP.add(new OperadorLSP("CD Media", "DA",
                new BigDecimal("3.929"), new BigDecimal("4.450"),
                new BigDecimal("4.825"), new BigDecimal("5.111")));
        this.listOperadorLSP.add(new OperadorLSP("CD Fuerte (+)", "D++",
                new BigDecimal("20.63"), new BigDecimal("24.30"),
                new BigDecimal("27.11"), new BigDecimal("30.09")));
    }

    public BigDecimal getValR(String abrev, int entradas) throws Exception {
        BigDecimal bd = null;
        boolean existe = false;
        for (OperadorLSP operadorLSP : listOperadorLSP) {
            if (operadorLSP.getAbrev().equals(abrev)) {
                bd = operadorLSP.getValR(entradas);
                existe = true;
                break;
            }
        }
        if (!existe) {
            throw new Exception("El código ingresado no es válido.");
        }
        return bd;
    }
}
