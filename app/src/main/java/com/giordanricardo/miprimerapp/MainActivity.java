package com.giordanricardo.miprimerapp;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giordanricardo.miprimerapp.modelos.Usuario;

import java.lang.Thread;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaracion de variables:
    EditText nombre,correo,contrasena;
    Button btnRegistrar, btnIniciar;
    boolean seEncontroUsuario = false;
    Usuario usuarioEncontrado;
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    //Metodos:
    private void registrar()
    {
        if (nombre.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty())
        {
            Toast.makeText(MainActivity.this, "Llena todos los campos para registrarte", Toast.LENGTH_LONG).show();
        } else
        {
            listaUsuarios.add(new Usuario(
                            nombre.getText().toString(),
                            correo.getText().toString(),
                            contrasena.getText().toString()
                    )
            );
            Toast.makeText(MainActivity.this, "Es un gusto conocerte " + nombre.getText(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, MenuPrincipal.class));
        }
    }
    private void ingresar()
    {
        if (nombre.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty())
        {
            Toast.makeText(MainActivity.this, "Llena todos los campos para iniciar sesion", Toast.LENGTH_LONG).show();
        } else
        {
            for (Usuario usuario : listaUsuarios) {
                if ( correo.getText().toString().equals(usuario.getCorreo()) && contrasena.getText().toString().equals(usuario.getContrasena())){
                    usuarioEncontrado = usuario;
                    seEncontroUsuario = true;
                }
            }
                if(seEncontroUsuario){
                    startActivity(new Intent(MainActivity.this, MenuPrincipalLogeado.class));
                    Toast.makeText(MainActivity.this, "Hola de nuevo " + usuarioEncontrado.getNombre(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }

        }
    }
    //onCreate:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.Theme_MiPrimerApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txtNombre);
        contrasena = findViewById(R.id.txtContrasena);
        correo = findViewById(R.id.txtCorreo);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnRegistrar.setOnClickListener(this);
        btnIniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnRegistrar.getId()){
            registrar();
        }
        if (view.getId() == btnIniciar.getId()){
            ingresar();
        }
    }
}