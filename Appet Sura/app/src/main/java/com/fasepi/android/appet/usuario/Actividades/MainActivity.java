package com.fasepi.android.appet.usuario.Actividades;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasepi.android.appet.usuario.Actividades.Menu.HistoricoActivity;
import com.fasepi.android.appet.usuario.Actividades.Menu.ReservasActivity;
import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosTarjetaCreditoDTO;
import com.fasepi.android.appet.usuario.Clases.DatosUsuariosDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosGeocercaDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosMovilDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosPropietarioDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosServiciosDTOT;
import com.fasepi.android.appet.usuario.R;
import com.fasepi.android.appet.usuario.Servicios.ConexionTCP;
import com.fasepi.android.appet.usuario.Servicios.GeocoderHandler;
import com.fasepi.android.appet.usuario.Servicios.TextoVoz;
import com.fasepi.android.appet.usuario.Servicios.TimerApp;
import com.fasepi.android.appet.usuario.Servicios.TimerSocket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import android.widget.Filter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, android.location.LocationListener,
        GoogleMap.OnCameraChangeListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, AdapterView.OnItemClickListener {


    private static String TAG = "Appet";
    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    private final String ACTION_STRING_SERVICE = "ToService";
    private NavigationView navigationView;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private Globales appState;
    private Context context;
    private ConexionTCP sendData;
    private LinearLayout layout_inicio;
    private LinearLayout layout_origen_destino;
    private LinearLayout layout_tipo_vehiculo;
    private LinearLayout layout_con_servicio;
    private LinearLayout layout_con_servicio2;
    private LinearLayout layout_comunicacion_vehiculo;


    private Location mapLocation;
    private Address currentAddress;
    private EditText edt_origen;
    private EditText edt_destino;
    private LatLng INICIO;
    private ImageView imgen_central;
    private  TextView txt_tiempo_llegada;

    private ProgressDialog progress_loading;
    private FrameLayout img_civicar;
    private FrameLayout img_civivan;
    private FrameLayout img_civivan_mas;
    private FrameLayout img_civibus;
    private FrameLayout img_civibus_mas;
    //private TextView txt_distancia;
    private TextView txt_tiempo;
    private TextView txt_tarifa_aprox;
    private TextView txt_capacidad;
    private TextView txt_title_bar;
//    private TextView txt_nombre_conductor;
//    private TextView txt_datos_vehiculo;
    private Button btn_confirmar_reservar;

    private String tipoVehiculo = "12";
    private Intent callIntent;
    private int flagSolicitarServicio=0;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private boolean reservacion = false;
    private static String horaReserva;
    private static String fechaReserva;
    private String costoServicio;
    private int flagDesOri=0;
    private LinearLayout layout_edt_indicaciones;
    private LinearLayout layout_pedirya;
    private LinearLayout layout_reserva;
    private EditText edt_indicaciones;
    private ImageButton img_btn_add_destino;
    static int setadd = 0;
    private int contExist=0;
    private boolean menuReservas = false;
    private boolean markerDestino = false;
    private  int flagtarifica=0;
    private String metrosRecorridos;
    private String minutosRecorridos;
    private String direccionCalcula;
    private String ciudadOrigen;
    private String ciudadDestino;


    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyB0rZHS3nqpb2smPoRpOgze_dE_1oibFA4";

    /*============================================================================================*/
    /*============================================================================================*/

    private TextView txt_destino;
    private TextView txt_fecha;
    private TextView txt_nombre_conductor;
    private TextView txt_datos_vehiculo;
    private TextView txt_distancia;

    private int alfrenteflag = 0;
    private int flagAbordo = 0;
    private int flagnoAbordo = 0;
    private int flagnoAsignado = 0;
    private int flagmalDireccion = 0;



    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/

    private LinearLayout layout_botonesAppet;
    private LinearLayout layout_geocerca;
    private LinearLayout layout_historico;
    private SeekBar bar_radio;
    private Circle mCircle;
    private Marker mMarker;
    private TextView txt_metros_geocerca;
    private ImageButton btn_establecer_geocerca;
    private String ultimasHoras;
    private String radioGeocerca;



    private ArrayList<LatLng> puntosSeguimiento;
    private LatLng Inicio;
    private LatLng Final;
    private double latitudRutaSave;
    private double longitudRutaSave;
    private Marker appetMarker;

    private int contadorHistorico=0;
    private Timer timerHistorico = new Timer();



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
                MuestraAlertMensaje("", datos);
            } else {
                ProcessRecepcion(cmd, datos);
            }

        }
    };

    /*============================================================================================*/
    /*============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        appState = ((Globales) context);
        /*=======================================================================*/
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
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
        layout_botonesAppet = (LinearLayout) findViewById(R.id.layout_botonesAppet);
        layout_geocerca = (LinearLayout) findViewById(R.id.layout_geocerca);
        layout_historico =  (LinearLayout) findViewById(R.id.layout_historico);
        bar_radio = (SeekBar) findViewById(R.id.bar_radio);
        txt_metros_geocerca = (TextView) findViewById(R.id.txt_metros_geocerca);
        btn_establecer_geocerca = (ImageButton) findViewById(R.id.btn_establecer_geocerca);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.action_bar_home, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT
                )
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        InitLayout();
        CentrarPunto();
        appState.setEstadoUsuario("sinservicio");
        progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
        SendFuncion("02");                  //Lista de Mascotas


         /*++++++++++ INICIALIZACION DE LOS SERVICIOS  +++++++++++++++*/
        Intent timer = new Intent(this, TimerApp.class);
        startService(timer);

//        Intent timer = new Intent(this, TimerSocket.class);
//        startService(timer);
//
//        Intent talk = new Intent(this, TextoVoz.class);
//        startService(talk);

        /*========================================================================================*/

//        Intent activity = new Intent();
//        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.setClass(getApplicationContext(), DatosProfecionalActivity.class);
//        getApplicationContext().startActivity(activity);



        bar_radio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //la Seekbar siempre empieza en cero, si queremos que el valor mínimo sea otro podemos modificarlo
                //Log.i(TAG, progress + 20 + "");
