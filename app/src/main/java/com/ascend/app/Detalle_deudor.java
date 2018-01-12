package com.ascend.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.app.adapters.DocumentosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class Detalle_deudor extends AppCompatActivity {
    ListView lv;
    private String _urlGet;
    private String _url;
    public static final String idu = "idu";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private int valueID = 0;
    private String idStatus = "";

    long restr;

    public static ArrayList<String> listaNombreVeterinarios = new ArrayList<String>();

    public static ArrayList<String> listaFolioFiscal = new ArrayList<String>();
    public static ArrayList<String> listaTotalFactura = new ArrayList<String>();
    public static ArrayList<String> listaStatusFactura = new ArrayList<String>();
    public static ArrayList<String> listaStatusDeudor = new ArrayList<String>();
    public static ArrayList<String> listaObservaciones = new ArrayList<String>();
    public static ArrayList<String> listaAtendio = new ArrayList<String>();
    public static ArrayList<String> listaStatusColor = new ArrayList<String>();


    public static ArrayList<String> listaImagenVeterinarios = new ArrayList<String>();
    public static ArrayList<String> listaIdVeterinario = new ArrayList<String>();

    public Detalle_deudor mActivity = this;
    public DocumentosAdapter _mascotasAdapter;

    public Detalle_deudor _activity = this;
    RecyclerView lvMascotas;

    private String _urlNotificaciones;

    private String _urlStatus;
    private String _urlInconformidad;
    String idString;


    Date date_seleccionada;
    Date date_hoy;

    CharSequence[] items;
    //public int selStatus = 0;
    private int selStatus = 0;
    private int selInconformidad = 0;

    private String observaciones = "";
    EditText txtObservacionesFactura;
    EditText txtAtendioFactura;
    Button btnMesDia;

    public ArrayList<String> _status = new ArrayList<String>();
    public  ArrayList<Integer> _ids_status = new ArrayList<Integer>();

    public ArrayList<String> _inconformidad = new ArrayList<String>();
    public  ArrayList<Integer> _ids_inconformidad = new ArrayList<Integer>();

    String fechaValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_deudor);

        //lv = (ListView) findViewById(R.id.list_pagos);
        lv = (ListView) findViewById(R.id.list_facturas);
        txtObservacionesFactura = (EditText) findViewById(R.id.txtObservacionesFactura);

        txtAtendioFactura = (EditText) findViewById(R.id.txtAtendioFactura);

        btnMesDia = (Button) findViewById(R.id.btnMesDia);

        //showMsg("test");

        //String idString;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            idString= null;

        } else {
            //idString= extras.getString("idcliente");
            idString= extras.getString("iddeudor");
            Log.d("id_vet", idString);

        }


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        valueID = sharedpreferences.getInt("idu", 0);

        /*
        lvMascotas = (RecyclerView) findViewById(R.id.lvVeterinarios);

        //_urlGet = "http://hyperion.init-code.com/zungu/app/vt_principal.php?id_editar=" + Integer.toString(valueID);
        //_urlGet = "http://hyperion.init-code.com/zungu/app/vt_principal.php?id_editar=" + idString;

        _urlGet = "http://hyperion.init-code.com/zungu/app/vt_principal.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        new Detalle_veterinario.RetrieveFeedTaskGet().execute();

        _urlNotificaciones = "http://hyperion.init-code.com/zungu/app/vt_get_numero_notificaciones.php?idv=" + valueID;
        new Detalle_veterinario.RetrieveFeedTaskNotificaciones().execute();
        */
        //_urlGet = "http://hyperion.init-code.com/zungu/app/vt_principal.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        //_urlGet = "http://thekrakensolutions.com/cobradores/android_get_cliente.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        //_urlGet = "http://thekrakensolutions.com/cobradores/android_get_contrato.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        _urlGet = "http://ascendsystem.net/ejecutivo/app_detalle_deudor.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        new Detalle_deudor.RetrieveFeedTaskGet().execute();


        //_url = "http://ascendsystem.net/ejecutivo/app_deudores_hoy.php?id=" + Integer.toString(valueID);
        //_url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + Integer.toString(valueID);


        /**
         * DESCOMENTAR
        _url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString;
        Log.d("url_contratos", _url);
        new Detalle_deudor.RetrieveFeedTask().execute();
        */
        /**
        _url = "http://thekrakensolutions.com/cobradores/android_get_contratos.php?id=" + Integer.toString(valueID);
        Log.d("url_veterinarios", _url);
        new Detalle_contrato.RetrieveFeedTask().execute();
        */
        _urlStatus = "http://ascendsystem.net/ejecutivo/app_obtener_status.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus;
        Log.d("url_mascota", _urlStatus);
        new Detalle_deudor.RetrieveFeedTaskStatus().execute();

        _urlInconformidad = "http://ascendsystem.net/ejecutivo/app_obtener_inconformidad.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus;
        Log.d("url_mascota", _urlInconformidad);
        new Detalle_deudor.RetrieveFeedTaskInconformidad().execute();



        final Button btnMesDia = (Button) findViewById(R.id.btnMesDia);
        final Button btnHora = (Button) findViewById(R.id.btnHora);

        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        //c.add(Calendar.MONTH, 3);


        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute1 = c.get(Calendar.MINUTE);
        final Calendar v = Calendar.getInstance();
        final int year2 = v.get(Calendar.YEAR);
        final int month2 = v.get(Calendar.MONTH);
        final int day2 = v.get(Calendar.DAY_OF_MONTH);
        final int hour2 = v.get(Calendar.HOUR_OF_DAY);
        final int minute2 = v.get(Calendar.MINUTE);

        if (savedInstanceState == null) {
            //Bundle extras = getIntent().getExtras();
            if(extras == null) {
                //fechaValor = null;
                fechaValor = "";
                btnMesDia.setText("Mes/Día");
            } else {
                fechaValor = extras.getString("valor_fecha");
                //showMsg(fechaValor);
                btnMesDia.setText(fechaValor);

            }
        }


        btnMesDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepick = new DatePickerDialog(Detalle_deudor.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        // Date results here
                        //showMsg(String.valueOf(day));
                        /*
                        showMsg(String.valueOf(year));
                        showMsg(String.valueOf(month));
                        showMsg(String.valueOf(dayOfMonth));
                        */



                        Date fecha_hoy = new Date();
                        //System.out.println(fecha_hoy);
                        String valor_fecha = new SimpleDateFormat("yyyy-MM-dd").format(fecha_hoy);

                        //String fecha_completa = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        //String fecha_completa = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth + 30);
                        String fecha_completa = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);


                        //String dtStart = "2010-10-15T09:27:37Z";
                        String dtStart = valor_fecha;
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            //Date date_hoy = format.parse(dtStart);
                            date_hoy = format.parse(dtStart);
                            //System.out.println(date);
                            //Log.d("valor Inicio", date_hoy.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String dtFin = fecha_completa;
                        try {
                            //Date date_seleccionada = format.parse(dtFin);
                            date_seleccionada = format.parse(dtFin);
                            //System.out.println(date);
                            //Log.d("valor Fin", date_seleccionada.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(date_seleccionada.compareTo(date_hoy)>0){
                            //ES MAYOR
                            //showMsg("Test");
                            btnMesDia.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth));
                        }else if(date_seleccionada.compareTo(date_hoy)<0){
                            //ES MENOR
                            //showMsg("Test 2");
                            btnMesDia.setText(valor_fecha);
                        }else{
                            //ES IGUAL
                            //showMsg("Test 3");
                            btnMesDia.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth));
                        }


                        /*
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date convertedCurrentDate = sdf.parse("2013-09-18");

                        //SimpleDateFormat.parse(String);

                        String date=sdf.format(convertedCurrentDate );
                        System.out.println(date);
                        */


                        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


                        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        //Date valor_fecha_final = formato.parse(valor_fecha);

                        //Date valor_fecha_final = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(valor_fecha);
                        //Date valor_fecha_final = new SimpleDateFormat("yyyy-MM-dd").parse(valor_fecha);

                        //Date valor_fecha_final = valor_fecha;
