package com.ascend.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ascend.app.adapters.DeudoresAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class Lista_deudores extends AppCompatActivity {
    ListView lv;
    EditText buscador;
    Context context;
    private String _url;

    public static final String idu = "idu";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private int valueID = 0;

    public static ArrayList<String> listaNombreVeterinarios = new ArrayList<String>();

    public static ArrayList<String> listaRFCDeudores = new ArrayList<String>();
    public static ArrayList<String> listaRazonSocialDeudores = new ArrayList<String>();
    public static ArrayList<String> listaEstatusDeudores = new ArrayList<String>();

    public static ArrayList<String> listaImagenVeterinarios = new ArrayList<String>();
    public static ArrayList<String> listaIdDeudor = new ArrayList<String>();



    public Lista_deudores mActivity = this;
    public DeudoresAdapter _deudoresAdapter;

    private String _urlNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_deudores);

        lv = (ListView) findViewById(R.id.lista_deudores);
        //buscador = (EditText) findViewById(R.id.txtBuscador);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        valueID = sharedpreferences.getInt("idu", 0);

        /*
        _urlNotificaciones = "http://hyperion.init-code.com/zungu/app/vt_get_numero_notificaciones.php?idv=" + valueID;
        new Lista_clientes.RetrieveFeedTaskNotificaciones().execute();
        */


        //_url = "http://hyperion.init-code.com/zungu/app/vt_get_veterinarios.php?idp=" + Integer.toString(valueID);
        //_url = "http://thekrakensolutions.com/cobradores/android_get_clientes.php?id=" + Integer.toString(valueID);
        //_url = "http://thekrakensolutions.com/cobradores/android_get_contratos.php?id=" + Integer.toString(valueID);
        _url = "http://ascendsystem.net/ejecutivo/app_deudores_hoy.php?id=" + Integer.toString(valueID);
        Log.d("url_contratos", _url);
        new Lista_deudores.RetrieveFeedTask().execute();
    }

    public void goMenu(View v){
        Intent i = new Intent(Lista_deudores.this, Menu.class);
        startActivity(i);
    }
    /*
    public void goMenu(View v){
        Intent i = new Intent(Lista_veterinarios.this, Menu.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        Intent i = new Intent(Lista_veterinarios.this, Notificaciones.class);
        startActivity(i);
    }

    public void goBack(View v){
        finish();
    }

    public void showMiCuenta(View v){
        Intent i = new Intent(Lista_veterinarios.this, MiCuentaSuscripciones.class);
        startActivity(i);
    }
    */

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

                    listaRFCDeudores.clear();
                    listaRazonSocialDeudores.clear();
                    listaEstatusDeudores.clear();
                    listaIdDeudor.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonobject = arr.getJSONObject(i);


                        Log.i("INFO", jsonobject.getString("RFC_deudor"));


                        //listaRFCDeudores.add(jsonobject.getString("RFC_deudor") + " " + jsonobject.getString("cuenta_deudor"));
                        listaRFCDeudores.add(jsonobject.getString("cuenta_deudor") + " " + jsonobject.getString("RFC_deudor"));
                        listaRazonSocialDeudores.add(jsonobject.getString("deudor"));
                        listaEstatusDeudores.add(jsonobject.getString("status_texto"));

                        Log.i("INFO", listaRFCDeudores.toString());

                        //listaImagenVeterinarios.add(jsonobject.getString("cantidad"));
                        //listaImagenVeterinarios.add(jsonobject.getString("id_documento"));
                        //listaIdVeterinario.add(jsonobject.getString("total"));
                        listaIdDeudor.add(jsonobject.getString("id_deudor"));
                    }

                    //_mascotasAdapter = new DeudoresAdapter(valueID, mActivity, listaNombreVeterinarios, listaImagenVeterinarios, listaIdVeterinario);
                    //_mascotasAdapter = new DeudoresAdapter(valueID, mActivity, listaNombreVeterinarios, listaRFCDeudores, listaRazonSocialDeudores, listaEstatusDeudores, listaImagenVeterinarios, listaIdVeterinario);
                    _deudoresAdapter = new DeudoresAdapter(valueID, mActivity, listaRFCDeudores, listaRazonSocialDeudores, listaEstatusDeudores, listaIdDeudor);
                    lv.setAdapter(_deudoresAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i("INFO", response);
        }
    }
    /*
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