//                Log.i(TAG,  progress * 10 + "-------------------------");
                //int radio = progress * 10;
                int radio = progress;
                switch (radio){

                    case 1:
                        Log.i(TAG, "----------100 metros---------------");
                        txt_metros_geocerca.setText("100 metros");
                        Geocerca(100);
                        break;
                    case 2:
                        Log.i(TAG, "----------200 metros---------------");
                        txt_metros_geocerca.setText("200 metros");
                        Geocerca(200);
                        break;
                    case 3:
                        Log.i(TAG, "----------300 metros---------------");
                        txt_metros_geocerca.setText("300 metros");
                        Geocerca(300);
                        break;
                    case 4:
                        Log.i(TAG, "----------400 metros---------------");
                        txt_metros_geocerca.setText("400 metros");
                        Geocerca(400);
                        break;
                    case 5:
                        Log.i(TAG, "----------500 metros---------------");
                        txt_metros_geocerca.setText("500 metros");
                        Geocerca(500);
                        break;
                    case 6:
                        Log.i(TAG, "----------600 metros---------------");
                        txt_metros_geocerca.setText("600 metros");
                        Geocerca(600);
                        break;
                    case 7:
                        Log.i(TAG, "----------700 metros---------------");
                        txt_metros_geocerca.setText("700 metros");
                        Geocerca(700);
                        break;
                    case 8:
                        Log.i(TAG, "----------800 metros---------------");
                        txt_metros_geocerca.setText("800 metros");
                        Geocerca(800);
                        break;
                    case 9:
                        Log.i(TAG, "----------900 metros---------------");
                        txt_metros_geocerca.setText("900 metros");
                        Geocerca(900);
                        break;
                    case 10:
                        Log.i(TAG, "----------1000 metros---------------");
                        txt_metros_geocerca.setText("1000 metros");
                        Geocerca(1000);
                        break;

                }


            }

            /**
             * El usuario inicia la interacción con la Seekbar.
             */
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }
            /**
             * El usuario finaliza la interacción con la Seekbar.
             */
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
        });

    }

    /*========================================================================================*/
    /*========================================================================================*/
    /*========================================================================================*/
    public void InitLayout() {
//        layout_inicio = (LinearLayout) findViewById(R.id.layout_inicio);
//        layout_origen_destino = (LinearLayout) findViewById(R.id.layout_origen_destino);
//        layout_tipo_vehiculo = (LinearLayout) findViewById(R.id.layout_tipo_vehiculo);
//        layout_con_servicio = (LinearLayout) findViewById(R.id.layout_con_servicio);
//        layout_con_servicio2 = (LinearLayout) findViewById(R.id.layout_con_servicio2);
//        layout_comunicacion_vehiculo = (LinearLayout) findViewById(R.id.layout_comunicacion_vehiculo);
//        edt_origen = (EditText) findViewById(R.id.edt_origen);
//        edt_destino = (EditText) findViewById(R.id.edt_destino);
//        imgen_central = (ImageView) findViewById(R.id.imgen_central);
//
//        img_civicar = (FrameLayout) findViewById(R.id.img_civicar);
//        img_civivan = (FrameLayout) findViewById(R.id.img_civivan);
//        img_civivan_mas = (FrameLayout) findViewById(R.id.img_civivan_mas);
//        img_civibus = (FrameLayout) findViewById(R.id.img_civibus);
//        img_civibus_mas = (FrameLayout) findViewById(R.id.img_civibus_mas);
//        txt_tarifa_aprox = (TextView) findViewById(R.id.txt_tarifa_aprox);
//        txt_capacidad = (TextView) findViewById(R.id.txt_capacidad);
//        btn_confirmar_reservar = (Button) findViewById(R.id.btn_confirmar_reservar);
//        txt_distancia = (TextView) findViewById(R.id.txt_distancia);
//        txt_tiempo = (TextView) findViewById(R.id.txt_tiempo);
        txt_title_bar = (TextView) findViewById(R.id.txt_title_bar);
//        txt_nombre_conductor = (TextView) findViewById(R.id.txt_nombre_conductor);
//        txt_datos_vehiculo = (TextView) findViewById(R.id.txt_datos_vehiculo);
//        txt_tiempo_llegada = (TextView) findViewById(R.id.txt_tiempo_llegada);
//        img_btn_add_destino = (ImageButton) findViewById(R.id.img_btn_add_destino);
//        edt_indicaciones = (EditText) findViewById(R.id.edt_indicaciones);
//        layout_edt_indicaciones = (LinearLayout) findViewById(R.id.layout_edt_indicaciones);
//        layout_pedirya = (LinearLayout) findViewById(R.id.layout_pedirya);
//        layout_reserva = (LinearLayout) findViewById(R.id.layout_reserva);


//        edt_destino.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
//        edt_destino.setOnItemClickListener(this);

 //       edt_destino.setKeyListener(null);



        txt_destino = (TextView) findViewById(R.id.txt_destino);
        txt_fecha = (TextView) findViewById(R.id.txt_fecha);
        txt_nombre_conductor = (TextView) findViewById(R.id.txt_nombre_conductor);
        txt_datos_vehiculo = (TextView) findViewById(R.id.txt_datos_vehiculo);
        txt_distancia = (TextView) findViewById(R.id.txt_distancia);



        /************************** Para el Menu **************************************************/

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DisplayMetrics metrics = new DisplayMetrics();
        /***Convertir Foto*****/

//        byte[] byteArray = decodeImage(sharedPreferences.getString(Constantes.FotoConductor, ""));
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        View headView = navigationView.getHeaderView(0);

        String localVersion = "";
        try {
            localVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) headView.findViewById(R.id.versionapp)).setText("APPET V. " + localVersion);

        String[] nombreapellido = sharedPreferences.getString(Constants.customer_name, "").split(" ");
        if (nombreapellido.length < 3) {
            ((TextView) headView.findViewById(R.id.txt_mombre_menu)).setText(sharedPreferences.getString(Constants.customer_name, ""));
        } else if (nombreapellido.length == 3) {
            ((TextView) headView.findViewById(R.id.txt_mombre_menu)).setText(nombreapellido[0] + " " + nombreapellido[1]);
        } else {
            if (nombreapellido[1].length() > 7)
                ((TextView) headView.findViewById(R.id.txt_mombre_menu)).setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Small);
            ((TextView) headView.findViewById(R.id.txt_mombre_menu)).setText(nombreapellido[0] + " " + nombreapellido[2]);
        }

        //((TextView) headView.findViewById(R.id.txt_correo_menu)).setText(sharedPreferences.getString(Constants.customer_email, ""));
        ((TextView) headView.findViewById(R.id.txt_correo_menu)).setText("");
        ((TextView) headView.findViewById(R.id.txt_telefono_menu)).setText(sharedPreferences.getString(Constants.customer_phone, ""));

        //((ImageView)headView.findViewById(R.id.imageViewMenu)).setImageResource(R.drawable.avatar);
        //((ImageView) headView.findViewById(R.id.imageViewMenu)).setImageBitmap(bmp);

    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:co");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    private void CentrarPunto() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    flagDesOri=0;
