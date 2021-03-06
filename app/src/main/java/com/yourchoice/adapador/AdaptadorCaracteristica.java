package com.yourchoice.adapador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.yourchoice.clases.Caracteristica;

/**
 * Created by federico on 05/10/2015.
 */
public class AdaptadorCaracteristica extends ArrayAdapter<Caracteristica> {
    private Caracteristica[] datos;

    public AdaptadorCaracteristica(Context context, Caracteristica[] datos) {
        super(context, android.R.layout.simple_list_item_single_choice, datos);
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(android.R.layout.simple_list_item_single_choice, null);
        CheckedTextView itemDetalle = (CheckedTextView) item.findViewById(android.R.id.text1);
        itemDetalle.setText(datos[position].getNombre());
        return (item);
    }
}
