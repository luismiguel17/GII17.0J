package com.tfg_gii14b.mario.interfaz;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mario.gii_14b.BuildConfig;
import com.example.mario.gii_14b.R;
import com.tfg_gii14b.mario.persistencia.DataBaseManager;

/**
 * Esta clase gestiona los elementos del Perfil de usuario.
 *
 * @author Raúl Marticorena Sánchez
 * @author: Mario López Jiménez
 * @version: 1.1
 */

public class Perfil extends AppCompatActivity {

    /**
     * Tag for log.
     */
    private static String TAG = Perfil.class.getName();


    EditText nombreEt;
    String nombre;
    EditText edadEt;
    Integer edad;
    EditText estaturaEt;
    Integer estatura;
    EditText pesoEt;
    Integer peso;
    EditText maxEt;
    Integer maxVal;
    EditText minEt;
    Integer minVal;
    EditText uds1Et;
    Integer udsBas;
    EditText uds2Et;
    RadioButton rapidaCheck;
    Integer udsRap;

    RadioButton ultrarrapidaCheck;
    String tipo ="";

    //Coexion a la BD.
    DataBaseManager dbmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        enlazarObjetos();

        //cargarPreferencias();

        
    }



    /**
     * enlazarObjetos. Metodo que enlaza los objetos del layout "fragment_perfil" con nuestras variables de clase.
     */
    private void enlazarObjetos() {

        //nombre de usuario
        nombreEt = (EditText) findViewById(R.id.et_nombre);
        //nombre = nombreEt.getText().toString();
        //edad usuario
        edadEt = (EditText) findViewById(R.id.et_edad);
        //edad = Integer.parseInt(edadEt.getText().toString());
        //estatura del usuario en cm
        estaturaEt = (EditText) findViewById(R.id.et_estatura);
        //estatura = Integer.parseInt(estaturaEt.getText().toString());
        //peso del usuario en kg
        pesoEt = (EditText) findViewById(R.id.et_peso);
        //peso=Integer.parseInt(pesoEt.getText().toString());
        //Glucemia maxima deseada en mg/dl
        maxEt = (EditText) findViewById(R.id.et_max);
        //maxVal = Integer.parseInt(maxEt.getText().toString());
        //Glucemia minima deseada en mg/dl
        minEt = (EditText) findViewById(R.id.et_min);
        //minVal = Integer.parseInt(minEt.getText().toString());
        //uds insulina basal
        uds1Et = (EditText) findViewById(R.id.et_udsBasal);
        //udsBas=Integer.parseInt(uds1Et.getText().toString());
        //uds insulina rapida
        uds2Et = (EditText) findViewById(R.id.et_udsRapida);
        //udsRap=Integer.parseInt(uds2Et.getText().toString());


        //Tipo insulina del bolo:rapida
        rapidaCheck = (RadioButton) findViewById(R.id.rb_rapida);
        //Tipo insulina del bolo:ultrrapida
        ultrarrapidaCheck = (RadioButton) findViewById(R.id.rb_ultrarrapida);

        if (rapidaCheck.isChecked()) {
            tipo = "rapida";
        }else{
            tipo = "ultrarrapida";
            }

    }

    private Boolean comprobarRegistro() {
        Boolean result=true;
        minVal = Integer.parseInt(minEt.getText().toString());
        maxVal = Integer.parseInt(maxEt.getText().toString());

        if (minVal < 80 || maxVal > 250) {
            //Toast.makeText(Perfil.this, R.string.minmax_incorrecto, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),R.string.minmax_incorrecto,Toast.LENGTH_SHORT).show();
            result = false;
        } else if (minVal > maxVal) {
            //Toast.makeText(Perfil.this, R.string.minmax_orden, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), R.string.minmax_orden, Toast.LENGTH_SHORT).show();
            result = false;
        } else if (nombre.length() == 0 || edad.toString().length() == 0 || estatura.toString().length() == 0 ||
                peso.toString().length() == 0 || maxVal.toString().length() == 0 || minVal.toString().length() == 0 ||
                udsBas.toString().length() == 0 || udsRap.toString().length() == 0) {
            Toast.makeText(Perfil.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * registrarUsuarios. Metodo que se encarga de almacenar los usuarios
     * en la BD.
     */
    private void registrarUsuarios() {

        Boolean result = comprobarRegistro();
        nombre = nombreEt.getText().toString();
        edad = Integer.parseInt(edadEt.getText().toString());
        estatura = Integer.parseInt(estaturaEt.getText().toString());
        peso=Integer.parseInt(pesoEt.getText().toString());
        udsBas=Integer.parseInt(uds1Et.getText().toString());
        udsRap=Integer.parseInt(uds2Et.getText().toString());
        //resultado de la insercion
        long insertar;

        if(result){
            //abrimos la conexion a la Bd
            dbmanager = new DataBaseManager(this);

            //Realizamos las insercion dle usuario
            insertar = dbmanager.insertar("usuarios", generarContentValues(nombre,edad,estatura,peso,
                    minVal,maxVal,tipo,udsBas,udsRap));
            //Mensaje por pantalla despues de la insercion
            if (insertar != -1) {
                Toast.makeText(getApplicationContext(),"Usuario insertado correctamente:" + insertar,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Insertado usuario con id " + insertar);

                //Cerramos la BD
                dbmanager.closeBD();

            } else {
                Toast.makeText(getApplicationContext(), "Error, Uusuario no insertado correctamente", Toast.LENGTH_SHORT).show();
                //cerramos la BD
                //dbmanager.closeBD();
            }

        }

    }

    /**
     * generarContentValues. Metodo que genera el ContentValues para poder realizar el insert en la BD.
     * @param nom nombre del usuario.
     * @param edad edad del usuario.
     * @param estatura estatura en cm del usuario.
     * @param peso peso en kg del usuario.
     * @param min Glucemia minima deseada en mg/dl.
     * @param max Glucemia maxima deseada en mg/dl
     * @param tipo Tipo insulina del bolo.
     * @param udsB unidades de insulina basal.
     * @param udsR unidades de insulina rapida.
     * @return values Contenedor de valores.
     */
    public ContentValues generarContentValues(String nom,Integer edad,Integer estatura,Integer peso,
                                              Integer min,Integer max,String tipo,Integer udsB,Integer udsR) {
        ContentValues values  = new ContentValues();
        values.put("nombre", nom);
        values.put("edad",edad) ;
        values.put("estatura", estatura);
        values.put("peso",peso);
        values.put("glu_min",min);
        values.put("glu_max",max);
        values.put("insu_bolo_tipo",tipo);
        values.put("insu_basal",udsB);
        values.put("insu_basal_rap",udsR);

        return values;
    }
    /**
     * Función que carga en los editText los datos previamente registrados si los hubiera
     */
    /**
    public final void cargarPreferencias() {
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);

        nombreEt = (EditText) findViewById(R.id.et_nombre);
        edadEt = (EditText) findViewById(R.id.et_edad);
        estaturaEt = (EditText) findViewById(R.id.et_estatura);
        pesoEt = (EditText) findViewById(R.id.et_peso);
        maxEt = (EditText) findViewById(R.id.et_max);
        minEt = (EditText) findViewById(R.id.et_min);
        uds1Et = (EditText) findViewById(R.id.et_udsBasal);
        uds2Et = (EditText) findViewById(R.id.et_udsRapida);
        rapidaCheck = (RadioButton) findViewById(R.id.rb_rapida);
        ultrarrapidaCheck = (RadioButton) findViewById(R.id.rb_ultrarrapida);

        nombreEt.setText(misPreferencias.getString(getString(R.string.nombre), ""));
        edadEt.setText(misPreferencias.getString(getString(R.string.edad), ""));
        estaturaEt.setText(misPreferencias.getString(getString(R.string.estatura), ""));
        pesoEt.setText(misPreferencias.getString(getString(R.string.peso), ""));
        minEt.setText(misPreferencias.getString(getString(R.string.min), ""));
        maxEt.setText(misPreferencias.getString(getString(R.string.max), ""));
        uds1Et.setText(misPreferencias.getString(getString(R.string.udsBasal), ""));
        uds2Et.setText(misPreferencias.getString(getString(R.string.udsRapida), ""));
        rapidaCheck.setChecked(misPreferencias.getBoolean(getString(R.string.rapida), false));
        ultrarrapidaCheck.setChecked(misPreferencias.getBoolean(getString(R.string.ultrarrapida), false));

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Preferences loaded with previous values (if exist).");
        }
    }
    */

    /**
     * Función que registra los datos introducidos en el perfil de usuario.
     * Realiza pruebas de validación de los datos introducidos antes de guardarlos.
     */

    public void guardarperfilOnClick(View view) {

        registrarUsuarios();

        /**
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = misPreferencias.edit();

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt = (EditText) findViewById(R.id.et_edad);
        EditText estaturaEt = (EditText) findViewById(R.id.et_estatura);
        EditText pesoEt = (EditText) findViewById(R.id.et_peso);
        EditText maxEt = (EditText) findViewById(R.id.et_max);
        EditText minEt = (EditText) findViewById(R.id.et_min);
        EditText udsBasalEt = (EditText) findViewById(R.id.et_udsBasal);
        EditText udsRapidaEt = (EditText) findViewById(R.id.et_udsRapida);
        RadioButton rapidaCheck = (RadioButton) findViewById(R.id.rb_rapida);
        RadioButton ultrarrapidaCheck = (RadioButton) findViewById(R.id.rb_ultrarrapida);

        String nombre = nombreEt.getText().toString();
        String edad = edadEt.getText().toString();
        String estatura = estaturaEt.getText().toString();
        String peso = pesoEt.getText().toString();
        String max = maxEt.getText().toString();
        String min = minEt.getText().toString();
        String udsBasal = udsBasalEt.getText().toString();
        String udsRapida = udsRapidaEt.getText().toString();
        Boolean rapida = rapidaCheck.isChecked();
        Boolean ultrarrapida = ultrarrapidaCheck.isChecked();

        int minVal = Integer.parseInt(min);
        int maxVal = Integer.parseInt(max);

        if (minVal < 80 || maxVal > 250) {
            Toast.makeText(Perfil.this, R.string.minmax_incorrecto, Toast.LENGTH_SHORT).show();
        } else if (minVal > maxVal) {
            Toast.makeText(Perfil.this, R.string.minmax_orden, Toast.LENGTH_SHORT).show();
        } else if (nombre.length() == 0 || edad.length() == 0 || estatura.length() == 0 || peso.length() == 0 || max.length() == 0 || min.length() == 0 ||
                udsBasal.length() == 0 || udsRapida.length() == 0) {
            Toast.makeText(Perfil.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            editorPreferencias.putBoolean("primeraEjecucion", true);
            editorPreferencias.putString(getString(R.string.nombre), nombre);
            editorPreferencias.putString(getString(R.string.edad), edad);
            editorPreferencias.putString(getString(R.string.estatura), estatura);
            editorPreferencias.putString(getString(R.string.peso), peso);
            editorPreferencias.putString(getString(R.string.min), min);
            editorPreferencias.putString(getString(R.string.max), max);
            editorPreferencias.putString(getString(R.string.udsBasal), udsBasal);
            editorPreferencias.putString(getString(R.string.udsRapida), udsRapida);
            editorPreferencias.putBoolean(getString(R.string.rapida), rapida);
            editorPreferencias.putBoolean(getString(R.string.ultrarrapida), ultrarrapida);

            editorPreferencias.apply(); // changed a commy by apply by recommendation of IntelliJ

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Saved new user preferences in profile.");
                String outputValues = String.format("Nombre:%s Edad:%s Estatura:%s Peso:%s" +
                        " Min:%s Max:%s UdsBasal:%s UdsRapida:%s Rapida:%s Ultrarrapida:%s",
                        nombre, edad, estatura, peso, min, max, udsBasal, udsRapida, rapida, ultrarrapida);
                Log.d(TAG, outputValues);
            }

            finish();
        }
         */
        //Nuevo-Cambio


    }

}