//                    markerDestino=false;
//                    mMap.clear();
//                    INICIO = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 14));
//                    edt_destino.setText("");
//                    Log.e(TAG, " SE CUADRA EL ZOOM   ");
////                    VehiculosCerca(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    CentrarPunto();
//                }
//            }
//        }, 3000);
    }

    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    public void MuestraAlertMensajeCerrar(String titulo, String mensaje) {
        Log.i(TAG, " ================ SE MUESTRA MENSAJE EN ALERT : " + mensaje);
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle(titulo);
            if(mensaje==null) alertBuilder.setMessage("timeout intente de nuevo.");
            else alertBuilder.setMessage(mensaje);
            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            alertBuilder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MuestraAlertMensaje(String titulo, String mensaje) {
        Log.i(TAG, " ================ SE MUESTRA MENSAJE EN ALERT : " + mensaje);

        if(mensaje.equalsIgnoreCase("El móvil se encuentra al frente")){

        }else {

            try {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle(titulo);
                if (mensaje == null) alertBuilder.setMessage("timeout intente de nuevo.");
                else alertBuilder.setMessage(mensaje);
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
    }

    public void MuestraAlertMensajeCustom(String msg) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_popup, null);
        dialogBuilder.setView(dialogView);

        final TextView text = (TextView) dialogView.findViewById(R.id.text_dialog);
        text.setText(msg);

        final Button aceptar = (Button) dialogView.findViewById(R.id.btn_dialog);


        final ImageView img_alert = (ImageView) dialogView.findViewById(R.id.icon_alert);

        if(msg.contains("geocerca")){
            img_alert.setBackgroundResource(R.drawable.icono_cerca);
        }else{
            img_alert.setBackgroundResource(R.drawable.icono_bateria);
        }

        final AlertDialog aletCustom = dialogBuilder.create();
        aletCustom.show();

        aletCustom.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aletCustom.dismiss();
            }
        });
    }

    public void MuestraAlertMensajeCerrarCustom(String msg) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_popup, null);
        dialogBuilder.setView(dialogView);

        final TextView text = (TextView) dialogView.findViewById(R.id.text_dialog);
        text.setText(msg);

        final Button aceptar = (Button) dialogView.findViewById(R.id.btn_dialog);

        final ImageView img_alert = (ImageView) dialogView.findViewById(R.id.icon_alert);

        if(msg.contains("geocerca")){
            img_alert.setBackgroundResource(R.drawable.icono_cerca);
        }else{
            img_alert.setBackgroundResource(R.drawable.icono_bateria);
        }

        final AlertDialog aletCustom = dialogBuilder.create();
        aletCustom.show();

        aletCustom.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*========================================================================================*/
    /*========================================================================================*/
    /*========================================================================================*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.cerrar) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.customer_name, "");
            editor.putString(Constants.customer_phone, "");
            editor.putString(Constants.customer_email, "");
            editor.putString(Constants.customer_cedula, "");

            editor.commit();
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*============================================================================================*/
    /*============================================================================================*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // mMap.setMyLocationEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setTrafficEnabled(false);
        mMap.setOnCameraChangeListener(this);
        mMap.setOnMarkerClickListener(this);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged");
        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        this.mMap.moveCamera(center);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        this.mMap.animateCamera(zoom);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i(TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i(TAG, "onProviderdISABLE");
    }

    @Override
    public void onCameraChange(CameraPosition position) {
        Log.i(TAG, " -------- Se mueve el Mapa -----------------");
        Log.i(TAG, " ------------------------- " + position.target.latitude);
        Log.i(TAG, " ------------------------- " + position.target.longitude);




        mapLocation = new Location("Location");
        mapLocation.setLatitude(position.target.latitude);
        mapLocation.setLongitude(position.target.longitude);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!markerDestino) {

            editor.putString(Constants.Latitud, String.valueOf(position.target.latitude));
            editor.putString(Constants.Longitud, String.valueOf(position.target.longitude));
            editor.commit();
        }else{
            editor.putString(Constants.LatitudDestino, String.valueOf(position.target.latitude));
            editor.putString(Constants.LongitudDestino, String.valueOf(position.target.longitude));
            editor.commit();
        }


        GeocoderHandler geocode = new GeocoderHandler(position.target.latitude, position.target.longitude, handler, getApplicationContext());
        geocode.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            currentAddress = (Address) msg.obj;
            if (currentAddress != null) {
//                String direccionOriginal = currentAddress.getAddressLine(0);
//                String direccionModificada = direccionOriginal.replaceAll("( a ).*", "");
//                //DireccionGoogle.setText("  " + direccionModificada);
//                direccionOriginal = direccionOriginal.replace("Colombia", "");
//                direccionOriginal = direccionOriginal.replace("Colombia", "");
//
//
//                Log.d(TAG, " -----------JAIME LOMBANA----------- " + currentAddress.getLocality());
//
//                if(flagDesOri==0) {
//                    edt_origen.setText("  " + direccionModificada);
//                    edt_origen.setSelection(edt_origen.getText().length());
//                    ciudadOrigen = currentAddress.getLocality();
//                }else{
//                    edt_destino.setText("  " + direccionModificada);
//                    edt_destino.setSelection(edt_destino.getText().length());
//
//                    Log.d(TAG, " -----------JAIME----------- " + direccionOriginal);
//                    Log.d(TAG, " -----------CLAUDIA----------- " + currentAddress.getCountryName());
//                    direccionCalcula = direccionOriginal+  currentAddress.getCountryName();
//                    ciudadDestino = currentAddress.getLocality();
//
//                    if(layout_tipo_vehiculo.getVisibility() == View.VISIBLE){
//                        EstadoPantalla("sinservicio");
//                    }
//
//                }
//                Log.d(TAG, " ----------------------    " + direccionModificada);


            } else {
                mapLocation = null;
            }
        }
    };

    /*============================================================================================*/
    /*============================================================================================*/
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        try {
                            mMap.setMyLocationEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
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
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "resumeeeen");
        if (activityReceiver != null) {
            registerReceiver(activityReceiver, new IntentFilter(ACTION_STRING_ACTIVITY));
        }
        Intent timer = new Intent(this, TimerApp.class);
        startService(timer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Activity --> onPause");
        Intent timer = new Intent(this, TimerApp.class);
        stopService(timer);
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
        Intent timer = new Intent(this, TimerApp.class);
        stopService(timer);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                Intent timer = new Intent(this, TimerApp.class);
                stopService(timer);
                Intent timer2 = new Intent(this, TimerSocket.class);
                stopService(timer2);
                Intent talk = new Intent(this, TextoVoz.class);
                stopService(talk);

                finish();


                return true;
            case KeyEvent.KEYCODE_HOME:
                Log.i(TAG, "Se Oprimio el Boton de Back");
        }
        return super.onKeyDown(keyCode, event);
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

                RevisaMensajeAviso(datos);


                if (informacion.getFuncion().equalsIgnoreCase("00")) {

                    mMap.clear();

                    double latitud = Double.parseDouble(appState.getDatosMovilDTOT().getLatitud());
                    double longitud = Double.parseDouble(appState.getDatosMovilDTOT().getLongitud());

                    LatLng sydney = new LatLng(latitud, longitud);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(appState.getDatosMovilDTOT().getMovPlaca()).icon(BitmapDescriptorFactory.fromResource(R.drawable.huella_verde))).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));


                }else if (informacion.getFuncion().equalsIgnoreCase("01")) {                // Crear Servicio
//
//                    if(reservacion){
//                        edt_destino.setText("");
//                        MuestraAlertMensaje("Respuesta reserva", "La reserva fue creada con éxito.");
//                        CentrarPunto();
//                    }else{
//                        /********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
//                        Log.d(TAG, "------------Debe Iniciaizar el servicio de TimerSocket");
//                        Intent intentComunication = new Intent(MainActivity.this, TimerSocket.class);
//                        startService(intentComunication);
//                        /**********************************************************************************/
//
//                        Intent services = new Intent();
//                        services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        services.putExtra("DIR_SERVICIO", "");
//                        services.putExtra("LAT_SERVICIO", sharedPreferences.getString(Constants.Latitud, ""));
//                        services.putExtra("LON_SERVICIO", sharedPreferences.getString(Constants.Longitud, ""));
//                        services.setClass(getApplicationContext(), NewServiceActivity.class);
//                        getApplicationContext().startActivity(services);
//                    }


                }else if (informacion.getFuncion().equalsIgnoreCase("02")) {
                    // Add a marker in Sydney, Australia, and move the camera.



                    if(appState.getListaMoviles().size() == 1){

                        double latitud = Double.parseDouble(appState.getListaMoviles().get(0).getLatitud());
                        double longitud = Double.parseDouble(appState.getListaMoviles().get(0).getLongitud());


                        LatLng sydney;
                        if(latitud < 1 && longitud < 1){

                            sydney = new LatLng(4.00000000, -72.0000000);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 4));
                            Toast.makeText(getApplicationContext(), "Sin coordenadas validas...", Toast.LENGTH_LONG).show();

                        }else {


                            sydney = new LatLng(latitud, longitud);
                            mMap.addMarker(new MarkerOptions().position(sydney).title(appState.getListaMoviles().get(0).getMovPlaca()).icon(BitmapDescriptorFactory.fromResource(R.drawable.huella_verde)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                        }







                    }else{
                        Toast.makeText(getApplicationContext(), "Tiene mas de 1 mascota", Toast.LENGTH_LONG).show();
                    }



                }
                else if (informacion.getFuncion().equalsIgnoreCase("03")) {
                    Intent timer1 = new Intent(this, TimerApp.class);
                    stopService(timer1);



                    if(appState.getListaTracking().size() < 1){
                        MuestraAlertMensajeCustom("No se encontraron registros de la unidad.");
                    }else {


                        double latitud = Double.parseDouble(appState.getListaTracking().get(0).getLatitud());
                        double longitud = Double.parseDouble(appState.getListaTracking().get(0).getLongitud());

                        LatLng RutaInicio = new LatLng(latitud, longitud);

                        appetMarker = mMap.addMarker(new MarkerOptions()
                                .position(RutaInicio)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.huella_verde))
                                .title("Mi Ruta"));
                        appetMarker.isInfoWindowShown();
