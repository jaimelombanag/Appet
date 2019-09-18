package com.fasepi.android.appet.usuario.Actividades.Menu;



import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.DatosListas;
import com.fasepi.android.appet.usuario.Appet.DatosListasAdapter;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.fasepi.android.appet.usuario.Servicios.TimerSocket;
import com.google.gson.Gson;

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
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class HistoricoActivity extends AppCompatActivity {




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
    private TextView txt_titulo;


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
                        if (informacion.getFuncion().equalsIgnoreCase("05")) {
                            Toast.makeText(getApplicationContext(), "Servicio Cancelado", Toast.LENGTH_LONG).show();

                            progress_loading =ProgressDialog.show(HistoricoActivity.this,"Espere un momento ...","Esperando Respuesta ...",true,true);
                            datosArray.clear();
                            listServicio.setAdapter(datoslstAdapter);

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


                                    //datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion: \n" + appState.getListServicios().get(i).getDireccion(), "Nombre: \n" + appState.getListServicios().get(i).getNombre(), "Costo: \n$" + appState.getListServicios().get(i).getCostoServicio(), colorVivi));


                                } else {

                                    datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion  Origen: \n" + appState.getListServicios().get(i).getDireccion()+
                                            "\n\nFecha Finalización: " + appState.getListServicios().get(i).getFechaFinalizacion() + "\nDireccion Destino: \n" + appState.getListServicios().get(i).getDestino(), "Nombre Conductor: \n" + appState.getListServicios().get(i).getDatosMovil().getNombreConductor()+ "\nPlaca: " + appState.getListServicios().get(i).getDatosMovil().getPlaca(), "Valor Servicio: \n" + redondeaSinDecimal(Double.parseDouble(appState.getListServicios().get(i).getCostoServicio()), true), colorHistorico,8));
                                }


                            }

                            datoslstAdapter = new DatosListasAdapter(HistoricoActivity.this, R.layout.listas_layout, datosArray);
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


        txt_titulo = (TextView) findViewById(R.id.txt_titulo);
        txt_titulo.setText("Lista de Servicios");


        DatosListas datosListas = new DatosListas();
        for (int i = 0; i < appState.getListServicios().size(); i++) {
            Log.i(TAG, "=================================   " + appState.getListServicios().get(i).getDireccion());
            Log.i(TAG, "============Fecha=====================   " + appState.getListServicios().get(i).getFechaReservacion());


            int colorHistorico = R.color.colorListaHistorico;
            int colorVivi = R.color.colorBlanco;

            String fecha;
            if(appState.getListServicios().get(i).getFechaReservacion() == null || appState.getListServicios().get(i).getFechaReservacion().equalsIgnoreCase("")){
                fecha = appState.getListServicios().get(i).getFechaCreacion();
            }else{
                fecha = appState.getListServicios().get(i).getFechaReservacion();
            }


            if (appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("4") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("7") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("10")
                    || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("11") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("13") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("16")) {



                Log.i(TAG, "=============VIVOS====================   " );


                //datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion: \n" + appState.getListServicios().get(i).getDireccion(), "Nombre: \n" + appState.getListServicios().get(i).getNombre(), "Costo: \n$" + appState.getListServicios().get(i).getCostoServicio(), colorVivi));


            } else {

                Log.i(TAG, "=============HISTORICO====================   " );


                 datosArray.add(new DatosListas("Fecha: " + fecha + "\nDireccion  Origen: \n" + appState.getListServicios().get(i).getDireccion()+
                         "\n\nFecha Finalización: " + appState.getListServicios().get(i).getFechaFinalizacion()  + "\nDireccion Destino: \n" + appState.getListServicios().get(i).getDestino(), "Nombre Conductor: \n" + appState.getListServicios().get(i).getDatosMovil().getNombreConductor()+ "\nPlaca: " + appState.getListServicios().get(i).getDatosMovil().getPlaca(), "Valor Servicio: \n " + redondeaSinDecimal(Double.parseDouble(appState.getListServicios().get(i).getCostoServicio()), true), colorHistorico,8));
            }


        }

        datoslstAdapter = new DatosListasAdapter(HistoricoActivity.this, R.layout.listas_layout, datosArray);
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
                    Intent intentComunication = new Intent(HistoricoActivity.this, TimerSocket.class);
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


        progress_loading =ProgressDialog.show(HistoricoActivity.this,"Espere un momento ...","Esperando Respuesta ...",true,true);
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





