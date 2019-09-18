package com.fasepi.android.appet.usuario.Appet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.fasepi.android.appet.usuario.R;

import java.util.ArrayList;

/**
 * Created by jaimelombana on 17/08/17.
 */

public class DatosListasAdapter extends ArrayAdapter<DatosListas> {


    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    Context context;
    int layoutResourceId;
    ArrayList<DatosListas> data = new ArrayList<DatosListas>();


    public DatosListasAdapter(Context context, int layoutResourceId, ArrayList<DatosListas> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.direccion = (TextView)row.findViewById(R.id.txt_lst_direccion);
            holder.nombre = (TextView)row.findViewById(R.id.txt_lst_nombre);
            holder.costo = (TextView)row.findViewById(R.id.txt_lst_costo);
            holder.delete = (ImageButton) row.findViewById(R.id.btn_delete);
            holder.fondo = (LinearLayout) row.findViewById(R.id.layout_fondo_lista);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        DatosListas datosListas = data.get(position);
        holder.direccion.setText(datosListas.getDireccion());
        holder.nombre.setText(datosListas.getNombre());
        holder.costo.setText(datosListas.getCosto());
        holder.fondo.setBackgroundResource(datosListas.getColor());
        holder.delete.setVisibility(datosListas.getVisibilidadDelete());



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Delete Button Clicked", "**********");
                Intent new_intent = new Intent();
                new_intent.putExtra("CMD", "Delete");
                new_intent.putExtra("DATOS", ""+position);
                new_intent.setAction(ACTION_STRING_ACTIVITY);
                context.sendBroadcast(new_intent);

            }
        });

        return row;
    }

    static class WeatherHolder
    {
        TextView direccion;
        TextView nombre;
        TextView costo;
        ImageButton delete;
        LinearLayout fondo;
    }


}
