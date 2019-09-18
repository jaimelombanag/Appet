package com.fasepi.android.appet.usuario.ClasesTraking;

import java.util.ArrayList;

/**
 * Created by jaimelombana on 21/11/17.
 */

public class DatosServiciosDTOT {


    private String error;
    private String funcion;
    private String mensajes;
    private DatosMovilDTOT movil;
    private DatosPropietarioDTOT propietario;
    private String tipoProyecto;
    private String codigoLogin;
    private ArrayList<DatosMovilDTOT> listaMoviles;
    private ArrayList<DatosTrackingDTO> listaTracking;
    private String horasHistorico;
    private DatosGeocercaDTO geocerca;
    private ArrayList<DatosGeocercaDTO> listaGeocercas;



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public DatosMovilDTOT getMovil() {
        return movil;
    }

    public void setMovil(DatosMovilDTOT movil) {
        this.movil = movil;
    }

    public DatosPropietarioDTOT getPropietario() {
        return propietario;
    }

    public void setPropietario(DatosPropietarioDTOT propietario) {
        this.propietario = propietario;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getCodigoLogin() {
        return codigoLogin;
    }

    public void setCodigoLogin(String codigoLogin) {
        this.codigoLogin = codigoLogin;
    }

    public ArrayList<DatosMovilDTOT> getListaMoviles() {
        return listaMoviles;
    }

    public void setListaMoviles(ArrayList<DatosMovilDTOT> listaMoviles) {
        this.listaMoviles = listaMoviles;
    }

    public ArrayList<DatosTrackingDTO> getListaTracking() {
        return listaTracking;
    }

    public void setListaTracking(ArrayList<DatosTrackingDTO> listaTracking) {
        this.listaTracking = listaTracking;
    }

    public String getHorasHistorico() {
        return horasHistorico;
    }

    public void setHorasHistorico(String horasHistorico) {
        this.horasHistorico = horasHistorico;
    }


    public DatosGeocercaDTO getGeocerca() {
        return geocerca;
    }

    public void setGeocerca(DatosGeocercaDTO geocerca) {
        this.geocerca = geocerca;
    }

    public ArrayList<DatosGeocercaDTO> getListaGeocercas() {
        return listaGeocercas;
    }

    public void setListaGeocercas(ArrayList<DatosGeocercaDTO> listaGeocercas) {
        this.listaGeocercas = listaGeocercas;
    }
}
