package com.giordanricardo.miprimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalRegistrado extends AppCompatActivity implements View.OnClickListener {

    Button btnIrCrud;


    private void IrCrud(){


        Intent intent = new Intent(this, MenuPrincipalLogeado.class);


        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_registrado);

        btnIrCrud = findViewById(R.id.btnIrCrud);
        btnIrCrud.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == btnIrCrud.getId()){
            IrCrud();
        }


    }


}