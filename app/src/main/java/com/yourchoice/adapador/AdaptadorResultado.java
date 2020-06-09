package com.yourchoice.adapador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yourchoice.clases.Alternativa;
import com.yourchoice.R;

/**
 * Created by federico on 09/10/2015.
 */
public class AdaptadorResultado extends ArrayAdapter<Alternativa> {

    private Alternativa[] datos;

    public AdaptadorResultado(Context context, Alternativa[] datos) {
        super(context, R.layout.list_item_resultado, datos);
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View item = inflater.inflate(R.layout.list_item_resultado, null);
        TextView alter_res = (TextView) item.findViewById(R.id.alter_res);
        TextView ig_res = (TextView) item.findViewById(R.id.ig_res);
        ProgressBar progressBar = (ProgressBar) item.findViewById(R.id.barra_res);
        Integer ig = this.datos[position].getIG().intValue();
        alter_res.setText(this.datos[position].getNombre());
        ig_res.setText(ig.toString());
        progressBar.setProgress(ig);
        return (item);
    }
}
