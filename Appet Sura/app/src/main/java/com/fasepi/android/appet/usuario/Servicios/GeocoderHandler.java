package com.fasepi.android.appet.usuario.Servicios;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

public class GeocoderHandler extends Thread {
	
	private Geocoder geocoder;
	private Handler handler;
	private Double latitud;
	private Double longitud;
	public static final String TAG = GeocoderHandler.class.getSimpleName();
	
	public GeocoderHandler(Double latitud, Double longitud, Handler handler, Context context){
		super("Geocoding: "+latitud+", "+longitud);
		try {
			this.latitud = latitud;
			this.longitud = longitud;
			geocoder = new Geocoder(context);
			
			this.handler = handler;
		} catch (Exception e) {
			Log.e(TAG, "Geocoder error: "+e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);
			Message m = new Message();
			if(direcciones.size()>0){
				m.obj = direcciones.get(0);
			}
			handler.sendMessage(m);			
		} catch (Exception e) {
			Log.e(TAG, "Geocoder error: "+e.getMessage());
		}
	}

}