// ACA ES PARA QUE LISTE CON MAPA


//    private ListFragment mList;
//    private MapAdapter mAdapter;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_historico);
//        setTitle("   SERVICIOS  ");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Set a custom list adapter for a list of locations
//        mAdapter = new MapAdapter(this, LIST_LOCATIONS);
//        mList = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list);
//        mList.setListAdapter(mAdapter);
//
//        // Set a RecyclerListener to clean up MapView from ListView
//        AbsListView lv = mList.getListView();
//        lv.setRecyclerListener(mRecycleListener);
//
//
//    }
//
//    /**
//     * Adapter that displays a title and {@link com.google.android.gms.maps.MapView} for each item.
//     * The layout is defined in <code>lite_list_demo_row.xml</code>. It contains a MapView
//     * that is programatically initialised in
//     * {@link #getView(int, android.view.View, android.view.ViewGroup)}
//     */
//    private class MapAdapter extends ArrayAdapter<NamedLocation> {
//
//        private final HashSet<MapView> mMaps = new HashSet<MapView>();
//
//        public MapAdapter(Context context, NamedLocation[] locations) {
//            super(context, R.layout.lite_list_demo_row, R.id.lite_listrow_text, locations);
//        }
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View row = convertView;
//            ViewHolder holder;
//
//            // Check if a view can be reused, otherwise inflate a layout and set up the view holder
//            if (row == null) {
//                // Inflate view from layout file
//                row = getLayoutInflater().inflate(R.layout.lite_list_demo_row, null);
//
//                // Set up holder and assign it to the View
//                holder = new ViewHolder();
//                holder.mapView = (MapView) row.findViewById(R.id.lite_listrow_map);
//                holder.title = (TextView) row.findViewById(R.id.lite_listrow_text);
//                // Set holder as tag for row for more efficient access.
//                row.setTag(holder);
//
//                // Initialise the MapView
//                holder.initializeMapView();
//
//                // Keep track of MapView
//                mMaps.add(holder.mapView);
//            } else {
//                // View has already been initialised, get its holder
//                holder = (ViewHolder) row.getTag();
//            }
//
//            // Get the NamedLocation for this item and attach it to the MapView
//            NamedLocation item = getItem(position);
//            holder.mapView.setTag(item);
//
//            // Ensure the map has been initialised by the on map ready callback in ViewHolder.
//            // If it is not ready yet, it will be initialised with the NamedLocation set as its tag
//            // when the callback is received.
//            if (holder.map != null) {
//                // The map is already ready to be used
//                setMapLocation(holder.map, item);
//            }
//
//            // Set the text label for this item
//            holder.title.setText(item.name);
//
//            return row;
//        }
//
//        /**
//         * Retuns the set of all initialised {@link MapView} objects.
//         *
//         * @return All MapViews that have been initialised programmatically by this adapter
//         */
//        public HashSet<MapView> getMaps() {
//            return mMaps;
//        }
//    }
//
//    /**
//     * Displays a {@link LiteListDemoActivity.NamedLocation} on a
//     * {@link com.google.android.gms.maps.GoogleMap}.
//     * Adds a marker and centers the camera on the NamedLocation with the normal map type.
//     */
//    private static void setMapLocation(GoogleMap map, NamedLocation data) {
//        // Add a marker for this item and set the camera
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
//        map.addMarker(new MarkerOptions().position(data.location));
//
//        // Set the map type back to normal.
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//    }
//
//    /**
//     * Holder for Views used in the {@link LiteListDemoActivity.MapAdapter}.
//     * Once the  the <code>map</code> field is set, otherwise it is null.
//     * When the {@link #onMapReady(com.google.android.gms.maps.GoogleMap)} callback is received and
//     * the {@link com.google.android.gms.maps.GoogleMap} is ready, it stored in the {@link #map}
//     * field. The map is then initialised with the NamedLocation that is stored as the tag of the
//     * MapView. This ensures that the map is initialised with the latest data that it should
//     * display.
//     */
//    class ViewHolder implements OnMapReadyCallback {
//
//        MapView mapView;
//
//        TextView title;
//
//        GoogleMap map;
//
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            MapsInitializer.initialize(getApplicationContext());
//            map = googleMap;
//            NamedLocation data = (NamedLocation) mapView.getTag();
//            if (data != null) {
//                setMapLocation(map, data);
//            }
//        }
//
//        /**
//         * Initialises the MapView by calling its lifecycle methods.
//         */
//        public void initializeMapView() {
//            if (mapView != null) {
//                // Initialise the MapView
//                mapView.onCreate(null);
//                // Set the map ready callback to receive the GoogleMap object
//                mapView.getMapAsync(this);
//            }
//        }
//
//    }
//
//    /**
//     * RecycleListener that completely clears the {@link com.google.android.gms.maps.GoogleMap}
//     * attached to a row in the ListView.
//     * Sets the map type to {@link com.google.android.gms.maps.GoogleMap#MAP_TYPE_NONE} and clears
//     * the map.
//     */
//    private AbsListView.RecyclerListener mRecycleListener = new AbsListView.RecyclerListener() {
//
//        @Override
//        public void onMovedToScrapHeap(View view) {
//            ViewHolder holder = (ViewHolder) view.getTag();
//            if (holder != null && holder.map != null) {
//                // Clear the map and free up resources by changing the map type to none
//                holder.map.clear();
//                holder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
//            }
//
//        }
//    };
//
//    /**
//     * Location represented by a position ({@link com.google.android.gms.maps.model.LatLng} and a
//     * name ({@link java.lang.String}).
//     */
//    private static class NamedLocation {
//
//        public final String name;
//
//        public final LatLng location;
//
//        NamedLocation(String name, LatLng location) {
//            this.name = name;
//            this.location = location;
//        }
//    }
//
//    /**
//     * A list of locations to show in this ListView.
//     */
//    private static final NamedLocation[] LIST_LOCATIONS = new NamedLocation[]{
//            new NamedLocation("Cape Town", new LatLng(-33.920455, 18.466941)),
//            new NamedLocation("Beijing", new LatLng(39.937795, 116.387224)),
//            new NamedLocation("Bern", new LatLng(46.948020, 7.448206)),
//            new NamedLocation("Breda", new LatLng(51.589256, 4.774396)),
//            new NamedLocation("Brussels", new LatLng(50.854509, 4.376678)),
//            new NamedLocation("Copenhagen", new LatLng(55.679423, 12.577114)),
//            new NamedLocation("Hannover", new LatLng(52.372026, 9.735672)),
//            new NamedLocation("Helsinki", new LatLng(60.169653, 24.939480)),
//            new NamedLocation("Hong Kong", new LatLng(22.325862, 114.165532)),
//            new NamedLocation("Istanbul", new LatLng(41.034435, 28.977556)),
//            new NamedLocation("Johannesburg", new LatLng(-26.202886, 28.039753)),
//            new NamedLocation("Lisbon", new LatLng(38.707163, -9.135517)),
//            new NamedLocation("London", new LatLng(51.500208, -0.126729)),
//            new NamedLocation("Madrid", new LatLng(40.420006, -3.709924)),
//            new NamedLocation("Mexico City", new LatLng(19.427050, -99.127571)),
//            new NamedLocation("Moscow", new LatLng(55.750449, 37.621136)),
//            new NamedLocation("New York", new LatLng(40.750580, -73.993584)),
//            new NamedLocation("Oslo", new LatLng(59.910761, 10.749092)),
//            new NamedLocation("Paris", new LatLng(48.859972, 2.340260)),
//            new NamedLocation("Prague", new LatLng(50.087811, 14.420460)),
//            new NamedLocation("Rio de Janeiro", new LatLng(-22.90187, -43.232437)),
//            new NamedLocation("Rome", new LatLng(41.889998, 12.500162)),
//            new NamedLocation("Sao Paolo", new LatLng(-22.863878, -43.244097)),
//            new NamedLocation("Seoul", new LatLng(37.560908, 126.987705)),
//            new NamedLocation("Stockholm", new LatLng(59.330650, 18.067360)),
//            new NamedLocation("Sydney", new LatLng(-33.873651, 151.2068896)),
//            new NamedLocation("Taipei", new LatLng(25.022112, 121.478019)),
//            new NamedLocation("Tokyo", new LatLng(35.670267, 139.769955)),
//            new NamedLocation("Tulsa Oklahoma", new LatLng(36.149777, -95.993398)),
//            new NamedLocation("Vaduz", new LatLng(47.141076, 9.521482)),
//            new NamedLocation("Vienna", new LatLng(48.209206, 16.372778)),
//            new NamedLocation("Warsaw", new LatLng(52.235474, 21.004057)),
//            new NamedLocation("Wellington", new LatLng(-41.286480, 174.776217)),
//            new NamedLocation("Winnipeg", new LatLng(49.875832, -97.150726))
//    };
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
