package com.tfg_gii14b.mario.interfaz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mario.gii_14b.R;

/**
 * Esta clase gestiona los elementos de la Actividad física
 * @author: Mario López Jiménez
 * @version: 1.0
 */

public class ActividadFisica extends AppCompatActivity {
    private Spinner listaTipos, listaIntensidad;
    final private int REQUEST_EXIT=0;
    final private int RESULT_CODE=1;
    private String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaTipos = (Spinner) findViewById(R.id.sp_tipoejer);
        final ArrayAdapter adpTipos = ArrayAdapter.createFromResource(this,R.array.spinnerIntensidad, android.R.layout.simple_spinner_item);
        listaTipos.setAdapter(adpTipos);
        final ArrayAdapter adpLarga = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidadLarga, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpMedia = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidaMedia, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpIrregular = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidadIrregular, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpSinIntensidad = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidadNinguna, android.R.layout.simple_spinner_item);
        listaIntensidad = (Spinner) findViewById(R.id.sp_intensidad);

        listaTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = adpTipos.getItem(position).toString();
                tipo = opcion;
                if(position==0) {
                    listaIntensidad.setAdapter(adpSinIntensidad);
                }else if(position==1){
                        listaIntensidad.setAdapter(adpLarga);
                }else if(position==2){
                    listaIntensidad.setAdapter(adpMedia);
                }else if (position==3){
                    listaIntensidad.setAdapter(adpIrregular);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    /**
     * Función que define el comportamiento de la aplicacion al pulsar el boton Siguiente
     * @param view
     */
    public void siguienteOnClick(View view){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("tipoEjer", tipo);
        editor.commit();

        Intent i = new Intent(this, Carbohidratos.class);
        startActivityForResult(i, REQUEST_EXIT);

    }

    /**
     * Override de onActivityResult en el que definimos cuando debe finalizar la activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == REQUEST_EXIT)
        {
            setResult(RESULT_CODE);
            finish();

        }
    }

}
