package com.fasepi.android.appet.usuario.ClasesTraking;

/**
 * Created by jaimelombana on 22/11/17.
 */

public class DatosGeocercaDTO {

    private String idGeocerca;
    private String latitud;
    private String longitud;
    private String distancia;
    private String activa;
    private String nombre;

    public String getIdGeocerca() {
        return idGeocerca;
    }

    public void setIdGeocerca(String idGeocerca) {
        this.idGeocerca = idGeocerca;
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

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
