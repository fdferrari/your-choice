package com.yourchoice.adapador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yourchoice.R;
import com.yourchoice.clases.CaracteristicaValor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by federico on 07/10/2015.
 */
public class AdaptadorCaracteristicaValor extends ArrayAdapter<CaracteristicaValor> {
    private CaracteristicaValor[] datos;
    private Map<String, Integer> listaValoracion;
    private List<CaracteristicaValor> valorList;


    public AdaptadorCaracteristicaValor(Context context, CaracteristicaValor[] datos, List<CaracteristicaValor> valorList) {
        super(context, R.layout.list_item_rating, datos);
        this.datos = datos;
        this.valorList = valorList;
        this.listaValoracion = new HashMap<>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View item = inflater.inflate(R.layout.list_item_rating, null);
        TextView alternativa = (TextView) item.findViewById(R.id.texto_puntaje);
        alternativa.setText(this.datos[position].getCaracteristica().getNombre());
        TextView textoBarra = (TextView) item.findViewById(R.id.texto_barra);
        textoBarra.setText(this.datos[position].getPuntaje() + "/10");
        SeekBar barra = (SeekBar) item.findViewById(R.id.barra);
        barra.setProgress(this.datos[position].getPuntaje());
        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TextView texto = (TextView) item.findViewById(R.id.texto_puntaje);
                listaValoracion.put(texto.getText().toString(), seekBar.getProgress());
                for (CaracteristicaValor cValor : valorList) {
                    cValor.setPuntaje(listaValoracion.get(cValor.getCaracteristica().getNombre()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textoBarra = (TextView) item.findViewById(R.id.texto_barra);
                textoBarra.setText(progress + "/10");
            }
        });
        return (item);
    }

}
