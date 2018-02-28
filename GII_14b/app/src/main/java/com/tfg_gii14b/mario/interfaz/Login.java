package com.tfg_gii14b.mario.interfaz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.gii_14b.R;

public class Login extends AppCompatActivity {

    //variables/objetos usados en el layout activity_login
    EditText nomUsuario;
    CheckBox recordarPass;
    Button entrar;
    TextView registrarse;
    TextView recuperarPass;
    EditText password;

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
        recuperarPass=(TextView)findViewById(R.id.tv_recuperarPassword);
        registrarse=(TextView)findViewById(R.id.tv_registrarse);
        recordarPass=(CheckBox)findViewById(R.id.chb_login);
        entrar =(Button)findViewById(R.id.bt_entrar);
    }
    public void iniciarSesionOnClick(View view) {

        Intent menuP = new Intent(this, MenuPrincipal.class);
        /**

        if(password.equals("")){
            startActivity(menuP);
        }else{
            Toast.makeText(getApplicationContext(),"Contraseña Incorrecta:" ,Toast.LENGTH_SHORT).show();
        }*/
        startActivity(menuP);
    }
}
