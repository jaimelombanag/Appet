package com.fasepi.android.appet.usuario.Appet;

public class Constants {


	public static final String TipoProyecto = "2"; // Produccion
	public static final int PuertoServer = 11001; // Produccion
	public static final String IpServer = "vps137473.vps.ovh.ca";
	public static String idServicio = "idServicio";
	public static String idTarjeta = "idTarjeta";
	public static String fotoConductor = "fotoConductor";

	public static final String Separator = "|";
	public static String customer_name = "customer_name";
	public static String customer_phone = "customer_phone";
	public static String customer_email = "customer_email";
	public static String customer_cedula = "customer_cedula";
	public static String service_numberID = "service_numberID";
	public static String driveVehicleNumber = "driveVehicleNumber";
	public static String PinVehiculo = "PinVehiculo";
	public static String driveFullName = "driveFullName";
	public static String LatitudVehiculo = "LatitudVehiculo";
	public static String LongitudVehiculo = "LongitudVehiculo";
	public static String CostoServicio = "CostoServicio";
	public static final String Latitud = "Lat";
	public static final String Longitud = "Lon";
	public static final String LatitudDestino = "LatitudDestino";
	public static final String LongitudDestino = "LongitudDestino";
	public static final String LatitudServicio = "LatitudServicio";
	public static final String LongitudServicio = "LongitudServicio";
	public static final String FuncionNewBD = "0";
	public static final String NumeroVale = "NumeroVale";
	public static final String RtaVale = "RtaVale";
	public static final String DatosUsuarioVale = "DatosUsuarioVale";
	public static final String numeroValesAsignados = "0";
	public static final String numeroValesUsar = "12345";
	public static final String fechaReserva = "fechaReserva";
	public static final String horaReserva = "horaReserva";
	public static final String ResrvarServicio = "ResrvarServicio";
	public static final String HoraFinal = "HoraFinal";

	
	
	public static final String RegTarjeta = "RegTarjeta";
	
	
	// MODULO COMUNICACION
	public static final String COM = "COM";
	public static final int SEND = 1;
	public static final int DISCONECT = 2;

	//COSTOS DEL SERVICIO
	public static final String costoSeleccion = "costoSeleccion";
	public static final String costoAutomovil = "costoAutomovil";
	public static final String costoCamioneta = "costoCamioneta";
	public static final String costoVan = "costoVan";
	public static final String costoMiniVan = "costoMiniVan";
	public static final String costoServicio = "costoServicio";


	//PARA EL GEOCODER
	public static final int SUCCESS_RESULT = 0;
	public static final int FAILURE_RESULT = 1;

	public static final int USE_ADDRESS_NAME = 1;
	public static final int USE_ADDRESS_LOCATION = 2;

	public static final String PACKAGE_NAME = "com.sample.foo.simplelocationapp";
	public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
	public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
	public static final String RESULT_ADDRESS = PACKAGE_NAME + ".RESULT_ADDRESS";
	public static final String LOCATION_LATITUDE_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_LATITUDE_DATA_EXTRA";
	public static final String LOCATION_LONGITUDE_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_LONGITUDE_DATA_EXTRA";
	public static final String LOCATION_NAME_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_NAME_DATA_EXTRA";
	public static final String FETCH_TYPE_EXTRA = PACKAGE_NAME + ".FETCH_TYPE_EXTRA";


	//PARA TARIFICADOR POR MINUTOS
	public static final String cotizaParada1Taxi = "costoParada1Taxi";
	public static final String cotizaParada1Auto = "cotizaParada1Auto";
	public static final String precioMinuto1 = "precioMinuto1";
	public static final String cotizaParada2Taxi = "costoParada2Taxi";
	public static final String cotizaParada2Auto = "cotizaParada2Auto";
	public static final String precioMinuto2 = "precioMinuto2";
	public static final String cotizaParada3Taxi = "costoParada3Taxi";
	public static final String cotizaParada3Auto = "cotizaParada3Auto";
	public static final String precioMinuto3 = "precioMinuto3";
	public static final String cotizaParada4Taxi = "costoParada4Taxi";
	public static final String cotizaParada4Auto = "cotizaParada4Auto";
	public static final String precioMinuto4 = "precioMinuto4";



	public static final String precioTipo1 = "precioTipo1";
	public static final String precioTipo2 = "precioTipo2";
	public static final String precioTipo3 = "precioTipo3";
	public static final String precioTipo4 = "precioTipo4";
	public static final String precioTipo5 = "precioTipo5";


	/**********************************************************************************************/
	/**************************        para el gps       ******************************************/
	public static String gpsLatitud = "gpsLatitud";
	public static String gpsLongitud = "gpsLongitud";
	public static String gpsVelocidad = "gpsVelocidad";
	public static String gpsAltura = "gpsAltura";
	public static String gpsCurso = "gpsCurso";
	public static String gpsDate = "gpsDate";
	/**********************************************************************************************/
	/**************************      para servicios de google     *********************************/

	public static final String KEY_SERVER = "AIzaSyD12_dL05kmK5eTJkKS3rJe67O-5eWPy-4";
	public static final String METHOD_GEOCODER_API_GOOGLE = "/geocode/json";
	public static final String METHOD_DISTANCEMATRIX_API_GOOGLE = "/distancematrix/json";
	public static final String URL_API_GOOGLE = "https://maps.googleapis.com/maps/api";


	/**********************************************************************************************/
	/**************************      para la app de traking       *********************************/
	public static String idMovil = "idMovil";

	
}
