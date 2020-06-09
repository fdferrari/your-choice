package com.yourchoice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yourchoice.adapador.AdaptadorCaracteristica;
import com.yourchoice.clases.Caracteristica;
import com.yourchoice.clases.ModeloSistema;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ModelaActivity extends Fragment {

    private AlertDialog dialogInfo;
    private EditText nombreCaract;
    private RatingBar puntuacion;
    private ListView lista;
    private TextView textoPuntaje;
    private ImageButton btnOk;
    private ImageButton btnEdit;
    private ImageButton btnDelete;
    private ModeloSistema modeloSistema;
    private Caracteristica caractSeleccionada;
    private Comparator<Caracteristica> comparator = new Comparator<Caracteristica>() {
        public int compare(Caracteristica c1, Caracteristica c2) {
            return c1.getNombre().compareToIgnoreCase(c2.getNombre());
        }
    };

    public List<Caracteristica> getListaCaract() {
        return modeloSistema.getListaCaract();
    }

    public void setModeloSistema(ModeloSistema modeloSistema) {
        this.modeloSistema = modeloSistema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_modela, container, false);
        nombreCaract = (EditText) rootView.findViewById(R.id.name_caract);
        puntuacion = (RatingBar) rootView.findViewById(R.id.puntaje_caract);
        textoPuntaje = (TextView) rootView.findViewById(R.id.texto_puntaje);
        puntuacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float val = rating;
                if (val.intValue() == 1) {
                    textoPuntaje.setText("Sin importancia");
                } else {
                    if (val.intValue() == 2) {
                        textoPuntaje.setText("Poco importante");
                    } else {
                        if (val.intValue() == 3) {
                            textoPuntaje.setText("Importante");
                        } else {
                            if (val.intValue() == 4) {
                                textoPuntaje.setText("Muy importante");
                            } else {
                                if (val.intValue() == 5) {
                                    textoPuntaje.setText("Demasiado Importante");
                                }
                            }
                        }
                    }
                }
            }
        });
        lista = (ListView) rootView.findViewById(R.id.lista_caract);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                caractSeleccionada = ((Caracteristica) a.getItemAtPosition(position));
                nombreCaract.setText(caractSeleccionada.getNombre());
                puntuacion.setRating(caractSeleccionada.getImportancia() * 1.0f);
                if (caractSeleccionada.getImportancia() == 1) {
                    textoPuntaje.setText("Sin importancia");
                } else {
                    if (caractSeleccionada.getImportancia() == 2) {
                        textoPuntaje.setText("Poco importante");
                    } else {
                        if (caractSeleccionada.getImportancia() == 3) {
                            textoPuntaje.setText("Importante");
                        } else {
                            if (caractSeleccionada.getImportancia() == 4) {
                                textoPuntaje.setText("Muy importante");
                            } else {
                                if (caractSeleccionada.getImportancia() == 5) {
                                    textoPuntaje.setText("Demasiado Importante");
                                }
                            }
                        }
                    }
                }
            }
        });
        btnOk = (ImageButton) rootView.findViewById(R.id.btn_ok_modela);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCaract(v);
            }
        });
        btnEdit = (ImageButton) rootView.findViewById(R.id.btn_edit_modela);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarCaract(v);
            }
        });
        btnDelete = (ImageButton) rootView.findViewById(R.id.btn_delete_modela);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarCaract(v);
            }
        });
        ImageButton btnInfo = (ImageButton) rootView.findViewById(R.id.btn_info_modela);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               lanzarDialog("Información...", getString(R.string.mensaje_modela));
            }
        });
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("modelo", modeloSistema);
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

    public void agregarCaract(View v) {
        if (this.modeloSistema.getListaAlternativa().isEmpty()) {
            if (getListaCaract().size() == 5) {
                this.lanzarToast(Toast.LENGTH_SHORT, "No es posible agregar mas de 5 elementos.");
            } else {
                if (nombreCaract.getText().toString().equals("")) {
                    this.lanzarToast(Toast.LENGTH_SHORT, "Debe ingresar el detalle de la característica.");
                } else {
                    Caracteristica nuevoCaract = new Caracteristica();
                    nuevoCaract.setNombre(nombreCaract.getText().toString());
                    Float rating = puntuacion.getRating();
                    nuevoCaract.setImportancia(rating.intValue());
                    nuevoCaract.setPeso(this.calcularPesoIndividual(rating.intValue()));
                    getListaCaract().add(nuevoCaract);
                    this.calcularPesoGlobal();
                    this.cargarLista();
                    this.limpiar();
                }
            }
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "No es posible agregar características habiendo alternativas.");
        }
    }

    public void editarCaract(View v) {
        if (caractSeleccionada != null) {
            if (nombreCaract.getText().toString().equals("")) {
                this.lanzarToast(Toast.LENGTH_SHORT, "Debe ingresar el detalle de la característica.");
            } else {
                getListaCaract().remove(caractSeleccionada);
                caractSeleccionada.setNombre(nombreCaract.getText().toString());
                Float rating = puntuacion.getRating();
                caractSeleccionada.setImportancia(rating.intValue());
                caractSeleccionada.setPeso(this.calcularPesoIndividual(rating.intValue()));
                getListaCaract().add(caractSeleccionada);
                this.calcularPesoGlobal();
                this.cargarLista();
                this.limpiar();
            }
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "Debe seleccionar un elemento para realizar esta operación.");
        }
    }

    public void borrarCaract(View v) {
        if (this.modeloSistema.getListaAlternativa().isEmpty()) {
            if (caractSeleccionada != null) {
                getListaCaract().remove(caractSeleccionada);
                this.calcularPesoGlobal();
                this.cargarLista();
                this.limpiar();
            } else {
                this.lanzarToast(Toast.LENGTH_SHORT, "Debe seleccionar un elemento para realizar esta operación.");
            }
        } else {
            this.lanzarToast(Toast.LENGTH_SHORT, "No es posible eliminar características habiendo alternativas.");
        }
    }

    public void cargarLista() {
        Collections.sort(getListaCaract(), comparator);
        AdaptadorCaracteristica adaptador = new AdaptadorCaracteristica(this.getContext(),
                getListaCaract().toArray(new Caracteristica[0]));
        lista.setAdapter(adaptador);
    }

    private void limpiar() {
        nombreCaract.setText(null);
        puntuacion.setRating(1.0f);
        textoPuntaje.setText("Sin importancia");
        lista.clearChoices();
        caractSeleccionada = null;
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


    private Double calcularPesoIndividual(Integer importancia) {
        Double peso;
        if (importancia == 2) {
            peso = 0.05;
        } else {
            if (importancia == 3) {
                peso = 0.1;
            } else {
                if (importancia == 4) {
                    peso = 0.15;
                } else {
                    if (importancia == 5) {
                        peso = 0.2;
                    } else {
                        peso = 0.01;
                    }
                }
            }
        }
        return peso;
    }

    private void calcularPesoGlobal() {
        Double pesoTotal = 0.0;
        int cantSinImpo = 0;
        int cantPocoImpo = 0;
        int cantImpo = 0;
        int cantMuyImpo = 0;
        int cantDemImpo = 0;
        for (Caracteristica caracteristica : this.getListaCaract()) {
            switch (caracteristica.getImportancia()) {
                case 1: {
                    cantSinImpo++;
                    pesoTotal = pesoTotal + 0.01;
                    break;
                }
                case 2: {
                    cantPocoImpo++;
                    pesoTotal = pesoTotal + 0.05;
                    break;
                }
                case 3: {
                    cantImpo++;
                    pesoTotal = pesoTotal + 0.1;
                    break;
                }
                case 4: {
                    cantMuyImpo++;
                    pesoTotal = pesoTotal + 0.15;
                    break;
                }
                case 5: {
                    cantDemImpo++;
                    pesoTotal = pesoTotal + 0.2;
                    break;
                }
            }
        }
        Double pesoDif = 1 - pesoTotal;
        Double pesoSinImpo = 0.0;
        Double pesoPocoImpo = 0.0;
        Double pesoImpo = 0.0;
        Double pesoMuyImpo = 0.0;
        Double pesoDemImpo = 0.0;
        if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
            pesoSinImpo = (pesoDif * 0.1) / cantSinImpo;
            pesoPocoImpo = (pesoDif * 0.15) / cantPocoImpo;
            pesoImpo = (pesoDif * 0.2) / cantImpo;
            pesoMuyImpo = (pesoDif * 0.25) / cantMuyImpo;
            pesoDemImpo = (pesoDif * 0.3) / cantDemImpo;
        } else {
            if (cantDemImpo == 0) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.1) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.4) / cantMuyImpo;
                }
            }
            if (cantMuyImpo == 0) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.1) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoDemImpo = (pesoDif * 0.4) / cantDemImpo;
                }
            }
            if (cantImpo == 0) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.1) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.4) / cantDemImpo;
                }
            }
            if (cantPocoImpo == 0) {
                if ((cantSinImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.1) / cantSinImpo;
                    pesoImpo = (pesoDif * 0.2) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.4) / cantDemImpo;
                }
            }
            if (cantSinImpo == 0) {
                if ((cantPocoImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.1) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.2) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.4) / cantDemImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantMuyImpo == 0)) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.3) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.5) / cantImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantImpo == 0)) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantMuyImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.3) / cantPocoImpo;
                    pesoMuyImpo = (pesoDif * 0.5) / cantMuyImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.5) / cantMuyImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantImpo != 0) && (cantMuyImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.5) / cantMuyImpo;
                }
            }
            if ((cantMuyImpo == 0) && (cantImpo == 0)) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.3) / cantPocoImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }
            if ((cantMuyImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }
            if ((cantMuyImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantImpo != 0) && (cantDemImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.3) / cantImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }
            if ((cantImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.2) / cantSinImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }

            if ((cantImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.2) / cantPocoImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }

            if ((cantPocoImpo == 0) && (cantSinImpo == 0)) {
                if ((cantImpo != 0) && (cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoImpo = (pesoDif * 0.2) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.3) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.5) / cantDemImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantMuyImpo == 0) && (cantImpo == 0)) {
                if ((cantSinImpo != 0) && (cantPocoImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.4) / cantSinImpo;
                    pesoPocoImpo = (pesoDif * 0.6) / cantPocoImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantMuyImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.4) / cantSinImpo;
                    pesoImpo = (pesoDif * 0.6) / cantImpo;
                }
            }
            if ((cantDemImpo == 0) && (cantMuyImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.4) / cantPocoImpo;
                    pesoImpo = (pesoDif * 0.6) / cantImpo;
                }
            }

            if ((cantDemImpo == 0) && (cantImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantMuyImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.4) / cantSinImpo;
                    pesoMuyImpo = (pesoDif * 0.6) / cantMuyImpo;
                }
            }

            if ((cantDemImpo == 0) && (cantImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantMuyImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.4) / cantPocoImpo;
                    pesoMuyImpo = (pesoDif * 0.6) / cantMuyImpo;
                }
            }

            if ((cantDemImpo == 0) && (cantPocoImpo == 0) && (cantSinImpo == 0)) {
                if ((cantImpo != 0) && (cantMuyImpo != 0)) {
                    pesoImpo = (pesoDif * 0.4) / cantImpo;
                    pesoMuyImpo = (pesoDif * 0.6) / cantMuyImpo;
                }
            }

            if ((cantMuyImpo == 0) && (cantImpo == 0) && (cantPocoImpo == 0)) {
                if ((cantSinImpo != 0) && (cantDemImpo != 0)) {
                    pesoSinImpo = (pesoDif * 0.4) / cantSinImpo;
                    pesoDemImpo = (pesoDif * 0.6) / cantDemImpo;
                }
            }

            if ((cantMuyImpo == 0) && (cantImpo == 0) && (cantSinImpo == 0)) {
                if ((cantPocoImpo != 0) && (cantDemImpo != 0)) {
                    pesoPocoImpo = (pesoDif * 0.4) / cantPocoImpo;
                    pesoDemImpo = (pesoDif * 0.6) / cantDemImpo;
                }
            }
            if ((cantMuyImpo == 0) && (cantPocoImpo == 0) && (cantSinImpo == 0)) {
                if ((cantImpo != 0) && (cantDemImpo != 0)) {
                    pesoImpo = (pesoDif * 0.4) / cantImpo;
                    pesoDemImpo = (pesoDif * 0.6) / cantDemImpo;
                }
            }

            if ((cantImpo == 0) && (cantPocoImpo == 0) && (cantSinImpo == 0)) {
                if ((cantMuyImpo != 0) && (cantDemImpo != 0)) {
                    pesoMuyImpo = (pesoDif * 0.4) / cantMuyImpo;
                    pesoDemImpo = (pesoDif * 0.6) / cantDemImpo;
                }
            }
            if ((cantSinImpo != 0) && (cantPocoImpo == 0) && (cantImpo == 0) && (cantMuyImpo == 0) && (cantDemImpo == 0)) {
                pesoSinImpo = (pesoDif * 1) / cantSinImpo;
            } else {
                if ((cantSinImpo == 0) && (cantPocoImpo != 0) && (cantImpo == 0) && (cantMuyImpo == 0) && (cantDemImpo == 0)) {
                    pesoPocoImpo = (pesoDif * 1) / cantPocoImpo;
                } else {
                    if ((cantSinImpo == 0) && (cantPocoImpo == 0) && (cantImpo != 0) && (cantMuyImpo == 0) && (cantDemImpo == 0)) {
                        pesoImpo = (pesoDif * 1) / cantImpo;
                    } else {
                        if ((cantSinImpo == 0) && (cantPocoImpo == 0) && (cantImpo == 0) && (cantMuyImpo != 0) && (cantDemImpo == 0)) {
                            pesoMuyImpo = (pesoDif * 1) / cantMuyImpo;
                        } else {
                            if ((cantSinImpo == 0) && (cantPocoImpo == 0) && (cantImpo == 0) && (cantMuyImpo == 0) && (cantDemImpo != 0)) {
                                pesoDemImpo = (pesoDif * 1) / cantDemImpo;
                            }
                        }
                    }
                }
            }
        }
        Iterator<Caracteristica> iter = this.getListaCaract().iterator();
        Caracteristica caracteristica;
        while (iter.hasNext()) {
            caracteristica = iter.next();
            switch (caracteristica.getImportancia()) {
                case 1: {
                    caracteristica.setPeso(0.01 + pesoSinImpo);
                    break;
                }
                case 2: {
                    caracteristica.setPeso(0.05 + pesoPocoImpo);
                    break;
                }
                case 3: {
                    caracteristica.setPeso(0.1 + pesoImpo);
                    break;
                }
                case 4: {
                    caracteristica.setPeso(0.15 + pesoMuyImpo);
                    break;
                }
                case 5: {
                    caracteristica.setPeso(0.2 + pesoDemImpo);
                    break;
                }
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

}