//
//
//
//                    ProcessMueveAppet(RutaInicio);
//
//                    for(int i =0;  i < appState.getListaMoviles().size(); i++){
//
//                        double latitud2 = Double.parseDouble(appState.getListaMoviles().get(i).getLatitud());
//                        double longitud2 = Double.parseDouble(appState.getListaMoviles().get(i).getLongitud());
//
//                        LatLng sydney2 = new LatLng(latitud2, longitud2);
//
//                        ProcessMueveAppet(sydney2);
//                    }


                        //ProcessMueveRuta(RutaInicio);

                        mMap.clear();
                        timerHistorico.scheduleAtFixedRate(new MuestraAppet(), 0, 4000);

                    }


                }



                else if (informacion.getFuncion().equalsIgnoreCase("04")) {
                  MuestraAlertMensajeCustom("Se creo la geocerca correctamente.");
                }



                else if (informacion.getFuncion().equalsIgnoreCase("05")) {
                    mMap.clear();
                    EstadoPantalla("sinservicio");
                    CentrarPunto();

                }else if (informacion.getFuncion().equalsIgnoreCase("11")) {

                    if(menuReservas){
                        Intent activity = new Intent();
                        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.setClass(getApplicationContext(), ReservasActivity.class);
                        getApplicationContext().startActivity(activity);
                    }else{
                        Intent activity = new Intent();
                        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.setClass(getApplicationContext(), HistoricoActivity.class);
                        getApplicationContext().startActivity(activity);
                    }


                }else if (informacion.getFuncion().equalsIgnoreCase("12")) {          //Zonificar
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.LatitudDestino, informacion.getLatitud());
                    editor.putString(Constants.LongitudDestino, informacion.getLongitud());
                    editor.commit();
                    SendFuncion("13");

                } else if (informacion.getFuncion().equalsIgnoreCase("13")) {          //Calcula Costos
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.costoSeleccion, informacion.getTarificador().getPrecioAutomovil());
                    editor.commit();
                    txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(informacion.getTarificador().getPrecioTipo1()), true));
                    tipoVehiculo = "12";

                    img_civicar.setBackgroundResource(R.drawable.circular_button_pressed);
                    img_civivan.setBackgroundResource(R.drawable.circular_button);
                    img_civivan_mas.setBackgroundResource(R.drawable.circular_button);
                    img_civibus.setBackgroundResource(R.drawable.circular_button);
                    img_civibus_mas.setBackgroundResource(R.drawable.circular_button);

                    costoServicio = informacion.getTarificador().getPrecioTipo1();
                    layout_tipo_vehiculo.setVisibility(View.VISIBLE);
                    layout_inicio.setVisibility(View.GONE);
                }else if (informacion.getFuncion().equalsIgnoreCase("14")) {          //Lista de Tarjetas

                    if(flagSolicitarServicio == 1) {
                        flagSolicitarServicio = 0;
                        if (appState.getLstTarjetasCredito().size() == 1) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.idTarjeta, appState.getLstTarjetasCredito().get(0).getId());
                            editor.commit();

                            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
                            SendFuncion("01");

                        } else if (appState.getLstTarjetasCredito().size() == 0){
                            Toast.makeText(getApplicationContext(), "Debe agregar un tarjeta de credito", Toast.LENGTH_LONG).show();
                            Intent services = new Intent();
                            services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            services.setClass(getApplicationContext(), CreditCardActivity.class);
                            getApplicationContext().startActivity(services);

                        }else{
                            //Se debe listar las tarjetas para q el usuario seleccione
                            Intent services = new Intent();
                            services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            services.setClass(getApplicationContext(), CreditCardActivity.class);
                            getApplicationContext().startActivity(services);
                        }

                    }else{
                        Intent services = new Intent();
                        services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        services.setClass(getApplicationContext(), CreditCardActivity.class);
                        getApplicationContext().startActivity(services);
                    }

                }else if (informacion.getFuncion().equalsIgnoreCase("26")) {          //Estado del Usuario


                    for (int i = 0; i < appState.getListServicios().size(); i++) {
//                        if (appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("4") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("7") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("10")
//                                || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("11") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("13") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("16")) {
                        if (appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("4") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("7") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("10")
                                || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("13") || appState.getListServicios().get(i).getEstadoServicio().equalsIgnoreCase("16")) {



                            /********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
                            Log.d(TAG, "------------Debe Iniciaizar el servicio de TimerSocket");
                            Intent intentComunication = new Intent(MainActivity.this, TimerSocket.class);
                            startService(intentComunication);
                            /**********************************************************************************/


                        }
                    }



                }

                } catch (Exception e) {
                e.printStackTrace();
                MuestraAlertMensaje("Error Al recibir", "Ocurrio un error al recibir la información");
            }
        } else if (funcion.equalsIgnoreCase("vehiculoAsignado")) {

            Log.i(TAG, "Ya se encontro vehiculo");
            EstadoPantalla("conservicio");

        } else if (funcion.equalsIgnoreCase("dentroVehiculo")) {
            Log.i(TAG, "Usuario Abordo");


            flagAbordo ++;
            if(flagAbordo == 1){
                //MuestraAlertMensaje(" ", "El conductor reporta que se encuentra a bordo del vehículo.");
                MuestraAlertMensajeCustom( "El conductor reporta que se encuentra a bordo del vehículo.");
                Intent talk = new Intent();
                talk.putExtra("CMD", "TextoVoz");
                talk.putExtra("DATA", "Se encuentra abordo del vehículo");
                talk.setAction(ACTION_STRING_SERVICE);
                sendBroadcast(talk);
            }

            EstadoPantalla("abordo");
        } else if (funcion.equalsIgnoreCase("VehiculoAlfrente")) {
            Log.i(TAG, "Vehiculo lo espera");
            EstadoPantalla("movilalfrente");
        } else if (funcion.equalsIgnoreCase("FinServicio")) {

            Log.i(TAG, "Fin del Servicio");
            mMap.clear();
            EstadoPantalla("sinservicio");
            Intent services = new Intent();
            services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            services.putExtra("COSTO",sharedPreferences.getString(Constants.CostoServicio, ""));
            services.setClass(getApplicationContext(), CalificarActivity.class);
            getApplicationContext().startActivity(services);

            Intent talk = new Intent();
            talk.putExtra("CMD", "TextoVoz");
            talk.putExtra("DATA", "Por favor califica el servicio");
            talk.setAction(ACTION_STRING_SERVICE);
            sendBroadcast(talk);


            Intent talks = new Intent(this, TextoVoz.class);
            stopService(talks);

            finish();


        } else if (funcion.equalsIgnoreCase("cancelaServicio")) {
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
            SendFuncion("05");
        }else if (funcion.equalsIgnoreCase("mensajeMovil")) {
            MuestraAlertMensaje("Mensaje Conductor", datos);
        }else if (funcion.equalsIgnoreCase("cancelaServicioConductor")){
            mMap.clear();
            EstadoPantalla("sinservicio");
            CentrarPunto();
            MuestraAlertMensaje("Mensaje Conductor", "Servicio cancelado por el conductor");
        }else if (funcion.equalsIgnoreCase("cancelaServicioUsuario")){
            mMap.clear();
            EstadoPantalla("sinservicio");
            CentrarPunto();
            MuestraAlertMensaje("Mensaje Conductor", "Servicio cancelado por el usuario");
        } else if (funcion.equalsIgnoreCase("solicitaServicioTc")){
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
            SendFuncion("01");
        }else if (funcion.equalsIgnoreCase("noabordo")){

            flagnoAbordo ++;
            if(flagnoAbordo == 1){
                MuestraAlertMensajeCerrarCustom("El conductor informa que usted no abordó el vehículo.");
                Intent talk = new Intent();
                talk.putExtra("CMD", "TextoVoz");
                talk.putExtra("DATA", "El conductor informa que usted no abordó el vehículo.");
                talk.setAction(ACTION_STRING_SERVICE);
                sendBroadcast(talk);

            }


        }else if (funcion.equalsIgnoreCase("noasignado")){

            flagnoAsignado ++;
            if(flagnoAsignado == 1){
                MuestraAlertMensajeCerrarCustom("El conductor informa que su servicio no estaba asignado.");
                Intent talk = new Intent();
                talk.putExtra("CMD", "TextoVoz");
                talk.putExtra("DATA", "El conductor informa que su servicio no estaba asignado.");
                talk.setAction(ACTION_STRING_SERVICE);
                sendBroadcast(talk);

            }


        }else if (funcion.equalsIgnoreCase("maldireccion")){

            flagmalDireccion ++;
            if(flagmalDireccion == 1){
                MuestraAlertMensajeCerrarCustom("El conductor informa que no encuentra la dirección.");
                Intent talk = new Intent();
                talk.putExtra("CMD", "TextoVoz");
                talk.putExtra("DATA", "El conductor informa que no encuentra la dirección.");
                talk.setAction(ACTION_STRING_SERVICE);
                sendBroadcast(talk);

            }
        }

    }

    /**********************************************************************************************/


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
    private void SendFuncion(String funcion) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        DatosServiciosDTO datosServicio = new DatosServiciosDTO();
//        datosServicio.setFuncion(funcion);
//        datosServicio.setTipoProyecto(Constants.TipoProyecto);
//
//        DatosUsuariosDTO datosUsuariosDTO = new DatosUsuariosDTO();
//        datosUsuariosDTO.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));
//        datosServicio.setUsuario(datosUsuariosDTO);


        DatosServiciosDTOT datosServiciosDTOT = new DatosServiciosDTOT();
        datosServiciosDTOT.setFuncion(funcion);
        datosServiciosDTOT.setTipoProyecto(Constants.TipoProyecto);
        DatosPropietarioDTOT datosPropietarioDTOT = new DatosPropietarioDTOT();
        DatosMovilDTOT datosMovilDTOT = new DatosMovilDTOT();


        switch (funcion) {
            case "01":

                Log.i(TAG, "===========COSTOOOOOOO "   +  costoServicio);

//                datosServicio.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));
//                datosServicio.setDireccion(edt_origen.getText().toString());
//                datosServicio.setLatitud(sharedPreferences.getString(Constants.Latitud, ""));
//                datosServicio.setLongitud(sharedPreferences.getString(Constants.Longitud, ""));
//                datosServicio.setNombre(sharedPreferences.getString(Constants.customer_name, ""));
//                datosServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));
//                datosServicio.setCiudadDestino(ciudadDestino);
//                datosServicio.setCiudadOrigen(ciudadOrigen);
//
//                if(reservacion) datosServicio.setFechaReservacion(fechaReserva + " " + horaReserva);
//
//                //datosServicio.setFechaReservacion("2017-08-16 12:10");
//                datosServicio.setTipoServicio(tipoVehiculo);  //Civicar 12, CiviVan 13, CiviVan+ 14, CiviBus 15, CiviBus+ 16
//                datosServicio.setFormaPago("3");
//
//
//
//                DatosTarjetaCreditoDTO datosTarjetaCreditoDTO = new DatosTarjetaCreditoDTO();
//                datosTarjetaCreditoDTO.setId(sharedPreferences.getString(Constants.idTarjeta, ""));
//                datosServicio.setDatosTarjeta(datosTarjetaCreditoDTO);
//
//                datosServicio.setDestino(edt_destino.getText().toString() + " " + edt_indicaciones.getText().toString());
//                datosServicio.setLatitudDestino(sharedPreferences.getString(Constants.LatitudDestino, ""));
//                datosServicio.setLongitudDestino(sharedPreferences.getString(Constants.LongitudDestino, ""));
//                //datosServicio.setCostoServicio(sharedPreferences.getString(Constants.costoSeleccion, ""));
//                datosServicio.setCostoServicio(costoServicio);
//                datosServicio.setDireccionGoogle(edt_origen.getText().toString());
//                datosServicio.setOrigenSolicitud("3");


                break;

            case "02":




                datosPropietarioDTOT.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));
                datosServiciosDTOT.setPropietario(datosPropietarioDTOT);


                break;

            case "03":



                datosMovilDTOT.setIdMovil(sharedPreferences.getString(Constants.idMovil, ""));
                datosPropietarioDTOT.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));
                datosServiciosDTOT.setHorasHistorico(ultimasHoras);
                datosServiciosDTOT.setMovil(datosMovilDTOT);
                datosServiciosDTOT.setPropietario(datosPropietarioDTOT);




                break;



            case "04":



                datosMovilDTOT.setIdMovil(sharedPreferences.getString(Constants.idMovil, ""));
                datosPropietarioDTOT.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));


                DatosGeocercaDTO datosGeocercaDTO = new DatosGeocercaDTO();
                datosGeocercaDTO.setDistancia(appState.getDatosGeocercaDTO().getDistancia());
                datosGeocercaDTO.setLatitud(appState.getDatosGeocercaDTO().getLatitud());
                datosGeocercaDTO.setLongitud(appState.getDatosGeocercaDTO().getLongitud());
                datosGeocercaDTO.setNombre("Jardin");

                datosServiciosDTOT.setMovil(datosMovilDTOT);

                datosServiciosDTOT.setGeocerca(datosGeocercaDTO);




                break;


            case "05":
