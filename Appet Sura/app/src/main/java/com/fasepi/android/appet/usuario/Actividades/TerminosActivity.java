package com.fasepi.android.appet.usuario.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.fasepi.android.appet.usuario.R;


public class TerminosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);
        setTitle("TERMINOS Y CONDICIONES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }


    /*********************************************************************************************/
    /*********************************************************************************************/

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
