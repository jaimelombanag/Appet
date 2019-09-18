package com.fasepi.android.appet.usuario.Clases;

/**
 * Created by jaimelombana on 16/02/17.
 */

public class DatosTarificadorMinutoDTO {


    private String numeroPersonas;
    private String cotizacionTaxi;
    private String cotizacionAutomovil;
    private String precioMinuto;
    private String precioPersona;


    public String getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(String numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getCotizacionTaxi() {
        return cotizacionTaxi;
    }

    public void setCotizacionTaxi(String cotizacionTaxi) {
        this.cotizacionTaxi = cotizacionTaxi;
    }

    public String getCotizacionAutomovil() {
        return cotizacionAutomovil;
    }

    public void setCotizacionAutomovil(String cotizacionAutomovil) {
        this.cotizacionAutomovil = cotizacionAutomovil;
    }

    public String getPrecioMinuto() {
        return precioMinuto;
    }

    public void setPrecioMinuto(String precioMinuto) {
        this.precioMinuto = precioMinuto;
    }

    public String getPrecioPersona() {
        return precioPersona;
    }

    public void setPrecioPersona(String precioPersona) {
        this.precioPersona = precioPersona;
    }
}
