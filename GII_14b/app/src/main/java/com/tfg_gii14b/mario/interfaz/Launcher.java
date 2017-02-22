package com.tfg_gii14b.mario.interfaz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Esta clase gestiona el arranque de la aplicación
 * @author: Mario López Jiménez
 * @version: 1.0
 */

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);
        Boolean arranque = misPreferencias.getBoolean("primeraEjecucion", false);
        Intent perfil = new Intent(this, Perfil.class);
        Intent menuPrincipal = new Intent(this, MenuPrincipal.class);

        if(!arranque){
            startActivity(menuPrincipal);
            startActivity(perfil);

        }else{

            startActivity(menuPrincipal);
        }
        finish();
    }
}
