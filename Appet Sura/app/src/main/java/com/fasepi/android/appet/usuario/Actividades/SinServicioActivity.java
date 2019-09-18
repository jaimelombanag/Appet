package com.fasepi.android.appet.usuario.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fasepi.android.appet.usuario.R;


public class SinServicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_servicio);
    }



    public void Aceptar(View v){
        finish();
    }
}
