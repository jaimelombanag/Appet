package com.fasepi.android.appet.usuario.Servicios;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.UnidadDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class TimerSocket extends Service implements Runnable {

	private static final String TAG = "CivicarUsuario";
	private final String ACTION_STRING_ACTIVITY = "ToActivity";
	private static final String MODULO = "TmerServiceSocket";
	protected String module = Constants.COM;
	private Globales appState;
	private final IBinder mBinder = new LocalBinder();
	private Selector selector = null;
	private static SocketChannel channel = null;
	private int port;
	private String host;
	private ByteBuffer inBuffer = null;
	private ByteBuffer outBuffer = null;
	private long LastRX; 
	private boolean SocketConnected= false;
	private int reconexionflag;
	private int flagenvio;
	private Timer timer;
	private Timer timer2 = new Timer();
	private int contadorSend;
	private int contadorTmeout;
	private TimerTask verifyTask;
	private Thread hilo;
	private TelephonyManager tm;
	private Context context;
	private boolean initialized = false;
	private StringBuffer stringBuffer = null;
	private static final int INBUFFERSIZE = 32768;
	private static final int OUTBUFFERSIZE = 2048;
	private static final int STRINGBUFFERSIZE = 32768;
	private String mensajeMovil = "nada";
	

	
	/*****************************************************************************************/
	/*      					         BROADCAST  										 */
	/*****************************************************************************************/
	
	public final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Integer cmd = intent.getIntExtra("CMD", 0);
			switch(cmd){
				case(Constants.SEND):	
					String data = intent.getStringExtra("DATA");
					try {

					} catch (Exception e) {
						e.printStackTrace();
					}				
					break;
				case(Constants.DISCONECT):									
					onDestroy();					
					break;
			}
		}
	};
	
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	
	public class LocalBinder extends Binder {
        public TimerSocket getService() {
            return TimerSocket.this;
        }
    } 
	
	private class mainTask extends TimerTask {
        public void run() {
        	if(flagenvio==1){
        		Temporizador(); 
        	}
        }
    }  
	
	private void Temporizador() {
		contadorTmeout++;
		contadorSend++;
		//Log.i(TAG, MODULO +" Este es el Contador: " +  contadorSend);
		if(contadorSend>20){
			try {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				DatosServiciosDTO nuevoServicio = new DatosServiciosDTO();
				nuevoServicio.setFuncion("02");
				nuevoServicio.setTipoProyecto(Constants.TipoProyecto);
				nuevoServicio.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));
				nuevoServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));

				Gson gson = new Gson();
				String json = gson.toJson(nuevoServicio);
	    		writeToChannel(json+"\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			contadorSend=0;
		}
	}
	
	/*****************************************************************************************/
	/*      					         RUN DEL SERVICIO									 */
	/*****************************************************************************************/
	@Override
	public void run() {
		try {
			Log.i(TAG, ": RUN");
			selector = Selector.open();
			port =  Constants.PuertoServer;			
			host = Constants.IpServer;
			
			Log.i(TAG, ": OPEN CHANNEL");
			channel = SocketChannel.open(new InetSocketAddress(host, port));
			channel.configureBlocking(false);
			Log.i(TAG, ": REGISTER CHANNEL");
			//BUGZASO ISSUEITO
			channel.register(selector, SelectionKey.OP_READ);
			LastRX=new Date().getTime();
			SocketConnected=true;
	
			reconexionflag=0;	
			flagenvio=1;
			timer2.scheduleAtFixedRate(new mainTask(), 0, 1000);
			contadorSend=10;	
			/************************************************************/
			while (true) {
				Log.i(TAG,": CONNECTION EVENT" );
				selector.select(60000);
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey selKey = it.next();
					it.remove();
					if (selKey.isValid() && selKey.isReadable()) {
						SocketChannel channel = (SocketChannel) selKey.channel();
						readFromChannel(channel);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, ": "+e.getMessage());
			stopChannel();
		}
		
	}
	/*****************************************************************************************/
	/*      					ESTADO DE LA CONEXION  										 */
	/*****************************************************************************************/
	private void verifyCommunicationStatus() {
		try {
			//LastRX= 1400291707745;
			long now = new Date().getTime();
			long rxAge = now - LastRX;
			boolean reset = rxAge > 60000;
			Log.d(TAG, MODULO + ": VALIDCONN "  + " CHANNEL " + SocketConnected + " RESET " + reset + " RXAGE " + rxAge+ " ms");
			if (SocketConnected && reset ) {
				stopChannel();
				return;
			}
		} catch (Exception e) {
			Log.e(TAG, MODULO+": VERIFYSTATE ERROR " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*****************************************************************************************/
	/*      					SE INICIA EL SOCKET   										 */
	/*****************************************************************************************/
	private void startChannel() {
		try {
			Log.i(TAG, ": CONNECTING...");
			if (verifyTask != null) {
				verifyTask.cancel();
			}
			verifyTask = new TimerTask() {
				@Override
				public void run() {
					verifyCommunicationStatus();
				}
			};
			outBuffer.clear();
			inBuffer.clear();
			hilo = new Thread(this);
			hilo.setName(MODULO);
			hilo.start();
		} catch (Exception e) {
			Log.e(TAG, MODULO + ": STARTCHANNEL " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*****************************************************************************************/
	/*      					SE PARA EL SOCKET   										 */
	/*****************************************************************************************/
	private void stopChannel() {
		TimerTask startTask = new TimerTask() {
			@Override
			public void run() {
				startChannel();
			}
		};
		try {
			Log.w(TAG, ": DISCONNECTED!!!");
			if (hilo != null)
				hilo.interrupt();
			try {
				if (channel != null)
					channel.close();
			} catch (IOException e) {
				Log.e(TAG, ": "+e.getMessage());
				e.printStackTrace();
			}
			hilo = null;
			channel = null;
			outBuffer.clear();
			inBuffer.clear();
			SocketConnected=false;
		
			timer.schedule(startTask, 10000);	
			reconexionflag++;
			Log.w(TAG, ": reconexionflag:  " + reconexionflag);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG,": "+e.getMessage());
		}
	}
	/*****************************************************************************************/
	/*      					SE ESCRIBE EN EL SOCKET 									 */
	/*****************************************************************************************/
	private boolean writeToChannel(String data) {
		try {
			if(SocketConnected){
				//channel.register(selector, SelectionKey.OP_WRITE);
				outBuffer.put(data.getBytes());
				outBuffer.flip();
				int written = channel.write(outBuffer);
				outBuffer.clear();
				String msg = module+": "+written+" BYTES WRITTEN TO CHANNEL. '"+data+"'";
				Log.i(TAG,MODULO +  msg);
				return(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, ": "+e.getMessage());
			stopChannel();
		}
		return (false);
	}
	
	/*****************************************************************************************/
	/*      					SE LEE LO QUE LLEGA 										 */
	/*****************************************************************************************/
	private void readFromChannel(SocketChannel channel) throws IOException {
		inBuffer.clear();
		int numBytesRead = channel.read(inBuffer);
		if (numBytesRead < 0) {
			throw new IOException();
		} else {
			inBuffer.flip();
			byte[] data = new byte[inBuffer.remaining()];
            inBuffer.get(data, 0, data.length);
            LastRX= new Date().getTime();
            String dataString = new String(data).trim();
            String msg = module+": "+dataString.length()+" BYTES READ FROM CHANNEL. '"+dataString+"'";
			Log.i(TAG, "------  "    + msg);
			RevisaEstadoServicio(dataString);

		}
	}
	/*
	1;"NUEVO SERVICIO"
	2;"BUSCANDO APP"
	3;"BUSCANDO RADIO"
	4;"MOVIL ASIGNADO"
	5;"SERVICIO EXITOSO"
	6;"SERVICIO CANCELADO POR TAXISTA"
	7;"SERVICIO EN LISTA"
	8;"NO SE LOGRO UBICAR MOVIL"
	9;"SERVICIO CANCELADO POR USUARIO"
	10;"NUEVO SERVICIO PARA RESERVA"
	11;"SERVICIO RESERVA CON MOVIL ASIGNADO"
	12;"SERVICIO CANCELADO POR LA CENTRAL"
	13;"USUARIO A BORDO"
	 */
	private void RevisaEstadoServicio(String msg) {
		Gson gson=new Gson();
		DatosServiciosDTO informacion = null;
		try {
			informacion = gson.fromJson(msg, DatosServiciosDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int idServicio = Integer.parseInt(informacion.getIdServicio());
			if (idServicio < 0) {
				/*******************detiene EL SERVICIO DE LA J SOLO CON TC*********************/
				Log.d(TAG, "------------Debe Detiene el servicio de TimerSocket");
				Intent intentComunication = new Intent(TimerSocket.this, TimerSocket.class);
				stopService(intentComunication);
				/**********************************************************************************/
			}
		}catch (Exception e){}


		Log.d(TAG, " ------------------ Estado -------------------  " + informacion.getEstadoServicio());
		if (informacion.getEstadoServicio() == null) informacion.setEstadoServicio("0");

		if(informacion.getEstadoServicio().equalsIgnoreCase("4") || informacion.getEstadoServicio().equalsIgnoreCase("5") ||
				informacion.getEstadoServicio().equalsIgnoreCase("6") || informacion.getEstadoServicio().equalsIgnoreCase("7") ||
				informacion.getEstadoServicio().equalsIgnoreCase("8") || informacion.getEstadoServicio().equalsIgnoreCase("9")||
				informacion.getEstadoServicio().equalsIgnoreCase("13") || informacion.getEstadoServicio().equalsIgnoreCase("16")){




			try {
				UnidadDTO datosConductor = new UnidadDTO();
				datosConductor.setNombreConductor(informacion.getDatosMovil().getNombreConductor());
				datosConductor.setPlaca(informacion.getDatosMovil().getPlaca());
				datosConductor.setLatitud(informacion.getDatosMovil().getLatitud());
				datosConductor.setLongitud(informacion.getDatosMovil().getLongitud());
				datosConductor.setMarca(informacion.getDatosMovil().getMarca());
				datosConductor.setModelo(informacion.getDatosMovil().getModelo());
				datosConductor.setCelConductor(informacion.getDatosMovil().getCelConductor());
				datosConductor.setNombreConductor(informacion.getDatosMovil().getNombreConductor());
				datosConductor.setTipoProyecto("5");
				appState.setDatosConductor(datosConductor);


				appState.setMensajeCentral(informacion.getMensajeEntranteCentral());
				appState.setMensajeConductor(informacion.getMensajeEntranteMovil());

				Log.d(TAG, "----------Mensaje Central  "  + appState.getMensajeCentral());
				Log.d(TAG, "----------Mensaje Conductor  "  + appState.getMensajeConductor());

				RevisaMensajes(msg);
			}catch (Exception e){
				e.printStackTrace();
			}
		}


		if (informacion.getEstadoServicio().equalsIgnoreCase("7")) {
			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "servicioEnEspera");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
		} else if (informacion.getEstadoServicio().equalsIgnoreCase("4")) {


			appState.setEstadoUsuario("asignado");
			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "vehiculoAsignado");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
		}else if (informacion.getEstadoServicio().equalsIgnoreCase("5")) {

			appState.setEstadoUsuario("sinservicio");

			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(Constants.costoServicio, informacion.getCostoServicio());
			editor.commit();


			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "FinServicio");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);

			/********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
			Log.d(TAG, "------------Debe Detiene el servicio de TimerSocket");
			Intent intentComunication = new Intent(TimerSocket.this, TimerSocket.class);
			stopService(intentComunication);
			/**********************************************************************************/

		}else if (informacion.getEstadoServicio().equalsIgnoreCase("6")) {

			appState.setEstadoUsuario("sinservicio");

			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "cancelaServicioConductor");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);

			/********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
			Log.d(TAG, "------------Debe Detiene el servicio de TimerSocket");
			Intent intentComunication = new Intent(TimerSocket.this, TimerSocket.class);
			stopService(intentComunication);
			/**********************************************************************************/

		}
		else if (informacion.getEstadoServicio().equalsIgnoreCase("9") || informacion.getEstadoServicio().equalsIgnoreCase("12")) {

			appState.setEstadoUsuario("sinservicio");

			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "cancelaServicioUsuario");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);

			/********************INICIALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
			Log.d(TAG, "------------Debe Detiene el servicio de TimerSocket");
			Intent intentComunication = new Intent(TimerSocket.this, TimerSocket.class);
			stopService(intentComunication);
			/**********************************************************************************/

		}


		else if (informacion.getEstadoServicio().equalsIgnoreCase("16")) {

			appState.setEstadoUsuario("VehiculoAlfrente");

			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "VehiculoAlfrente");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);

			RevisaMensajes(msg);

		} else if (informacion.getEstadoServicio().equalsIgnoreCase("13")) {

			appState.setEstadoUsuario("dentroVehiculo");

			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "dentroVehiculo");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
			RevisaMensajes(msg);
		}else if (informacion.getEstadoServicio().equalsIgnoreCase("18")) {



			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "noabordo");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
			RevisaMensajes(msg);
		}else if (informacion.getEstadoServicio().equalsIgnoreCase("19")) {



			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "noasignado");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
			RevisaMensajes(msg);
		}else if (informacion.getEstadoServicio().equalsIgnoreCase("20")) {



			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "maldireccion");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
			RevisaMensajes(msg);
		}




		if (informacion.getFuncion().equalsIgnoreCase("05") || informacion.getEstadoServicio().equalsIgnoreCase("8")) {

			appState.setEstadoUsuario("sinservicio");
			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "error");
			new_intent.putExtra("DATOS", "No fue posible conseguir un vehículo, intenta de nuevo");
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
			/********************FINALIZA EL SERVICIO DE LA J SOLO CON TC*********************/
			Log.d(TAG, "------------Debe Detiene el servicio de TimerSocket");
			Intent intentComunication = new Intent(TimerSocket.this, TimerSocket.class);
			stopService(intentComunication);
			/**********************************************************************************/
		}


