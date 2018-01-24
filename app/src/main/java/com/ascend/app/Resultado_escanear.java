package com.ascend.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.app.adapters.PagosAdapter;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class Resultado_escanear extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ListView lv;


    private ZXingScannerView mScannerView;

    long restr;

    CharSequence[] items;

    private String _urlGet;
    private String _url;
    public static final String idu = "idu";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private int valueID = 0;

    public static ArrayList<String> listaNombreVeterinarios = new ArrayList<String>();
    public static ArrayList<String> listaImagenVeterinarios = new ArrayList<String>();
    public static ArrayList<String> listaIdVeterinario = new ArrayList<String>();

    EditText detalleFolioFactura;
    EditText detalleAtendioFactura;
    EditText historialGestion;

    public Resultado_escanear mActivity = this;
    public PagosAdapter _mascotasAdapter;

    public Resultado_escanear _activity = this;
    RecyclerView lvMascotas;

    private String _urlNotificaciones;
    private String _urlStatus;
    private String _urlInconformidad;

    private String _urlCuentaDeudor;
    private String _urlPrefijo;


    String idString;
    String resultado_qr;


    private int selStatus = 17;

    public ArrayList<String> _status = new ArrayList<String>();
    public  ArrayList<Integer> _ids_status = new ArrayList<Integer>();

    private int selInconformidad = 0;

    public ArrayList<String> _inconformidad = new ArrayList<String>();
    public  ArrayList<Integer> _ids_inconformidad = new ArrayList<Integer>();


    private int selCuentaDeudor = 0;

    public ArrayList<String> _cuentaDeudor = new ArrayList<String>();
    public  ArrayList<Integer> _ids_cuentaDeudor = new ArrayList<Integer>();

    private int selPrefijo = 0;

    public ArrayList<String> _prefijo = new ArrayList<String>();
    public  ArrayList<Integer> _ids_prefijo = new ArrayList<Integer>();

    Button btnMesDia;

    String fechaValor;

    Date date_seleccionada;
    Date date_hoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_escanear);

        //lv = (ListView) findViewById(R.id.list_pagos);
        detalleFolioFactura = (EditText) findViewById(R.id.detalleFolioFactura);
        detalleAtendioFactura = (EditText) findViewById(R.id.detalleAtendioFactura);
        historialGestion = (EditText) findViewById(R.id.historialGestion);

        btnMesDia = (Button) findViewById(R.id.btnMesDia);


        //showMsg("test");

        //String idString;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            idString= null;
            resultado_qr = null;

        } else {
            //idString= extras.getString("idcliente");
            //idString= extras.getString("idcontrato");
            resultado_qr = extras.getString("resultado_qr");
            //Log.d("id_vet", idString);

        }


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        valueID = sharedpreferences.getInt("idu", 0);


        //showMsg(resultado_qr);
        //Log.d("resultado_qr", resultado_qr);

        //_urlGet = "http://thekrakensolutions.com/cobradores/android_get_contrato.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true&resultado=" + resultado_qr;
        _urlGet = "http://ascendsystem.net/ejecutivo/app_escanear_qr.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&resultado=" + Uri.encode(resultado_qr);
        new Resultado_escanear.RetrieveFeedTaskGet().execute();

        //_urlStatus = "http://ascendsystem.net/ejecutivo/app_obtener_status.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus;
        _urlStatus = "http://ascendsystem.net/ejecutivo/app_obtener_status_escanear.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus + "&resultado=" + Uri.encode(resultado_qr);;
        Log.d("url_mascota", _urlStatus);
        new Resultado_escanear.RetrieveFeedTaskStatus().execute();

        _urlInconformidad = "http://ascendsystem.net/ejecutivo/app_obtener_inconformidad.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus;
        Log.d("url_mascota", _urlInconformidad);
        new Resultado_escanear.RetrieveFeedTaskInconformidad().execute();
        /*

        _urlGet = "http://thekrakensolutions.com/cobradores/android_get_contrato.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        new Resultado_escanear.RetrieveFeedTaskGet().execute();


        _url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&idv=" + valueID + "&accion=true";
        new Resultado_escanear.RetrieveFeedTask().execute();

        */

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
                //btnMesDia.setText("hola");
                btnMesDia.setText("Mes/Día");



            }
        }


        btnMesDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepick = new DatePickerDialog(Resultado_escanear.this, new DatePickerDialog.OnDateSetListener() {

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


        onPause();
    }









    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();         // Start camera<br />
    }
    @Override
    public void onPause() {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        super.onPause();
        mScannerView.stopCamera();   // Stop camera on pause<br />
    }
    /*
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here</p>
        Log.e(&quot;handler&quot;, rawResult.getText()); // Prints scan results<br />
        Log.e(&quot;handler&quot;, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)</p>
<p>        // show the scanner result into dialog box.<br />
                AlertDialog.Builder builder = new AlertDialog.Builder(this);<br />
                builder.setTitle(&quot;Scan Result&quot;);<br />
                builder.setMessage(rawResult.getText());<br />
                AlertDialog alert1 = builder.create();<br />
                alert1.show();</p>
<p>        // If you would like to resume scanning, call this method below:<br />
        // mScannerView.resumeCameraPreview(this);<br />
    }<br />


    */










    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here</p>
        Log.e("handler", rawResult.getText()); // Prints scan results<br />
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)</p>
        // show the scanner result into dialog box.<br />
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        // If you would like to resume scanning, call this method below:<br />
        // mScannerView.resumeCameraPreview(this);<br />
    }
    /*
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here</p>
        Log.e("handler", rawResult.getText()); // Prints scan results<br />
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)</p>
        // show the scanner result into dialog box.<br />
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        // If you would like to resume scanning, call this method below:<br />
        // mScannerView.resumeCameraPreview(this);<br />
    }

    */
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
                Log.i("INFO QR: ", _urlGet);
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


                TextView txtRFCEmisor = (TextView) findViewById(R.id.txtRFCEmisor);
                TextView txtRFCReceptor = (TextView) findViewById(R.id.txtRFCReceptor);
                TextView txtTotalFactura = (TextView) findViewById(R.id.txtTotalFactura);
                TextView txtFolioFiscal = (TextView) findViewById(R.id.txtFolioFiscal);
                TextView txtRazonSocialEmisor = (TextView) findViewById(R.id.txtRazonSocialEmisor);
                TextView txtRazonSocialReceptor = (TextView) findViewById(R.id.txtRazonSocialReceptor);

                Button btnMesDia = (Button) findViewById(R.id.btnMesDia);







                TextView lblNombreVo = (TextView) findViewById(R.id.txtNombreA);
                TextView lblEmailVo = (TextView) findViewById(R.id.txtEmailA);
                TextView lblCelVo = (TextView) findViewById(R.id.txtCelA);
                //TextView lblCedVo = (TextView) findViewById(R.id.txtCedA);
                TextView txtDireccion = (TextView) findViewById(R.id.txtDireccion);

                final ImageView fotoVeterinario = (ImageView) findViewById(R.id.imgVeterinario);

                try {

                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();



                    //String _nombre_vo = object.getString("numero_cliente") + " - " + object.getString("nombre") + " " + object.getString("apaterno") + " " + object.getString("amaterno");
                    String _nombre_vo = object.getString("rfc_emisor");

                    //String _telefono_vo = object.getString("telefono_casa");
                    String _cedula_vo = object.getString("rfc_emisor");
                    String _email_vo = object.getString("rfc_emisor");
                    //String _imagen_vo = object.getString("sexo");
                    String _imagen_vo = object.getString("rfc_emisor");




                    String _RFCEmisor = object.getString("rfc_emisor");
                    String _RFCReceptor = object.getString("rfc_receptor");
                    String _TotalFactura = object.getString("total_factura");
                    String _FolioFiscal = object.getString("folio_fiscal");
                    String _RazonSocialEmisor = object.getString("razon_social_emisor");
                    String _RazonSocialReceptor = object.getString("razon_social_receptor");





                    txtRFCEmisor.setText(_RFCEmisor);
                    txtRFCReceptor.setText(_RFCReceptor);
                    txtTotalFactura.setText(_TotalFactura);
                    txtFolioFiscal.setText(_FolioFiscal);
                    txtRazonSocialEmisor.setText(_RazonSocialEmisor);
                    txtRazonSocialReceptor.setText(_RazonSocialReceptor);

                    btnMesDia.setText(object.getString("fecha_manana"));


                    //str == null | str.length() == 0
                    //if(_RazonSocialReceptor.equals("") || _RazonSocialEmisor.equals("")){
                    //if(_RazonSocialReceptor == null || _RazonSocialEmisor == null){
                    //if(object.getString("razon_social_receptor") == null || object.getString("razon_social_emisor") == null){
                    Log.d("razon social emisor", object.getString("razon_social_emisor"));
                    //showMsg(object.getString("razon_social_emisor"));

                    Log.d("razon social receptor", object.getString("razon_social_receptor"));
                    //showMsg(object.getString("razon_social_receptor"));

                    _urlCuentaDeudor = "http://ascendsystem.net/ejecutivo/app_obtener_cuenta_deudor.php?id_veterinario=" + valueID + "&id_usuario=" + selStatus + "&rfc_emisor=" + object.getString("rfc_emisor") + "&rfc_receptor=" + object.getString("rfc_receptor");
                    Log.d("url_cuenta_deudor", _urlCuentaDeudor);
                    new Resultado_escanear.RetrieveFeedTaskCuentaDeudor().execute();


                    if(object.getString("razon_social_receptor") == null | object.getString("razon_social_receptor").length() == 0 | object.getString("razon_social_receptor").isEmpty() | object.getString("razon_social_emisor") == null  | object.getString("razon_social_emisor").length() == 0 | object.getString("razon_social_emisor").isEmpty()){
                        if(object.getString("razon_social_receptor") == null | object.getString("razon_social_receptor").length() == 0 | object.getString("razon_social_receptor").isEmpty()){
                            showMsg("No se ha encontrado el receptor de la factura");

                            LinearLayout btn_agregar = (LinearLayout) findViewById(R.id.btn_agregar);
                            btn_agregar.setVisibility(LinearLayout.INVISIBLE);


                            txtRazonSocialReceptor.setText("No se ha encontrado el receptor de la factura");
                            txtRazonSocialReceptor.setTextColor(getResources().getColor(R.color.rojo_denegado));



                        }
                        if(object.getString("razon_social_emisor") == null | object.getString("razon_social_emisor").length() == 0 | object.getString("razon_social_emisor").isEmpty()){
                            showMsg("No se ha encontrado el emisor de la factura");

                            LinearLayout btn_agregar = (LinearLayout) findViewById(R.id.btn_agregar);
                            //btn_agregar.setVisibility(LinearLayout.GONE);
                            btn_agregar.setVisibility(LinearLayout.INVISIBLE);

                            txtRazonSocialEmisor.setText("No se ha encontrado el emisor de la factura");
                            txtRazonSocialEmisor.setTextColor(getResources().getColor(R.color.rojo_denegado));


                        }

                    }else{
                        //showMsg("Todo esta perfecto");
                    }







                    //showMsg("tesst2");

                    //showMsg(_email_vo);



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


                    if(_nombre_vo.length() > 3)
                        lblNombreVo.setText(_nombre_vo);

                    if(_email_vo.length() > 3)
                        lblEmailVo.setText(_email_vo);

                    */
                    /*
                    if(_telefono_vo.length() > 3)
                        lblCelVo.setText(_telefono_vo);

                    */
                    /*
                    if(_cedula_vo.length() > 3)
                        lblCedVo.setText(_cedula_vo);
                        */


                    /*
                    //DIRECCION
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("delegacion_municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais")  + " , entre calle " + object.getString("entre_calle")  + " y calle " + object.getString("y_calle")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno")  + " " + object.getString("amaterno");
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("delegacion_municipio")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais")  + " , entre calle " + object.getString("entre_calle")  + " y calle " + object.getString("y_calle");
                    //String txtDireccion_ = object.getString("calle") + " " + object.getString("numero_exterior") + " " + object.getString("numero_interior")  + " , Colonia " + object.getString("colonia")  + " , Delegación/Municipio " + object.getString("poblacion")  + " , Estado " + object.getString("estado")  + " , C.P. " + object.getString("codigo_postal")  + " , País " + object.getString("pais");
                    String txtDireccion_ = object.getString("rfc_emisor");


                    if(txtDireccion_.length() > 3)
                        txtDireccion.setText(txtDireccion_);


                    Log.d("INFO", _nombre_vo);


                    if(_imagen_vo.length() > 3){
                        String _urlFoto = "http://thekrakensolutions.com/administrativos/images/clientes/" + _imagen_vo;
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
                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();


                    //String _razon_social = object.getString("RFC_deudor") + " - " + object.getString("deudor") + " " + object.getString("cuenta_deudor");


                    //String _telefono_vo = object.getString("telefono_casa");
                    //String _imagen_vo = object.getString("sexo");
                    //String _imagen_vo = object.getString("imagen");

                    //if (object.getString("id_documento").length() > 3) {
                    if (object.getString("status_operacion").equals("2")) {
                        showMsg("La factura ya ha sido agregada anteriormente");
                    }
                    if (object.getString("status_operacion").equals("1")) {
                        showMsg("Factura agregada correctamente");

                        Intent i = new Intent(Resultado_escanear.this, Resultado_final.class);
                        startActivity(i);
                    }
                    /*
                    if (object.getString("status_texto").length() > 3) {

                    }
                    */



//JSONTokener tokener = new JSONTokener(response);
                    //JSONArray arr = new JSONArray(tokener);








                    /*
                    _mascotasAdapter = new PagosAdapter(valueID, mActivity, listaNombreVeterinarios, listaImagenVeterinarios, listaIdVeterinario);
                    lv.setAdapter(_mascotasAdapter);
                    */

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
    public void showCuentaDeudorList(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        items = _cuentaDeudor.toArray(new CharSequence[_cuentaDeudor.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button btnCuentaDeudor = (Button)findViewById(R.id.btnCuentaDeudor);
                btnCuentaDeudor.setText(items[item]);
                selCuentaDeudor = _ids_cuentaDeudor.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("cuenta_deudor", Integer.toString(selCuentaDeudor));

                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_clientes.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
                //_urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
                Button btnPrefijo = (Button)findViewById(R.id.btnPrefijo);
                btnPrefijo.setText("Prefijo folio factura");
                selPrefijo = 0; //En la variable selCliente esta guardado el id del cliente.

                //_urlPrefijo = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
                //_urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_cuenta_deudor=" + selCuentaDeudor + "&id_usuario=" + selCliente;
                //_urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_cuenta_deudor=" + selCuentaDeudor;
                _urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_deudor=" + selCuentaDeudor;
                Log.d("url_prefijo", _urlPrefijo);
                new Resultado_escanear.RetrieveFeedTaskPrefijo().execute();

            }
        });

        AlertDialog alert = builder.create();

        ListView listView = alert.getListView();
        listView.setDivider(new ColorDrawable(Color.GRAY)); // set color
        listView.setDividerHeight(1);
        listView.setOverscrollFooter(new ColorDrawable(Color.TRANSPARENT));

        alert.show();

        /*

        Button btnMascota = (Button)findViewById(R.id.btnMascota);
        btnMascota.setText("Mascota");
        selMascota = 0; //En la variable selCliente esta guardado el id del cliente.

        _urlMascota = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
        Log.d("url_mascota", _urlMascota);
        new Agregar_cita.RetrieveFeedTaskMascota().execute();
        */
        /*
        Button btnPrefijo = (Button)findViewById(R.id.btnPrefijo);
        btnPrefijo.setText("Prefijo folio factura");
        selPrefijo = 0; //En la variable selCliente esta guardado el id del cliente.

        //_urlPrefijo = "http://hyperion.init-code.com/zungu/app/vt_obtener_mascotas.php?id_veterinario=" + valueID + "&id_usuario=" + selCliente;
        //_urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_cuenta_deudor=" + selCuentaDeudor + "&id_usuario=" + selCliente;
        //_urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_cuenta_deudor=" + selCuentaDeudor;
        _urlPrefijo = "http://ascendsystem.net/ejecutivo/app_obtener_prefijo.php?id_deudor=" + selCuentaDeudor;
        Log.d("url_prefijo", _urlPrefijo);
        new Resultado_escanear.RetrieveFeedTaskPrefijo().execute();
        */


    }
    public void showPrefijoList(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        items = _prefijo.toArray(new CharSequence[_prefijo.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button btnPrefijo = (Button)findViewById(R.id.btnPrefijo);
                btnPrefijo.setText(items[item]);
                selPrefijo = _ids_prefijo.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("id_status", Integer.toString(selPrefijo));

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
                Button btnEstatus = (Button)findViewById(R.id.btnEstatus);
                btnEstatus.setText(items[item]);
                selStatus = _ids_status.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("id_status", Integer.toString(selStatus));

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
    public void showInconformidadList(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        items = _inconformidad.toArray(new CharSequence[_inconformidad.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Button btnInconformidad = (Button)findViewById(R.id.btnInconformidad);
                btnInconformidad.setText(items[item]);
                selInconformidad = _ids_inconformidad.get(item); //En la variable selCliente esta guardado el id del cliente.
                Log.d("id_status", Integer.toString(selInconformidad));


                /*
                //_url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_status.get(item) + "&otro=" + selStatus;
                _url = "http://ascendsystem.net/ejecutivo/app_listar_documentos.php?id_deudor=" + idString + "&test=" + _ids_inconformidad.get(item) + "&otro=" + selInconformidad;
                Log.d("url_documentos", _url);
                new Resultado_escanear.RetrieveFeedTask().execute();
                */


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
    public void goBack(View v){
        //Intent i = new Intent(Detalle_contrato.this, Lista_clientes.class);
        //Intent i = new Intent(Resultado_escanear.this, Lista_contratos.class);
        Intent i = new Intent(Resultado_escanear.this, Menu.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        /*
        Intent i = new Intent(Resultado_escanear.this, Menu.class);
        startActivity(i);
        */
    }

    public void goAgregarPago(View v){
        Intent i = new Intent(Resultado_escanear.this, Agregar_pago.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("idcontrato", idString);
        startActivity(i);
    }
    public void goAgregarFactura(View v){

        //_url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&folio_factura=" + detalleFolioFactura.getText().toString() + "&atendio=" + detalleAtendioFactura.getText().toString() + "&bitacora=" + historialGestion.getText().toString() + "&id_status=" + selStatus + "&id_tipo_inconformidad=" + selInconformidad + "&id_deudor=" + selCuentaDeudor + "&resultado=" + Uri.encode(resultado_qr);


        //if(detalleFolioFactura.getText().toString() != null && selStatus != 0 && detalleAtendioFactura.getText().toString() != null && historialGestion.getText().toString() != null && selCuentaDeudor != 0){
        //if(detalleFolioFactura.getText().toString().equals("") || selStatus != 0 || detalleAtendioFactura.getText().toString().equals("") || historialGestion.getText().toString().equals("") && selCuentaDeudor != 0){
        if(detalleFolioFactura.getText().toString().equals("") || selStatus == 0 || detalleAtendioFactura.getText().toString().equals("") || historialGestion.getText().toString().equals("") || selCuentaDeudor == 0){
            showMsg("Todos los campos son necesarios");
        }else{

            Log.d("welcm",Integer.toString(selStatus));
            //historialGestion
            //_url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&folio_factura=" + detalleFolioFactura.getText().toString() + "&id_status=" + selStatus + "&resultado=" + Uri.encode(resultado_qr);
            //URLEncoder.encode(
            //_url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&folio_factura=" + detalleFolioFactura.getText().toString() + "&atendio=" + detalleAtendioFactura.getText().toString() + "&bitacora=" + historialGestion.getText().toString() + "&id_status=" + selStatus + "&id_tipo_inconformidad=" + selInconformidad + "&id_deudor=" + selCuentaDeudor + "&resultado=" + Uri.encode(resultado_qr);
            //_url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&folio_factura=" + URLEncoder.encode(detalleFolioFactura.getText().toString()) + "&atendio=" + URLEncoder.encode(detalleAtendioFactura.getText().toString()) + "&bitacora=" + URLEncoder.encode(historialGestion.getText().toString()) + "&id_status=" + selStatus + "&id_tipo_inconformidad=" + selInconformidad + "&id_deudor=" + selCuentaDeudor + "&resultado=" + Uri.encode(resultado_qr);
            _url = "http://ascendsystem.net/ejecutivo/app_guardar_factura.php?id_editar=" + idString + "&id_usuario=" + valueID + "&accion=true&folio_factura=" + URLEncoder.encode(detalleFolioFactura.getText().toString()) + "&atendio=" + URLEncoder.encode(detalleAtendioFactura.getText().toString()) + "&bitacora=" + URLEncoder.encode(historialGestion.getText().toString()) + "&id_status=" + selStatus + "&id_tipo_inconformidad=" + selInconformidad + "&id_deudor=" + selCuentaDeudor + "&id_prefijo=" + selPrefijo + "&resultado=" + Uri.encode(resultado_qr);
            Log.d("tes", _url);
            new Resultado_escanear.RetrieveFeedTask().execute();


        }

        /*
        Intent i = new Intent(Resultado_escanear.this, Agregar_pago.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("idcontrato", idString);
        startActivity(i);
        */
    }
    public void goMenu(View v){
        Intent i = new Intent(Resultado_escanear.this, Menu.class);
        startActivity(i);
    }

    class RetrieveFeedTaskCuentaDeudor extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlCuentaDeudor);
                URL url = new URL(_urlCuentaDeudor);
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

                    _cuentaDeudor.clear();
                    _ids_cuentaDeudor.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);
                        Log.d("cuenta_deudor",jsonobject.getString("cuenta_deudor"));

                        //_clientes.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        //_mascotas.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        _cuentaDeudor.add(jsonobject.getString("cuenta_deudor"));
                        //_ids_cliente.add(jsonobject.getInt("id_mascota"));
                        _ids_cuentaDeudor.add(jsonobject.getInt("id_deudor"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }
    class RetrieveFeedTaskPrefijo extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                Log.i("INFO url: ", _urlPrefijo);
                URL url = new URL(_urlPrefijo);
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

                    _prefijo.clear();
                    _ids_prefijo.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);
                        //Log.d("cuenta_deudor",jsonobject.getString("cuenta_deudor"));
                        //Log.d("cuenta_deudor",jsonobject.getString("cuenta_deudor"));

                        //_clientes.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        //_mascotas.add(jsonobject.getString("nombre_usuario") + " - " + jsonobject.getString("nombre"));
                        _prefijo.add(jsonobject.getString("deudor_prefijo"));
                        //_ids_cliente.add(jsonobject.getInt("id_mascota"));
                        _ids_prefijo.add(jsonobject.getInt("id_deudor_prefijo"));
                    }

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
