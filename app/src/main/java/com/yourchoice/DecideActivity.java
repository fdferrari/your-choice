package com.yourchoice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.yourchoice.adapador.AdaptadorResultado;
import com.yourchoice.clases.Alternativa;
import com.yourchoice.clases.ModeloSistema;
import com.yourchoice.clases.lsp.CalculatorLSP;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DecideActivity extends Fragment {

    //private Typeface typeFace;
    private SeekBar barraPolaridad;
    private ModeloSistema modeloSistema;
    private Dialog dialog;
    private AlertDialog dialogInfo;


    public List<Alternativa> getListaAlternativa() {
        return modeloSistema.getListaAlternativa();
    }

    private Comparator<Alternativa> comparator = new Comparator<Alternativa>() {
        public int compare(Alternativa a1, Alternativa a2) {
            return a1.getIG().compareTo(a2.getIG());
        }
    };


    public void setModeloSistema(ModeloSistema modeloSistema) {
        this.modeloSistema = modeloSistema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_decide, container, false);
        // typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
        barraPolaridad = (SeekBar) rootView.findViewById(R.id.barra_polaridad);
        barraPolaridad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                modeloSistema.setPolaridad(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
        });
        Button btnDecidir = (Button) rootView.findViewById(R.id.btn_decidir);
        // btnDecidir.setTypeface(typeFace);
        btnDecidir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decidir(v);
            }
        });

        ImageButton btnInfo = (ImageButton) rootView.findViewById(R.id.btn_info_decide);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDialog("Información...", getString(R.string.mensaje_polaridad));
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("modelo", modeloSistema);
        if (dialog != null)
            dialog.dismiss();
        if (dialogInfo != null)
            dialogInfo.dismiss();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (modeloSistema == null) {
                modeloSistema = (ModeloSistema) savedInstanceState.getSerializable("modelo");
            }
        }
    }

    public void decidir(View v) {
        if (this.getListaAlternativa().isEmpty()) {
            this.lanzarToast(Toast.LENGTH_SHORT, "No es posible decidir sin alternativas.");
        } else {
            if (this.getListaAlternativa().size() == 1) {
                this.lanzarToast(Toast.LENGTH_SHORT, "Es necesario ingresar al menos dos alternativas.");
            } else {
                CalculatorLSP calculatorLSP = new CalculatorLSP(modeloSistema.getPolaridad());
                for (Alternativa alternativa : getListaAlternativa()) {
                    alternativa.setIG(calculatorLSP.calcularLSP(alternativa.getListaValor()));
                }
                dialog = new Dialog(this.getContext());
                dialog.setContentView(R.layout.activity_resultado);
                TextView textModelo = (TextView) dialog.findViewById(R.id.text_modelo);
                ListView lista = (ListView) dialog.findViewById(R.id.lista_resultado);
                textModelo.setText(modeloSistema.getNombreModelo());
                //textModelo.setTypeface(typeFace);
                Collections.sort(getListaAlternativa(), comparator);
                Collections.reverse(getListaAlternativa());
                AdaptadorResultado adaptador = new AdaptadorResultado(dialog.getContext(),
                        getListaAlternativa().toArray(new Alternativa[0]));
                lista.setAdapter(adaptador);
                Button dialogCLOSEButton = (Button) dialog.findViewById(R.id.btn_cancel_res);
                // dialogCLOSEButton.setTypeface(typeFace);
                dialogCLOSEButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    private void lanzarDialog(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(titulo);
        builder.setIcon(R.drawable.info);
        builder.setMessage(mensaje);
        builder.setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialogInfo = builder.create();
        dialogInfo.show();
    }

    private void lanzarToast(int duracion, String mensaje) {
        Toast toast = Toast.makeText(this.getContext(), mensaje, duracion);
        toast.show();
    }


}
