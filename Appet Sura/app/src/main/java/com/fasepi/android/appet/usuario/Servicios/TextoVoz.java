package com.fasepi.android.appet.usuario.Servicios;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Locale;

/**
 * Created by jalombanag on 06/09/2016.
 */

public class TextoVoz extends Service implements TextToSpeech.OnInitListener{


    private String TAG = "Arama";
    private final IBinder mBinder = new MyBinder();
    private final String ACTION_STRING_SERVICE = "ToService";
    public static TextoVoz instance;
    private TextToSpeech tts;
    private String spokenText = "";
    private Context context;



    /**********************************************************************************************/
    /**********************************************************************************************/
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale loc = new Locale("es", "ES");
            //int result = tts.setLanguage(Locale.US);
            try{
                int result = tts.setLanguage(loc);
                if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
                    tts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            Log.e("TTS", "Initilization Failed!   " + status);
        }
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    public class MyBinder extends Binder {
        TextoVoz getService() {
            return TextoVoz.instance;
        }
    }
    /**********************************************************************************************/
    /**********************************************************************************************/

    private final BroadcastReceiver serviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.i(TAG, "Funcion recibida desde la actividad: "+ intent.getStringExtra("CMD"));
            if (intent.getStringExtra("CMD").equalsIgnoreCase("TextoVoz")) {
                try {
                    String text = intent.getStringExtra("DATA");
                    String text2 =text.toLowerCase();
                    tts.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**********************************************************************************************/
    /**********************************************************************************************/
    @Override
    public void onCreate() {
        try {
            super.onCreate();
            Log.i(TAG, "Metodo onCreate del Service, se prepara el socket y demas instanceas");
            TextoVoz.instance = this;
            tts = new TextToSpeech(this, this);

            //STEP2: register the receiver
            if (serviceReceiver != null) {
                //Create an intent filter to listen to the broadcast sent with the action "ACTION_STRING_SERVICE"
                IntentFilter intentFilter = new IntentFilter(ACTION_STRING_SERVICE);
                registerReceiver(serviceReceiver, intentFilter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        //STEP3: Unregister the receiver
        Log.i(getClass().getSimpleName(), "Metodo onDestroy del Service, Deteniendo los timers, Hilos etc...");
        unregisterReceiver(serviceReceiver);
        TextoVoz.instance = null;
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    /**********************************************************************************************/
    /**********************************************************************************************/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}
