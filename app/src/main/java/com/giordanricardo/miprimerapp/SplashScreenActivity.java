package com.giordanricardo.miprimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(5000);
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}