//		if (informacion.getEstadoServicio().equalsIgnoreCase("4")||informacion.getEstadoServicio().equalsIgnoreCase("13")) {
//			contadorTmeout = 0;
//		} else{
//
//			if (contadorTmeout > 120) {
//				contadorTmeout = 0;
//				try {
//					Log.d(TAG, "------------TIEMPOOOOOOOOOOOOOOOOOOOOO");
//					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//					DatosServiciosDTO nuevoServicio = new DatosServiciosDTO();
//					nuevoServicio.setFuncion("05");
//					nuevoServicio.setIdServicio(sharedPreferences.getString(Constants.idServicio, ""));
//					nuevoServicio.setNumeroCelular(sharedPreferences.getString(Constants.customer_phone, ""));
//
//					Gson gson2 = new Gson();
//					String json = gson2.toJson(nuevoServicio);
//					writeToChannel(json + "\n");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}

	}

	/*****************************************************************************************/
	/*********************     REVISA SI HAY MENSAJES PARA MOSTRAR     ***********************/
	/*****************************************************************************************/

	private void RevisaMensajes(String dato){
		Gson gson=new Gson();
		DatosServiciosDTO informacion = null;
		try {
			informacion = gson.fromJson(dato, DatosServiciosDTO.class);
			if(informacion.getMensajeEntranteCentral().length() >1 || informacion.getMensajeEntranteMovil().length() >1){
				if(informacion.getMensajeEntranteMovil().length() >1){

					if(!mensajeMovil.equalsIgnoreCase(informacion.getMensajeEntranteMovil())){
						Intent new_intent = new Intent();
						new_intent.putExtra("CMD", "mensajeMovil");
						new_intent.putExtra("DATOS", informacion.getMensajeEntranteMovil());
						new_intent.setAction(ACTION_STRING_ACTIVITY);
						context.sendBroadcast(new_intent);
					}
					mensajeMovil = informacion.getMensajeEntranteMovil();

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/*****************************************************************************************/
	/*********************           SE PRENDE LA PANTALLA       *****************************/
	/*****************************************************************************************/
	private void unlockScreen() {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.ON_AFTER_RELEASE, "tag");
		wl.acquire();
	}
	
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/
	
	@Override
	public void onCreate(){
		super.onCreate();
		try{
			context = getApplicationContext();
			appState = (Globales) context;
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
     			if ((android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.HONEYCOMB)||
     					(android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.HONEYCOMB_MR1)){
     			}else{
     				//es menor a 3 es 3.2
     				if ((android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)){
     					
     				}else{
     					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
     					StrictMode.setThreadPolicy(policy);
     				}
     			}
     		}else{
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
     		}			
		}catch(Exception e){
			Log.e(TAG,MODULO+" "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, MODULO+": START " + startId + ": " + intent+" INIT: "+initialized);
		if(!initialized){		
			IntentFilter filter = new IntentFilter();
			filter.addAction(module);
			//filter.addAction("LOCATION_PING");
			timer2.scheduleAtFixedRate(new mainTask(), 0, 1000);
			registerReceiver(receiver, filter);
			initialized = true;
			inBuffer = ByteBuffer.allocateDirect(INBUFFERSIZE);
			outBuffer = ByteBuffer.allocateDirect(OUTBUFFERSIZE);
			stringBuffer = new StringBuffer(STRINGBUFFERSIZE);
			timer = new Timer("CHANNEL_POLICE", true);
			startChannel();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy(){
	    Log.i(TAG, MODULO + "onDestroy");
	    try {
	    	super.onDestroy();
		    unregisterReceiver(receiver);
		    timer.cancel();
		    timer2.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG,": ONBIND");
		return mBinder;
	}

}
