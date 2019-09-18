package com.fasepi.android.appet.usuario.Actividades.Menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.DatosListas;
import com.fasepi.android.appet.usuario.Appet.DatosListasAdapter;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.fasepi.android.appet.usuario.Servicios.TimerSocket;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReservasActivity extends AppCompatActivity {


    private static String TAG = "CivicarUsuario";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private Globales appState;
    private Context context;
    private ConexionTCP sendData;
    private ListView listServicio;
    DatosListasAdapter datoslstAdapter;
    ArrayList<DatosListas> datosArray = new ArrayList<DatosListas>();
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
            try {
                progress_loading.dismiss();
            } catch (Exception e) {
            }
            if (cmd.equalsIgnoreCase("error")) {
                MuestraAlertMensaje("Mensaje Servidor", datos);
            } else if (cmd.equalsIgnoreCase("Delete")) {

                Log.i(TAG, "=================ENTRA A BORRAR LA POSICION:  "  + datos);
                int position = Integer.parseInt(datos);

                if(appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("4")||appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("13")||
                        appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("16")){

                    MuestarAlert2(position);
                }else{
                    MuestarAlert(position);
                }

            }

            else {
                Gson gson = new Gson();
                DatosServiciosDTO informacion = null;
                try {
                    informacion = gson.fromJson(datos, DatosServiciosDTO.class);
                    if (cmd.equalsIgnoreCase("Respuesta")) {
                        if (informacion.getFuncion().equalsIgnoreCase("05")) {
                            Toast.makeText(getApplicationContext(), "Servicio Cancelado", Toast.LENGTH_LONG).show();

                            progress_loading =ProgressDialog.show(ReservasActivity.this,"Espere un momento ...","Esperando Respuesta ...",true,true);
                            datosArray.clear();
                            listServicio.setAdapter(datoslstAdapter);

                            RevisaMensajeAviso(datos);

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            DatosServiciosDTO datosServicio = new DatosServiciosDTO();
                            datosServicio.setFuncion("11");
                            datosServicio.setTipoProyecto("4");
                            datosServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));


                            Gson gson1 = new Gson();
                            String json = gson1.toJson(datosServicio);
                            sendData =new

                                    ConexionTCP(getApplicationContext());
                            sendData.sendData(json);
                            finish();


                        }else if (informacion.getFuncion().equalsIgnoreCase("11")) {


                            DatosListas datosListas = new DatosListas();
                            for (int i = 0; i < appState.getListServicios().size(); i++) {
                                Log.i(TAG, "=================================   " + appState.getListServicios().get(i).getDireccion());


                                int colorHistorico = R.color.colorListaHistorico;
                                int colorVivi = R.color.colorBlanco;

                                String fecha;
                                if(appState.getListServicios().get(i).getFechaReservacion() == null || appState.getListServicios().get(i).getFechaReservacion().equalsIgnoreCase("")){

                                    Date dt = new Date();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    String formatteDate = df.format(dt.getDate());
                                    fecha = formatteDate;

                                }else{
                                    fecha = appState.getListServicios().get(i).getFechaReservacion();
                                }


                                if (appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("4") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("7") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("10")
                                        || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("11") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("13") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("16")) {


                                    Log.i(TAG, "=============VIVOS====================   " );
                                    datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion Origen: \n" + appState.getListServicios().get(i).getDireccion()+ "\nDireccion Destino: \n" + appState.getListServicios().get(i).getDestino(), "Nombre Conductor: \n" + appState.getListServicios().get(i).getDatosMovil().getNombreConductor()+ "\nPlaca: " + appState.getListServicios().get(i).getDatosMovil().getPlaca(), "Valor Servicio: \n" + redondeaSinDecimal(Double.parseDouble(appState.getListServicios().get(i).getCostoServicio()), true), colorVivi,0));


                                } else {

                                    Log.i(TAG, "=============HISTORICOS====================   " );
                                   // datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion: \n" + appState.getListServicios().get(i).getDireccion(), "Nombre: \n" + appState.getListServicios().get(i).getNombre(), "Costo: \n$" + appState.getListServicios().get(i).getCostoServicio(), colorHistorico));
                                }


                            }

                            datoslstAdapter = new DatosListasAdapter(ReservasActivity.this, R.layout.listas_layout, datosArray);
                            listServicio = (ListView) findViewById(R.id.listView);
                            listServicio.setItemsCanFocus(false);
                            listServicio.setAdapter(datoslstAdapter);


                        }
                    }
                } catch (Exception e) {
                }

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        setTitle("   SERVICIOS  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        appState = ((Globales) context);


        if (activityReceiver != null) {
            try {
                registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
            } catch (Exception e) {
            }
        }

        DatosListas datosListas = new DatosListas();
        for (int i = 0; i < appState.getListServicios().size(); i++) {
            Log.i(TAG, "=================================   " + appState.getListServicios().get(i).getDireccion());
            Log.i(TAG, "============Fecha=====================   " + appState.getListServicios().get(i).getFechaReservacion());


            int colorHistorico = R.color.colorListaHistorico;
            int colorVivi = R.color.colorBlanco;

            String fecha;
            if(appState.getListServicios().get(i).getFechaReservacion() == null || appState.getListServicios().get(i).getFechaReservacion().equalsIgnoreCase("")){

//                Date d = new Date();
//                CharSequence fecha1 = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
//                fecha = fecha1.toString();
//
//                Log.i(TAG, "============Fecha2=====================   " + fecha);

                fecha = appState.getListServicios().get(i).getFechaCreacion();

            }else{
                fecha = appState.getListServicios().get(i).getFechaReservacion();
            }

            if (appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("4") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("7") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("10")
                    || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("11") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("13") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("16")) {

                datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion Origen: \n" + appState.getListServicios().get(i).getDireccion()+ "\nDireccion Destino: \n" + appState.getListServicios().get(i).getDestino(), "Nombre Conductor: \n" + appState.getListServicios().get(i).getDatosMovil().getNombreConductor() + "\nPlaca: " + appState.getListServicios().get(i).getDatosMovil().getPlaca(), "Valor Servicio: \n" + redondeaSinDecimal(Double.parseDouble(appState.getListServicios().get(i).getCostoServicio()), true), colorVivi,0));

            } else {
               // datosArray.add(new DatosListas("Fecha: " + fecha+ "\nDireccion Origen: \n" + appState.getListServicios().get(i).getDireccion() + "\nDireccion Destino: \n" + appState.getListServicios().get(i).getDestino(), "Nombre Conductor: \n" + appState.getListServicios().get(i).getDatosMovil().getNombreConductor(), "Costo: \n$" + appState.getListServicios().get(i).getCostoServicio(), colorHistorico));
            }


        }

        datoslstAdapter = new DatosListasAdapter(ReservasActivity.this, R.layout.listas_layout, datosArray);
        listServicio = (ListView) findViewById(R.id.listView);
        listServicio.setItemsCanFocus(false);
        listServicio.setAdapter(datoslstAdapter);


        listServicio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Object o = listServicio.getItemAtPosition(position);
                DatosListas fullObject = (DatosListas) o;
                //Toast.makeText(ReservasActivity.this, "Tarjeta: " + " " + fullObject.getColor() +  " Seleccionada.", Toast.LENGTH_LONG).show();
                if (fullObject.getColor() == R.color.colorBlanco) {
                    //Toast.makeText(ReservasActivity.this, "Se puede Cancelar ", Toast.LENGTH_LONG).show();
                    if(appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("4")||appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("13")||
                            appState.getListServicios().get(position).getEstadoServicio().equalsIgnoreCase("16")){

                        MuestarAlert2(position);
                    }else{
                        MuestarAlert(position);
                    }

                } else {
                   // Toast.makeText(ReservasActivity.this, "No se puede Cancelar", Toast.LENGTH_LONG).show();
                }


            }
        });


    }


    /*============================================================================================*/
    /*============================================================================================*/
    private void RevisaMensajeAviso(String datos){

        Gson gson = new Gson();
        DatosServiciosDTO informacion = null;
        try {
            informacion = gson.fromJson(datos, DatosServiciosDTO.class);

            if(informacion.getMensajeEntranteCentral().length()>0){
                MuestraAlertMensaje("Mensaje Central", informacion.getMensajeEntranteCentral());
            }
            if(informacion.getMensajeEntranteMovil().length()>0){
                MuestraAlertMensaje("Mensaje Central", informacion.getMensajeEntranteMovil());
            }

        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestarAlert2(final int posicion) {
        try {
            android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertBuilder.setTitle("Que desea realizar");
            //alertBuilder.setMessage("Confirma la cancelacion del servicio?");
            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            alertBuilder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CancelaServicio(posicion);
                }
            });

            alertBuilder.setNegativeButton("Recuperar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
                    Log.d(TAG, "------------Debe Iniciaizar el servicio de TimerSocket");
                    Intent intentComunication = new Intent(ReservasActivity.this, TimerSocket.class);
                    startService(intentComunication);
                    /**********************************************************************************/
                    finish();

                }
            });
            android.support.v7.app.AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void MuestarAlert(final int posicion) {
        try {
            android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertBuilder.setTitle("Cancelar Servicio.");
            alertBuilder.setMessage("Confirma la cancelacion del servicio?");
            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CancelaServicio(posicion);
                }
            });

            alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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

    public void CancelaServicio(int posicion) {


        progress_loading =ProgressDialog.show(ReservasActivity.this,"Espere un momento ...","Esperando Respuesta ...",true,true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DatosServiciosDTO datosServicio = new DatosServiciosDTO();
        datosServicio.setFuncion("05");
        datosServicio.setTipoProyecto("4");
        datosServicio.setIdServicio(appState.getListServicios().get(posicion).getIdServicio());



        Gson gson = new Gson();
        String json = gson.toJson(datosServicio);
        sendData =new

        ConexionTCP(getApplicationContext());
        sendData.sendData(json);
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
