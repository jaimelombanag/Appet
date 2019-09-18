package com.fasepi.android.appet.usuario.Clases;

/**
 * Created by jaimelombana on 26/07/17.
 */

public class ListaTarjetas {

    private String number = "";
    private String otro = "";
    private String id = "";



    public ListaTarjetas(String number, String otro, String id){
        super();
        this.number = number;
        this.otro = otro;
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
