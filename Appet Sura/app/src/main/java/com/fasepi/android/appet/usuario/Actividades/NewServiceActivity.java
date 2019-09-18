package com.fasepi.android.appet.usuario.Actividades;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class NewServiceActivity extends AppCompatActivity implements OnMapReadyCallback {


    private String TAG = "CivicarConductor";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private GoogleMap mMap;
    private LatLng servicio;
    private RelativeLayout layout_servicio;



    private ProgressBar tiempoServicio;
    int pStatus = 0;
    private Handler handler = new Handler();
    private MediaPlayer mp;



    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    private final BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.i(TAG, "received message in MainActivity..!");
            String cmd = intent.getStringExtra("CMD");
            String datos = intent.getStringExtra("DATOS");

            if(cmd.equalsIgnoreCase("vehiculoAsignado")){
                finish();
            }

        }
    };


    /*============================================================================================*/
    /*============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);
        context = getApplicationContext();
        appState = ((Globales) context);
         /*=======================================================================*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*========================================================================================*/
        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }
        /*========================================================================================*/
        layout_servicio = (RelativeLayout) findViewById(R.id.layout_servicio);
        tiempoServicio = (ProgressBar) findViewById(R.id.progressBar1);
        setTitle("                    CIVICAR  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String dir_Servicio = extras.getString("DIR_SERVICIO");


        Double latitud = Double.parseDouble(extras.getString("LAT_SERVICIO"));
        Double longitud = Double.parseDouble(extras.getString("LON_SERVICIO"));
        
        servicio = new LatLng(latitud, longitud);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 45) {

                    pStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            tiempoServicio.setProgress(pStatus);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        }).start();


//        for(int i=0; i<10; i++){
//            mp = MediaPlayer.create(context, R.raw.sonido);
//            mp.start();
//        }



    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void ReservarServicio(View v){
        Log.i(TAG, "Se oprime la pantalla para Reservar");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(servicio, 18));
        mMap.addMarker(new MarkerOptions()
                .title("Servicio")
                .snippet("Servicio Disponible")
                .position(servicio));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG, " Se Oprime el boton de Back");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "resumeeeen");
        if (activityReceiver != null) {
            registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Activity --> onPause");
    }

    @Override
    public void finish() {
        super.finish();
        try {
            unregisterReceiver(activityReceiver);
            Intent data = new Intent();
            setResult(Activity.RESULT_CANCELED, data);
        }catch (Exception e){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    /*============================================================================================*/
    /*============================================================================================*/
    public void CancelarServicio(View v){
        Intent new_intent = new Intent();
        new_intent.putExtra("CMD", "cancelaServicio");
        new_intent.setAction(ACTION_STRING_ACTIVITY);
        context.sendBroadcast(new_intent);
        finish();
    }
}
