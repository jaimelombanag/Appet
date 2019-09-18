package com.fasepi.android.appet.usuario.ClasesTraking;

/**
 * Created by jaimelombana on 21/11/17.
 */

public class DatosTrackingDTO {


    private String idTracking;
    private String idMovil;
    private String fecha;
    private String latitud;
    private String longitud;
    private String altitud;
    private String curso;
    private String fechaGps;
    private String velocidad;
    private String idEstado;
    private String idEstadoDescripcion;
    private String idOrigen;
    private String idOrigenDescripcion;
    private String direccionIp;


    public String getIdTracking() {
        return idTracking;
    }

    public void setIdTracking(String idTracking) {
        this.idTracking = idTracking;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getFechaGps() {
        return fechaGps;
    }

    public void setFechaGps(String fechaGps) {
        this.fechaGps = fechaGps;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getIdEstadoDescripcion() {
        return idEstadoDescripcion;
    }

    public void setIdEstadoDescripcion(String idEstadoDescripcion) {
        this.idEstadoDescripcion = idEstadoDescripcion;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(String idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getIdOrigenDescripcion() {
        return idOrigenDescripcion;
    }

    public void setIdOrigenDescripcion(String idOrigenDescripcion) {
        this.idOrigenDescripcion = idOrigenDescripcion;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
}
