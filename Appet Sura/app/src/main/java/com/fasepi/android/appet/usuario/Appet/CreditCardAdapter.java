package com.fasepi.android.appet.usuario.Appet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fasepi.android.appet.usuario.Clases.ListaTarjetas;
import com.fasepi.android.appet.usuario.R;


import java.util.ArrayList;

/**
 * Created by jaimelombana on 26/07/17.
 */

public class CreditCardAdapter extends ArrayAdapter<ListaTarjetas> {


    private final String ACTION_STRING_ACTIVITY = "ToActivity";
    Context context;
    int layoutResourceId;
    ArrayList<ListaTarjetas> data = new ArrayList<ListaTarjetas>();
    private LayoutInflater mInflater;

//    public CreditCardAdapter(Context context, ArrayList<ListaTarjetas> results) {
//        searchArrayList = results;
//        mInflater = LayoutInflater.from(context);
//        this.context = context;
//    }


    public CreditCardAdapter(Context context, int layoutResourceId,
                             ArrayList<ListaTarjetas> data) {


        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }





    public View getView(final int position, View convertView, ViewGroup parent) {


        View row = convertView;
        ViewHolder holder = null;


        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtNumero = (TextView) row.findViewById(R.id.numbertc);
            holder.txtOtro = (TextView) row.findViewById(R.id.otro_tc);
            holder.btn_delete = (Button) row.findViewById(R.id.delete);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final ListaTarjetas listaTarjetas = data.get(position);
        holder.txtNumero.setText(listaTarjetas.getNumber());
        holder.txtOtro.setText(listaTarjetas.getOtro());
        holder.id = listaTarjetas.getId();

        holder.btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent new_intent = new Intent();
                new_intent.putExtra("CMD", "tarjetaCredito");
                new_intent.putExtra("DATOS", listaTarjetas.getId());
                new_intent.setAction(ACTION_STRING_ACTIVITY);
                context.sendBroadcast(new_intent);


            }
        });

        return row;
    }

    static class ViewHolder {
        TextView txtNumero;
        TextView txtOtro;
        Button btn_delete;
        String id;

    }
}
