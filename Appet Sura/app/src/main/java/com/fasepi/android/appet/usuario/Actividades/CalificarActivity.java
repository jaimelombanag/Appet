package com.fasepi.android.appet.usuario.Actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;


import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosCalificacionDTO;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Locale;


public class CalificarActivity extends AppCompatActivity {



    private static String TAG = "CivicarConductor";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private RatingBar ratingBar;
    private EditText comentarios;

    private ConexionTCP sendData;
    private ProgressDialog progress_loading;

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
            Log.i(TAG, "MensajesActivity "+ cmd);
            Log.i(TAG, "MensajesActivity "+ datos);

            try {
                progress_loading.dismiss();
            } catch (Exception e) {
            }


            if (cmd.equalsIgnoreCase("error")) {
                MuestraMessageAlert(datos);
            } else {

                try {
                    Gson gson = new Gson();
                    DatosServiciosDTO informacion = null;

                    informacion = gson.fromJson(datos, DatosServiciosDTO.class);
                    if (cmd.equalsIgnoreCase("Respuesta")) {
                        if (informacion.getFuncion().equalsIgnoreCase("21")) {
                            Toast.makeText(getApplicationContext(), "Gracias por ayudarnos a mejorar", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);
        setTitle("    CALIFICANOS  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        appState = ((Globales) context);


        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }


        comentarios = (EditText) findViewById(R.id.comentarios);

        addListenerOnRatingBar();

    }



    /*============================================================================================*/
    /*============================================================================================*/
    public String redondeaSinDecimal(double valor, boolean conSigno) {
        String ret = "$0";
        DecimalFormat df;
        try {
            if (conSigno) {
                df = new DecimalFormat("$ #,###");
            } else {
                df = new DecimalFormat("#,###");
            }
            ret = df.format(valor);
        } catch (Exception e) {
        }
        return ret;
    }



    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.calificacion);


        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                //txt_costo_servicio.setText(String.valueOf(rating));

            }
        });
    }




    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestraMessageAlert(String error) {
        try {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            //alertBuilder.setTitle("Error...");
            alertBuilder.setIcon(R.mipmap.ic_launcher);
            alertBuilder.setMessage(error);
            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*============================================================================================*/
    /*============================================================================================*/
    public String getLocalIpAddressWifi() {
        try {
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return String.format(Locale.getDefault(), "%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        } catch (Exception ex) {
            Log.e("TaxisPanama", ex.getMessage());
            return null;
        }
    }

    /*********************************************************************************************/
    /*********************************************************************************************/
    public void EnviarCalificacion(View v){


        String comentariosFinales = comentarios.getText().toString();
        comentariosFinales = comentariosFinales.replace("\n", " ");

        double numeroEs = ratingBar.getRating();
        int numeroEstrellas = (int) numeroEs;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        progress_loading = ProgressDialog.show(CalificarActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
        DatosServiciosDTO datosTrama = new DatosServiciosDTO();
        DatosCalificacionDTO datosCalificacionDTO = new DatosCalificacionDTO();

        datosCalificacionDTO.setEstrellas(""+numeroEstrellas);
        datosCalificacionDTO.setCedula(sharedPreferences.getString(Constants.customer_cedula, ""));
        //datosCalificacionDTO.setIdUsuario(sharedPreferences.getString(Constants.customer_cedula, ""));
        datosCalificacionDTO.setMotivo(comentariosFinales);



        datosTrama.setCalificacion(datosCalificacionDTO);
        datosTrama.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));
        datosTrama.setFuncion("21");
        datosTrama.setOrigenSolicitud("1");
        datosTrama.setTipoProyecto("5");        //ARAMA


        Gson gson = new Gson();
        String json = gson.toJson(datosTrama);
        sendData = new ConexionTCP(getApplicationContext());
        sendData.sendData(json);

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "resumeeeen");
        if (activityReceiver != null) {
            registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
        }
    }

    @Override
    public void finish() {
        super.finish();
        unregisterReceiver(activityReceiver);
        Intent data = new Intent();
        setResult(Activity.RESULT_CANCELED, data);
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
