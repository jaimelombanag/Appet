package com.fasepi.android.appet.usuario.Clases;

import java.util.ArrayList;

/**
 * Created by Jaime on 24/06/2016.
 */
public class DatosServiciosDTO {

    private String direccion;
    private String nombre;
    private String latitud;
    private String longitud;
    private String fechaCreacion;
    private String distanciaServicio;
    private String correoElectronico;
    private String tipoServicio;
    private String idServicio;
    private String barrio;
    private String empresa;
    private String destino;
    private String latitudDestino;
    private String longitudDestino;
    private String costoServicio;
    private String formaPago;
    private String formaPagoDescripcion;
    private String fechaFinalizacion;
    private String fechaReservacion;
    private String error;
    private String idPush;
    private String funcion;
    private String numeroCelular;
    private String pais;
    private String propina;
    private UnidadDTO datosMovil;
    private String mensajeSaliente;
    private String mensajeEntranteMovil;
    private String mensajeEntranteCentral;
    private String estadoServicio;
    private ArrayList<DatosValesDTO> listaValesAsignados;
    private DatosValesDTO datoVale;
    private String ciudad;
    private String codigoLogin;
    private DatosUsuariosDTO usuario;
    private ArrayList<DatosServiciosDTO> listaServicios;
    private String direccionGoogle;
    private DatosTarificadorDTO tarificador;
    private String codigoCiudad;
    private String estadoServicioDescripcion;
    private DatosConductorDTO datosConductor;
    private String origenSolicitud;
    private String origenSolicitudDescripcion;
    private DatosPagoDTO datosPago;
    private DatosTarjetaCreditoDTO datosTarjeta;
    private ArrayList<DatosTarjetaCreditoDTO> listaDatosTarjeta;
    private ArrayList<UnidadDTO> movilesCercanos;
    private DatosDireccionesDTO dirFavorita;
    private ArrayList<DatosDireccionesDTO> listadirFavoritas;
    private String idProyecto;
    private DatosCalificacionDTO calificacion;
    private String tipoProyecto;
    private String metrosRecorridos;
    private String minutosRecorridos;
    private String ciudadOrigen;
    private String ciudadDestino;



    private int fondo;

    public DatosServiciosDTO(){

    }