//                datosServicio.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));

                break;


            case "11":
//                datosServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));

                break;



            case "12":
//                datosServicio.setDireccion(edt_destino.getText().toString());

                break;

            case "13":
//                datosServicio.setLatitud(sharedPreferences.getString(Constants.Latitud, ""));
//                datosServicio.setLongitud(sharedPreferences.getString(Constants.Longitud, ""));
//                datosServicio.setLatitudDestino(sharedPreferences.getString(Constants.LatitudDestino, ""));
//                datosServicio.setLongitudDestino(sharedPreferences.getString(Constants.LongitudDestino, ""));
//                datosServicio.setDireccion(edt_origen.getText().toString());
//                datosServicio.setDestino(edt_destino.getText().toString());
//                datosServicio.setMetrosRecorridos(metrosRecorridos);
//                datosServicio.setMinutosRecorridos(minutosRecorridos);
//                datosServicio.setCiudadDestino(ciudadDestino);
//                datosServicio.setCiudadOrigen(ciudadOrigen);
//                datosServicio.setFormaPago("3");

                break;

            case "14":
                //datosServicio.setNombre(sharedPreferences.getString(Constants.customer_name, ""));
//                datosServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));
//                DatosUsuariosDTO datosUsuariosDTO2 = new DatosUsuariosDTO();
//                datosUsuariosDTO2.setCorreoElectronico(sharedPreferences.getString(Constants.customer_email, ""));
//                datosUsuariosDTO2.setNombre(sharedPreferences.getString(Constants.customer_name, ""));
////                datosUsuariosDTO2.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));
//
//                datosServicio.setUsuario(datosUsuariosDTO2);


                break;

            case "26":
