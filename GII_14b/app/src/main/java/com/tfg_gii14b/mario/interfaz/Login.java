package com.tfg_gii14b.mario.interfaz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mario.gii_14b.R;

public class Login extends AppCompatActivity {

    //variables/objetos usados en el layout activity_login
    EditText nomUsuario;
    EditText password;
    CheckBox recordarPass;
    Button entrar;
    TextView registrarse;
    TextView recuperarPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enlazarObjetos();
    }

    /**
     * enlazarObjetos. Metodo que enlaza los objettos del layout activity_login
     * con las variables de esta clase.
     */
    public final void enlazarObjetos(){

        nomUsuario = (EditText)findViewById(R.id.et_nombre);
        password=(EditText)findViewById(R.id.et_password);
        recordarPass=(CheckBox)findViewById(R.id.chb_login);
        entrar =(Button)findViewById(R.id.bt_entrar);
        registrarse=(TextView)findViewById(R.id.tv_registrarse);
        recuperarPass=(TextView)findViewById(R.id.tv_recuperarPassword);
    }
}
