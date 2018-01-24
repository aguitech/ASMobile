package com.ascend.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascend.app.Detalle_deudor;
import com.ascend.app.Lista_deudores;
import com.ascend.app.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class DeudoresAdapter extends BaseAdapter {

    ArrayList<String> _listaRFCDeudores;
    ArrayList<String> _listaRazonSocialDeudores;
    ArrayList<String> _listaEstatus;
    ArrayList<String> _listaColor;
    ArrayList<String> _listaDiaHora;
    ArrayList<String> _listaIdDeudor;

    Context context;
    public String _url;
    public String _urlGo;
    public int _valueID;

    private static LayoutInflater inflater=null;

    public DeudoresAdapter(int valueID, Lista_deudores mainActivity, ArrayList<String> listaRFCDeudores, ArrayList<String> listaRazonSocialDeudores, ArrayList<String> listaEstatus, ArrayList<String> listaColor, ArrayList<String> listaDiaHora , ArrayList<String> listaIdDeudor){

        _listaIdDeudor = listaIdDeudor;

        _listaRFCDeudores = listaRFCDeudores;
        _listaRazonSocialDeudores = listaRazonSocialDeudores;
        _listaEstatus = listaEstatus;
        _listaDiaHora = listaDiaHora;
        _listaColor = listaColor;

        _valueID = valueID;

        context = mainActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _listaIdDeudor.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder{
        TextView RFCDeudor;
        TextView RazonSocialDeudor;
        TextView EstatusDeudor;
        TextView DiasHoras;
        LinearLayout statusColor;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.list_deudores, null);
        final int pos = i;

        //holder.nombreVeterinario = (TextView) rowView.findViewById(R.id.txtNombreVeterinario);


        holder.RFCDeudor = (TextView) rowView.findViewById(R.id.txtRFCDeudor);
        holder.RazonSocialDeudor = (TextView) rowView.findViewById(R.id.txtRazonSocialDeudor);
        holder.EstatusDeudor = (TextView) rowView.findViewById(R.id.txtEstatusPagoDeudor);
        holder.DiasHoras = (TextView) rowView.findViewById(R.id.txtDiasHoras);
        holder.statusColor = (LinearLayout) rowView.findViewById(R.id.statusColor);

        holder.RFCDeudor.setText(_listaRFCDeudores.get(i));
        holder.RazonSocialDeudor.setText(_listaRazonSocialDeudores.get(i));
        holder.EstatusDeudor.setText(_listaEstatus.get(i));
        holder.DiasHoras.setText(_listaDiaHora.get(i));



        //holder.imagenVeterinario = (ImageView) rowView.findViewById(R.id.imgVeterinario);
        //holder.agregarVeterinario = (Button) rowView.findViewById(R.id.buttonAgregar);
        //holder.detalleVeterinario = (ImageView) rowView.findViewById(R.id.imgDetalle);

        /*

        holder.agregarVeterinario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                _urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                Log.d("urlgo",_urlGo);
                new AgregarVeterinariosAdapter.RetrieveFeedTask().execute();
                Toast.makeText(holder.nombreVeterinario.getContext(), "Veterinario agregado con éxito.", Toast.LENGTH_LONG).show();
            }
        });
        */

        rowView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                //Log.d("urlgo",_urlGo);
                //new AgregarVeterinariosAdapter.RetrieveFeedTask().execute();

                //Toast.makeText(holder.nombreVeterinario.getContext(), "Veterinario agregado con éxito.", Toast.LENGTH_LONG).show();
                //Intent i = new Intent(context, Detalle_veterinario.class);
                //context.startActivity(i);


                //Intent intent = new Intent(context, Detalle_veterinario.class);
                //intent.putExtra("idveterinario", i);
                //context.startActivity(intent);

                Log.d("click", String.valueOf(i));
                Log.d("click", _listaIdDeudor.get(i));

                //Intent intent = new Intent(context, Detalle_cliente.class);
                //Intent intent = new Intent(context, Detalle_contrato.class);
                Intent intent = new Intent(context, Detalle_deudor.class);
                //intent.putExtra("idveterinario", _listaIdVeterinarios.get(i));
                //intent.putExtra("idcliente", _listaIdVeterinarios.get(i));
                intent.putExtra("iddeudor", _listaIdDeudor.get(i));
                context.startActivity(intent);

            }
        });

        if(_listaColor.get(i).equals("VERDE")){
            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackgroundColor(Color.GREEN);
            holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));

            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackground(context.getResources().getColor(R.color.verde_autorizado));
            holder.statusColor.setBackground(context.getResources().getDrawable(R.drawable.border_autorizado_radius));
        }else{
            holder.statusColor.setBackgroundColor(Color.RED);

            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackground(context.getResources().getColor(R.color.verde_autorizado));
            holder.statusColor.setBackground(context.getResources().getDrawable(R.drawable.border_denegado_radius));
        }

        /*
        holder.detalleVeterinario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                //Log.d("urlgo",_urlGo);
                //new AgregarVeterinariosAdapter.RetrieveFeedTask().execute();

                //Toast.makeText(holder.nombreVeterinario.getContext(), "Veterinario agregado con éxito.", Toast.LENGTH_LONG).show();
                //Intent i = new Intent(context, Detalle_veterinario.class);
                //context.startActivity(i);


                //Intent intent = new Intent(context, Detalle_veterinario.class);
                //intent.putExtra("idveterinario", i);
                //context.startActivity(intent);

                Log.d("click", String.valueOf(i));
                Log.d("click", _listaIdVeterinarios.get(i));

                //Intent intent = new Intent(context, Detalle_cliente.class);
                Intent intent = new Intent(context, Detalle_contrato.class);
                //intent.putExtra("idveterinario", _listaIdVeterinarios.get(i));
                //intent.putExtra("idcliente", _listaIdVeterinarios.get(i));
                intent.putExtra("iddeudor", _listaIdVeterinarios.get(i));
                context.startActivity(intent);

            }
        });
        */
/*
        holder.nombreVeterinario.setText(_listaNombreVeterinarios.get(i));
        _url = "http://hyperion.init-code.com/zungu/imagen_establecimiento/" + _listaImagenVeterinarios.get(i);
        Log.d("url", _url);
        Log.d("entro", "sii");

        Picasso.with(holder.imagenVeterinario.getContext()).load(_url)
                .into(holder.imagenVeterinario, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) holder.imagenVeterinario.getDrawable()).getBitmap();
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap);
                        circularBitmapDrawable.setCircular(true);
                        holder.imagenVeterinario.setImageDrawable(circularBitmapDrawable);
                    }
                    @Override
                    public void onError() {

                    }
                });

        */
        return rowView;
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlGo);
                URL url = new URL(_urlGo);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            } else {
                /*
                DESCOMENTAR
                Intent i = new Intent(context, Principal.class);
                context.startActivity(i);
                */
            }
            Log.i("INFO", response);
        }
    }
}
