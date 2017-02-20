package com.tfg_gii14b.mario.calculos;

import android.support.v7.app.AppCompatActivity;

import com.example.mario.gii_14b.R;

/**
 * Esta clase realiza los calculos del bolo corrector
 * @author: Mario López Jiménez
 * @version: 1.0
 */
public class CalculaBolo extends AppCompatActivity {

    private static final int valorObjetivo = 100;
    private int ultimaMedicion,insulinaBasal;
    private double raciones;

    public CalculaBolo(int ultimaMedicion, int insulinaTotal, double sumatorio){
        this.ultimaMedicion=ultimaMedicion;
        this.insulinaBasal=insulinaTotal;
        this.raciones=sumatorio;
    }

    /**
     * Función que se encarga de realizar el calculo del Ratio
     */
    public double calculaRatio(){
        return 500/insulinaBasal;
    }

    /**
     * Función que se encarga de realizar el calculo del factor de sensibilidad
     */
    public double calculaFactorSensibilidad(){

        double dosisTresDias=insulinaBasal*3;

        return 1800/dosisTresDias;
    }

    /**
     * Función que se encarga de realizar el calculo del bolo corrector aplicando las formulas necesarias
     */
    public double calculoBoloCorrector(){

        double factorSensibilidad = calculaFactorSensibilidad();
        double bolo=  (ultimaMedicion+raciones-valorObjetivo)/factorSensibilidad;

        return Math.floor(bolo);
    }





}
