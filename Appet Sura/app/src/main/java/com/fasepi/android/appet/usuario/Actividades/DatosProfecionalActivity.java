package com.fasepi.android.appet.usuario.Actividades;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.R;

public class DatosProfecionalActivity extends AppCompatActivity {

    private TextView txt_profesional;
    private TextView txt_vehiculo;
    private TextView txt_distancia;
    private Globales appState;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_profecional);
        setTitle("DATOS PROFESIONAL");
        context = getApplicationContext();
        appState = ((Globales) context);

        txt_profesional = (TextView) findViewById(R.id.txt_profecional);
        txt_vehiculo = (TextView) findViewById(R.id.txt_vehiculo);
        txt_distancia = (TextView) findViewById(R.id.txt_distancia);



        txt_profesional.setText(appState.getListServicios().get(0).getDatosMovil().getNombreConductor());
        txt_vehiculo.setText(appState.getListServicios().get(0).getDatosMovil().getPlaca());
        txt_distancia.setText("1200 metros");

    }

    public void Aceptar(View v){
        finish();
    }


}
