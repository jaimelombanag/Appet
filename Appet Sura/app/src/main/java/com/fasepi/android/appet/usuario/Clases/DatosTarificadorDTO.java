package com.fasepi.android.appet.usuario.Clases;

import java.util.ArrayList;

/**
 * Created by jaimelombana on 16/01/17.
 */

public class DatosTarificadorDTO {


    private String precioCamioneta;
    private String precioVan;
    private String precioAutomovil;
    private String conductorElegido;
    private String precioMinivan;
    private String metros;
    private String minutos;
    private String numeroPersonas;
    private String precioTipo1;
    private String precioTipo2;
    private String precioTipo3;
    private String precioTipo4;
    private String precioTipo5;
    private String precioTipo6;




    private ArrayList<DatosTarificadorMinutoDTO> cotizacion;


    public String getPrecioCamioneta() {
        return precioCamioneta;
    }

    public void setPrecioCamioneta(String precioCamioneta) {
        this.precioCamioneta = precioCamioneta;
    }

    public String getPrecioVan() {
        return precioVan;
    }

    public void setPrecioVan(String precioVan) {
        this.precioVan = precioVan;
    }

    public String getPrecioAutomovil() {
        return precioAutomovil;
    }

    public void setPrecioAutomovil(String precioAutomovil) {
        this.precioAutomovil = precioAutomovil;
    }

    public String getConductorElegido() {
        return conductorElegido;
    }

    public void setConductorElegido(String conductorElegido) {
        this.conductorElegido = conductorElegido;
    }

    public String getPrecioMinivan() {
        return precioMinivan;
    }

    public void setPrecioMinivan(String precioMinivan) {
        this.precioMinivan = precioMinivan;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(String numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getPrecioTipo1() {
        return precioTipo1;
    }

    public void setPrecioTipo1(String precioTipo1) {
        this.precioTipo1 = precioTipo1;
    }

    public String getPrecioTipo2() {
        return precioTipo2;
    }

    public void setPrecioTipo2(String precioTipo2) {
        this.precioTipo2 = precioTipo2;
    }

    public String getPrecioTipo3() {
        return precioTipo3;
    }

    public void setPrecioTipo3(String precioTipo3) {
        this.precioTipo3 = precioTipo3;
    }

    public String getPrecioTipo4() {
        return precioTipo4;
    }

    public void setPrecioTipo4(String precioTipo4) {
        this.precioTipo4 = precioTipo4;
    }

    public String getPrecioTipo5() {
        return precioTipo5;
    }

    public void setPrecioTipo5(String precioTipo5) {
        this.precioTipo5 = precioTipo5;
    }

    public String getPrecioTipo6() {
        return precioTipo6;
    }

    public void setPrecioTipo6(String precioTipo6) {
        this.precioTipo6 = precioTipo6;
    }

    public ArrayList<DatosTarificadorMinutoDTO> getCotizacion() {
        return cotizacion;
    }




    public void setCotizacion(ArrayList<DatosTarificadorMinutoDTO> cotizacion) {
        this.cotizacion = cotizacion;
    }
}
