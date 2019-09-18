package com.fasepi.android.appet.usuario.Actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.CreditCardAdapter;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosTarjetaCreditoDTO;
import com.fasepi.android.appet.usuario.Clases.DatosUsuariosDTO;
import com.fasepi.android.appet.usuario.Clases.ListaTarjetas;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.google.gson.Gson;

import java.util.ArrayList;


public class CreditCardActivity extends AppCompatActivity {


    private static String TAG = "CivicarUsuario";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private ConexionTCP sendData;
    private LinearLayout layout_list_tarjeta;
    private LinearLayout layout_add_tarjeta;
    private EditText edt_nombre_tarjeta;
    private EditText edt_numero_tarjeta;
    private EditText edt_mes_tarjeta;
    private EditText edt_ano_tarjeta;
    private EditText edt_cvv_tarjeta;
    private Spinner spinner_tipo_tc;
    private String St_tipo_tc;
    private ListaTarjetas lstTc;
    private ProgressDialog progress_loading;



    ListView tcList;
    CreditCardAdapter tcAdapter;
    ArrayList<ListaTarjetas> tcArray = new ArrayList<ListaTarjetas>();

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
                ProcessRecepcion(cmd, datos);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        setTitle("   NUEVA TARJETA  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();
        appState = ((Globales) context);
         /*========================================================================================*/
        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }

        /*========================================================================================*/


        layout_add_tarjeta = (LinearLayout) findViewById(R.id.layout_add_tarjeta);
        layout_list_tarjeta = (LinearLayout) findViewById(R.id.layout_list_tarjeta);
        spinner_tipo_tc = (Spinner) findViewById(R.id.spinner_tipo_tc);
        edt_nombre_tarjeta = (EditText) findViewById(R.id.edt_nombre_tarjeta);
        edt_numero_tarjeta = (EditText) findViewById(R.id.edt_numero_tarjeta);
        edt_mes_tarjeta = (EditText) findViewById(R.id.edt_mes_tarjeta);
        edt_ano_tarjeta = (EditText) findViewById(R.id.edt_ano_tarjeta);
        edt_cvv_tarjeta = (EditText) findViewById(R.id.edt_cvv_tarjeta);




