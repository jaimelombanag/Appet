package com.fasepi.android.appet.usuario.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosUsuariosDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosMovilDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosServiciosDTOT;
import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jaimelombana on 27/07/17.
 */

public class TimerApp extends Service {

    private String TAG="CivicarConductor";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Timer timer = new Timer();
    private Globales appState;
    private Context context;
    private int contRevServicio;
    private ConexionTCP sendData;

    /**********************************************************************************************/
    public IBinder onBind(Intent arg0) {
        return null;
    }
    /**********************************************************************************************/
    public void onCreate() {
        Log.i(TAG, "Se Inicializa OnCreate.......: ");
        super.onCreate();
        startService();
    }
    /**********************************************************************************************/
    private void startService(){
        context = getApplicationContext();
        appState = (Globales) getApplicationContext();
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }
    /**********************************************************************************************/
    private class mainTask extends TimerTask {
        public void run() {
            Temporizador();
        }
    }
    /**********************************************************************************************/
    private void Temporizador() {
        contRevServicio++;

        if(contRevServicio> 5){
            contRevServicio = 0;


            if(appState.getEstadoUsuario() != null) {

                if (appState.getEstadoUsuario().equalsIgnoreCase("sinservicio")) {

                    Log.i(TAG, "-------------------ENTRA A REVISAR SI HAY SERVICIOS  ------------");

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    DatosServiciosDTOT datosServiciosDTOT = new DatosServiciosDTOT();
                    datosServiciosDTOT.setFuncion("00");
                    datosServiciosDTOT.setTipoProyecto(Constants.TipoProyecto);

                    DatosMovilDTOT datosMovilDTOT = new DatosMovilDTOT();
                    datosMovilDTOT.setIdMovil(sharedPreferences.getString(Constants.idMovil, ""));

                    datosServiciosDTOT.setMovil(datosMovilDTOT);


                    Gson gson = new Gson();
                    String json = gson.toJson(datosServiciosDTOT);
                    sendData = new ConexionTCP(getApplicationContext());
                    sendData.sendData(json);
                }
            }

        }

    }
    /**********************************************************************************************/

    /**********************************************************************************************/
    public void onDestroy() {
        Log.i(TAG, "Se Cancela el Timer.......: " );
        super.onDestroy();
        timer.cancel();
    }
}
