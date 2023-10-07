package com.giordanricardo.miprimerapp;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    Button btnRegistrar, btnIniciar, btnConsultarUsuarios;
    boolean diligenciados;


    public MainActivity() {
    }

    //Metodos:
    private void registrar()
    {
        //Iniciar BD:
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        diligenciados = validarCamposllenos();
        
        if (diligenciados){
            ContentValues datos = new ContentValues();
            datos.put("Nombre",nombre.getText().toString());
            datos.put("Correo",correo.getText().toString());
            datos.put("Contrasena",contrasena.getText().toString());

            db.insert(AdminSQLite.TABLE_NAME,null,datos);

            Toast.makeText(MainActivity.this, "Es un gusto conocerte " + nombre.getText(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, MenuPrincipalRegistrado.class));
            limpiar();
        }
    }
    private void ingresar()
    {
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        diligenciados = validarCamposllenos();

        if (diligenciados)
        {
            Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE Nombre='"+nombre.getText().toString()+"' AND Correo='"+correo.getText().toString()+"' AND Contrasena='"+contrasena.getText().toString()+"'", null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    startActivity(new Intent(MainActivity.this, MenuPrincipalLogeado.class));
                    Toast.makeText(MainActivity.this, "Hola de nuevo " + cursor.getString(1), Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    Toast.makeText(MainActivity.this, "No hay registros con esos datos", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void buscarUsuario(){
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        diligenciados = validarCamposllenos();

        if (diligenciados)
        {
            Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE Nombre='"+nombre.getText().toString()+"' AND Correo='"+correo.getText().toString()+"' AND Contrasena='"+contrasena.getText().toString()+"'", null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    Toast.makeText(MainActivity.this, "Si existe en los registros", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "No hay registros con esos datos", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public ArrayList<Usuario> mostrarUsuarios(){
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursor = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        diligenciados = validarCamposllenos();
        if (diligenciados)
        {
            cursor = db.rawQuery("SELECT * FROM Usuarios", null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        usuario = new Usuario();
                        usuario.setNombre(cursor.getString(1));
                        usuario.setCorreo(cursor.getString(2));
                        usuario.setContrasena(cursor.getString(3));
                        listaUsuarios.add(usuario);

                    } while (cursor.moveToNext());
                    startActivity(new Intent(MainActivity.this, MenuConsultaUsuarios.class));
                    Toast.makeText(MainActivity.this, "Si hay registros", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "No hay registros", Toast.LENGTH_LONG).show();
                }
            }
            cursor.close();
        }
        return listaUsuarios;
    }



    public boolean validarCamposllenos() {
        boolean diligenciados = false;
        if (nombre.getText().toString().isEmpty())
        {
            nombre.setError("Nombre Vacio");
            nombre.requestFocus();

        } else if (correo.getText().toString().isEmpty())
        {
            correo.setError("Correo Vacio");
            correo.requestFocus();
        } else if (contrasena.getText().toString().isEmpty()) {
            contrasena.setError("Contraseña Vacia");
            contrasena.requestFocus();
        } else {
             diligenciados = true;
        }
        return diligenciados;
    }
    private void limpiar(){
        nombre.setText("");
        correo.setText("");
        contrasena.setText("");
    }
/*
    private void cambiarActivity(){
        startActivity(new Intent(MainActivity.this, MenuConsultaUsuarios.class));
    }
*/
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
        //btnConsultarUsuarios = findViewById(R.id.btnConsultarUsuarios);

        btnRegistrar.setOnClickListener(this);
        btnIniciar.setOnClickListener(this);
        //btnConsultarUsuarios.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnRegistrar.getId()){
            registrar();
        }
        if (view.getId() == btnIniciar.getId()){
            ingresar();
        }
      /*  if (view.getId() == btnConsultarUsuarios.getId()){
            startActivity(new Intent(MainActivity.this, MenuConsultaUsuarios.class));
        }

       */
    }
}