        String[] valores = {"Tipo de tarjeta","VISA","MASTER CARD","CREDENCIAL MASTER CARD","DINERS CLUB","AMERICAN EXPRESS"};
        spinner_tipo_tc.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));


        /*++++++++++++++++++++  Spinner de tipo de Tarjetas +++++++++++++++++++++++++++++++++++++++*/
        spinner_tipo_tc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String franquicia = (String) adapterView.getItemAtPosition(position);
                if(franquicia.contains("VISA")){
                    St_tipo_tc = "1";
                }else  if(franquicia.contains("MASTER")){
                    St_tipo_tc = "2";
                }else  if(franquicia.contains("AMERICAN")){
                    St_tipo_tc = "3";
                }else{
                    St_tipo_tc = "0";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        /*++++++++++++++++++++      Lista  de Tarjetas     +++++++++++++++++++++++++++++++++++++++*/

        for(int i=0; i<appState.getLstTarjetasCredito().size(); i++){
            tcArray.add(new ListaTarjetas(appState.getLstTarjetasCredito().get(i).getNumero_tarjeta(), appState.getLstTarjetasCredito().get(i).getNombre(), appState.getLstTarjetasCredito().get(i).getId()));
        }

//        tcArray.add(new ListaTarjetas("**** **** **** 1234", "Visa", "12"));
//        tcArray.add(new ListaTarjetas("**** **** **** 9876", "Master", "345"));
//        tcArray.add(new ListaTarjetas("**** **** **** 3456", "American", "987"));


        tcAdapter = new CreditCardAdapter(CreditCardActivity.this, R.layout.list_tarjetas, tcArray);
        tcList = (ListView) findViewById(R.id.list_credit_card);
        tcList.setItemsCanFocus(false);
        tcList.setAdapter(tcAdapter);


        tcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                Log.i("List View Clicked", "**********");
                Object o = tcList.getItemAtPosition(position);
                ListaTarjetas fullObject = (ListaTarjetas) o;
                Toast.makeText(CreditCardActivity.this, "Tarjeta: " + " " + fullObject.getOtro() +  " Seleccionada.", Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.idTarjeta, fullObject.getId());
                editor.commit();


                Intent new_intent = new Intent();
                new_intent.putExtra("CMD", "solicitaServicioTc");
                new_intent.setAction(ACTION_STRING_ACTIVITY);
                context.sendBroadcast(new_intent);

                finish();
            }
        });



    }


    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestraAlertMensaje(String titulo, String mensaje) {
        Log.i(TAG, " ================ SE MUESTRA MENSAJE EN ALERT : " + mensaje);
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
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
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void ProcessRecepcion(String funcion, String datos) {
        Log.i(TAG, "--------------------------- La Funcion: " + funcion);
        Log.i(TAG, "--------------------------- Los Datos : " + datos);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (funcion.equalsIgnoreCase("Respuesta")) {
            Gson gson = new Gson();
            DatosServiciosDTO informacion = null;
            try {
                informacion = gson.fromJson(datos, DatosServiciosDTO.class);
                Log.i(TAG, "--------------------------- Entra : " + informacion.getFuncion());


                if (informacion.getFuncion().equalsIgnoreCase("15")) {
                    Toast.makeText(getApplicationContext(), "Se agrego tarjeta correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }else

                    if (informacion.getFuncion().equalsIgnoreCase("16")) {                // Add Tarjeta
                        Toast.makeText(getApplicationContext(), "Se elimino la tarjeta", Toast.LENGTH_LONG).show();
                        progress_loading = ProgressDialog.show(CreditCardActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
                        SendFuncion("14");
                        finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (funcion.equalsIgnoreCase("tarjetaCredito")) {

            //Toast.makeText(getApplicationContext(), datos, Toast.LENGTH_LONG).show();
            progress_loading = ProgressDialog.show(CreditCardActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);

            DatosServiciosDTO datosServicio = new DatosServiciosDTO();
            datosServicio.setFuncion("16");
            datosServicio.setTipoProyecto("4");

            DatosTarjetaCreditoDTO datosTarjetaCreditoDTO = new DatosTarjetaCreditoDTO();
            datosTarjetaCreditoDTO.setId(datos);

            datosServicio.setDatosTarjeta(datosTarjetaCreditoDTO);

            Gson gson = new Gson();
            String json = gson.toJson(datosServicio);
            sendData = new ConexionTCP(getApplicationContext());
            sendData.sendData(json);

        }
    }


    public void AddTarjeta(View v){
//        layout_add_tarjeta.setVisibility(View.GONE);
//        layout_list_tarjeta.setVisibility(View.VISIBLE);

        progress_loading = ProgressDialog.show(CreditCardActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
        SendFuncion("15");



    }

    private void SendFuncion(String funcion) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DatosServiciosDTO datosServicio = new DatosServiciosDTO();
        datosServicio.setFuncion(funcion);
        datosServicio.setTipoProyecto("4");


        DatosUsuariosDTO datosUsuariosDTO = new DatosUsuariosDTO();
        datosUsuariosDTO.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));

        datosServicio.setUsuario(datosUsuariosDTO);


        DatosTarjetaCreditoDTO datosTarjetaCreditoDTO = new DatosTarjetaCreditoDTO();
        datosTarjetaCreditoDTO.setCodigo_seguridad(edt_cvv_tarjeta.getText().toString());
        datosTarjetaCreditoDTO.setFranquicia(St_tipo_tc);
        datosTarjetaCreditoDTO.setNombre(edt_nombre_tarjeta.getText().toString());
        datosTarjetaCreditoDTO.setNumero_tarjeta(edt_numero_tarjeta.getText().toString());
        datosTarjetaCreditoDTO.setVencimiento(edt_mes_tarjeta.getText().toString() + edt_ano_tarjeta.getText().toString());

        datosServicio.setDatosTarjeta(datosTarjetaCreditoDTO);

        Gson gson = new Gson();
        String json = gson.toJson(datosServicio);
        sendData = new ConexionTCP(getApplicationContext());
        sendData.sendData(json);

    }

    public void AgregarTarjeta(View v){
        layout_add_tarjeta.setVisibility(View.VISIBLE);
        layout_list_tarjeta.setVisibility(View.GONE);
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
        unregisterReceiver(activityReceiver);
        Intent data = new Intent();
        setResult(Activity.RESULT_CANCELED, data);
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
