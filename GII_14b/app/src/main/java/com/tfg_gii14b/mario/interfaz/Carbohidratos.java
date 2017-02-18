package com.tfg_gii14b.mario.interfaz;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tfg_gii14b.mario.calculos.CalculaBolo;
import com.example.mario.gii_14b.R;
import com.tfg_gii14b.mario.persistencia.DataBaseManager;

import java.util.ArrayList;

/**
 * Esta clase gestiona los elementos del Gestion de Carbohidratos
 *
 * @author: Mario López Jiménez
 * @version: 1.0
 */

public class Carbohidratos extends AppCompatActivity {
    private Spinner listaComida, listaTipo;
    private final int RESULT_EXIT = 0;
    private static final String INTENSIDAD_ALTA = "Larga (más de 2 horas)";
    private static final String INTENSIDAD_MEDIA = "Media (de 60 a 90 minutos)";
    private static final String INTENSIDAD_IRREGULAR = "Irregular e intermitente";
    private String comida;
    private int sumatorioRaciones;
    private String[] tipoAlimento;
    private String[] alimento;
    public static final int[] raciones = {150, 150, 150, 120, 120, 120, 120, 120, 120, 120, 150, 150, 150, 50, 250, 200, 30, 30, 30, 250, 250, 250};
    public static final int[] cargas = {14, 16, 19, 1, 12, 4, 4, 5, 5, 7, 3, 3, 3, 8, 3, 3, 4, 18, 10, 14, 13, 13};


    ArrayList<String> arrayAlimentos = new ArrayList<String>();
    ArrayList<Integer> arrayRaciones = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbohidratos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText carboEt = (EditText) findViewById(R.id.et_gramos);
        carboEt.setText("0");
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();

        if (misPreferencias.getBoolean("tablaAlimentos", false) == false) {
            rellenarTablaAlimentos();
        } else {
            editor.putBoolean("tablaAlimentos", true);
            editor.commit();
        }