//                datosServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));

                break;


        }


        Gson gson = new Gson();
        String json = gson.toJson(datosServiciosDTOT);
        sendData = new ConexionTCP(getApplicationContext());
        sendData.sendData(json);
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void Confirmacion(View v) {

        flagDesOri=0;

        if(btn_confirmar_reservar.getText().toString().equalsIgnoreCase("CONFIRMAR")){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            layout_tipo_vehiculo.setVisibility(View.GONE);
            layout_inicio.setVisibility(View.VISIBLE);
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);

            flagSolicitarServicio=1;
            SendFuncion("14");
        }else if(btn_confirmar_reservar.getText().toString().equalsIgnoreCase("RESERVAR")){
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month+1, day);
        }

    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void CalculaCostos(View v) {
        if (edt_destino.length() < 6) {
            Toast.makeText(getApplicationContext(), "Digite el destino", Toast.LENGTH_LONG).show();
        } else {
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
            SendFuncion("12");
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void CiviCar(View v) {

        tipoVehiculo = "12";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //txt_tarifa_aprox.setText("$ " + sharedPreferences.getString(Constants.precioTipo1, ""));
        txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(sharedPreferences.getString(Constants.precioTipo1, "")), true));
        txt_capacidad.setText("4 Personas");

        img_civicar.setBackgroundResource(R.drawable.circular_button_pressed);
        img_civivan.setBackgroundResource(R.drawable.circular_button);
        img_civivan_mas.setBackgroundResource(R.drawable.circular_button);
        img_civibus.setBackgroundResource(R.drawable.circular_button);
        img_civibus_mas.setBackgroundResource(R.drawable.circular_button);

        costoServicio= sharedPreferences.getString(Constants.precioTipo1, "");

    }

    public void CiviVan(View v) {

        tipoVehiculo = "13";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //txt_tarifa_aprox.setText("$ " + sharedPreferences.getString(Constants.precioTipo2, ""));
        txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(sharedPreferences.getString(Constants.precioTipo2, "")), true));
        txt_capacidad.setText("8 Personas");
        img_civicar.setBackgroundResource(R.drawable.circular_button);
        img_civivan.setBackgroundResource(R.drawable.circular_button_pressed);
        img_civivan_mas.setBackgroundResource(R.drawable.circular_button);
        img_civibus.setBackgroundResource(R.drawable.circular_button);
        img_civibus_mas.setBackgroundResource(R.drawable.circular_button);

        costoServicio= sharedPreferences.getString(Constants.precioTipo2, "");

    }

    public void CiviVanMas(View v) {

        tipoVehiculo = "14";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //txt_tarifa_aprox.setText("$ " + sharedPreferences.getString(Constants.precioTipo3, ""));
        txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(sharedPreferences.getString(Constants.precioTipo3, "")), true));
        txt_capacidad.setText("19 Personas");
        img_civicar.setBackgroundResource(R.drawable.circular_button);
        img_civivan.setBackgroundResource(R.drawable.circular_button);
        img_civivan_mas.setBackgroundResource(R.drawable.circular_button_pressed);
        img_civibus.setBackgroundResource(R.drawable.circular_button);
        img_civibus_mas.setBackgroundResource(R.drawable.circular_button);

        costoServicio= sharedPreferences.getString(Constants.precioTipo3, "");

    }

    public void CiviBus(View v) {

        tipoVehiculo = "15";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //txt_tarifa_aprox.setText("$ " + sharedPreferences.getString(Constants.precioTipo4, ""));
        txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(sharedPreferences.getString(Constants.precioTipo4, "")), true));
        txt_capacidad.setText("28 Personas");
        img_civicar.setBackgroundResource(R.drawable.circular_button);
        img_civivan.setBackgroundResource(R.drawable.circular_button);
        img_civivan_mas.setBackgroundResource(R.drawable.circular_button);
        img_civibus.setBackgroundResource(R.drawable.circular_button_pressed);
        img_civibus_mas.setBackgroundResource(R.drawable.circular_button);

        costoServicio= sharedPreferences.getString(Constants.precioTipo4, "");

    }

    public void CiviBusMas(View v) {

        tipoVehiculo = "16";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //txt_tarifa_aprox.setText("$ " + sharedPreferences.getString(Constants.precioTipo5, ""));
        txt_tarifa_aprox.setText(redondeaSinDecimal(Double.parseDouble(sharedPreferences.getString(Constants.precioTipo5, "")), true));
        txt_capacidad.setText("40 Personas");
        img_civicar.setBackgroundResource(R.drawable.circular_button);
        img_civivan.setBackgroundResource(R.drawable.circular_button);
        img_civivan_mas.setBackgroundResource(R.drawable.circular_button);
        img_civibus.setBackgroundResource(R.drawable.circular_button);
        img_civibus_mas.setBackgroundResource(R.drawable.circular_button_pressed);

        costoServicio= sharedPreferences.getString(Constants.precioTipo5, "");

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

    /*============================================================================================*/
    /*============================================================================================*/
    public void EstadoPantalla(String estado) {
        switch (estado) {
            case "conservicio":
                txt_title_bar.setText("ASIGNADO");

                txt_destino.setText(appState.getListServicios().get(0).getDestino());
                txt_fecha.setText(appState.getListServicios().get(0).getFechaReservacion());
                txt_nombre_conductor.setText(appState.getDatosConductor().getNombreConductor());
                txt_datos_vehiculo.setText(appState.getDatosConductor().getMarca() + "-" + appState.getDatosConductor().getPlaca() + "-" + appState.getDatosConductor().getModelo());
//                imgen_central.setVisibility(View.GONE);
//                layout_origen_destino.setVisibility(View.GONE);
//                layout_inicio.setVisibility(View.GONE);
//                layout_tipo_vehiculo.setVisibility(View.GONE);
//                layout_con_servicio.setVisibility(View.VISIBLE);
//                layout_con_servicio2.setVisibility(View.VISIBLE);
//                layout_comunicacion_vehiculo.setVisibility(View.VISIBLE);
                MarkerUsuCondu();
                break;
            case "movilalfrente":
                txt_title_bar.setText("TU MOVIL HA LLEGADO");
                txt_destino.setText(appState.getListServicios().get(0).getDestino());
                txt_fecha.setText(appState.getListServicios().get(0).getFechaReservacion());
                txt_nombre_conductor.setText(appState.getDatosConductor().getNombreConductor());
                txt_datos_vehiculo.setText(appState.getDatosConductor().getMarca() + "-" + appState.getDatosConductor().getPlaca() + "-" + appState.getDatosConductor().getModelo());
//                imgen_central.setVisibility(View.GONE);
//                layout_origen_destino.setVisibility(View.GONE);
//                layout_inicio.setVisibility(View.GONE);
//                layout_tipo_vehiculo.setVisibility(View.GONE);
//                layout_con_servicio.setVisibility(View.VISIBLE);
//                layout_con_servicio2.setVisibility(View.GONE);
//                layout_comunicacion_vehiculo.setVisibility(View.VISIBLE);


                alfrenteflag ++;
                if(alfrenteflag == 1){
                    alfrenteflag = 10;
                    MuestraAlertMensajeCustom("El vehículo se encuentra Alfrente.");

                    Intent talk = new Intent();
                    talk.putExtra("CMD", "TextoVoz");
                    talk.putExtra("DATA", "El vehículo se encuentra Alfrente");
                    talk.setAction(ACTION_STRING_SERVICE);
                    sendBroadcast(talk);

                }


                MarkerUsuCondu();
                break;
            case "abordo":
                mMap.clear();
//                imgen_central.setVisibility(View.VISIBLE);


                try {
                    flagDesOri=0;
                    markerDestino=false;
                    mMap.clear();
                    INICIO = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 14));
                    edt_destino.setText("");
                    Log.e(TAG, " SE CUADRA EL ZOOM   ");
//                    VehiculosCerca(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());

                } catch (Exception e) {
                    e.printStackTrace();
                    CentrarPunto();
                }


                txt_title_bar.setText("VIAJE HA INICIADO");
                txt_destino.setText(appState.getListServicios().get(0).getDestino());
                txt_fecha.setText(appState.getListServicios().get(0).getFechaReservacion());
                txt_nombre_conductor.setText(appState.getDatosConductor().getNombreConductor());
                txt_datos_vehiculo.setText(appState.getDatosConductor().getMarca() + "-" + appState.getDatosConductor().getPlaca() + "-" + appState.getDatosConductor().getModelo());
//                imgen_central.setVisibility(View.GONE);
//                layout_origen_destino.setVisibility(View.GONE);
//                layout_inicio.setVisibility(View.GONE);
//                layout_tipo_vehiculo.setVisibility(View.GONE);
//                layout_con_servicio.setVisibility(View.VISIBLE);
//                layout_con_servicio2.setVisibility(View.GONE);
//                layout_comunicacion_vehiculo.setVisibility(View.GONE);

                break;
            case "sinservicio":
                txt_title_bar.setText("ARAMA");


                Intent services = new Intent();
                services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                services.setClass(getApplicationContext(), CalificarActivity.class);
                getApplicationContext().startActivity(services);

//                imgen_central.setVisibility(View.VISIBLE);
//                layout_origen_destino.setVisibility(View.VISIBLE);
//                layout_inicio.setVisibility(View.VISIBLE);
//                layout_tipo_vehiculo.setVisibility(View.GONE);
//                layout_con_servicio.setVisibility(View.GONE);
//                layout_con_servicio2.setVisibility(View.GONE);
//                layout_comunicacion_vehiculo.setVisibility(View.GONE);
//                edt_destino.setText("");
                break;
        }
    }
    /*============================================================================================*/
    /*============================================================================================*/
    public void MarkerUsuCondu() {

        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            double latitud = Double.parseDouble(sharedPreferences.getString(Constants.Latitud, ""));
            double longitud = Double.parseDouble(sharedPreferences.getString(Constants.Longitud, ""));
            double lat = Double.parseDouble(appState.getDatosConductor().getLatitud());
            double lon = Double.parseDouble(appState.getDatosConductor().getLongitud());


            LatLng VEHICULO = new LatLng(lat, lon);
            //LatLng USUARIO = new LatLng(lat, lon);


            try {
                LatLng USUARIO = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                Log.i(TAG, "Limpiando el mapa, para colocar los marcadores, y centrar la camara  " + USUARIO + "  " + VEHICULO);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(USUARIO)
                        .title("Usuario")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));

                mMap.addMarker(new MarkerOptions()
                        .position(VEHICULO)
                        .title("Yo")
                        //.snippet(driverData.get(Constants.driveVehiclePlaue) + ", " + driverData.get(Constants.driveFullName))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(VEHICULO);
                builder.include(VEHICULO);
                LatLngBounds bounds = builder.build();


                LatLngBounds bounds2 = new LatLngBounds.Builder()
                        .include(USUARIO)
                        .include(VEHICULO).build();
                Point displaySize2 = new Point();
                getWindowManager().getDefaultDisplay().getSize(displaySize2);
                //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds2, displaySize2.x, 450, 30));


                if (txt_title_bar.getText().toString().equalsIgnoreCase("ASIGNADO")) {
                    String locationOrigen = lat + "," + lon;
                    String locationDestino = latitud + "," + longitud;
                    MainActivity.CalculaDistanciaTiempo calculaDistancia = new MainActivity.CalculaDistanciaTiempo();
                    calculaDistancia.execute(locationOrigen, locationDestino, "2");
                }

                //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                //   mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 350));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();


    }

    /*============================================================================================*/
    /*============================================================================================*/
    public class CalculaDistanciaTiempo extends AsyncTask<String, Void, String> {
        JSONObject response;
        String tipoConsulta;

        @Override
        protected String doInBackground(String... params) {
            String locationOrigen = params[0];
            String locationDestino = params[1];
            String tipo = params[2];

            Log.d(TAG, "*** doInBackgroundCalculaTarifa ** Origen :" + locationOrigen + " Destino: " +locationDestino);
            String OK = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + locationOrigen + "&destinations=" + locationDestino + "&key=AIzaSyB0rZHS3nqpb2smPoRpOgze_dE_1oibFA4";
            //"https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=\(a_coordinate_string)&destinations=\(b_coordinate_string)&key=AIzaSyB0rZHS3nqpb2smPoRpOgze_dE_1oibFA4"

            Log.d(TAG, OK);

            tipoConsulta = tipo;
            String pametros = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost post = post = new HttpPost(OK);
                post.setHeader("Content-Type", "application/json");
                StringEntity entity = new StringEntity(pametros);
                post.setEntity(entity);

                HttpResponse resp = httpClient.execute(post);
                String responseString = EntityUtils.toString(resp.getEntity());
                Log.i(TAG, "--------------------------responseString = " + responseString);
                response = new JSONObject(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String rta) {
            Log.i(TAG, "---------------------ONPOS DEL WEBSERVICE CALCULA ------------------   " + response);
            try {
                if ("OK".equalsIgnoreCase(response.getString("status"))) {
                    JSONArray jsonArray = response.getJSONArray("rows");

                    JSONObject jsonElements = jsonArray.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
                    String statusElements = jsonElements.getString("status");

                    if ("OK".equalsIgnoreCase(statusElements)) {
                        String Timer = jsonElements.getJSONObject("duration").getString("text");
                        if (Timer != null) {
                            Log.i(TAG, "--------------------EL TIEMPO ------------------   " + Timer);
                            // txt_tiempo.setText("Tiempo: " +  Timer);

                        }
                        String Timer2 = jsonElements.getJSONObject("duration").getString("value");
                        if (Timer2 != null) {
                            Log.i(TAG, "--------------------EL TIEMPO2 ------------------   " + Timer2);

                        }
                        String Distance = jsonElements.getJSONObject("distance").getString("text");
                        if (Distance != null) {
                            Log.i(TAG, "--------------------LA DISTANCIA ------------------   " + Distance);
                            txt_distancia.setText("Distancia: " + Distance);

                        }
                        String Distance2 = jsonElements.getJSONObject("distance").getString("value");
                        if (Distance2 != null) {
                            Log.i(TAG, "--------------------LA DISTANCIA2 ------------------   " + Distance2);

                        }

//                        if(flagtarifica==1){
//                            flagtarifica=0;
//                            metrosRecorridos= Distance2;
//                            minutosRecorridos = Timer2;
//                        }
//
//
//                        if(tipoConsulta.equals("1")){
//                            SendFuncion("13");
//                        }else{
//                            txt_tiempo_llegada.setText(Timer + " " + Distance);
//                        }

                    }
                }else{



                }
            } catch (JSONException e) {
                e.printStackTrace();


            }catch (Exception e){
                e.printStackTrace();


            }

        }
    }
    /*============================================================================================*/
    /*============================================================================================*/
    public void MensajeUsuario(View v) {
        Log.d(TAG, "------------mensaje mensajr");
//        Intent services = new Intent();
//        services.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        services.setClass(getApplicationContext(), MensajeActivity.class);
//        getApplicationContext().startActivity(services);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("MENSAJE A CONDUCTOR");
        alertDialog.setMessage("Ingrese el mensaje.");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("Enviar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String mensaje = input.getText().toString();

                        if(mensaje.length() < 2 ){
                            Toast.makeText(getApplicationContext(), "Por favor digite el mensaje", Toast.LENGTH_LONG).show();
                        }else{
                            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            DatosServiciosDTO datosServicio = new DatosServiciosDTO();
                            datosServicio.setFuncion("03");
                            datosServicio.setTipoProyecto("5");
                            datosServicio.setMensajeSaliente(mensaje);
                            datosServicio.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));


                            Gson gson = new Gson();
                            String json = gson.toJson(datosServicio);
                            sendData = new ConexionTCP(getApplicationContext());
                            sendData.sendData(json);
                        }


                    }
                });

        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void LlamarUsuario(View v) {
        try {
            Log.d(TAG, "------------llama conductor" + appState.getDatosConductor().getCelConductor());
            callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+appState.getDatosConductor().getCelConductor()));
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else {
                startActivity(callIntent);
            }
        } catch (Exception e) {
            MuestraAlertMensaje("Respuesta Servicio", "No fue posible realizar la LLamada...");
            e.printStackTrace();
        }
    }

    public void CancelarServicio(View v) {

        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Desea cancelar el servicio?");
            alertBuilder.setMessage("");
            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
                    SendFuncion("05");
                }
            });
            alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
    public void PedirYa(View v) {
        if (edt_destino.length() < 6) {
            Toast.makeText(getApplicationContext(), "Digite el destino", Toast.LENGTH_LONG).show();
        } else {
            layout_pedirya.setVisibility(View.VISIBLE);
            layout_reserva.setVisibility(View.GONE);
            reservacion = false;
            btn_confirmar_reservar.setText("CONFIRMAR");
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
            //SendFuncion("12");


            getLocationFromAddress(direccionCalcula);

            //SendFuncion("13");
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/

    public void Ubicar(View v){

        try {
            flagDesOri = 0;
            edt_destino.setText("");
            LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void AddDestino(View v){

        if(setadd==0) {
            img_btn_add_destino.setBackgroundResource(R.drawable.menos_destino);
            layout_edt_indicaciones.setVisibility(View.VISIBLE);
            setadd=1;
        } else {
            img_btn_add_destino.setBackgroundResource(R.drawable.add_destino);
            layout_edt_indicaciones.setVisibility(View.GONE);
            setadd=0;
        }
    }

    private static final int REQUEST_CALL = 1;

    public void Reservar(View v) {

        if (edt_destino.length() < 6) {
            Toast.makeText(getApplicationContext(), "Digite el destino", Toast.LENGTH_LONG).show();
        } else {
//            calendar = Calendar.getInstance();
//            year = calendar.get(Calendar.YEAR);
//
//            month = calendar.get(Calendar.MONTH);
//            day = calendar.get(Calendar.DAY_OF_MONTH);
//            showDate(year, month+1, day);

            layout_pedirya.setVisibility(View.GONE);
            layout_reserva.setVisibility(View.VISIBLE);
            reservacion = true;
            btn_confirmar_reservar.setText("RESERVAR");
            progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);
            //SendFuncion("12");
            getLocationFromAddress(direccionCalcula);
        }



//        try{
//
//
//
//            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                    .setBoundsBias(new LatLngBounds(
//                            new LatLng(4.473276, -74.175409),
//                            new LatLng(4.818605, -74.023325)))
//                    .build(this);
//
//
//
//
//        //Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
//
//        startActivityForResult(intent, 1);
//        } catch (GooglePlayServicesRepairableException e) {
//            // TODO: Handle the error.
//        } catch (GooglePlayServicesNotAvailableException e) {
//            // TODO: Handle the error.
//        }
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void BuscaDestino(View v){
        try{
            //CentrarPunto();
            EstadoPantalla("sinservicio");
            flagDesOri=1;
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(new LatLngBounds(
                            new LatLng(4.473276, -74.175409),
                            new LatLng(4.818605, -74.023325)))
                    .build(this);
        //Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }



//        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(this)
//                //.setHeaderImage(R.drawable.dialog_header)
//                .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
//                    @Override
//                    public void locationName(String locationName) {
//                        edt_destino.setText(locationName);
//                    }
//                })
//                .build();
//        placeSearchDialog.show();
    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void BuscaOrigen(View v){
        try{
            EstadoPantalla("sinservicio");
            flagDesOri=0;
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(new LatLngBounds(
                            new LatLng(4.473276, -74.175409),
                            new LatLng(4.818605, -74.023325)))
                    .build(this);
            //Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if(flagDesOri == 1){

                    // retrive the data by using getPlace() method.

                    Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                    String direccion = ""+place.getName();
                    edt_destino.setText(place.getName());
                    edt_destino.setSelection(edt_destino.getText().toString().length());
                    markerDestino = true;
                    //getLocationFromAddressFirst(direccion);
                    direccionCalcula = direccion;
                    Log.e(TAG, getString(R.string.place_details, place.getName(), place.getId(), place.getAddress(), place.getPhoneNumber(),
                            place.getWebsiteUri(), place.getLatLng().latitude,  place.getLatLng().longitude));

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.LatitudDestino, ""+place.getLatLng().latitude);
                    editor.putString(Constants.LongitudDestino, ""+place.getLatLng().longitude);
                    editor.commit();


                    try {
                        flagDesOri=1;
                        mMap.clear();
                        INICIO = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 16));
                    } catch (Exception e) {
                        e.printStackTrace();

                    }



//                    mMap.clear();
//                    double latOrigen = mMap.getMyLocation().getLatitude();
//                    double lonOrigen = mMap.getMyLocation().getLongitude();
//                    double latDestino = Double.parseDouble(String.valueOf(place.getLatLng().latitude));
//                    double lonDestino = Double.parseDouble(String.valueOf(place.getLatLng().longitude));
//
//                    LatLng origin = new LatLng(latOrigen, lonOrigen);
//                    LatLng destination = new LatLng(latDestino, lonDestino);
//
//
//                    DrawRouteMaps.getInstance(this).draw(origin, destination, mMap);
//                    DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.user, "Origin Location");
//                    DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.user1, "Destination Location");
//
//                    LatLngBounds bounds = new LatLngBounds.Builder()
//                            .include(origin)
//                            .include(destination).build();
//                    Point displaySize = new Point();
//                    getWindowManager().getDefaultDisplay().getSize(displaySize);
//                    //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 350, 30));



                }else{

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.Latitud, ""+place.getLatLng().latitude);
                    editor.putString(Constants.Longitud, ""+place.getLatLng().longitude);
                    editor.commit();

                    try {
                        edt_origen.setText(place.getName());
                        edt_origen.setSelection(edt_destino.getText().toString().length());

                        flagDesOri=0;
                        mMap.clear();
                        INICIO = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 16));
                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public Barcode.GeoPoint getLocationFromAddress(String strAddress){

        Log.i(TAG, "=================Address: " + strAddress);


        if(strAddress.contains("Bogotá, Colombia")){

            Log.i(TAG, "=================YA LO TRAE: " );

        }else{

            strAddress = strAddress + ",Bogotá, Colombia";
            Log.i(TAG, "================NO LA TRAE " + strAddress );
        }


        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            Log.i(TAG, "--------- Lat    "  + location.getLatitude());
            Log.i(TAG, "--------- Lat    "  + location.getLongitude());

            flagtarifica=1;
            //String locationOrigen = mMap.getMyLocation().getLatitude() + "," + mMap.getMyLocation().getLongitude();


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String locationOrigen = sharedPreferences.getString(Constants.Latitud, "") + "," + sharedPreferences.getString(Constants.Longitud, "");



            String locationDestino = sharedPreferences.getString(Constants.LatitudDestino, "") + "," + sharedPreferences.getString(Constants.LongitudDestino, "");
            MainActivity.CalculaDistanciaTiempo calculaDistancia = new MainActivity.CalculaDistanciaTiempo();
            calculaDistancia.execute(locationOrigen, locationDestino, "1");




//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(Constants.LatitudDestino, ""+location.getLatitude());
//            editor.putString(Constants.LongitudDestino, ""+location.getLongitude());
//            editor.commit();



//            try {
//                flagDesOri=1;
//                mMap.clear();
//                INICIO = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 18));
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }


            p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        }catch (Exception e){
            return null;
        }
    }

    public Barcode.GeoPoint getLocationFromAddressFirst(String strAddress){

        Log.i(TAG, "=================Address: " + strAddress);

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            Log.i(TAG, "--------- Lat    "  + location.getLatitude());
            Log.i(TAG, "--------- Lat    "  + location.getLongitude());

            flagtarifica=1;
//            String locationOrigen = mMap.getMyLocation().getLatitude() + "," + mMap.getMyLocation().getLongitude();
//            String locationDestino = location.getLatitude() + "," + location.getLongitude();
//            MainActivity.CalculaDistanciaTiempo calculaDistancia = new MainActivity.CalculaDistanciaTiempo();
//            calculaDistancia.execute(locationOrigen, locationDestino);



            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.LatitudDestino, ""+location.getLatitude());
            editor.putString(Constants.LongitudDestino, ""+location.getLongitude());
            editor.commit();


            try {
                flagDesOri=1;
                mMap.clear();
                INICIO = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INICIO, 18));
            } catch (Exception e) {
                e.printStackTrace();

            }


            p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        }catch (Exception e){
            return null;
        }
    }

    /*============================================================================================*/
    /*============================================================================================*/

    private void showDate(int year, int month, int day) {
        Log.i(TAG, "============" + new StringBuilder().append(day).append("/").append(month).append("/").append(year));

        TimePicker mTimePicker = new TimePicker();
        mTimePicker.show(getFragmentManager(), "Select time");

    }

    /*============================================================================================*/
    /*============================================================================================*/
     public  class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            String minutos;
            if(minute <10) minutos = "0"+minute;
            else minutos = ""+minute;
            Log.i(TAG, "============Selected Time: " + String.valueOf(hourOfDay) + ":" + minutos);

            horaReserva = String.valueOf(hourOfDay) + ":" + minutos;

            DatePickerFragment mDatePicker = new DatePickerFragment();
            mDatePicker.show(getFragmentManager(), "Select date");

        }
    }
    /*============================================================================================*/
    /*============================================================================================*/
    public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH) + 1;
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //"2017-08-16 12:10"
            String mes;
            String dia;

            month +=1;
            if(month <10) mes = "0"+month;
            else mes = ""+month;
            if(day <10) dia = "0"+day;
            else dia = ""+day;

            fechaReserva = String.valueOf(year) + "-" + mes + "-" + dia;
            Log.i(TAG, "============Selected date: " + String.valueOf(year) + "-" + mes + "-" + dia);

            SolicitaReserva();

        }
    }

    public  void SolicitaReserva(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        layout_tipo_vehiculo.setVisibility(View.GONE);
        layout_inicio.setVisibility(View.VISIBLE);
        progress_loading = ProgressDialog.show(MainActivity.this, "Espere un momento ...", "Esperando Respuesta ...", true, true);

        flagSolicitarServicio=1;
        SendFuncion("14");

    }


    /***********************************************************************************************************************/
    /***********************************************************************************************************************/
    /***********************************************************************************************************************/
    /***********************************************************************************************************************/
    /***********************************************************************************************************************/

    public void Geocerca(View v){


        Intent timer = new Intent(this, TimerApp.class);
        stopService(timer);

        Geocerca(100);
        layout_botonesAppet.setVisibility(View.GONE);
        layout_geocerca.setVisibility(View.VISIBLE);
        btn_establecer_geocerca.setVisibility(View.VISIBLE);

    }

    public void Historico(View v){


        layout_historico.setVisibility(View.VISIBLE);
        layout_botonesAppet.setVisibility(View.GONE);
        layout_geocerca.setVisibility(View.GONE);



        //MuestraAlertMensajeCustom("Salida de geocerca");



        final ArrayList<String> mensajes = new ArrayList<String>();
        mensajes.add("1 Hora");
        mensajes.add("2 Horas");
        mensajes.add("3 Horas");
        mensajes.get(2);
        //Aca se muestra la lista
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.list);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Ver las ultimas:");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        int contadorDir = 0;
        while (contadorDir < 3) {
            arrayAdapter.add(mensajes.get(contadorDir));
            contadorDir++;
        }
        builderSingle.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which1) {
                        switch (which1) {
                            case 0:
                                ultimasHoras = "1";
                                SendFuncion("03");
                                break;
                            case 1:
                                ultimasHoras = "2";
                                SendFuncion("03");
                                break;
                            case 2:
                                ultimasHoras = "3";
                                SendFuncion("03");
                                break;
                        }
                    }
                });
        builderSingle.show();


    }

    public void EstableceGeocerca(View v){

        Intent timer = new Intent(this, TimerApp.class);
        startService(timer);

        layout_botonesAppet.setVisibility(View.VISIBLE);
        layout_geocerca.setVisibility(View.GONE);
        btn_establecer_geocerca.setVisibility(View.GONE);

        SendFuncion("04");
    }

    public void EndHistorico(View v){
        layout_historico.setVisibility(View.GONE);
        layout_botonesAppet.setVisibility(View.VISIBLE);
        layout_geocerca.setVisibility(View.GONE);
        timerHistorico.cancel();
        mMap.clear();

        Intent timer = new Intent(this, TimerApp.class);
        startService(timer);

    }

    /*============================================================================================*/
    /*============================================================================================*/
    public void Geocerca(double radio){

        try {
            mMap.clear();
            radioGeocerca = String.valueOf(radio);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 16));
            MarkerOptions options = new MarkerOptions();


            DatosGeocercaDTO datosGeocercaDTO = new DatosGeocercaDTO();

            appState.setDatosGeocercaDTO(datosGeocercaDTO);

            appState.getDatosGeocercaDTO().setLatitud(String.valueOf(mMap.getMyLocation().getLatitude()));
            appState.getDatosGeocercaDTO().setLongitud(String.valueOf(mMap.getMyLocation().getLongitude()));
            appState.getDatosGeocercaDTO().setDistancia(radioGeocerca);


            // Setting the position of the marker
            options.position(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
            //googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
            drawMarkerWithCircle2(latLng, radio);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    float[] distance = new float[2];

                        /*
                        Location.distanceBetween( mMarker.getPosition().latitude, mMarker.getPosition().longitude,
                                mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
                                */

                    Location.distanceBetween( location.getLatitude(), location.getLongitude(),
                            mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);

                    float radius =  Float.parseFloat(mCircle.getRadius()+"");
                    float distanceInMeter = Float.parseFloat(distance[0]+"");
                    if( distanceInMeter > radius )
                    {
                        //Toast.makeText(getBaseContext(), "You are Outside of the circle, Distance from center: " + distance[0] + " Radius: " + mCircle.getRadius(), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getBaseContext(), "Your are Inside the circle, Distance from center: " + distance[0] + " Radius: " + mCircle.getRadius() , Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void drawMarkerWithCircle2(LatLng position, double radio) {
        double radiusInMeters = radio;  // increase decrease this distancce as per your requirements
        int strokeColor = 0xff00BC47; //green outline
        int shadeColor = 0x4400BC47; //opaque green fill

        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);

        mMarker = mMap.addMarker(markerOptions);
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.huella_verde));
    }

    /***********************************************************************************************************************/
    /***********************************************************************************************************************/
    /***********************************************************************************************************************/

    private void ProcessMueveRuta(LatLng latLng){


//        Inicio = new LatLng(latitudRutaSave, longitudRutaSave);
//        MoverAppet(appetMarker,Inicio, latLng);
//        latitudRutaSave = latLng.latitude;
//        longitudRutaSave = latLng.longitude;
//
//        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(Inicio);
//        builder.include(latLng);
//        LatLngBounds bounds = builder.build();
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500));


        Log.i(TAG, "=========Antes=======  "   +  contadorHistorico);
        Log.i(TAG, "=========Antes=======  "   +  appState.getListaTracking().size());
        while (contadorHistorico < appState.getListaTracking().size()){

            Log.i(TAG, "================  "   +  contadorHistorico);

            double latitudInicio = Double.parseDouble(appState.getListaTracking().get(contadorHistorico).getLatitud());
            double longitudInicio = Double.parseDouble(appState.getListaTracking().get(contadorHistorico).getLongitud());

            double latitudFinal = Double.parseDouble(appState.getListaTracking().get(contadorHistorico+1).getLatitud());
            double longitudFinal = Double.parseDouble(appState.getListaTracking().get(contadorHistorico+1).getLongitud());


            Inicio = new LatLng(latitudInicio, longitudInicio);
            Final = new LatLng(latitudFinal, longitudFinal);
            MoverAppet(appetMarker,Inicio, Final);


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(Inicio);
            builder.include(Final);
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500));


            contadorHistorico++;
        }


    }

    private void MoverAppet(final Marker myMarker, LatLng startLatLng, LatLng finalLatLng) {

        final LatLng startPosition = new LatLng(startLatLng.latitude, startLatLng.longitude);
        final LatLng finalPosition = new LatLng(finalLatLng.latitude, finalLatLng.longitude);
        //final Handler handler = new Handler(getContext().getMainLooper());
        final Handler handler = new Handler();

        final long start = System.currentTimeMillis();

        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 9000;
        final boolean hideMarker = false;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = System.currentTimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                        startPosition.longitude * (1 - t) + finalPosition.longitude * t);

                myMarker.setPosition(currentPosition);

                // Repeat till progress is complete.
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        myMarker.setVisible(false);
                    } else {
                        myMarker.setVisible(true);
                    }
                }
            }
        });
    }



    private class MuestraAppet extends TimerTask {
        public void run() {
            Log.i(TAG, "================  "   +  contadorHistorico);
            //mMap.clear();
            // Get a handler that can be used to post to the main thread
            Handler mainHandler = new Handler(context.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    if(contadorHistorico < appState.getListaTracking().size()) {

                        double latitud = Double.parseDouble(appState.getListaTracking().get(contadorHistorico).getLatitud());
                        double longitud = Double.parseDouble(appState.getListaTracking().get(contadorHistorico).getLongitud());

                        LatLng sydney = new LatLng(latitud, longitud);
                        mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromResource(R.drawable.huella_verde)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));

                    }
                    contadorHistorico++;

                    if(contadorHistorico > appState.getListaTracking().size()){
                        Log.i(TAG, "========Se debe cancelar el timer========  "   +  contadorHistorico);
                        timerHistorico.cancel();

                        MuestraAlertMensajeCustom("Fin del recorrido");
                    }

                }
            };
            mainHandler.post(myRunnable);

        }
    }


}
