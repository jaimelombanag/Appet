package com.fasepi.android.appet.usuario.Servicios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.fasepi.android.appet.usuario.Appet.Constants;
import com.fasepi.android.appet.usuario.Appet.Globales;
import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosTarjetaCreditoDTO;
import com.fasepi.android.appet.usuario.Clases.UnidadDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosMovilDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosServiciosDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosTrackingDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;



public class ConexionTCP {

	private static final String TAG = "CivicarUsuario";
	private static final String MODULO = "TCP";
	private final String ACTION_STRING_ACTIVITY = "ToActivity";
	private Socket socket;
	private Globales appState;

	protected PrintWriter dataOutputStream;
	protected InputStreamReader dataInputStream;
	private String mensajeEncriptado;
	private Context context;
	public AlertDialog alert;


	public ConexionTCP(Context _context) {
		try{
//			context = _context;
//			appState = (AppUsuario) _context;

			this.context = _context;
			appState = ((Globales) context);


		}catch(Exception e){
			Log.e(TAG, MODULO + "  "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendData(final String data) {
		mensajeEncriptado = data;
		Log.i(TAG, MODULO + "================================Mensaje Enviado:      " + mensajeEncriptado);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String IP = "200.91.204.38";
					//String IP = "192.168.23.118";
					int Puerto = 11260;
					socket = new Socket(IP, Puerto);
					socket.setSoTimeout(20000);


					dataOutputStream = new PrintWriter(socket.getOutputStream(), true);


					//dataOutputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_16), true);


					dataInputStream = new InputStreamReader(socket.getInputStream());
					Log.i(TAG, MODULO + "Socket y Flujos creados " + Puerto + "  "  +  IP);
					dataOutputStream.println(mensajeEncriptado + "\n\r");


					String dataSocket = new BufferedReader(dataInputStream).readLine();
					String mensajeDesencriptado;
					mensajeDesencriptado= dataSocket;
					Log.i(TAG, MODULO  + "========================= SE RECIBE: "+ mensajeDesencriptado+"\n");
					if (mensajeDesencriptado != null) {
						ProcessRespuesta(mensajeDesencriptado);
					}

				} catch (UnknownHostException e) {
					Log.e(TAG, MODULO + "Error tipo: UnknownHostException");
					e.printStackTrace();
				} catch (ConnectException e) {
					Log.e(TAG, MODULO + "Error tipo: ConnectException");
					e.printStackTrace();
				} catch (SocketTimeoutException e) {
					Log.e(TAG, MODULO + "Error por SocketTimeoutException   " );
					e.printStackTrace();
					Intent error = new Intent();
					error.putExtra("CMD", "Error");
					error.putExtra("DATA", "SocketTimeoutException");
					error.setAction(ACTION_STRING_ACTIVITY);
					context.sendBroadcast(error);
				} catch (IOException e) {
					Log.e(TAG, MODULO + "Error tipo: IOException");
					e.printStackTrace();
				} finally {
					Log.i(TAG, MODULO + "Dando por terminada la tarea del Soket, se cierran los flujos y conexin");
					if (socket != null) {
						try {
							if (dataOutputStream != null) {
								dataOutputStream.close();
							}
							if (dataInputStream != null) {
								dataInputStream.close();
							}
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}).start();
	}

	private void ProcessRespuesta(String datos) {


		Log.i(TAG, "¿¿¿¿¿ESTOS SON LOS DATOS:   "  +  datos);

		Gson gson=new Gson();
		DatosServiciosDTOT informacion = null;
		try {
			informacion = gson.fromJson(datos, DatosServiciosDTOT.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (informacion.getError().length() > 0) {
			Intent new_intent = new Intent();
			new_intent.putExtra("CMD", "error");
			new_intent.putExtra("DATOS", informacion.getError());
			new_intent.setAction(ACTION_STRING_ACTIVITY);
			context.sendBroadcast(new_intent);
		}else {


			if(informacion.getFuncion() != null){


				if (informacion.getFuncion().equalsIgnoreCase("00")) {


					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(Constants.idMovil, informacion.getMovil().getIdMovil());
					editor.commit();

					DatosServiciosDTOT datosServiciosDTOT = new DatosServiciosDTOT();
					DatosMovilDTOT datosMovilDTOT = new DatosMovilDTOT();
					datosMovilDTOT.setMovPlaca(informacion.getMovil().getMovPlaca());
					datosMovilDTOT.setMovMarca(informacion.getMovil().getMovMarca());
					datosMovilDTOT.setMovLinea(informacion.getMovil().getMovLinea());
					datosMovilDTOT.setMovColor(informacion.getMovil().getMovColor());
					datosMovilDTOT.setClaseUnidadDescripcion(informacion.getMovil().getClaseUnidadDescripcion());
					datosMovilDTOT.setLatitud(informacion.getMovil().getLatitud());
					datosMovilDTOT.setLongitud(informacion.getMovil().getLongitud());

					appState.setDatosMovilDTOT(datosMovilDTOT);


				}


				if (informacion.getFuncion().equalsIgnoreCase("01")) {
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(Constants.customer_cedula, informacion.getPropietario().getProDocumento());
					//editor.putString(Constants.customer_name, informacion.getPropietario().getProNombre());
					editor.putString(Constants.customer_name, informacion.getPropietario().getCorreoElectronico());
					editor.putString(Constants.customer_phone, informacion.getPropietario().getNumeroCelular());
					editor.putString(Constants.customer_email, informacion.getPropietario().getCorreoElectronico());
					editor.commit();
				}


				if (informacion.getFuncion().equalsIgnoreCase("02")) {

					for (int j = 0; j < informacion.getListaMoviles().size(); j++) {

						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.putString(Constants.idMovil, informacion.getListaMoviles().get(0).getIdMovil());
						editor.commit();

						DatosServiciosDTOT datosServiciosDTOT = new DatosServiciosDTOT();
						DatosMovilDTOT datosMovilDTOT = new DatosMovilDTOT();
						datosMovilDTOT.setMovPlaca(informacion.getListaMoviles().get(j).getMovPlaca());
						datosMovilDTOT.setMovMarca(informacion.getListaMoviles().get(j).getMovMarca());
						datosMovilDTOT.setMovLinea(informacion.getListaMoviles().get(j).getMovLinea());
						datosMovilDTOT.setMovColor(informacion.getListaMoviles().get(j).getMovColor());
						datosMovilDTOT.setClaseUnidadDescripcion(informacion.getListaMoviles().get(j).getClaseUnidadDescripcion());
						datosMovilDTOT.setLatitud(informacion.getListaMoviles().get(j).getLatitud());
						datosMovilDTOT.setLongitud(informacion.getListaMoviles().get(j).getLongitud());

						appState.getListaMoviles().add(datosMovilDTOT);

					}
				}


				if (informacion.getFuncion().equalsIgnoreCase("03")) {


					Log.i(TAG, "LISTA DE TRAKING TAMAÑO: " + informacion.getListaTracking().size());

					for (int j = 0; j < informacion.getListaTracking().size(); j++) {


						DatosTrackingDTO datosTrackingDTO = new DatosTrackingDTO();
						datosTrackingDTO.setLatitud(informacion.getListaTracking().get(j).getLatitud());
						datosTrackingDTO.setLongitud(informacion.getListaTracking().get(j).getLongitud());
						appState.getListaTracking().add(datosTrackingDTO);

						Log.i(TAG, "====================: " + appState.getListaTracking().get(j).getLatitud());
						Log.i(TAG, "====================: " + appState.getListaTracking().get(j).getLongitud());


					}

				}


				if (informacion.getFuncion().equalsIgnoreCase("11")) {
//					appState.getListServicios().clear();
//					for (int j = 0; j < informacion.getListaServicios().size(); j++) {
//
//						Log.i(TAG, "---------   "    +  informacion.getListaServicios().get(2).getDatosMovil().getNombreConductor());
//
//						DatosServiciosDTO service = new DatosServiciosDTO();
//						service.setLatitud(informacion.getListaServicios().get(j).getLatitud());
//						service.setLongitud(informacion.getListaServicios().get(j).getLongitud());
//						service.setDireccion(informacion.getListaServicios().get(j).getDireccion());
//						service.setNombre(informacion.getListaServicios().get(j).getNombre());
//
//
//						UnidadDTO datonMovil = new UnidadDTO();
//						datonMovil.setNombreConductor(informacion.getListaServicios().get(j).getDatosMovil().getNombreConductor());
//						datonMovil.setPlaca(informacion.getListaServicios().get(j).getDatosMovil().getPlaca());
//						service.setDatosMovil(datonMovil);
//
//
//						service.setDistanciaServicio(informacion.getListaServicios().get(j).getDistanciaServicio());
//						service.setNumeroCelular(informacion.getListaServicios().get(j).getNumeroCelular());
//						service.setIdServicio(informacion.getListaServicios().get(j).getIdServicio());
//						service.setFechaFinalizacion(informacion.getListaServicios().get(j).getFechaFinalizacion());
//						service.setFormaPago(informacion.getListaServicios().get(j).getFormaPago());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setCostoServicio(informacion.getListaServicios().get(j).getCostoServicio());
//						service.setFechaCreacion(informacion.getListaServicios().get(j).getFechaCreacion());
//						service.setFechaReservacion(informacion.getListaServicios().get(j).getFechaReservacion());
//						service.setLatitudDestino(informacion.getListaServicios().get(j).getLatitudDestino());
//						service.setLongitudDestino(informacion.getListaServicios().get(j).getLongitudDestino());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setOrigenSolicitudDescripcion(informacion.getListaServicios().get(j).getOrigenSolicitudDescripcion());
//						service.setFormaPagoDescripcion(informacion.getListaServicios().get(j).getFormaPagoDescripcion());
//						service.setEstadoServicio(informacion.getListaServicios().get(j).getEstadoServicio());
//						appState.getListServicios().add(service);
//					}



				}



				if (informacion.getFuncion().equalsIgnoreCase("12") || informacion.getFuncion().equalsIgnoreCase("13")) {

//					if (informacion.getFuncion().equalsIgnoreCase("13")) {
//						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//						SharedPreferences.Editor editor = sharedPreferences.edit();
//						editor.putString(Constants.precioTipo1, informacion.getTarificador().getPrecioTipo1());
//						editor.putString(Constants.precioTipo2, informacion.getTarificador().getPrecioTipo2());
//						editor.putString(Constants.precioTipo3, informacion.getTarificador().getPrecioTipo3());
//						editor.putString(Constants.precioTipo4, informacion.getTarificador().getPrecioTipo4());
//						editor.putString(Constants.precioTipo5, informacion.getTarificador().getPrecioTipo5());
//						editor.commit();
//					}

				}


				if (informacion.getFuncion().equalsIgnoreCase("14")) {
//					Log.i(TAG, "Tamaño de la Lista de Tarjetas:  " + informacion.getListaDatosTarjeta().size());
//					if (informacion.getListaDatosTarjeta().size() > 0) {
//
//						appState.setAppName("");
//
//						appState.getLstTarjetasCredito().clear();
//
//						for (int i = 0; i < informacion.getListaDatosTarjeta().size(); i++) {
//							Log.i(TAG, "Franquisia:  " + informacion.getListaDatosTarjeta().get(i).getFranquicia());
//							Log.i(TAG, "Numero:  " + informacion.getListaDatosTarjeta().get(i).getNumero_tarjeta());
//							Log.i(TAG, "Nombre:  " + informacion.getListaDatosTarjeta().get(i).getNombre());
//							Log.i(TAG, "Id:  " + informacion.getListaDatosTarjeta().get(i).getId());
//							Log.i(TAG, "Vencimiento:  " + informacion.getListaDatosTarjeta().get(i).getVencimiento());
//
//
//							DatosTarjetaCreditoDTO datosTarjetaCreditoDTO = new DatosTarjetaCreditoDTO();
//							datosTarjetaCreditoDTO.setNumero_tarjeta(informacion.getListaDatosTarjeta().get(i).getNumero_tarjeta());
//							datosTarjetaCreditoDTO.setNombre(informacion.getListaDatosTarjeta().get(i).getNombre());
//							datosTarjetaCreditoDTO.setId(informacion.getListaDatosTarjeta().get(i).getId());
//							datosTarjetaCreditoDTO.setVencimiento(informacion.getListaDatosTarjeta().get(i).getVencimiento());
//							datosTarjetaCreditoDTO.setFranquicia(informacion.getListaDatosTarjeta().get(i).getFranquicia());
//
//							appState.getLstTarjetasCredito().add(datosTarjetaCreditoDTO);
//
//						}
//					} else {
//						appState.getLstTarjetasCredito().clear();
//					}
				}


				if (informacion.getFuncion().equalsIgnoreCase("15") ) {


				}


				//if(informacion.getFuncion().equalsIgnoreCase("13")|| informacion.getFuncion().equalsIgnoreCase("23")){

//			if(informacion.getFuncion().equalsIgnoreCase("13")){
//				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//				SharedPreferences.Editor editor = sharedPreferences.edit();
//				editor.putString(Constants.costoAutomovil, informacion.getTarificador().getPrecioAutomovil());
//				editor.putString(Constants.costoCamioneta, informacion.getTarificador().getPrecioCamioneta());
//				editor.putString(Constants.costoMiniVan, informacion.getTarificador().getPrecioMinivan());
//				editor.putString(Constants.costoVan, informacion.getTarificador().getPrecioVan());
//				editor.commit();
//
//				Intent new_intent = new Intent();
//				new_intent.putExtra("CMD", "Respuesta");
//				new_intent.putExtra("DATOS", datos);
//				new_intent.setAction(ACTION_STRING_ACTIVITY);
//				context.sendBroadcast(new_intent);
//
//			}


				if (informacion.getFuncion().equalsIgnoreCase("17")) {
//					Log.i(TAG, "===================MOVILES CERCANOS:  " + informacion.getMovilesCercanos().size());
//					for (int i = 0; i < informacion.getMovilesCercanos().size(); i++) {
//						appState.getMovilesCercanos().add(informacion.getMovilesCercanos().get(i));
//						Log.i(TAG, "===================MOVILES CERCANOS LAT:  " + informacion.getMovilesCercanos().get(i).getLatitud());
//						Log.i(TAG, "===================MOVILES CERCANOS LON:  " + informacion.getMovilesCercanos().get(i).getLongitud());
//					}
				}

				if (informacion.getFuncion().equalsIgnoreCase("23")) {
//					for (int i = 0; i < informacion.getTarificador().getCotizacion().size(); i++) {
//						Log.i(TAG, "===================PARADAS:  " + informacion.getTarificador().getCotizacion().get(i).getNumeroPersonas());
//						Log.i(TAG, "===================AUTO:  " + informacion.getTarificador().getCotizacion().get(i).getCotizacionAutomovil());
//						Log.i(TAG, "===================TAXI:  " + informacion.getTarificador().getCotizacion().get(i).getCotizacionTaxi());
//						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//						SharedPreferences.Editor editor = sharedPreferences.edit();
//						if (i == 0) {
//							editor.putString(Constants.cotizaParada1Auto, informacion.getTarificador().getCotizacion().get(i).getCotizacionAutomovil());
//							editor.putString(Constants.cotizaParada1Taxi, informacion.getTarificador().getCotizacion().get(i).getCotizacionTaxi());
//							editor.putString(Constants.precioMinuto1, informacion.getTarificador().getCotizacion().get(i).getPrecioMinuto());
//						}
//						if (i == 1) {
//							editor.putString(Constants.cotizaParada2Auto, informacion.getTarificador().getCotizacion().get(i).getCotizacionAutomovil());
//							editor.putString(Constants.cotizaParada2Taxi, informacion.getTarificador().getCotizacion().get(i).getCotizacionTaxi());
//							editor.putString(Constants.precioMinuto2, informacion.getTarificador().getCotizacion().get(i).getPrecioMinuto());
//						}
//						if (i == 2) {
//							editor.putString(Constants.cotizaParada3Auto, informacion.getTarificador().getCotizacion().get(i).getCotizacionAutomovil());
//							editor.putString(Constants.cotizaParada3Taxi, informacion.getTarificador().getCotizacion().get(i).getCotizacionTaxi());
//							editor.putString(Constants.precioMinuto3, informacion.getTarificador().getCotizacion().get(i).getPrecioMinuto());
//						}
//						if (i == 3) {
//							editor.putString(Constants.cotizaParada4Auto, informacion.getTarificador().getCotizacion().get(i).getCotizacionAutomovil());
//							editor.putString(Constants.cotizaParada4Taxi, informacion.getTarificador().getCotizacion().get(i).getCotizacionTaxi());
//							editor.putString(Constants.precioMinuto4, informacion.getTarificador().getCotizacion().get(i).getPrecioMinuto());
//						}
//
//
//						editor.commit();
//					}

				}


				if (informacion.getFuncion().equalsIgnoreCase("26")) {
//					appState.getListServicios().clear();
//					for (int j = 0; j < informacion.getListaServicios().size(); j++) {
//
//						DatosServiciosDTO service = new DatosServiciosDTO();
//						service.setLatitud(informacion.getListaServicios().get(j).getLatitud());
//						service.setLongitud(informacion.getListaServicios().get(j).getLongitud());
//						service.setDireccion(informacion.getListaServicios().get(j).getDireccion());
//						service.setNombre(informacion.getListaServicios().get(j).getNombre());
//						service.setDistanciaServicio(informacion.getListaServicios().get(j).getDistanciaServicio());
//						service.setNumeroCelular(informacion.getListaServicios().get(j).getNumeroCelular());
//						service.setIdServicio(informacion.getListaServicios().get(j).getIdServicio());
//						service.setFechaFinalizacion(informacion.getListaServicios().get(j).getFechaFinalizacion());
//						service.setFormaPago(informacion.getListaServicios().get(j).getFormaPago());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setCostoServicio(informacion.getListaServicios().get(j).getCostoServicio());
//						service.setFechaCreacion(informacion.getListaServicios().get(j).getFechaCreacion());
//						service.setFechaReservacion(informacion.getListaServicios().get(j).getFechaReservacion());
//						service.setLatitudDestino(informacion.getListaServicios().get(j).getLatitudDestino());
//						service.setLongitudDestino(informacion.getListaServicios().get(j).getLongitudDestino());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setOrigenSolicitudDescripcion(informacion.getListaServicios().get(j).getOrigenSolicitudDescripcion());
//						service.setFormaPagoDescripcion(informacion.getListaServicios().get(j).getFormaPagoDescripcion());
//						service.setEstadoServicio(informacion.getListaServicios().get(j).getEstadoServicio());
//						appState.getListServicios().add(service);
//					}



				}


				if (informacion.getFuncion().equalsIgnoreCase("70")) {

//					appState.getListServicios().clear();
//					for (int j = 0; j < informacion.getListaServicios().size(); j++) {
//
//						DatosServiciosDTO service = new DatosServiciosDTO();
//						service.setLatitud(informacion.getListaServicios().get(j).getLatitud());
//						service.setLongitud(informacion.getListaServicios().get(j).getLongitud());
//						service.setDireccion(informacion.getListaServicios().get(j).getDireccion());
//						service.setNombre(informacion.getListaServicios().get(j).getNombre());
//						service.setDistanciaServicio(informacion.getListaServicios().get(j).getDistanciaServicio());
//						service.setNumeroCelular(informacion.getListaServicios().get(j).getNumeroCelular());
//						service.setIdServicio(informacion.getListaServicios().get(j).getIdServicio());
//						service.setFechaFinalizacion(informacion.getListaServicios().get(j).getFechaFinalizacion());
//						service.setFormaPago(informacion.getListaServicios().get(j).getFormaPago());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setCostoServicio(informacion.getListaServicios().get(j).getCostoServicio());
//						service.setFechaCreacion(informacion.getListaServicios().get(j).getFechaCreacion());
//						service.setFechaReservacion(informacion.getListaServicios().get(j).getFechaReservacion());
//						service.setLatitudDestino(informacion.getListaServicios().get(j).getLatitudDestino());
//						service.setLongitudDestino(informacion.getListaServicios().get(j).getLongitudDestino());
//						service.setDestino(informacion.getListaServicios().get(j).getDestino());
//						service.setOrigenSolicitudDescripcion(informacion.getListaServicios().get(j).getOrigenSolicitudDescripcion());
//						service.setFormaPagoDescripcion(informacion.getListaServicios().get(j).getFormaPagoDescripcion());
//						service.setEstadoServicio(informacion.getListaServicios().get(j).getEstadoServicio());
//
//						UnidadDTO datosConductor = new UnidadDTO();
//						datosConductor.setNombreConductor(informacion.getListaServicios().get(j).getDatosMovil().getNombreConductor());
//						datosConductor.setPlaca(informacion.getListaServicios().get(j).getDatosMovil().getPlaca());
//
//						service.setDatosMovil(datosConductor);
//
//						appState.getListServicios().add(service);
//					}


				}


				Intent new_intent = new Intent();
				new_intent.putExtra("CMD", "Respuesta");
				new_intent.putExtra("DATOS", datos);
				new_intent.setAction(ACTION_STRING_ACTIVITY);
				context.sendBroadcast(new_intent);


			}else {

				Intent new_intent = new Intent();
				new_intent.putExtra("CMD", "error");
				new_intent.putExtra("DATOS", "Respuesta sin función.");
				new_intent.setAction(ACTION_STRING_ACTIVITY);
				context.sendBroadcast(new_intent);

			}

		}
	}

}