    //public DatosServiciosDTO(String direccion, String nombre, String distanciaServicio, String telefono, String tipoServicio, String idServicio){
    public DatosServiciosDTO(String direccion, String distanciaServicio, String idServicio, String fechaFinalizacion, int fondo){
        super();
        this.direccion = direccion;
        //this.nombre = nombre;
        this.distanciaServicio = distanciaServicio;
//        this.telefono = telefono;
//        this.tipoServicio = tipoServicio;
        this.idServicio = idServicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.fondo = fondo;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDistanciaServicio() {
        return distanciaServicio;
    }

    public void setDistanciaServicio(String distanciaServicio) {
        this.distanciaServicio = distanciaServicio;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public int getFondo() {
        return fondo;
    }

    public void setFondo(int fondo) {
        this.fondo = fondo;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getLatitudDestino() {
        return latitudDestino;
    }

    public void setLatitudDestino(String latitudDestino) {
        this.latitudDestino = latitudDestino;
    }

    public String getLongitudDestino() {
        return longitudDestino;
    }

    public void setLongitudDestino(String longitudDestino) {
        this.longitudDestino = longitudDestino;
    }

    public String getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(String costoServicio) {
        this.costoServicio = costoServicio;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }


    public String getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(String fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIdPush() {
        return idPush;
    }

    public void setIdPush(String idPush) {
        this.idPush = idPush;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
    }

    public UnidadDTO getDatosMovil() {
        return datosMovil;
    }

    public void setDatosMovil(UnidadDTO datosMovil) {
        this.datosMovil = datosMovil;
    }

    public String getMensajeSaliente() {
        return mensajeSaliente;
    }

    public void setMensajeSaliente(String mensajeSaliente) {
        this.mensajeSaliente = mensajeSaliente;
    }

    public String getMensajeEntranteMovil() {
        return mensajeEntranteMovil;
    }

    public void setMensajeEntranteMovil(String mensajeEntranteMovil) {
        this.mensajeEntranteMovil = mensajeEntranteMovil;
    }

    public String getMensajeEntranteCentral() {
        return mensajeEntranteCentral;
    }

    public void setMensajeEntranteCentral(String mensajeEntranteCentral) {
        this.mensajeEntranteCentral = mensajeEntranteCentral;
    }

    public String getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(String estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public ArrayList<DatosValesDTO> getListaValesAsignados() {
        return listaValesAsignados;
    }

    public void setListaValesAsignados(ArrayList<DatosValesDTO> listaValesAsignados) {
        this.listaValesAsignados = listaValesAsignados;
    }

    public DatosValesDTO getDatoVale() {
        return datoVale;
    }

    public void setDatoVale(DatosValesDTO datoVale) {
        this.datoVale = datoVale;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoLogin() {
        return codigoLogin;
    }

    public void setCodigoLogin(String codigoLogin) {
        this.codigoLogin = codigoLogin;
    }

    public DatosUsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(DatosUsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public ArrayList<DatosServiciosDTO> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(ArrayList<DatosServiciosDTO> listaServicios) {
        this.listaServicios = listaServicios;
    }


    public String getFormaPagoDescripcion() {
        return formaPagoDescripcion;
    }

    public void setFormaPagoDescripcion(String formaPagoDescripcion) {
        this.formaPagoDescripcion = formaPagoDescripcion;
    }

    public String getDireccionGoogle() {
        return direccionGoogle;
    }

    public void setDireccionGoogle(String direccionGoogle) {
        this.direccionGoogle = direccionGoogle;
    }

    public DatosTarificadorDTO getTarificador() {
        return tarificador;
    }

    public void setTarificador(DatosTarificadorDTO tarificador) {
        this.tarificador = tarificador;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getEstadoServicioDescripcion() {
        return estadoServicioDescripcion;
    }

    public void setEstadoServicioDescripcion(String estadoServicioDescripcion) {
        this.estadoServicioDescripcion = estadoServicioDescripcion;
    }

    public DatosConductorDTO getDatosConductor() {
        return datosConductor;
    }

    public void setDatosConductor(DatosConductorDTO datosConductor) {
        this.datosConductor = datosConductor;
    }

    public String getOrigenSolicitud() {
        return origenSolicitud;
    }

    public void setOrigenSolicitud(String origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    public String getOrigenSolicitudDescripcion() {
        return origenSolicitudDescripcion;
    }

    public void setOrigenSolicitudDescripcion(String origenSolicitudDescripcion) {
        this.origenSolicitudDescripcion = origenSolicitudDescripcion;
    }

    public DatosPagoDTO getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(DatosPagoDTO datosPago) {
        this.datosPago = datosPago;
    }

    public DatosTarjetaCreditoDTO getDatosTarjeta() {
        return datosTarjeta;
    }

    public void setDatosTarjeta(DatosTarjetaCreditoDTO datosTarjeta) {
        this.datosTarjeta = datosTarjeta;
    }

    public ArrayList<DatosTarjetaCreditoDTO> getListaDatosTarjeta() {
        return listaDatosTarjeta;
    }

    public void setListaDatosTarjeta(ArrayList<DatosTarjetaCreditoDTO> listaDatosTarjeta) {
        this.listaDatosTarjeta = listaDatosTarjeta;
    }

    public ArrayList<UnidadDTO> getMovilesCercanos() {
        return movilesCercanos;
    }

    public void setMovilesCercanos(ArrayList<UnidadDTO> movilesCercanos) {
        this.movilesCercanos = movilesCercanos;
    }

    public DatosDireccionesDTO getDirFavorita() {
        return dirFavorita;
    }

    public void setDirFavorita(DatosDireccionesDTO dirFavorita) {
        this.dirFavorita = dirFavorita;
    }

    public ArrayList<DatosDireccionesDTO> getListadirFavoritas() {
        return listadirFavoritas;
    }

    public void setListadirFavoritas(ArrayList<DatosDireccionesDTO> listadirFavoritas) {
        this.listadirFavoritas = listadirFavoritas;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public DatosCalificacionDTO getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(DatosCalificacionDTO calificacion) {
        this.calificacion = calificacion;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getMetrosRecorridos() {
        return metrosRecorridos;
    }

    public void setMetrosRecorridos(String metrosRecorridos) {
        this.metrosRecorridos = metrosRecorridos;
    }

    public String getMinutosRecorridos() {
        return minutosRecorridos;
    }

    public void setMinutosRecorridos(String minutosRecorridos) {
        this.minutosRecorridos = minutosRecorridos;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }
}
