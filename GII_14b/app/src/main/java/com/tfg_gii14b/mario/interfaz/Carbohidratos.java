package com.tfg_gii14b.mario.interfaz;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mario.gii_14b.BuildConfig;
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
    /**
     * Gramos de hidratos de carbono (HC).
     * 10 Gramos de HC = 1 RACIÓN DE HC.
     * Tomado de la tabla de raciones de hidratos de carbono
     * Ver: http://www.fundaciondiabetes.org/upload/publicaciones_ficheros/71/TABLAHC.pdf.
     */
    private static final double GRAMOS_DE_HC = 10.0;
    /**
     * Tag for log.
     */
    private static String TAG = Carbohidratos.class.getName();

    /**
     * Posición en la tabla de alimentos donde se almacenan los gramos por ración del alimento.
     */
    public static final int COLUMNA_RACION = 2;

    private Spinner listaComida, listaTipo;
    private final int RESULT_EXIT = 0;
    private static final String INTENSIDAD_ALTA = "Larga (más de 2 horas)";
    private static final String INTENSIDAD_MEDIA = "Media (de 60 a 90 minutos)";
    private static final String INTENSIDAD_IRREGULAR = "Irregular e intermitente";
    private String comida;
    private double sumatorioRaciones;
    private String[] tipoAlimento;
    private String[] numeroTipoAlimento;
    private String[] alimento;
    public static final int[] raciones =
            {200, 50, 50, 50, 100, // Lacteos
                    13, 38, 13, 40, 12, 13, // Cereales
                    150, 100, 30, 25, 100, // Frutas
                    300, 40, 300, 300, 500, // Hortalizas
                    250, 15, 150, 140, 150, // Fruta grasa y seca
                    130, 100, 0, 100, 250, // Bebidas,
                    10, 10, 20, 20, 20 // Otros
            };
;


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

        final ArrayAdapter adpLacteo = ArrayAdapter.createFromResource(this, R.array.spinerLacteos, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpArroz = ArrayAdapter.createFromResource(this, R.array.spinerCereales, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpFruta = ArrayAdapter.createFromResource(this, R.array.spinnerFrutas, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpHortaliza = ArrayAdapter.createFromResource(this, R.array.spinnerHortalizas, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpFrutaGrasaSeca = ArrayAdapter.createFromResource(this, R.array.spinnerFrutaGrasaSeca, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpBebida = ArrayAdapter.createFromResource(this, R.array.spinerBebidas, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpOtros = ArrayAdapter.createFromResource(this, R.array.spinerOtros, android.R.layout.simple_spinner_item);

        listaComida = (Spinner) findViewById(R.id.sp_comidas);
        listaComida.setAdapter(adpTipoAlimento);
        listaTipo = (Spinner) findViewById(R.id.spiner_tipo);
        listaComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Establecemos los datos del segundo spinner en función del primero
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    listaTipo.setAdapter(adpLacteo);
                } else if (position == 1) {
                    listaTipo.setAdapter(adpArroz);
                } else if (position == 2) {
                    listaTipo.setAdapter(adpFruta);
                } else if (position == 3) {
                    listaTipo.setAdapter(adpHortaliza);
                } else if (position == 4) {
                    listaTipo.setAdapter(adpFrutaGrasaSeca);
                } else if (position == 5) {
                    listaTipo.setAdapter(adpBebida);
                } else if (position == 6) {
                    listaTipo.setAdapter((adpOtros));
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
        numeroTipoAlimento = getResources().getStringArray(R.array.numeroTipoAlimento);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Tipos de alimentos: " + tipoAlimento.length);
            Log.d(TAG, "Numero de elementos de tipos de alimentos: " + numeroTipoAlimento.length);
        }


        alimento = getResources().getStringArray(R.array.arrayAlimentos);

        DataBaseManager dbmanager = new DataBaseManager(this);
        ContentValues values = new ContentValues();
        int contadorNumeroTipoAlimento = 0;
        int acumulado = Integer.parseInt(numeroTipoAlimento[contadorNumeroTipoAlimento]);

        for (int i = 0; i < alimento.length; ++i) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Procesando tipo de alimento: " + tipoAlimento[contadorNumeroTipoAlimento] +
                        " Alimento: " + alimento[i]);
            }
            values.put("tipoAlimento", tipoAlimento[contadorNumeroTipoAlimento]);
            values.put("alimento", alimento[i]);
            values.put("racion", raciones[i]);
            dbmanager.insertar("alimentos", values);
            values.clear();
            // si hemos procesado todos los alimentos de un cierto y no hay más tipos de alimentos a procesar
            if (i == acumulado - 1 && contadorNumeroTipoAlimento < tipoAlimento.length - 1) {
                contadorNumeroTipoAlimento++; // avanzamos al siguiente tipo de alimento
                acumulado += Integer.parseInt(numeroTipoAlimento[contadorNumeroTipoAlimento]);
            }
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
        int numeroGramos = 0;
        if (!gramos.equals("")) {
            numeroGramos = Integer.parseInt(gramos);
        }
        gramosEt.setText("0");

        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorAlimentos = dbmanager.selectAlimento(comida);
        if (cursorAlimentos.moveToFirst()) {
            String gramosPorRacion = cursorAlimentos.getString(COLUMNA_RACION);
            // RMS: Cambiamos el cálculo de la formula en la versión 1.1
            //sumatorioRaciones += Integer.parseInt(n) * nracion; // Versión 1.0
            sumatorioRaciones += (numeroGramos * GRAMOS_DE_HC) / Double.parseDouble(gramosPorRacion);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Grams per ration: " + gramosPorRacion + " Grams: " + numeroGramos);
                Log.d(TAG, "Current count of carbohidrates (HC): " + sumatorioRaciones);
            }
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
