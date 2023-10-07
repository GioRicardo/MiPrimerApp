package com.giordanricardo.miprimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giordanricardo.miprimerapp.modelos.Usuario;

public class MenuPrincipalLogeado extends AppCompatActivity implements View.OnClickListener {
    EditText nombreUsuario, emailBuscado, contraseñaBuscada;
    int idUsuario;
    Button btnBuscar, btnIrActualizar, btnIrEliminar, btnIrRegistrar, btnIrListar;

    Cursor cursor2;
    boolean diligenciadoUsuario, diligenciaOtrosCampos;

    public boolean validarCampoUsuario() {
        boolean diligenciadoUsuario = false;
        if (nombreUsuario.getText().toString().isEmpty()) {
            nombreUsuario.setError("Nombre Vacio");
            nombreUsuario.requestFocus();

        } else {
            diligenciadoUsuario = true;
        }
        return diligenciadoUsuario;
    }

    public boolean validarOtrosCampos() {
        if (emailBuscado.getText().toString().isEmpty()) {
            emailBuscado.setError("Correo Vacio");
            emailBuscado.requestFocus();
        } else if (contraseñaBuscada.getText().toString().isEmpty()) {
            contraseñaBuscada.setError("Contraseña Vacia");
            contraseñaBuscada.requestFocus();
        } else {
            diligenciaOtrosCampos = true;
        }
        return diligenciaOtrosCampos;
    }

    private void IrListar() {


        Intent intent = new Intent(this, MenuConsultaUsuarios.class);


        startActivity(intent);

    }

    private void IrRegistrar() {

        Toast.makeText(this, "funcion ir registrar", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);


        startActivity(intent);

    }

    private void IrActualizar() {
        if (cursor2 != null && cursor2.moveToFirst()) {
            String nombreUsuarioEnviar = cursor2.getString(1);
            String emailBuscadoEnviar = cursor2.getString(2);
            String contraseñaBuscadaEnviar = cursor2.getString(3);
            Usuario usuario = new Usuario(nombreUsuarioEnviar, emailBuscadoEnviar, contraseñaBuscadaEnviar);

            // Ahora puedes pasar 'usuario' a otra actividad o clase
            Intent intent = new Intent(this, ActualizarUsuario.class);
            intent.putExtra("usuario", usuario); // Pasa 'usuario' como un objeto Serializable

            startActivity(intent);
            limpiar();
            cursor2 = null;
        }
    }

    private void IrEliminar() {
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        if(cursor2!= null) {

            String nombreUsuarioAEliminar = cursor2.getString(1).toString(); // Obtén el nombre del usuario que deseas eliminar

            // Supongamos que tienes una columna llamada 'Nombre' para identificar al usuario
            int filasEliminadas = db.delete("Usuarios", "Nombre=?", new String[]{nombreUsuarioAEliminar});

            if (filasEliminadas > 0) {
                Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                limpiar();
                // Puedes realizar cualquier otra acción que necesites después de la eliminación
            } else {
                Toast.makeText(this, "No se encontró el usuario para eliminar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
            validarCampoUsuario();
        }
    }

    private void limpiar() {
        nombreUsuario.setText("");
        emailBuscado.setText("");
        contraseñaBuscada.setText("");
    }

    /*
        private void mostrarDialogoConfirmacion() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación");
            builder.setMessage("¿Estás seguro de que deseas eliminar este usuario?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Llamar al método para eliminar el usuario
                    IrEliminar();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // No hacer nada, simplemente cerrar el diálogo
                    dialog.dismiss();
                }
            });
            builder.show();
        }

    */
    private void buscarUsuario() {
        AdminSQLite admin = new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        diligenciadoUsuario = validarCampoUsuario();

        if (diligenciadoUsuario) {
            Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE Nombre='" + nombreUsuario.getText().toString() + "' ", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    Toast.makeText(MenuPrincipalLogeado.this, "Si existe en los registros " + cursor.getString(1), Toast.LENGTH_LONG).show();
                    idUsuario = cursor.getInt(0);
                    emailBuscado.setText(cursor.getString(2));
                    contraseñaBuscada.setText(cursor.getString(3));
                    cursor2 = cursor;

                } else {
                    Toast.makeText(MenuPrincipalLogeado.this, "No hay registros con esos datos", Toast.LENGTH_LONG).show();
                }
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_logeado);

        nombreUsuario = findViewById(R.id.txtBuscar);
        emailBuscado = findViewById(R.id.txtEmailBuscado);
        contraseñaBuscada = findViewById(R.id.txtContraseñaBuscada);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnIrRegistrar = findViewById(R.id.btnIrRegistrar2);
        btnIrActualizar = findViewById((R.id.btnIrActualizar));
        btnIrEliminar = findViewById(R.id.btnIrEliminar);
        btnIrListar = findViewById(R.id.btnIrListar);
        btnBuscar.setOnClickListener(this);
        btnIrActualizar.setOnClickListener(this);
        btnIrEliminar.setOnClickListener(this);
        btnIrRegistrar.setOnClickListener(this);
        btnIrListar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == btnBuscar.getId()) {
            buscarUsuario();
        }
        if (view.getId() == btnIrActualizar.getId()) {
            if (cursor2 != null && cursor2.moveToFirst()) {
                diligenciadoUsuario = validarCampoUsuario();
                if (diligenciadoUsuario) {
                    IrActualizar();
                }
                validarCampoUsuario();
            }
        }
            if (view.getId() == btnIrEliminar.getId()) {
                IrEliminar();
            }
            if (view.getId() == btnIrRegistrar.getId()) {
                Toast.makeText(this, "funcion ir registrar", Toast.LENGTH_SHORT).show();
                IrRegistrar();
            }
            if (view.getId() == btnIrListar.getId()) {
                IrListar();
            }

        }


    }

