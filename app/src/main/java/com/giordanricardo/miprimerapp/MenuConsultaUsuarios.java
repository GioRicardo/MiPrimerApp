package com.giordanricardo.miprimerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.giordanricardo.miprimerapp.modelos.Usuario;
import java.util.ArrayList;

public class MenuConsultaUsuarios extends AppCompatActivity implements View.OnClickListener {
    //Declaracion de Variables:
    ListView vistaUsuarios;

    Button btnIrCrud;
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<String> listaInformacion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_usuarios);
        btnIrCrud = findViewById(R.id.btnIrAlCrud);
        btnIrCrud.setOnClickListener(this);


        vistaUsuarios = (ListView) findViewById(R.id.viewUsuarios);

        mostrarUsuarios();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        vistaUsuarios.setAdapter(adapter);
    }

    public void irCrud() {
        Intent intent = new Intent(this, MenuPrincipalLogeado.class);


        startActivity(intent);
    }

    //Metodo que muestra a todos los usuarios:
    public void mostrarUsuarios(){
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursor = null;

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

                Toast.makeText(MenuConsultaUsuarios.this, "Si hay registros", Toast.LENGTH_LONG).show();
                construirLista();
            }else{
                Toast.makeText(MenuConsultaUsuarios.this, "No hay registros", Toast.LENGTH_LONG).show();
            }
        }
        cursor.close();
    }

    public void construirLista(){
        for(int i=0;i<listaUsuarios.size();i++){
            listaInformacion.add("Nombre: "+listaUsuarios.get(i).getNombre()+"\n Correo: "+listaUsuarios.get(i).getCorreo()+"\n Contraseña: "+listaUsuarios.get(i).getContrasena());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnIrCrud.getId()) {
            irCrud();
        }

        }


    }