        //Generamos los adaptadores para todos los posibles spinners
        final ArrayAdapter adpTipoAlimento = ArrayAdapter.createFromResource(this, R.array.spinerTipoAlimento, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpArroz = ArrayAdapter.createFromResource(this, R.array.spinerArroz, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpFruta = ArrayAdapter.createFromResource(this, R.array.spinerFruta, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpLegumbre = ArrayAdapter.createFromResource(this, R.array.spinerLegumbre, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpLacteo = ArrayAdapter.createFromResource(this, R.array.spinerLacteo, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpCereal = ArrayAdapter.createFromResource(this, R.array.spinerCereal, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpRefresco = ArrayAdapter.createFromResource(this, R.array.spinerRefresco, android.R.layout.simple_spinner_item);

        listaComida = (Spinner) findViewById(R.id.sp_comidas);
        listaComida.setAdapter(adpTipoAlimento);
        listaTipo = (Spinner) findViewById(R.id.spiner_tipo);
        listaComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Establecemos los datos del segundo spinner en función del primero
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    listaTipo.setAdapter(adpArroz);
                } else if (position == 1) {
                    listaTipo.setAdapter(adpFruta);
                } else if (position == 2) {
                    listaTipo.setAdapter(adpLegumbre);
                } else if (position == 3) {
                    listaTipo.setAdapter(adpLacteo);
                } else if (position == 4) {
                    listaTipo.setAdapter(adpCereal);
                } else if (position == 5) {
                    listaTipo.setAdapter(adpRefresco);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comida = listaTipo.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Función rellena la tabla de alimentos a partir del archivo arrays.xml
     */
    public void rellenarTablaAlimentos() {
        tipoAlimento = getResources().getStringArray(R.array.arrayTipoAlimento);
        alimento = getResources().getStringArray(R.array.arrayAlimentos);

        DataBaseManager dbmanager = new DataBaseManager(this);
        for (int i = 0; i < alimento.length; ++i) {
            ContentValues values = new ContentValues();
            values.put("tipoAlimento", tipoAlimento[i]);
            values.put("alimento", alimento[i]);
            values.put("racion", raciones[i]);
            values.put("carga", cargas[i]);
            dbmanager.insertar("alimentos", values);
        }
    }

    /**
     * Función que define el comportamiento de la aplicacion al pulsar el boton añadir
     * Acumula el resultado obtenido y deja el editext por defecto para permitir añadir mas alimentos.
     *
     * @param view
     */
    public void añadirOtroOnClick(View view) {
        EditText gramosEt = (EditText) findViewById(R.id.et_gramos);
        String gramos = gramosEt.getText().toString();
        int nracion = 0;
        if (!gramos.equals("")) {
            nracion = Integer.parseInt(gramos);
        }
        gramosEt.setText("0");

        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorGlucemias = dbmanager.selectAlimento(comida);
        if (cursorGlucemias.moveToFirst()) {
            String n = cursorGlucemias.getString(3);
            sumatorioRaciones += Integer.parseInt(n) * nracion;
        }


    }

    /**
     * Función que define el comportamiento de la aplicación al pulsar el boton Finalizar
     * Genera una instancia de CalculaBolo, obtiene el resultado, y lo muestra por pantalla
     * lanzando un ventana emergente.
     *
     * @param view
     */
    public void finalizarOnClick(View view) {

        EditText carboEt = (EditText) findViewById(R.id.et_gramos);
        String carbo = carboEt.getText().toString();
        int nracion = 0;

        if (!carbo.equals("")) {
            nracion = Integer.parseInt(carbo);
        }

        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorGlucemias = dbmanager.selectAlimento(comida);

        if (cursorGlucemias.moveToFirst()) {
            String n = cursorGlucemias.getString(3);
            sumatorioRaciones += Integer.parseInt(n) * nracion;
        }

        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);

        String uds1txt = misPreferencias.getString(getString(R.string.udsBasal), "");
        String uds2txt = misPreferencias.getString(getString(R.string.udsRapida), "");
        String tipoEjer = misPreferencias.getString("tipoEjer", "");
        int uds1 = Integer.parseInt(uds1txt);
        int uds2 = Integer.parseInt(uds2txt);
        int insulinaBasal = uds1 + uds2;
        int ultimaMedicion = misPreferencias.getInt("glucemia", 0);

        CalculaBolo cb = new CalculaBolo(ultimaMedicion, insulinaBasal, sumatorioRaciones);

        double boloResult = cb.calculoBoloCorrector();
        String comentarioFinal = generaComentarioBolo(tipoEjer, boloResult);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(comentarioFinal)
                .setTitle(getString(R.string.bolo))
                .setCancelable(false)
                .setNeutralButton(getString(R.string.aceptar),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                setResult(RESULT_EXIT);
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * Función usada para generar el comentario final que se mostrara por pantalla
     *
     * @param bolo          resultado del calculo del bolo corrector
     * @param tipoEjercicio intensidad de la actividad fisica seleccionada
     */
    private String generaComentarioBolo(String tipoEjercicio, double bolo) {
        String consejoEjercicio = "";
        String comentario;

        if (!tipoEjercicio.equals("")) {
            if (tipoEjercicio.equals(INTENSIDAD_ALTA)) {
                consejoEjercicio = getString(R.string.consejo_intensidad_alta);
            } else if (tipoEjercicio.equals(INTENSIDAD_MEDIA)) {
                consejoEjercicio = getString(R.string.consejo_intensidad_media);
            } else if (tipoEjercicio.equals(INTENSIDAD_IRREGULAR)) {
                consejoEjercicio = getString(R.string.consejo_intensidad_irregular);
            }
        }
        if (bolo >= 0) {
            comentario = getString(R.string.resultado_bolo) + " " + Double.toString(bolo) + " " + consejoEjercicio;
        } else {
            comentario = getString(R.string.ingerir_carbohidratos);
        }
        return comentario;
    }

}
