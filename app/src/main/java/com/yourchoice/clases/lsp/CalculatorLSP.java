package com.yourchoice.clases.lsp;


import com.yourchoice.clases.CaracteristicaValor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author federico
 */
public class CalculatorLSP {

    private Tabla tabla;
    private List<CaracteristicaValor> listValor;
    private List<PreferenciaElemental> listPrefElem;
    private String codFun = "A";

    public CalculatorLSP(Integer polaridad) {
        this.listPrefElem = new ArrayList<>();
        this.tabla = new Tabla();
        switch (polaridad) {
            case 0: {
                codFun = "C++";
                break;
            }
            case 1: {
                codFun = "CA";
                break;
            }

            case 2: {
                codFun = "A";
                break;
            }
            case 3: {
                codFun = "DA";
                break;
            }
            case 4: {
                codFun = "D++";
                break;
            }

        }
    }


    public void setListValor(List<CaracteristicaValor> listValor) {
        this.listValor = listValor;
    }

    public Double calcularLSP(List<CaracteristicaValor> listValor) {
        Double resultado;
        try {
            this.setListValor(listValor);
            this.listPrefElem.clear();
            this.instanciar();
            resultado = this.wmp(listPrefElem, codFun).doubleValue();
        } catch (Exception e) {
            resultado = new BigDecimal("0.0").doubleValue();
        }
        return resultado;
    }

    private void instanciar() {
        PreferenciaElemental pe;
        int name = 1;
        //init modelo
        for (CaracteristicaValor caracteristicaValor : listValor) {
            pe = new PreferenciaElemental("IN " + name,
                    new BigDecimal(caracteristicaValor.normalizar().toString()),
                    new BigDecimal(caracteristicaValor.getCaracteristica().getPeso().toString()));
            this.listPrefElem.add(pe);
            name++;
        }

    }

    public BigDecimal wmp(List<PreferenciaElemental> listaPreferencia, String abrev)
            throws Exception {
        BigDecimal resultadoFinal;
        int cantEntradas = listaPreferencia.size();
        BigDecimal r = this.tabla.getValR(abrev, cantEntradas);
        BigDecimal oneOverR = new BigDecimal(Double.valueOf(1.0 / r.doubleValue()).toString());
        Double acumulador = 0.0;
        Double calcParcial;
        for (int i = 0; i < listaPreferencia.size(); i++) {
            calcParcial = Math.pow(listaPreferencia.get(i).getValor().doubleValue(),
                    r.doubleValue()) * listaPreferencia.get(i).getPeso().doubleValue();
            acumulador = acumulador + calcParcial;
        }
        calcParcial = Math.pow(acumulador, (oneOverR.doubleValue()));
        resultadoFinal = new BigDecimal(calcParcial.toString());
        return resultadoFinal;
    }
}
