package com.tfg_gii14b.mario.calculos;

//import android.support.v7.app.AppCompatActivity;

import com.tfg_gii14b.mario.persistencia.ValoresPOJO;

/**
 * Cálculos del bolo corrector
 *
 * @author: Mario López Jiménez
 * @author Raúl Marticorena Sánchez
 * @version: 1.1
 */
public class CalculaBolo { // extends AppCompatActivity {

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
     * Realiza el calculo del factor de sensibilidad.
     *
     * @return FSI factor de sensibilidad
     */
    private double calculaFactorSensibilidad() {
        double suma = valores.getInsulinaBasal() + valores.getInsulinaRapida();
        double constante = valores.isRapida() ? 1500 : 1800;
        return constante / suma;
    }

    /**
     * Calcula la glucemia objetivo como la media de la glucemia máxima y la mínima.
     *
     * @return glucemia objetivo
     */
    private double calculaGlucemiaObjetivo() {
        return (valores.getGlucemiaMaxima() + valores.getGlucemiaMinima()) / 2;
    }

    /**
     * Realiza el calculo del bolo corrector aplicando las formulas necesarias.
     */
    public double calculoBoloCorrector() {
        double operando1 = gramosHidratosCarbono / calculaRatio();
        double operando2 = (valores.getGlucemia() - calculaGlucemiaObjetivo()) / calculaFactorSensibilidad();
        // Bug versión 1.1, "El resultado para el cálculo de insulina si la glucosa en sangre es menor de
        // la glucemia objetivo, es negativo. DEBERIA SER CERO.
        if (operando2 < 0) { // si es negativo el operando es negativo
            operando2 = 0; // se toma valor cero
        }
        return operando1 + operando2;
    }
}
