package com.fasepi.android.appet.usuario.Appet;

/**
 * Created by jaimelombana on 17/08/17.
 */

public class DatosListas {


    public String direccion;
    public String nombre;
    public String costo;
    public int color;
    public int visibilidadDelete;


    public DatosListas(){
        super();
    }

    public DatosListas(String direccion, String nombre, String costo, int color, int visibilidadDelete) {
        super();
        this.direccion = direccion;
        this.nombre = nombre;
        this.costo = costo;
        this.color = color;
        this.visibilidadDelete = visibilidadDelete;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public int getVisibilidadDelete() {
        return visibilidadDelete;
    }

    public void setVisibilidadDelete(int visibilidadDelete) {
        this.visibilidadDelete = visibilidadDelete;
    }
}
