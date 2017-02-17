package com.tfg_gii14b.mario.interfaz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mario.gii_14b.R;

/**
 * Esta clase gestiona los elementos del Perfil de usuario
 * @author: Mario López Jiménez
 * @version: 1.0
 */

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        cargarPreferencias();
    }

    /**
     * Función que carga en los editText los datos previamente registrados si los hubiera
     */
    public void cargarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt=(EditText) findViewById(R.id.et_edad);
        EditText estaturaEt=(EditText) findViewById(R.id.et_estatura);
        EditText pesoEt=(EditText) findViewById(R.id.et_peso);
        EditText maxEt=(EditText) findViewById(R.id.et_max);
        EditText minEt=(EditText) findViewById(R.id.et_min);
        EditText uds1Et=(EditText) findViewById(R.id.et_uds1);
        EditText uds2Et=(EditText) findViewById(R.id.et_uds2);
        RadioButton determirCheck = (RadioButton) findViewById(R.id.rd_determir);
        RadioButton glarginaCheck = (RadioButton) findViewById(R.id.rd_Glargina);

        nombreEt.setText(misPreferencias.getString("nombre",""));
        edadEt.setText(misPreferencias.getString("edad",""));
        estaturaEt.setText(misPreferencias.getString("estatura",""));
        pesoEt.setText(misPreferencias.getString("peso",""));
        minEt.setText(misPreferencias.getString("min",""));
        maxEt.setText(misPreferencias.getString("max",""));
        uds1Et.setText(misPreferencias.getString("uds1",""));
        uds2Et.setText(misPreferencias.getString("uds2", ""));
        determirCheck.setChecked(misPreferencias.getBoolean("determir", false));
        glarginaCheck.setChecked(misPreferencias.getBoolean("glargina",false));
    }

    /**
     * Función que registra los datos introducidos en el Perfil de usuario.
     * Realiza pruebas de validación de los datos introducidos antes de guardarlos.
     */
    public void guardarperfilOnClick(View view){

        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = misPreferencias.edit();

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt=(EditText) findViewById(R.id.et_edad);
        EditText estaturaEt=(EditText) findViewById(R.id.et_estatura);
        EditText pesoEt=(EditText) findViewById(R.id.et_peso);
        EditText maxEt=(EditText) findViewById(R.id.et_max);
        EditText minEt=(EditText) findViewById(R.id.et_min);
        EditText uds1Et=(EditText) findViewById(R.id.et_uds1);
        EditText uds2Et=(EditText) findViewById(R.id.et_uds2);
        RadioButton determirCheck = (RadioButton) findViewById(R.id.rd_determir);
        RadioButton glarginaCheck = (RadioButton) findViewById(R.id.rd_Glargina);

        String nombre = nombreEt.getText().toString();
        String edad = edadEt.getText().toString();
        String estatura = estaturaEt.getText().toString();
        String peso = pesoEt.getText().toString();
        String max = maxEt.getText().toString();
        String min = minEt.getText().toString();
        String uds1 = uds1Et.getText().toString();
        String uds2 = uds2Et.getText().toString();
        Boolean determir = determirCheck.isChecked();
        Boolean glargina = glarginaCheck.isChecked();

        int minVal = Integer.parseInt(min);
        int maxVal = Integer.parseInt(max);

        if(minVal<80||maxVal>250){
            Toast.makeText(Perfil.this, R.string.minmax_incorrecto, Toast.LENGTH_SHORT).show();
        }else if(minVal>maxVal){
            Toast.makeText(Perfil.this, R.string.minmax_orden, Toast.LENGTH_SHORT).show();
        }else if(nombre.equals("")||edad.equals("")||estatura.equals("")||peso.equals("")||max.equals("")||min.equals("")||
                uds1.equals("")||uds2.equals("")){
            Toast.makeText(Perfil.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else {
            editorPreferencias.putBoolean("primeraEjecucion", true);
            editorPreferencias.putString("nombre", nombre);
            editorPreferencias.putString("edad", edad);
            editorPreferencias.putString("estatura", estatura);
            editorPreferencias.putString("peso", peso);
            editorPreferencias.putString("max", max);
            editorPreferencias.putString("min", min);
            editorPreferencias.putString("uds1", uds1);
            editorPreferencias.putString("uds2", uds2);
            editorPreferencias.putBoolean("determir", determir);
            editorPreferencias.putBoolean("glargina", glargina);

            editorPreferencias.commit();

            finish();
        }
    }
}