/*
                        if(valor_fecha_final.compareTo(fecha_completa_final)){

                        }
*/

                        Log.d("res", valor_fecha);





                        //btnMesDia.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth));



                        /*
                        Aqui la hora automatica
                        TimePickerDialog timepick = new TimePickerDialog(Detalle_deudor.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                String minuto = "";

                                // Time results here
                                //showMsg(String.valueOf(hourOfDay));
                                //showMsg(String.valueOf(minute));

                                //if(minute.getText().toString().length() < 1){
                                if(String.valueOf(minute).toString().length() == 1){
                                    //showMsg(String.valueOf(minute));
                                    minuto = "0" + String.valueOf(minute).toString();
                                }else{
                                    minuto = String.valueOf(minute);
                                }

                                //btnHora.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                                //btnHora.setText(String.valueOf(hourOfDay) + ":" + minute);
                                btnHora.setText(String.valueOf(hourOfDay) + ":" + minuto);

                            }
                        }, hour, minute1, true);
                        timepick.setTitle("Selecciona Hora");
                        timepick.show();
                        */
                    }
                } ,year,month,day);
                datepick.getDatePicker().setMinDate(restr);
                datepick.setTitle("Selecciona Fecha");
                datepick.show();
            }
        });

    }
    private void showMsg(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    class RetrieveFeedTaskGet extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }



        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url Contrato: ", _urlGet);
                URL url = new URL(_urlGet);
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
                TextView lblNombre = (TextView) findViewById(R.id.lblNombre);
                TextView lblDireccion = (TextView) findViewById(R.id.lblDireccion);
                ImageView foto = (ImageView) findViewById(R.id.imgFoto);
                */

                TextView lblRazonSocial = (TextView) findViewById(R.id.txtRazonSocial);

                TextView txtDireccion = (TextView) findViewById(R.id.txtDireccion);


                Button btnMesDia = (Button) findViewById(R.id.btnMesDia);

                //URL IMAGEN
                //final ImageView fotoVeterinario = (ImageView) findViewById(R.id.imgVeterinario);

                try {

                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();


                    //String _razon_social = object.getString("RFC_deudor") + " - " + object.getString("deudor") + " " + object.getString("cuenta_deudor");
                    String _razon_social = "";

                    //String _telefono_vo = object.getString("telefono_casa");
                    //String _imagen_vo = object.getString("sexo");
                    //String _imagen_vo = object.getString("imagen");

                    if(object.getString("cuenta_deudor").length() > 3){
                        _razon_social += object.getString("cuenta_deudor");
                    }
                    if(object.getString("RFC_deudor").length() > 3){
                        _razon_social += " | " + object.getString("RFC_deudor");
                    }
                    if(object.getString("deudor").length() > 3){
                        _razon_social += " | " + object.getString("deudor");
                    }


                    btnMesDia.setText(object.getString("fecha_programacion"));

                    if(_razon_social.length() > 3)
                        lblRazonSocial.setText(_razon_social);

                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais");
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais");


                    /*
                    if(object.getString("calle").length() > 3){
                        txtDireccion_.concat(object.getString("calle"));

                        if(object.getString("numero_exterior").length() > 1){
                            txtDireccion_.concat(" " + object.getString("numero_exterior"));

                            if(object.getString("numero_interior").length() > 1){
                                txtDireccion_.concat(" " + object.getString("numero_interior"));

                                if(object.getString("colonia").length() > 3){
                                    txtDireccion_.concat(" Colonia " + object.getString("colonia"));

                                    if(object.getString("municipio").length() > 1){
                                        txtDireccion_.concat(" Delegación/Municipio " + object.getString("municipio"));

                                        if(object.getString("estado").length() > 1){
                                            txtDireccion_.concat("  Estado " + object.getString("estado"));


                                            if(object.getString("codigo_postal").length() > 1){
                                                txtDireccion_.concat("  C.P. " + object.getString("codigo_postal"));


                                                if(object.getString("pais").length() > 1){
                                                    txtDireccion_.concat("  País " + object.getString("pais"));











                                                }









                                            }









                                        }




                                    }



                                }


                            }




                        }

                    }
                    */
                    String txtDireccion_ = "";
                    if(object.getString("calle").length() > 3){
                        txtDireccion_ += object.getString("calle");
                    }
                    if(object.getString("numero_exterior").length() > 1){
                        txtDireccion_ += " " + object.getString("numero_exterior");
                    }
                    if(object.getString("numero_interior").length() > 1){
                        txtDireccion_ += " " + object.getString("numero_interior");
                    }
                    if(object.getString("colonia").length() > 3){
                        txtDireccion_ += " Colonia " + object.getString("colonia");
                    }
                    if(object.getString("municipio").length() > 1){
                        txtDireccion_ += " Delegación/Municipio " + object.getString("municipio");
                    }
                    if(object.getString("estado").length() > 1){
                        txtDireccion_ += "  Estado " + object.getString("estado");
                    }
                    if(object.getString("codigo_postal").length() > 1){
                        txtDireccion_ += "  C.P. " + object.getString("codigo_postal");
                    }
                    if(object.getString("pais").length() > 1){
                        txtDireccion_ += "  País " + object.getString("pais");
                    }

                    if(txtDireccion_.length() > 3)
                        txtDireccion.setText(txtDireccion_);

                    /*
                    if(txtDireccion_.length() > 3)
                        txtDireccion.setText(txtDireccion_);
                    */


                    Log.d("INFO", _razon_social);


                    //showMsg("tesst2");

                    //showMsg(_telefono_vo);


                    /*
                    {"id_cliente":"1","cliente":"","numero_cliente":"0","fecha_nacimiento":"0000-00-00","sexo":"mkl","imagen":"",":"klmkl","":"mkl","":"mklm","":"klm","":"klmkl","telefono_casa":"","telefono_celular":"","telefono_oficina":"","":"","":"","ocupacion":"","direccion_trabajo":"","nombre_pareja":"","ocupacion_pareja":"","telefono_pareja":"","complexion":"","estatura":"","tez":"","edad_rango":"","cabello":"","color_cabello":"","tipo_identificacion":"","numero_identificacion":"","nombre_referencia_1":"","direccion_referencia_1":"","telefono_referencia_1":"","parentesco_referencia_1":"","anios_conocerce_referencia_1":"","nombre_referencia_2":"","direccion_referencia_2":"","telefono_referencia_2":"","parentesco_referencia_2":"","anios_conocerce_referencia_2":"","maps_localizacion":"","imagen_plano_localizacion":"","fachada_casa":"","a_lado_casa":"","enfrente_casa":"","autorizacion_contratos":"","id_creador":"0","id_empresa":"0"}

                    {"id_cliente":"1","cliente":"","numero_cliente":"0","nombre":"mklmklmklm","apaterno":"klmkl","amaterno":"mklm","fecha_nacimiento":"0000-00-00","sexo":"mkl","imagen":"","calle":"mklm","numero_exterior":"klm","numero_interior":"klmkl","colonia":"mkl","delegacion_municipio":"mkl","estado":"mklm","codigo_postal":"klm","pais":"klmkl","telefono_casa":"","telefono_celular":"","telefono_oficina":"","entre_calle":"","y_calle":"","ocupacion":"","direccion_trabajo":"","nombre_pareja":"","ocupacion_pareja":"","telefono_pareja":"","complexion":"","estatura":"","tez":"","edad_rango":"","cabello":"","color_cabello":"","tipo_identificacion":"","numero_identificacion":"","nombre_referencia_1":"","direccion_referencia_1":"","telefono_referencia_1":"","parentesco_referencia_1":"","anios_conocerce_referencia_1":"","nombre_referencia_2":"","direccion_referencia_2":"","telefono_referencia_2":"","parentesco_referencia_2":"","anios_conocerce_referencia_2":"","maps_localizacion":"","imagen_plano_localizacion":"","fachada_casa":"","a_lado_casa":"","enfrente_casa":"","autorizacion_contratos":"","id_creador":"0","id_empresa":"0"}

                    String _telefono_vo = object.getString("telefono_veterinario");
                    String _cedula_vo = object.getString("cedula_veterinario");
                    String _email_vo = object.getString("email_veterinario");
                    String _imagen_vo = object.getString("imagen_veterinario");
                    */



                    /*
                    if(_telefono_vo.length() > 3)
                        lblCelVo.setText(_telefono_vo);

                    */
                    /*
                    if(_cedula_vo.length() > 3)
                        lblCedVo.setText(_cedula_vo);
                        */


                    //DIRECCION
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("delegacion_municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais")  + " , entre calle " + object.getString("entre_calle")  + " y calle " + object.getString("y_calle")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno");
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("delegacion_municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais")  + " , entre calle " + object.getString("entre_calle")  + " y calle " + object.getString("y_calle");


                    /*

                    if(_imagen_vo.length() > 3){
                        String _urlFoto = "http://hector-aguilar.com/web/images/" + _imagen_vo;
                        //Picasso.with(fotoVeterinario.getContext()).load(_urlFoto).fit().centerCrop().into(fotoVeterinario);

                        Picasso.with(fotoVeterinario.getContext()).load(_urlFoto)
                                .into(fotoVeterinario, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Bitmap imageBitmap = ((BitmapDrawable) fotoVeterinario.getDrawable()).getBitmap();
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(fotoVeterinario.getContext().getResources(), imageBitmap);
                                        circularBitmapDrawable.setCircular(true);
                                        fotoVeterinario.setImageDrawable(circularBitmapDrawable);
                                    }
                                    @Override
                                    public void onError() {

                                    }
                                });

                    }
                    */




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            Log.i("INFO", response);
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _url);
                URL url = new URL(_url);
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
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {

                try {
                    JSONTokener tokener = new JSONTokener(response);
                    JSONArray arr = new JSONArray(tokener);

                    listaNombreVeterinarios.clear();

                    listaFolioFiscal.clear();
                    listaTotalFactura.clear();
                    listaStatusFactura.clear();
                    listaStatusColor.clear();


                    listaImagenVeterinarios.clear();
                    listaIdVeterinario.clear();


                    listaStatusDeudor.clear();
                    listaObservaciones.clear();
                    listaAtendio.clear();


                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);

                        /*
                        listaNombreVeterinarios.add(jsonobject.getString("nombre"));
                        listaImagenVeterinarios.add(jsonobject.getString("foto"));
                        listaIdVeterinario.add(jsonobject.getString("id_veterinario"));
                        */
                        //listaImagenVeterinarios.add(jsonobject.getString("foto"));

                        /*
                        listaNombreVeterinarios.add(jsonobject.getString("numero_cliente") + " " + jsonobject.getString("nombre") + " " + jsonobject.getString("apaterno"));

                        listaImagenVeterinarios.add(jsonobject.getString("imagen"));
                        listaIdVeterinario.add(jsonobject.getString("id_cliente"));
                        listaNombreVeterinarios.add(jsonobject.getString("folio") + " " + jsonobject.getString("importe") + " " + jsonobject.getString("saldo"));


                        listaNombreVeterinarios.add(jsonobject.getString("id_documento"));

                        listaImagenVeterinarios.add(jsonobject.getString("id_documento"));
                        //listaIdVeterinario.add(jsonobject.getString("total"));
                        listaIdVeterinario.add(jsonobject.getString("id_documento"));
                        */
                        listaNombreVeterinarios.add(jsonobject.getString("folio") + " $" + jsonobject.getString("importe"));



                        listaFolioFiscal.add(jsonobject.getString("folio"));
                        listaTotalFactura.add("$ " + jsonobject.getString("importe"));
                        listaStatusFactura.add(jsonobject.getString("status_texto"));
                        //listaStatusColor.add(jsonobject.getString("folio") + " $" + jsonobject.getString("importe"));
                        listaStatusColor.add(jsonobject.getString("status_color"));

                        listaImagenVeterinarios.add(jsonobject.getString("id_documento"));
                        //listaIdVeterinario.add(jsonobject.getString("total"));
                        listaIdVeterinario.add(jsonobject.getString("id_documento"));


                        if(jsonobject.getString("status_deudor").equals("")){
                            listaStatusDeudor.add(" ");
                        }else{
                            listaStatusDeudor.add(jsonobject.getString("status_deudor"));
                        }

                        if(jsonobject.getString("observaciones").equals("")){
                            listaObservaciones.add(" ");
                        }else{
                            listaObservaciones.add(jsonobject.getString("observaciones"));
                        }

                        if(jsonobject.getString("atendio").equals("")){
                            listaAtendio.add(" ");
                        }else{
                            listaAtendio.add(jsonobject.getString("atendio"));
                        }





                    }

                    //EditText txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);



                    //theFact.getText().toString();


                    //Log.d("observaciones", findViewById(R.id.txtObservacionesFactura).getText().toString());
                    Log.d("observaciones", txtObservacionesFactura.getText().toString());

                    //_mascotasAdapter = new DocumentosAdapter(selStatus, valueID, mActivity, listaNombreVeterinarios, listaImagenVeterinarios, listaIdVeterinario);
                    //_mascotasAdapter = new DocumentosAdapter(selStatus, selInconformidad, txtObservacionesFactura.getText().toString(), valueID, mActivity, listaNombreVeterinarios, listaFolioFiscal, listaTotalFactura, listaStatusFactura, listaStatusColor, listaStatusDeudor, listaObservaciones, listaAtendio, listaImagenVeterinarios, listaIdVeterinario);
                    _mascotasAdapter = new DocumentosAdapter(selStatus, selInconformidad, txtObservacionesFactura.getText().toString(), txtAtendioFactura.getText().toString(), btnMesDia.getText().toString(), valueID, mActivity, listaNombreVeterinarios, listaFolioFiscal, listaTotalFactura, listaStatusFactura, listaStatusColor, listaStatusDeudor, listaObservaciones, listaAtendio, listaImagenVeterinarios, listaIdVeterinario);

                    lv.setAdapter(_mascotasAdapter);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }


    class RetrieveFeedTaskInconformidad extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlInconformidad);
                URL url = new URL(_urlInconformidad);
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
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                try {
                    JSONTokener tokener = new JSONTokener(response);
                    JSONArray arr = new JSONArray(tokener);

                    _inconformidad.clear();
                    _ids_inconformidad.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);
                        //Log.d("status",jsonobject.getString("status"));

                        //_clientes.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        //_mascotas.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        _inconformidad.add(jsonobject.getString("tipo_inconformidad"));
                        //_ids_cliente.add(jsonobject.getInt("id_mascota"));
                        _ids_inconformidad.add(jsonobject.getInt("id_tipo_inconformidad"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }
    class RetrieveFeedTaskStatus extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlStatus);
                URL url = new URL(_urlStatus);
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
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                try {
                    JSONTokener tokener = new JSONTokener(response);
                    JSONArray arr = new JSONArray(tokener);

                    _status.clear();
                    _ids_status.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);
                        Log.d("status",jsonobject.getString("status"));

                        //_clientes.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        //_mascotas.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        _status.add(jsonobject.getString("status"));
                        //_ids_cliente.add(jsonobject.getInt("id_mascota"));
                        _ids_status.add(jsonobject.getInt("id_status"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }
    /*
    public void editarEstablecimiento(View view) {
        Intent i = new Intent(Detalle_veterinario.this, Editar_establecimiento.class);
        startActivity(i);
    }
    */
    public void showInconformidadList(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        items = _inconformidad.toArray(new CharSequence[_inconformidad.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button btnInconformidad = (Button)findViewById(R.id.btnInconformidad);
                btnInconformidad.setText(items[item]);
                selInconformidad = _ids_inconformidad.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("id_status", Integer.toString(selInconformidad));


                //_url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_status.get(item) + "&otro=" + selStatus;
                _url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_inconformidad.get(item) + "&otro=" + selInconformidad;
                Log.d("url_documentos", _url);
                new Detalle_deudor.RetrieveFeedTask().execute();


                //_mascotasAdapter = new DocumentosAdapter(_ids_status.get(item), valueID, mActivity, listaNombreVeterinarios, listaImagenVeterinarios, listaIdVeterinario);
                //lv.setAdapter(_mascotasAdapter);

                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_clientes.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;

            }
        });

        AlertDialog alert = builder.create();

        ListView listView = alert.getListView();
        listView.setDivider(new ColorDrawable(Color.GRAY)); // set color
        listView.setDividerHeight(1);
        listView.setOverscrollFooter(new ColorDrawable(Color.TRANSPARENT));

        alert.show();


    }

    public void showEstatusList(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        items = _status.toArray(new CharSequence[_status.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button btnEstatus = (Button)findViewById(R.id.btnEstatusDetalle);
                btnEstatus.setText(items[item]);
                selStatus = _ids_status.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("id_status", Integer.toString(selStatus));


                //_url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_status.get(item) + "&otro=" + selStatus;
                _url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_status.get(item) + "&otro=" + selStatus;
                Log.d("url_documentos", _url);
                new Detalle_deudor.RetrieveFeedTask().execute();


                //_mascotasAdapter = new DocumentosAdapter(_ids_status.get(item), valueID, mActivity, listaNombreVeterinarios, listaImagenVeterinarios, listaIdVeterinario);
                //lv.setAdapter(_mascotasAdapter);

                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_clientes.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;

            }
        });

        AlertDialog alert = builder.create();

        ListView listView = alert.getListView();
        listView.setDivider(new ColorDrawable(Color.GRAY)); // set color
        listView.setDividerHeight(1);
        listView.setOverscrollFooter(new ColorDrawable(Color.TRANSPARENT));

        alert.show();


    }
    public void actualizarDocumentos(View v){
        _url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + selStatus + "&otro=" + selStatus;
        Log.d("url_documentos", _url);
        new Detalle_deudor.RetrieveFeedTask().execute();
    }

    public void goBack(View v){
        //Intent i = new Intent(Detalle_contrato.this, Lista_clientes.class);
        Intent i = new Intent(Detalle_deudor.this, Lista_contratos.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        
    }

    public void goAgregarPago(View v){
        Intent i = new Intent(Detalle_deudor.this, Agregar_pago.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("idcontrato", idString);
        startActivity(i);
    }
    public void goMenu(View v){
        Intent i = new Intent(Detalle_deudor.this, Menu.class);
        startActivity(i);
    }

    /*

    public void goMenu(View v){
        Intent i = new Intent(Detalle_veterinario.this, Menu.class);
        startActivity(i);
    }
    public void goBack(View v){
        Intent i = new Intent(Detalle_veterinario.this, Principal.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        Intent i = new Intent(Detalle_veterinario.this, Notificaciones.class);
        startActivity(i);
    }

    public void goAgregar(View v){
        Intent i = new Intent(Detalle_veterinario.this, Agregar_veterinarios.class);
        startActivity(i);
    }

    public void goClientes(View v){
        Intent i = new Intent(Detalle_veterinario.this, Clientes.class);
        startActivity(i);
    }

    public void goMascotas(View v){
        Intent i = new Intent(Detalle_veterinario.this, Mascotas.class);
        startActivity(i);
    }

    public void goServicio(View v){
        Intent i = new Intent(Detalle_veterinario.this, Servicio.class);
        startActivity(i);
    }

    public void goTienda(View v){
        Intent i = new Intent(Detalle_veterinario.this, Tienda.class);
        startActivity(i);
    }

    public void goEditarPerfil(View v){
        Intent i = new Intent(Detalle_veterinario.this, Editar_perfil.class);
        startActivity(i);
    }



    class RetrieveFeedTaskGet extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlGet);
                URL url = new URL(_urlGet);
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
                TextView lblNombre = (TextView) findViewById(R.id.lblNombre);
                TextView lblDireccion = (TextView) findViewById(R.id.lblDireccion);
                ImageView foto = (ImageView) findViewById(R.id.imgFoto);

                TextView lblNombreVo = (TextView) findViewById(R.id.txtNombreA);
                TextView lblEmailVo = (TextView) findViewById(R.id.txtEmailA);
                TextView lblCelVo = (TextView) findViewById(R.id.txtCelA);
                TextView lblCedVo = (TextView) findViewById(R.id.txtCedA);
                final ImageView fotoVeterinario = (ImageView) findViewById(R.id.imgVeterinario);

                try {

                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();


                    String _nombre_vo = object.getString("nombre_veterinario");
                    String _telefono_vo = object.getString("telefono_veterinario");
                    String _cedula_vo = object.getString("cedula_veterinario");
                    String _email_vo = object.getString("email_veterinario");
                    String _imagen_vo = object.getString("imagen_veterinario");

                    if(_nombre_vo.length() > 3)
                        lblNombreVo.setText(_nombre_vo);

                    if(_email_vo.length() > 3)
                        lblEmailVo.setText(_email_vo);

                    if(_telefono_vo.length() > 3)
                        lblCelVo.setText(_telefono_vo);

                    if(_cedula_vo.length() > 3)
                        lblCedVo.setText(_cedula_vo);

                    if(_imagen_vo.length() > 3){
                        String _urlFoto = "http://hyperion.init-code.com/zungu/imagen_establecimiento/" + _imagen_vo;
                        //Picasso.with(fotoVeterinario.getContext()).load(_urlFoto).fit().centerCrop().into(fotoVeterinario);

                        Picasso.with(fotoVeterinario.getContext()).load(_urlFoto)
                                .into(fotoVeterinario, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Bitmap imageBitmap = ((BitmapDrawable) fotoVeterinario.getDrawable()).getBitmap();
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(fotoVeterinario.getContext().getResources(), imageBitmap);
                                        circularBitmapDrawable.setCircular(true);
                                        fotoVeterinario.setImageDrawable(circularBitmapDrawable);
                                    }
                                    @Override
                                    public void onError() {

                                    }
                                });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            Log.i("INFO", response);
        }
    }

    class RetrieveFeedTaskNotificaciones extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlNotificaciones);
                URL url = new URL(_urlNotificaciones);
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
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                try {
                    JSONTokener tokener = new JSONTokener(response);
                    JSONArray arr = new JSONArray(tokener);

                    TextView numeroNotificaciones = (TextView) findViewById(R.id.numeroNotificaciones);


                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);
                        //Log.d("nombre",jsonobject.getString("nombre"));

                        //"":"45","":"9","":"0","fecha":"2016-11-19 15:59:00","":"","acepto":"0","":"","":"1","id_pago":"0","id_veterinaria":"0"},

                        if(jsonobject.getString("numero_notificaciones").equals("")){
                            numeroNotificaciones.setVisibility(View.GONE);
                        }else{
                            numeroNotificaciones.setVisibility(View.VISIBLE);
                            numeroNotificaciones.setText(jsonobject.getString("numero_notificaciones"));
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }
    */
}
