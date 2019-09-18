package com.fasepi.android.appet.usuario.Clases;

import java.util.ArrayList;

/**
 * Created by Jaime on 20/06/2016.
 */
public class UnidadDTO {

    private String transaccion;
    private String pinUnidad;
    private String latitud;
    private String longitud;
    private String altitud;
    private String curso;
    private String velocidad;
    private String fecha;
    private String dirreccionIp;
    private String respuestaUnidad;
    private String documento;
    private String clave;
    private String nombreConductor;
    private String error;
    private ArrayList<String> listaPlacas;
    private String placa;
    private String correoElectronico;
    private String estado;
    private String idPush;
    private String tipoServicio;
    private String idMovil;
    private String marca;
    private String modelo;
    private DatosServiciosDTO servicioActual;
    private ArrayList<DatosServiciosDTO> serviciosSinReservar;
    private ArrayList<DatosServiciosDTO> serviciosCumplidos;
    private String dirIpCliente;
    private String imei;
    private ArrayList<DatosServiciosDTO> misServiciosEnReserva;
    private ArrayList<DatosServiciosDTO> serviciosAfuturoParaReservar;
    private String tipoLogin;
    private String mensaje;
    private String destinoMensaje;//1: mesaje al usuario. 2:Mensaje a la omega
    private String soloNombres;
    private String soloApellidos;
    private String celConductor;
    //private ListaMovilesAutorizados listaMovilesAutorizados;
    private DatosServiciosDTO reservarServicio;
    private String propina;
    private String mensajeSaliente;
    private String mensajeEntrante;
    private DatosServiciosDTO cancelarServicio;
    private ArrayList<DatosValesDTO> listaVales;
    private DatosValesDTO datosVale;
    private String formaPago;
    private String fotoTaxista;
    private String pais;
    private String ciudad;
    private String documentoResponsable;
    private String tipoMovil;
    private String version;
    private String tipoProyecto;



    public UnidadDTO() {
        listaPlacas = new ArrayList<>();
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getPinUnidad() {
        return pinUnidad;
    }

    public void setPinUnidad(String pinUnidad) {
        this.pinUnidad = pinUnidad;
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

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDirreccionIp() {
        return dirreccionIp;
    }

    public void setDirreccionIp(String dirreccionIp) {
        this.dirreccionIp = dirreccionIp;
    }

    public String getRespuestaUnidad() {
        return respuestaUnidad;
    }

    public void setRespuestaUnidad(String respuestaUnidad) {
        this.respuestaUnidad = respuestaUnidad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<String> getListaPlacas() {
        return listaPlacas;
    }

    public void setListaPlacas(ArrayList<String> listaPlacas) {
        this.listaPlacas = listaPlacas;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdPush() {
        return idPush;
    }

    public void setIdPush(String idPush) {
        this.idPush = idPush;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public DatosServiciosDTO getServicioActual() {
        return servicioActual;
    }

    public void setServicioActual(DatosServiciosDTO servicioActual) {
        this.servicioActual = servicioActual;
    }

    public ArrayList<DatosServiciosDTO> getServiciosSinReservar() {
        return serviciosSinReservar;
    }

    public void setServiciosSinReservar(ArrayList<DatosServiciosDTO> serviciosSinReservar) {
        this.serviciosSinReservar = serviciosSinReservar;
    }

    public ArrayList<DatosServiciosDTO> getServiciosCumplidos() {
        return serviciosCumplidos;
    }

    public void setServiciosCumplidos(ArrayList<DatosServiciosDTO> serviciosCumplidos) {
        this.serviciosCumplidos = serviciosCumplidos;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public ArrayList<DatosServiciosDTO> getMisServiciosEnReserva() {
        return misServiciosEnReserva;
    }

    public void setMisServiciosEnReserva(ArrayList<DatosServiciosDTO> misServiciosEnReserva) {
        this.misServiciosEnReserva = misServiciosEnReserva;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestinoMensaje() {
        return destinoMensaje;
    }

    public void setDestinoMensaje(String destinoMensaje) {
        this.destinoMensaje = destinoMensaje;
    }

    public String getSoloNombres() {
        return soloNombres;
    }

    public void setSoloNombres(String soloNombres) {
        this.soloNombres = soloNombres;
    }

    public String getSoloApellidos() {
        return soloApellidos;
    }

    public void setSoloApellidos(String soloApellidos) {
        this.soloApellidos = soloApellidos;
    }

    public String getCelConductor() {
        return celConductor;
    }

    public void setCelConductor(String celConductor) {
        this.celConductor = celConductor;
    }

    public DatosServiciosDTO getReservarServicio() {
        return reservarServicio;
    }

    public void setReservarServicio(DatosServiciosDTO reservarServicio) {
        this.reservarServicio = reservarServicio;
    }

    public String getDirIpCliente() {
        return dirIpCliente;
    }

    public void setDirIpCliente(String dirIpCliente) {
        this.dirIpCliente = dirIpCliente;
    }

    public ArrayList<DatosServiciosDTO> getServiciosAfuturoParaReservar() {
        return serviciosAfuturoParaReservar;
    }

    public void setServiciosAfuturoParaReservar(ArrayList<DatosServiciosDTO> serviciosAfuturoParaReservar) {
        this.serviciosAfuturoParaReservar = serviciosAfuturoParaReservar;
    }

//    public ListaMovilesAutorizados getListaMovilesAutorizados() {
//        return listaMovilesAutorizados;
//    }
//
//    public void setListaMovilesAutorizados(ListaMovilesAutorizados listaMovilesAutorizados) {
//        this.listaMovilesAutorizados = listaMovilesAutorizados;
//    }


    public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
    }

    public String getMensajeSaliente() {
        return mensajeSaliente;
    }

    public void setMensajeSaliente(String mensajeSaliente) {
        this.mensajeSaliente = mensajeSaliente;
    }

    public String getMensajeEntrante() {
        return mensajeEntrante;
    }

    public void setMensajeEntrante(String mensajeEntrante) {
        this.mensajeEntrante = mensajeEntrante;
    }

    public DatosServiciosDTO getCancelarServicio() {
        return cancelarServicio;
    }

    public void setCancelarServicio(DatosServiciosDTO cancelarServicio) {
        this.cancelarServicio = cancelarServicio;
    }

    public ArrayList<DatosValesDTO> getListaVales() {
        return listaVales;
    }

    public void setListaVales(ArrayList<DatosValesDTO> listaVales) {
        this.listaVales = listaVales;
    }

    public DatosValesDTO getDatosVale() {
        return datosVale;
    }

    public void setDatosVale(DatosValesDTO datosVale) {
        this.datosVale = datosVale;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getFotoTaxista() {
        return fotoTaxista;
    }

    public void setFotoTaxista(String fotoTaxista) {
        this.fotoTaxista = fotoTaxista;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDocumentoResponsable() {
        return documentoResponsable;
    }

    public void setDocumentoResponsable(String documentoResponsable) {
        this.documentoResponsable = documentoResponsable;
    }

    public String getTipoMovil() {
        return tipoMovil;
    }

    public void setTipoMovil(String tipoMovil) {
        this.tipoMovil = tipoMovil;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }
}
