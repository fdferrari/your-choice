package com.yourchoice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.yourchoice.adapador.AdaptadorAlternativa;
import com.yourchoice.adapador.AdaptadorCaracteristicaValor;
import com.yourchoice.clases.Alternativa;
import com.yourchoice.clases.Caracteristica;
import com.yourchoice.clases.CaracteristicaValor;
import com.yourchoice.clases.ModeloSistema;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AgregaActivity extends Fragment {

    //private Typeface typeFace;
    private Dialog dialog;
    private AlertDialog dialogInfo;
    private ListView lista;
    private EditText nombreAlter;
    private ModeloSistema modeloSistema;
    private Alternativa alternativaSeleccionada;
    private Comparator<Alternativa> comparator = new Comparator<Alternativa>() {
        public int compare(Alternativa a1, Alternativa a2) {
            return a1.getNombre().compareToIgnoreCase(a2.getNombre());
        }
    };

    public List<Caracteristica> getListaCaract() {
        return modeloSistema.getListaCaract();
    }

    public List<Alternativa> getListaAlternativa() {
        return this.modeloSistema.getListaAlternativa();
    }

    public void setModeloSistema(ModeloSistema modeloSistema) {
        this.modeloSistema = modeloSistema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_agrega, container, false);
        //typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
        nombreAlter = (EditText) rootView.findViewById(R.id.name_alter);
        lista = (ListView) rootView.findViewById(R.id.lista_alter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                alternativaSeleccionada = ((Alternativa) a.getItemAtPosition(position));
                nombreAlter.setText(alternativaSeleccionada.getNombre());
            }
        });
        ImageButton btnOk = (ImageButton) rootView.findViewById(R.id.btn_ok_agrega);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarAlt(v);
            }
        });
        ImageButton btnEdita = (ImageButton) rootView.findViewById(R.id.btn_edit_agrega);
        btnEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarAlter(v);
            }
        });
        ImageButton btnDelete = (ImageButton) rootView.findViewById(R.id.btn_delete_agrega);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarAlter(v);
            }
        });
        ImageButton btnAgrega = (ImageButton) rootView.findViewById(R.id.btn_add_agrega);
        btnAgrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCaractValor(v);
            }
        });
        ImageButton btnInfo = (ImageButton) rootView.findViewById(R.id.btn_info_agrega);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDialog("Información...", getString(R.string.mensaje_agrega));
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
        this.cargarLista();
    }


    public void agregarAlt(View v) {
        if (getListaAlternativa().size() == 5) {
            this.lanzarToast(Toast.LENGTH_SHORT, "No es posible agregar mas de 5 elementos.");
        } else {
            if (getListaCaract().isEmpty()) {
                this.lanzarToast(Toast.LENGTH_SHORT, "No es posible agregar alternativas sin características.");
            } else {
                if (getListaCaract().size() == 1) {
                    this.lanzarToast(Toast.LENGTH_SHORT, "No es posible agregar alternativas con una sola característica.");
                } else {
                    if (nombreAlter.getText().toString().equals("")) {
                        this.lanzarToast(Toast.LENGTH_SHORT, "Debe ingresar el detalle de la alternativa.");
                    } else {
                        getListaAlternativa().add(new Alternativa(nombreAlter.getText().toString(),
                                this.getListaCaract()));
                        this.cargarLista();
                        this.limpiar();
                    }
                }
            }
        }
    }

    public void editarAlter(View v) {
        if (alternativaSeleccionada != null) {
            if (nombreAlter.getText().toString().equals("")) {
                this.lanzarToast(Toast.LENGTH_SHORT, "Debe ingresar el detalle de la alternativa.");
            } else {
                getListaAlternativa().remove(alternativaSeleccionada);
                alternativaSeleccionada.setNombre(nombreAlter.getText().toString());
                getListaAlternativa().add(alternativaSeleccionada);
                this.cargarLista();
                this.limpiar();
            }
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "Debe seleccionar un elemento para realizar esta operación.");
        }
    }

    public void borrarAlter(View v) {
        if (alternativaSeleccionada != null) {
            getListaAlternativa().remove(alternativaSeleccionada);
            this.cargarLista();
            this.limpiar();
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "Debe seleccionar un elemento para realizar esta operación.");
        }
    }

    public void agregarCaractValor(View v) {
        if (alternativaSeleccionada != null) {
            dialog = new Dialog(this.getContext());
            dialog.setContentView(R.layout.dialog_alternativa);
            TextView texto_alternativa = (TextView) dialog.findViewById(R.id.texto_alter);
            //dialog.setTitle(alternativaSeleccionada.getNombre());
            texto_alternativa.setText(alternativaSeleccionada.getNombre());
            ListView listaValorarAlternativa = (ListView) dialog.findViewById(R.id.lista);
            final AdaptadorCaracteristicaValor adaptador =
                    new AdaptadorCaracteristicaValor(dialog.getContext(),
                            alternativaSeleccionada.getListaValor().toArray(new CaracteristicaValor[0]),
                            alternativaSeleccionada.getListaValor());
            listaValorarAlternativa.setAdapter(adaptador);
            Button dialogCANCELButton = (Button) dialog.findViewById(R.id.btn_cancel);
            // dialogCANCELButton.setTypeface(typeFace);
            dialogCANCELButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limpiar();
                    dialog.dismiss();
                }
            });


            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            dialog.getWindow().setLayout(width, height);
            dialog.show();
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "Debe seleccionar un elemento para realizar esta operación.");
        }

    }

    public void cargarLista() {
        Collections.sort(getListaAlternativa(), comparator);
        AdaptadorAlternativa adaptador = new AdaptadorAlternativa(this.getContext(),
                getListaAlternativa().toArray(new Alternativa[0]));
        lista.setAdapter(adaptador);
    }

    private void limpiar() {
        nombreAlter.setText(null);
        lista.clearChoices();
        alternativaSeleccionada = null;
    }


//    private void lanzarDialog(String titulo, String mensaje) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setTitle(titulo);
//        builder.setIcon(R.drawable.info);
//        builder.setMessage(mensaje);
//        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void lanzarToast(int duracion, String mensaje) {
        Toast toast = Toast.makeText(this.getContext(), mensaje, duracion);
        toast.show();
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
        ImageView img = new ImageView(this.getContext());
        img.setImageResource(R.drawable.add);
        builder.setView(img);
        dialogInfo = builder.create();
        dialogInfo.show();
    }
}
