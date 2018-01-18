package com.ascend.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.app.Detalle_deudor;
import com.ascend.app.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class DocumentosAdapter extends BaseAdapter {

    ArrayList<String> _listaNombreVeterinarios;
    ArrayList<String> _listaFolioFiscal;
    ArrayList<String> _listaTotalFactura;
    ArrayList<String> _listaStatusFactura;
    ArrayList<String> _listaStatusColor;




    ArrayList<String> _listaStatusDeudor;
    ArrayList<String> _listaObservaciones;
    ArrayList<String> _listaAtendio;



    ArrayList<String> _listaImagenVeterinarios;
    ArrayList<String> _listaIdVeterinarios;
    Context context;
    public String _idDeudor;
    public String _url;
    public String _observaciones;
    public String _atendio;
    public String _fechaProgramacion;

    public int _idStatus;
    public int _idInconformidad;

    public String _urlGo;
    public int _valueID;

    private static LayoutInflater inflater=null;

    public DocumentosAdapter(String idDeudor, int idStatus, int idInconformidad, String observaciones, String atendio, String fechaProgramacion,  int valueID, Detalle_deudor mainActivity, ArrayList<String> listaNombreVeterinarios, ArrayList<String> listaFolioFiscal, ArrayList<String> listaTotalFactura, ArrayList<String> listaStatusFactura, ArrayList<String> listaStatusColor,  ArrayList<String> listaStatusDeudor,  ArrayList<String> listaObservaciones,  ArrayList<String> listaAtendio, ArrayList<String> listaImagenVeterinarios, ArrayList<String> listaIdVeterinarios){
        _listaIdVeterinarios = listaIdVeterinarios;
        _listaImagenVeterinarios = listaImagenVeterinarios;
        _listaNombreVeterinarios = listaNombreVeterinarios;

        _listaFolioFiscal = listaFolioFiscal;
        _listaTotalFactura = listaTotalFactura;
        _listaStatusFactura = listaStatusFactura;
        _listaStatusColor = listaStatusColor;

        //_listaStatusDeudor;  _listaObservaciones; _listaAtendio
        _listaStatusDeudor = listaStatusDeudor;
        _listaObservaciones = listaObservaciones;
        _listaAtendio = listaAtendio;


        _idDeudor = idDeudor;
        _valueID = valueID;
        _idStatus = idStatus;
        _idInconformidad = idInconformidad;
        _observaciones = observaciones;
        _atendio = atendio;
        _fechaProgramacion = fechaProgramacion;

        context = mainActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _listaIdVeterinarios.size();
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
        TextView nombreVeterinario;

        TextView folioFiscal;
        TextView totalFactura;
        TextView statusFactura;
        TextView statusDeudor;
        TextView observaciones;
        LinearLayout statusColor;

        CheckBox checkboxDocumento;



        ImageView imagenVeterinario;
        Button agregarVeterinario;
        ImageView imgActualizar;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.list_documentos, null);
        final int pos = i;

        holder.nombreVeterinario = (TextView) rowView.findViewById(R.id.txtNombreVeterinario);


        holder.folioFiscal = (TextView) rowView.findViewById(R.id.txtFolioFactura);
        holder.totalFactura = (TextView) rowView.findViewById(R.id.txtTotalFactura);
        holder.statusFactura = (TextView) rowView.findViewById(R.id.txtStatusFactura);
        holder.statusColor = (LinearLayout) rowView.findViewById(R.id.statusColor);

        holder.statusDeudor = (TextView) rowView.findViewById(R.id.txtStatusDeudor);
        holder.observaciones = (TextView) rowView.findViewById(R.id.txtObservaciones);


        holder.checkboxDocumento = (CheckBox) rowView.findViewById(R.id.checkboxDocumento);


        holder.imagenVeterinario = (ImageView) rowView.findViewById(R.id.imgVeterinario);
        //holder.agregarVeterinario = (Button) rowView.findViewById(R.id.buttonAgregar);
        holder.imgActualizar = (ImageView) rowView.findViewById(R.id.imgActualizar);

        //convertView.setBackgroundColor(Color.BLACK);
        /*
        if(_listaStatusColor.get(i).equals("VERDE")){
            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackgroundColor(Color.GREEN);
            holder.checkboxDocumento.setChecked(true);
            holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
        }else{
            holder.checkboxDocumento.setChecked(false);
            holder.statusColor.setBackgroundColor(Color.RED);
        }
        */


        /*

        holder.agregarVeterinario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                _urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                Log.d("urlgo",_urlGo);
                new AgregarVeterinariosAdapter.RetrieveFeedTask().execute();
                Toast.makeText(holder.nombreVeterinario.getContext(), "Veterinario agregado con éxito.", Toast.LENGTH_LONG).show();
            }
        });




        holder.imgEliminar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //confirm(holder.imgMascota.getContext(), position, "Eliminar mascota: " + _listaNombreMascota.get(position));
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Estas seguro que desea borrarlo ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        _urlGo = "http://hyperion.init-code.com/zungu/app/vt_eliminar_servicio.php?delete="  + _listaImgMascotas.get(position);
                        Log.d("urlgo",_urlGo);
                        new ServicioAdapter.RetrieveFeedTask().execute();
                        //Toast.makeText(holder.nombreTienda.getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();
                        _listaNombreMascota.remove(position);
                        _listaNombreCliente.remove(position);
                        _listaImgMascotas.remove(position);

                        notifyDataSetChanged();

                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

            }
        });

        */

        //holder.nombreVeterinario.setText(_listaNombreVeterinarios.get(i));

        holder.folioFiscal.setText(_listaFolioFiscal.get(i));
        holder.totalFactura.setText(_listaTotalFactura.get(i));
        holder.statusFactura.setText(_listaStatusFactura.get(i));
        holder.statusDeudor.setText(_listaStatusDeudor.get(i));
        holder.observaciones.setText(_listaObservaciones.get(i));


        //holder.nombreVeterinario.setText(_listaNombreVeterinarios.get(i));


        //holder.nombreVeterinario.setTextColor(Color.RED);



        //checkBox.setChecked(!checkBox.isChecked());
        //holder.checkboxDocumento.setChecked(!holder.checkboxDocumento.isChecked());
        //holder.checkboxDocumento.setChecked(true);

        holder.checkboxDocumento.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //confirm(holder.imgMascota.getContext(), position, "Eliminar mascota: " + _listaNombreMascota.get(position));



                /*
                public void onClick(View v) {
                    ((Activity)context).finish();

                }

                // + "&atendio=" + _atendio + "&fecha_programacion=" + _fechaProgramacion
                */

                if(_observaciones.equals("") || _atendio.equals("")){
                    Toast.makeText(holder.folioFiscal.getContext(), "Todos los campos son necesarios.", Toast.LENGTH_LONG).show();
                }else{
                    //holder.statusColor.setBackgroundColor(Color.BLUE);
                    holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
                    //holder.statusColor.setBackground(context.getResources().getColor(R.color.verde_autorizado));
                    holder.statusColor.setBackground(context.getResources().getDrawable(R.drawable.border_autorizado_radius));

                    //_urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + _observaciones;
                    //_urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + _observaciones + "&atendio=" + _atendio;
                    //URLEncoder.encode(
                    //URLEncoder.encode(

                    //_urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + _observaciones + "&atendio=" + _atendio + "&fecha_programacion=" + _fechaProgramacion;
                    //_urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + URLEncoder.encode(_observaciones) + "&atendio=" + URLEncoder.encode(_atendio) + "&fecha_programacion=" + _fechaProgramacion;
                    _urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + URLEncoder.encode(_observaciones) + "&atendio=" + URLEncoder.encode(_atendio) + "&id_deudor=" + URLEncoder.encode(_idDeudor) +   "&fecha_programacion=" + _fechaProgramacion;



                    Log.d("id_estatus go", Integer.toString(_idStatus));
                    Log.d("urlgo",_urlGo);
                    new DocumentosAdapter.RetrieveFeedTask().execute();

                    //Toast.makeText(holder.folioFiscal.getContext(), "Documento agregado con éxito.", Toast.LENGTH_LONG).show();

                    //notifyDataSetChanged();


                    //((Detalle_deudor)context).finish();

                    //Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                }


            }
        });

        /*
        holder.imgActualizar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //confirm(holder.imgMascota.getContext(), position, "Eliminar mascota: " + _listaNombreMascota.get(position));

                holder.statusColor.setBackgroundColor(Color.YELLOW);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Estas seguro que desea actualizar?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        //holder.statusColor.setBackgroundColor(Color.GREEN);
                        holder.statusColor.setBackgroundColor(Color.BLUE);
                        //holder.statusColor.getContext().setBackgroundColor(Color.GREEN);

                        _urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + _observaciones + "&atendio=" + _atendio;
                        Log.d("id_estatus go", Integer.toString(_idStatus));
                        Log.d("urlgo",_urlGo);
                        new DocumentosAdapter.RetrieveFeedTask().execute();

                        Toast.makeText(holder.folioFiscal.getContext(), "Documento agregado con éxito.", Toast.LENGTH_LONG).show();



                        _listaIdVeterinarios.remove(i);
                        _listaImagenVeterinarios.remove(i);
                        _listaNombreVeterinarios.remove(i);

                        _listaFolioFiscal.remove(i);
                        _listaTotalFactura.remove(i);
                        _listaStatusFactura.remove(i);
                        _listaStatusColor.remove(i);

                        //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_eliminar_servicio.php?delete="  + _listaImgMascotas.get(position);
                        //Log.d("urlgo",_urlGo);
                        //new ServicioAdapter.RetrieveFeedTask().execute();
                        //Toast.makeText(holder.nombreTienda.getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();
                        //_listaNombreMascota.remove(position);
                        //_listaNombreCliente.remove(position);
                        //_listaImgMascotas.remove(position);





                        notifyDataSetChanged();

                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

            }
        });
        holder.statusColor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //confirm(holder.imgMascota.getContext(), position, "Eliminar mascota: " + _listaNombreMascota.get(position));
                holder.statusColor.setBackgroundColor(Color.YELLOW);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Estas seguro que desea actualizar?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {


                        holder.statusColor.setBackgroundColor(Color.BLUE);

                        _urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus) + "&id_inconformidad=" + Integer.toString(_idInconformidad) + "&observaciones=" + _observaciones;;
                        Log.d("id_estatus go", Integer.toString(_idStatus));
                        Log.d("urlgo",_urlGo);
                        new DocumentosAdapter.RetrieveFeedTask().execute();
                        Toast.makeText(holder.folioFiscal.getContext(), "Documento agregado con éxito.", Toast.LENGTH_LONG).show();



                        //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_eliminar_servicio.php?delete="  + _listaImgMascotas.get(position);
                        //Log.d("urlgo",_urlGo);
                        //new ServicioAdapter.RetrieveFeedTask().execute();
                        //Toast.makeText(holder.nombreTienda.getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();

                        //_listaNombreMascota.remove(position);
                        //_listaNombreCliente.remove(position);
                        //_listaImgMascotas.remove(position);





                        notifyDataSetChanged();

                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

            }
        });
        */

        /*
        Click a toda la lista
        rowView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                //Log.d("urlgo",_urlGo);
                //new AgregarVeterinariosAdapter.RetrieveFeedTask().execute();

                //Toast.makeText(holder.nombreVeterinario.getContext(), "Veterinario agregado con éxito.", Toast.LENGTH_LONG).show();
                //Intent i = new Intent(context, Detalle_veterinario.class);
                //context.startActivity(i);


                holder.statusColor.setBackgroundColor(Color.GREEN);
                //Intent intent = new Intent(context, Detalle_veterinario.class);
                //intent.putExtra("idveterinario", i);
                //context.startActivity(intent);

                Log.d("click", String.valueOf(i));
                Log.d("click", _listaIdVeterinarios.get(i));


                //DESCOMENTAR
                //Intent intent = new Intent(context, Detalle_cliente.class);
                //Intent intent = new Intent(context, Detalle_contrato.class);
                //intent.putExtra("idveterinario", _listaIdVeterinarios.get(i));
                //intent.putExtra("idcliente", _listaIdVeterinarios.get(i));


                //Intent intent = new Intent(context, Lista_deudores.class);
                //intent.putExtra("iddeudor", _listaIdVeterinarios.get(i));
                //context.startActivity(intent);


                //_urlGo = "http://hyperion.init-code.com/zungu/app/vt_agregar_id_veterinario.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos);
                //_urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus);
                _urlGo = "http://ascendsystem.net/ejecutivo/app_actualizar_documento.php?idu=" + Integer.toString(_valueID) + "&idv=" + _listaIdVeterinarios.get(pos) + "&id_status=" + Integer.toString(_idStatus);
                Log.d("id_estatus go", Integer.toString(_idStatus));
                Log.d("urlgo",_urlGo);
                new DocumentosAdapter.RetrieveFeedTask().execute();
                Toast.makeText(holder.nombreVeterinario.getContext(), "Documento agregado con éxito.", Toast.LENGTH_LONG).show();

            }
        });
        */

        /*

        Cargar imagen en caso de ser necesaria
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
        if(_listaStatusColor.get(i).equals("VERDE")){
            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackgroundColor(Color.GREEN);
            holder.checkboxDocumento.setChecked(true);
            holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));

            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackground(context.getResources().getColor(R.color.verde_autorizado));
            holder.statusColor.setBackground(context.getResources().getDrawable(R.drawable.border_autorizado_radius));
        }else{
            holder.checkboxDocumento.setChecked(false);
            holder.statusColor.setBackgroundColor(Color.RED);

            //holder.statusColor.setBackgroundColor(context.getResources().getColor(R.color.verde_autorizado));
            //holder.statusColor.setBackground(context.getResources().getColor(R.color.verde_autorizado));
            holder.statusColor.setBackground(context.getResources().getDrawable(R.drawable.border_denegado_radius));
        }
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
