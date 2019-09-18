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
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.google.gson.Gson;
import java.util.Locale;


public class MensajeActivity extends AppCompatActivity {



    private static String TAG = "CivicarUsuario";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private ConexionTCP sendData;
    private ProgressDialog progress_loading;
    private String mensajeSend;
    private ImageView img_msg1;
    private ImageView img_msg2;
    private ImageView img_msg3;
    private ImageView img_msg4;
    private ImageView img_msg5;

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
            try {
                progress_loading.dismiss();
            } catch (Exception e) {
            }
            if (cmd.equalsIgnoreCase("error")) {
                MuestraAlertMensaje("Mensaje Servidor", datos);
            } else {
                Gson gson = new Gson();
                DatosServiciosDTO informacion = null;
                try {
                    informacion = gson.fromJson(datos, DatosServiciosDTO.class);
                    if (cmd.equalsIgnoreCase("Respuesta")) {
                        if (informacion.getFuncion().equalsIgnoreCase("03")) {
                            finish();
                        }
                    }
                }catch (Exception e){}

            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        setTitle("        MENSAJES  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        appState = ((Globales) context);

        img_msg1 = (ImageView) findViewById(R.id.img_msg1);
        img_msg2 = (ImageView) findViewById(R.id.img_msg2);
        img_msg3 = (ImageView) findViewById(R.id.img_msg3);
        img_msg4 = (ImageView) findViewById(R.id.img_msg4);
        img_msg5 = (ImageView) findViewById(R.id.img_msg5);

        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }
    }


    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestraAlertMensaje(String titulo, String mensaje) {
        Log.i(TAG, " ================ SE MUESTRA MENSAJE EN ALERT : " + mensaje);
        try {
            android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertBuilder.setTitle(titulo);
            alertBuilder.setMessage(mensaje);
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
            android.support.v7.app.AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Mensaje1(View v){
        mensajeSend="Listo";
        img_msg1.setImageResource(R.drawable.fle_verde);
        img_msg2.setImageResource(R.drawable.fle_roja);
        img_msg3.setImageResource(R.drawable.fle_roja);
        img_msg4.setImageResource(R.drawable.fle_roja);
        img_msg5.setImageResource(R.drawable.fle_roja);

    }

    public void Mensaje2(View v){
        mensajeSend="Estoy saliendo";
        img_msg2.setImageResource(R.drawable.fle_verde);
        img_msg1.setImageResource(R.drawable.fle_roja);
        img_msg3.setImageResource(R.drawable.fle_roja);
        img_msg4.setImageResource(R.drawable.fle_roja);
        img_msg5.setImageResource(R.drawable.fle_roja);

    }

    public void Mensaje3(View v){
        mensajeSend="Espérame 5 minutos";
        img_msg3.setImageResource(R.drawable.fle_verde);
        img_msg2.setImageResource(R.drawable.fle_roja);
        img_msg1.setImageResource(R.drawable.fle_roja);
        img_msg4.setImageResource(R.drawable.fle_roja);
        img_msg5.setImageResource(R.drawable.fle_roja);

    }

    public void Mensaje4(View v){
        mensajeSend="Te estoy esperando";
        img_msg4.setImageResource(R.drawable.fle_verde);
        img_msg2.setImageResource(R.drawable.fle_roja);
        img_msg3.setImageResource(R.drawable.fle_roja);
        img_msg1.setImageResource(R.drawable.fle_roja);
        img_msg5.setImageResource(R.drawable.fle_roja);

    }

    public void Mensaje5(View v){
        mensajeSend="Llámame";
        img_msg5.setImageResource(R.drawable.fle_verde);
        img_msg2.setImageResource(R.drawable.fle_roja);
        img_msg3.setImageResource(R.drawable.fle_roja);
        img_msg4.setImageResource(R.drawable.fle_roja);
        img_msg1.setImageResource(R.drawable.fle_roja);

    }

    public void EnviarMensaje(View v){
        //Toast.makeText(getApplicationContext(), "Se envia el Mensaje", Toast.LENGTH_LONG).show();
        progress_loading = ProgressDialog.show(MensajeActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DatosServiciosDTO datosServicio = new DatosServiciosDTO();
        datosServicio.setFuncion("03");
        datosServicio.setTipoProyecto("4");
        datosServicio.setMensajeSaliente(mensajeSend);
        datosServicio.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));


        Gson gson = new Gson();
        String json = gson.toJson(datosServicio);
        sendData = new ConexionTCP(getApplicationContext());
        sendData.sendData(json);


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
