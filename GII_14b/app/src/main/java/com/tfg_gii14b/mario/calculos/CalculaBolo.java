package com.tfg_gii14b.mario.calculos;

import android.support.v7.app.AppCompatActivity;

import com.tfg_gii14b.mario.persistencia.ValoresPOJO;

/**
 * Cálculos del bolo corrector
 *
 * @author: Mario López Jiménez
 * @version: 1.0
 */
public class CalculaBolo extends AppCompatActivity {
    /**
     * Tag for log.
     */
    private static String TAG = CalculaBolo.class.getName();


    /**
     * Gramos de hidratos de carbono.
     */
    private double gramosHidratosCarbono;

    /**
     * Valores.
     */
    private ValoresPOJO valores;

    /**
     * Constructor.
     *
     * @param sumatorio
     */
    public CalculaBolo(ValoresPOJO valoresPOJO, double sumatorio) {

        // Almacenar valor de HC
        gramosHidratosCarbono = sumatorio;
        valores = valoresPOJO;
    }

    /**
     * Realiza el calculo del Ratio.
     *
     * @return ratio
     */
    public double calculaRatio() {
        return 500 / (valores.getInsulinaBasal() + valores.getInsulinaRapida());
    }

    /**
     * Realiza el calculo del factor de sensibilidad
     *
     * @return FSI
     */
    private double calculaFactorSensibilidad() {
        double suma = valores.getInsulinaBasal() + valores.getInsulinaRapida();
        double constante = valores.isRapida() ? 1500 : 1800;
        return constante / suma;
    }

    /**
     * Calcula la glucemia objetivo como la media de la
     *
     * @return
     */
    private double calculaGlucemiaObjetivo() {
        return (valores.getGlucemiaMaxima() + valores.getGlucemiaMinima()) / 2;
    }

    /**
     * Realiza el calculo del bolo corrector aplicando las formulas necesarias.
     */
    public double calculoBoloCorrector() {
        double operando1 = gramosHidratosCarbono / calculaRatio();
        double operando2 = (valores.getGlucemiaMinima() - calculaGlucemiaObjetivo()) / calculaFactorSensibilidad();
        return operando1 + operando2;
    }
}
