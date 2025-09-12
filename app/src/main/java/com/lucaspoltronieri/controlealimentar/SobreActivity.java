package com.lucaspoltronieri.controlealimentar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Habilitar o Tema salvo no SharedPreferences
        ThemeUtils.applySavedTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        setTitle(R.string.sobre);
    }
}