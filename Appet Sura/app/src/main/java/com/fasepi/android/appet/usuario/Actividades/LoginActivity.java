package com.fasepi.android.appet.usuario.Actividades;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosUsuariosDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosPropietarioDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosServiciosDTOT;
import com.fasepi.android.appet.usuario.MessageText.SmsListener;
import com.fasepi.android.appet.usuario.MessageText.SmsReceiver;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {


    private static String TAG = "CivicarUsuario";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private LinearLayout layout_splash;
    private LinearLayout layout_login;
    private LinearLayout layout_init_sesion;
    private EditText edt_nombre_usuario;
    private EditText edt_telefono_usuario;
    private EditText edt_correo_usuario;
    private EditText edt_cedula_usuario;
    private EditText edt_pass_usuario;

    private ConexionTCP sendData;
    private ProgressDialog progress_loading;
    private String Imei;
    private CheckBox checkBox;

    private Intent callIntent;


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
                        if (informacion.getFuncion().equalsIgnoreCase("01")) {


                            //PreguntaServicio();


                          Intent activity = new Intent();
                          activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.setClass(getApplicationContext(), MainActivity.class);
                          getApplicationContext().startActivity(activity);
                          finish();
                        }else if (informacion.getFuncion().equalsIgnoreCase("70")) {

                            Log.i(TAG, "============TAMAÑO LISTA:   "  +   appState.getListServicios().size());

                            if(appState.getListServicios().size() != 0){


                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.idServicio, appState.getListServicios().get(0).getIdServicio());
                                editor.commit();


                                Log.i(TAG, "============TAMAÑO LISTA:   "  +   appState.getListServicios().get(0).getDatosMovil().getNombreConductor());


                                Intent activity = new Intent();
                                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.setClass(getApplicationContext(), MainActivity.class);
                                getApplicationContext().startActivity(activity);
                                finish();

                            }else{

                                Intent activity = new Intent();
                                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.setClass(getApplicationContext(), SinServicioActivity.class);
                                getApplicationContext().startActivity(activity);
                                finish();

                            }


                        }
                    }
                }catch (Exception e){}

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        appState = ((Globales) context);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text",messageText);
                Toast.makeText(LoginActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
            }
        });


        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }

        layout_splash = (LinearLayout) findViewById(R.id.layout_splash);
        layout_login = (LinearLayout) findViewById(R.id.layout_login);
        layout_init_sesion = (LinearLayout) findViewById(R.id.layout_init_sesion);
        edt_nombre_usuario = (EditText) findViewById(R.id.edt_nombre_usuario);
        edt_telefono_usuario = (EditText) findViewById(R.id.edt_telefono_usuario);
        edt_correo_usuario = (EditText) findViewById(R.id.edt_correo_usuario);
        edt_cedula_usuario = (EditText) findViewById(R.id.edt_cedula_usuario);
        edt_pass_usuario = (EditText) findViewById(R.id.edt_pass_usuario);
        checkBox = (CheckBox) findViewById(R.id.checkBox);



        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Imei = tm.getDeviceId(); // getDeviceId() Obtiene el imei
        }catch (Exception e){
            //e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    Log.i(TAG, "---------------Nombre: " + sharedPreferences.getString(Constants.customer_name, ""));
                    Log.i(TAG, "-------------Telefono: " + sharedPreferences.getString(Constants.customer_phone, ""));
                    Log.i(TAG, "---------------Correo: " + sharedPreferences.getString(Constants.customer_email, ""));


                    if(sharedPreferences.getString(Constants.customer_name, "").equalsIgnoreCase("") || sharedPreferences.getString(Constants.customer_name, "") == null ||
                            sharedPreferences.getString(Constants.customer_phone, "").equalsIgnoreCase("") || sharedPreferences.getString(Constants.customer_phone, "") == null ||
                            sharedPreferences.getString(Constants.customer_email, "").equalsIgnoreCase("") || sharedPreferences.getString(Constants.customer_email, "") == null ){

                        layout_splash.setVisibility(View.GONE);
                        layout_login.setVisibility(View.VISIBLE);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    layout_splash.setVisibility(View.GONE);
                                    layout_login.setVisibility(View.GONE);
                                    layout_init_sesion.setVisibility(View.VISIBLE);

//
//                                    Intent activity = new Intent();
//                                    activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    activity.setClass(getApplicationContext(), MainActivity.class);
//                                    getApplicationContext().startActivity(activity);
//                                    finish();



                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }
                        }, 2000);



                    }else{

                        Intent activity = new Intent();
                        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.setClass(getApplicationContext(), MainActivity.class);
                        getApplicationContext().startActivity(activity);
                        finish();

                        //PreguntaServicio();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, 1500);







    }




    /*============================================================================================*/
    /*============================================================================================*/
    public static final int MY_PERMISSIONS_REQUEST_MSG = 99;
    private static final int REQUEST_CALL = 1;

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_MSG);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_MSG);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MSG: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();

                    MuestraAlertMensaje("","Es necesario Aceptar los permisos de Mensajes");
                }
                return;
            }

            case REQUEST_CALL: {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivity(callIntent);


                }else{
                    ////
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/



    public void PreguntaServicio(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        progress_loading = ProgressDialog.show(LoginActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
        DatosServiciosDTO datosTrama = new DatosServiciosDTO();
        DatosUsuariosDTO datosUsuariosDTO = new DatosUsuariosDTO();


        datosUsuariosDTO.setNumeroCelular(edt_telefono_usuario.getText().toString());
        datosUsuariosDTO.setDocumento(edt_cedula_usuario.getText().toString());
        datosTrama.setUsuario(datosUsuariosDTO);


        datosTrama.setFuncion("70");
        datosTrama.setOrigenSolicitud("1");
        datosTrama.setTipoProyecto(Constants.TipoProyecto);        //ARAMA
        datosTrama.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));


        Gson gson = new Gson();
        String json = gson.toJson(datosTrama);
        sendData = new ConexionTCP(getApplicationContext());
        sendData.sendData(json);

    }

    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestraAlertMensaje(String titulo, final String mensaje) {
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
                    if(mensaje.equalsIgnoreCase("Es necesario Aceptar los permisos de Mensajes")){
                        checkPermission();
                    }
                }
            });
            android.support.v7.app.AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InicioSesion(View v){


//        if(checkBox.isChecked()){
//            if(edt_cedula_usuario.getText().toString().length() < 7){
//                Toast.makeText(getApplicationContext(), "Ingrese su cedula", Toast.LENGTH_LONG).show();
//            }else

            if(edt_telefono_usuario.getText().toString().length() < 10){
                Toast.makeText(getApplicationContext(), "Ingrese su numero de telefono", Toast.LENGTH_LONG).show();
            }else if(edt_correo_usuario.getText().toString().length() <  7){
                Toast.makeText(getApplicationContext(), "Ingrese su Correo", Toast.LENGTH_LONG).show();
            }else if(edt_pass_usuario.getText().toString().length() <  4){
                Toast.makeText(getApplicationContext(), "Ingrese su contraseña", Toast.LENGTH_LONG).show();
            }

            else{

                progress_loading = ProgressDialog.show(LoginActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);


                DatosServiciosDTOT datosServiciosDTOT = new DatosServiciosDTOT();
                datosServiciosDTOT.setFuncion("01");
                datosServiciosDTOT.setTipoProyecto(Constants.TipoProyecto);
                datosServiciosDTOT.setCodigoLogin("0000");

                DatosPropietarioDTOT datosPropietarioDTOT = new DatosPropietarioDTOT();
                datosPropietarioDTOT.setClave(edt_pass_usuario.getText().toString());
                datosPropietarioDTOT.setCorreoElectronico(edt_correo_usuario.getText().toString());
                datosPropietarioDTOT.setNumeroCelular(edt_telefono_usuario.getText().toString());
                datosServiciosDTOT.setPropietario(datosPropietarioDTOT);


                Gson gson = new Gson();
                String json = gson.toJson(datosServiciosDTOT);
                sendData = new ConexionTCP(getApplicationContext());
                sendData.sendData(json);

            }
//        }else{
//            Toast.makeText(getApplicationContext(), "Debe aceptar terminos y condiciones.", Toast.LENGTH_LONG).show();
//        }


//        Intent activity = new Intent();
//        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.setClass(getApplicationContext(), MainActivity.class);
//        getApplicationContext().startActivity(activity);
//        finish();

    }




    public void Ingresar(View v){

        layout_splash.setVisibility(View.GONE);
        layout_login.setVisibility(View.GONE);
        layout_init_sesion.setVisibility(View.VISIBLE);

    }

    public  void Terminos(View v){

        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), TerminosActivity.class);
        getApplicationContext().startActivity(activity);


        //showChangeLangDialog("El mensaje");

    }


    public void showChangeLangDialog(String msg) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_popup, null);
        dialogBuilder.setView(dialogView);

        final TextView text = (TextView) dialogView.findViewById(R.id.text_dialog);
        text.setText(msg);

        final Button aceptar = (Button) dialogView.findViewById(R.id.btn_dialog);

        final AlertDialog aletCustom = dialogBuilder.create();
        aletCustom.show();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aletCustom.dismiss();
            }
        });
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

}
