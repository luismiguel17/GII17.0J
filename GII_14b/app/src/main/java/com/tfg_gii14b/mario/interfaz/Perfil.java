package com.tfg_gii14b.mario.interfaz;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        cargarPreferencias();
    }

    /**
     * Función que carga en los editText los datos previamente registrados si los hubiera
     */
    public final void cargarPreferencias() {
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt = (EditText) findViewById(R.id.et_edad);
        EditText estaturaEt = (EditText) findViewById(R.id.et_estatura);
        EditText pesoEt = (EditText) findViewById(R.id.et_peso);
        EditText maxEt = (EditText) findViewById(R.id.et_max);
        EditText minEt = (EditText) findViewById(R.id.et_min);
        EditText uds1Et = (EditText) findViewById(R.id.et_udsBasal);
        EditText uds2Et = (EditText) findViewById(R.id.et_udsRapida);
        RadioButton rapidaCheck = (RadioButton) findViewById(R.id.rb_rapida);
        RadioButton ultrarrapidaCheck = (RadioButton) findViewById(R.id.rb_ultrarrapida);

        nombreEt.setText(misPreferencias.getString("nombre", ""));
        edadEt.setText(misPreferencias.getString("edad", ""));
        estaturaEt.setText(misPreferencias.getString("estatura", ""));
        pesoEt.setText(misPreferencias.getString("peso", ""));
        minEt.setText(misPreferencias.getString("min", ""));
        maxEt.setText(misPreferencias.getString("max", ""));
        uds1Et.setText(misPreferencias.getString("udsBasal", ""));
        uds2Et.setText(misPreferencias.getString("udsRapida", ""));
        rapidaCheck.setChecked(misPreferencias.getBoolean("rapida", false));
        ultrarrapidaCheck.setChecked(misPreferencias.getBoolean("ultrarrapida", false));

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Preferences loaded with previous values (if exist).");
        }
    }

    /**
     * Función que registra los datos introducidos en el perfil de usuario.
     * Realiza pruebas de validación de los datos introducidos antes de guardarlos.
     */
    public void guardarperfilOnClick(View view) {

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
        } else if (nombre.equals("") || edad.equals("") || estatura.equals("") || peso.equals("") || max.equals("") || min.equals("") ||
                udsBasal.equals("") || udsRapida.equals("")) {
            Toast.makeText(Perfil.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            editorPreferencias.putBoolean("primeraEjecucion", true);
            editorPreferencias.putString("nombre", nombre);
            editorPreferencias.putString("edad", edad);
            editorPreferencias.putString("estatura", estatura);
            editorPreferencias.putString("peso", peso);
            editorPreferencias.putString("min", min);
            editorPreferencias.putString("max", max);
            editorPreferencias.putString("udsBasal", udsBasal);
            editorPreferencias.putString("udsRapida", udsRapida);
            editorPreferencias.putBoolean("rapida", rapida);
            editorPreferencias.putBoolean("ultrarrapida", ultrarrapida);

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
    }
}
