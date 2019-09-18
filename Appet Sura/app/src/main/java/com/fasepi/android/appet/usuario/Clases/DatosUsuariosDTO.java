package com.fasepi.android.appet.usuario.Clases;

import java.util.ArrayList;

/**
 * Created by jalombanag on 21/07/2016.
 */
public class DatosUsuariosDTO {

    private String nombre;
    private String documento;
    private String correoElectronico;
    private String numeroCelular;
    private String codigoModipay;
    private ArrayList<DatosDireccionesDTO> listadirFavoritas;
    private String idUsuario;
    private String error;
    private String tipoProyecto;
    private String clave;
    private String nombreUsuarioSistema;
    private ArrayList<DatosUsuariosDTO> listaUsuarios;
    private String barrio;
    private String cargo;
    private String direccion;
    private String base;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getCodigoModipay() {
        return codigoModipay;
    }

    public void setCodigoModipay(String codigoModipay) {
        this.codigoModipay = codigoModipay;
    }


    public ArrayList<DatosDireccionesDTO> getListadirFavoritas() {
        return listadirFavoritas;
    }

    public void setListadirFavoritas(ArrayList<DatosDireccionesDTO> listadirFavoritas) {
        this.listadirFavoritas = listadirFavoritas;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreUsuarioSistema() {
        return nombreUsuarioSistema;
    }

    public void setNombreUsuarioSistema(String nombreUsuarioSistema) {
        this.nombreUsuarioSistema = nombreUsuarioSistema;
    }

    public ArrayList<DatosUsuariosDTO> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<DatosUsuariosDTO> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
