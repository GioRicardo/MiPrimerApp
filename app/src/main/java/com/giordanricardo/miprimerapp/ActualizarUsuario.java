package com.giordanricardo.miprimerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.giordanricardo.miprimerapp.modelos.Usuario;

public class ActualizarUsuario extends AppCompatActivity implements View.OnClickListener{

    EditText nombreUsuario2, emailBuscado2, contraseñaBuscada2;

    Button btnActualizarUsuario;
    boolean diligenciados;

    String nombreDeUsuarioDefault;

    private void IrCrud(){


        Intent intent = new Intent(this, MenuPrincipalLogeado.class);


        startActivity(intent);

    }
    public boolean validarCamposllenos() {
        boolean diligenciados = false;
        if (nombreUsuario2.getText().toString().isEmpty())
        {
            nombreUsuario2.setError("Nombre Vacio");
            nombreUsuario2.requestFocus();

        } else if (emailBuscado2.getText().toString().isEmpty())
        {
            emailBuscado2.setError("Correo Vacio");
            emailBuscado2.requestFocus();
        } else if (contraseñaBuscada2.getText().toString().isEmpty()) {
            contraseñaBuscada2.setError("Contraseña Vacia");
            contraseñaBuscada2.requestFocus();
        } else {
            diligenciados = true;
        }
        return diligenciados;
    }
    private void actualizarUsuario(){
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        diligenciados = validarCamposllenos();

        if (diligenciados)
        {
            String nuevoNombre = nombreUsuario2.getText().toString();
            String nuevoCorreo = emailBuscado2.getText().toString();
            String nuevaContraseña = contraseñaBuscada2.getText().toString();

            // Supongamos que tienes el ID del usuario que deseas actualizar
            //int idUsuario = obtenerIdDelUsuario(); // Debes implementar esta función

            if (!nuevoNombre.isEmpty()) {
                String sql = "UPDATE Usuarios SET Nombre=?, Correo=?, Contrasena=? WHERE Nombre=?";
                db.execSQL(sql, new String[]{nuevoNombre, nuevoCorreo, nuevaContraseña, nombreDeUsuarioDefault.toString()});

                Toast.makeText(ActualizarUsuario.this, "Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
                IrCrud();

                // Puedes realizar cualquier otra acción que necesites después de la actualización
            } else {
                Toast.makeText(ActualizarUsuario.this, "No se encontró el usuario para actualizar", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(ActualizarUsuario.this, "Si hay usuarios para actualizar", Toast.LENGTH_LONG).show();


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        nombreUsuario2 = findViewById(R.id.txtActualizarUsuario);
        emailBuscado2 = findViewById(R.id.txtActualizarEmail);
        contraseñaBuscada2 = findViewById(R.id.txtActualizarContraseña);
        btnActualizarUsuario = findViewById(R.id.btnActualizarUsuario);
        btnActualizarUsuario.setOnClickListener(this);

        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if (usuario != null) {
            String nombre = usuario.getNombre();
            String correo = usuario.getCorreo();
            String contraseña = usuario.getContrasena();
            nombreDeUsuarioDefault = usuario.getNombre();

            nombreUsuario2.setText(nombre);
            emailBuscado2.setText(correo);
            contraseñaBuscada2.setText(contraseña);
            // Haz algo con el nombre o los datos del usuario en esta actividad
        }

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == btnActualizarUsuario.getId()){
            actualizarUsuario();
        }
    }
}
