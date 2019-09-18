package com.fasepi.android.appet.usuario.Appet;

import android.app.Application;
import android.content.Context;


import com.fasepi.android.appet.usuario.Clases.DatosServiciosDTO;
import com.fasepi.android.appet.usuario.Clases.DatosTarjetaCreditoDTO;
import com.fasepi.android.appet.usuario.Clases.UnidadDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosGeocercaDTO;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosMovilDTOT;
import com.fasepi.android.appet.usuario.ClasesTraking.DatosTrackingDTO;

import java.util.ArrayList;

/**
 * Created by jaimelombana on 15/08/17.
 */

public class Globales extends Application {

    protected String appName = "AppUsuario";
    private static Context context;
    private UnidadDTO datosConductor;
    private  EstadosUnidad estadosUnidad;
    private String MensajeConductor;
    private String MensajeCentral;
    private ArrayList<DatosTarjetaCreditoDTO> lstTarjetasCredito;
    private ArrayList<UnidadDTO> movilesCercanos;
    private ArrayList<DatosServiciosDTO> listServicios;
    private String franquicia;
    private String nombreTarjeta;
    private String idTarjeta;
    private String numeroTarjeta;
    private String estadoUsuario;


    private ArrayList<DatosMovilDTOT> listaMoviles;
    private ArrayList<DatosTrackingDTO> listaTracking;

    private DatosMovilDTOT datosMovilDTOT;
    private DatosGeocercaDTO datosGeocercaDTO;


    public Globales() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            context = getApplicationContext();
            lstTarjetasCredito= new ArrayList<>();
            movilesCercanos= new ArrayList<>();
            listServicios = new ArrayList<>();
            listaMoviles = new ArrayList<>();
            listaTracking = new ArrayList<>();
        } catch (Exception e) {

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /***************************SET AND GET ****************************************/

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public UnidadDTO getDatosConductor() {
        return datosConductor;
    }

    public void setDatosConductor(UnidadDTO datosConductor) {
        this.datosConductor = datosConductor;
    }

    public EstadosUnidad getEstadosUnidad() {
        return estadosUnidad;
    }

    public void setEstadosUnidad(EstadosUnidad estadosUnidad) {
        this.estadosUnidad = estadosUnidad;
    }

    public String getMensajeConductor() {
        return MensajeConductor;
    }

    public void setMensajeConductor(String mensajeConductor) {
        MensajeConductor = mensajeConductor;
    }

    public String getMensajeCentral() {
        return MensajeCentral;
    }

    public void setMensajeCentral(String mensajeCentral) {
        MensajeCentral = mensajeCentral;
    }

    public ArrayList<DatosTarjetaCreditoDTO> getLstTarjetasCredito() {
        return lstTarjetasCredito;
    }

    public void setLstTarjetasCredito(ArrayList<DatosTarjetaCreditoDTO> lstTarjetasCredito) {
        this.lstTarjetasCredito = lstTarjetasCredito;
    }

    public ArrayList<UnidadDTO> getMovilesCercanos() {
        return movilesCercanos;
    }

    public void setMovilesCercanos(ArrayList<UnidadDTO> movilesCercanos) {
        this.movilesCercanos = movilesCercanos;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public ArrayList<DatosServiciosDTO> getListServicios() {
        return listServicios;
    }

    public void setListServicios(ArrayList<DatosServiciosDTO> listServicios) {
        this.listServicios = listServicios;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Globales.context = context;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public ArrayList<DatosMovilDTOT> getListaMoviles() {
        return listaMoviles;
    }

    public void setListaMoviles(ArrayList<DatosMovilDTOT> listaMoviles) {
        this.listaMoviles = listaMoviles;
    }

    public DatosMovilDTOT getDatosMovilDTOT() {
        return datosMovilDTOT;
    }

    public void setDatosMovilDTOT(DatosMovilDTOT datosMovilDTOT) {
        this.datosMovilDTOT = datosMovilDTOT;
    }

    public ArrayList<DatosTrackingDTO> getListaTracking() {
        return listaTracking;
    }

    public void setListaTracking(ArrayList<DatosTrackingDTO> listaTracking) {
        this.listaTracking = listaTracking;
    }

    public DatosGeocercaDTO getDatosGeocercaDTO() {
        return datosGeocercaDTO;
    }

    public void setDatosGeocercaDTO(DatosGeocercaDTO datosGeocercaDTO) {
        this.datosGeocercaDTO = datosGeocercaDTO;
    }
}
