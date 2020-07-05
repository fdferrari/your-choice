package com.yourchoice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.yourchoice.clases.CustomTypefaceSpan;
import com.yourchoice.clases.ModeloSistema;

public class InicioActivity extends AppCompatActivity {

    private Dialog dialog;
    private AlertDialog dialogInfo;
    private Typeface typeFace;
    //private ImageView myImage;
    private EditText nombreModelo;
    private ActionBar actionBar;
    private Button btnComenzar;
    private SpannableStringBuilder ss = new SpannableStringBuilder("YourChoice");
    private ModeloSistema modeloSistema = new ModeloSistema();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        ss.setSpan(new CustomTypefaceSpan("", typeFace), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar = this.getSupportActionBar();
        actionBar.setTitle(ss);
        nombreModelo = (EditText) this.findViewById(R.id.name_modelo);
        //myImage = (ImageView) findViewById(R.id.logo);
        btnComenzar = (Button) this.findViewById(R.id.btn_comenzar);
        // btnComenzar.setTypeface(typeFace);
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreModelo.getText().toString().equals("")) {
                    lanzarToast(Toast.LENGTH_SHORT, "Debe ingresar un valor.");
                } else {
                    // new ActualizarProgreso().execute();
                    Intent intent =
                            new Intent(InicioActivity.this, TabActivity.class);
                    Bundle b = new Bundle();
                    modeloSistema.limpiar();
                    modeloSistema.setNombreModelo(nombreModelo.getText().toString());
                    b.putSerializable("modelo", modeloSistema);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
        ImageButton btnInfo = (ImageButton) this.findViewById(R.id.btn_info_inicio);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDialog("Información...", getString(R.string.mensaje_inicio));
            }
        });
    }

 /*   private class ActualizarProgreso extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotator);
            myImage.startAnimation(myRotation);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("modelo", modeloSistema);
        if (dialog != null)
            dialog.dismiss();
        if (dialogInfo != null)
            dialogInfo.dismiss();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (modeloSistema == null) {
                modeloSistema = (ModeloSistema) savedInstanceState.getSerializable("modelo");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("finishApplication", false)) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_acerca);
            TextView texto = (TextView) dialog.findViewById(R.id.texto_copy);
            SpannableStringBuilder ssAbout = new SpannableStringBuilder(texto.getText());
            ssAbout.setSpan(new CustomTypefaceSpan("", typeFace), 0,
                    ssAbout.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            texto.setText(ssAbout);
            Button dialogCLOSEButton = (Button) dialog.findViewById(R.id.btn_cancel_about);
            // dialogCLOSEButton.setTypeface(typeFace);
            dialogCLOSEButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
       /* if (id == R.id.action_salir) {
            finish();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void lanzarToast(int duracion, String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, duracion);
        toast.show();
    }

    private void lanzarDialog(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
