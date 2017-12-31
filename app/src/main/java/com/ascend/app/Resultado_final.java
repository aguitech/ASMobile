package com.ascend.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ascend.app.adapters.ContratosAdapter;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hectoraguilar on 07/03/17.
 */

public class Resultado_final extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    Context context;
    private String _url;

    public static final String idu = "idu";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private int valueID = 0;


    public Resultado_final mActivity = this;
    public ContratosAdapter _mascotasAdapter;

    private String _urlNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_final);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        valueID = sharedpreferences.getInt("idu", 0);

        onPause();
        /*
        _urlNotificaciones = "http://hyperion.init-code.com/zungu/app/vt_get_numero_notificaciones.php?idv=" + valueID;
        new Lista_clientes.RetrieveFeedTaskNotificaciones().execute();
        */


        //_url = "http://hyperion.init-code.com/zungu/app/vt_get_veterinarios.php?idp=" + Integer.toString(valueID);
        //_url = "http://thekrakensolutions.com/cobradores/android_get_clientes.php?id=" + Integer.toString(valueID);

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

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here</p>
        Log.e("handler", rawResult.getText()); // Prints scan results<br />
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)</p>

        /*
        // show the scanner result into dialog box.<br />
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        */


        Intent i = new Intent(Resultado_final.this, Resultado_escanear.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("resultado_qr", rawResult.getText());

        startActivity(i);
        // If you would like to resume scanning, call this method below:<br />
        // mScannerView.resumeCameraPreview(this);<br />
    }

    public void goMenu(View v){
        Intent i = new Intent(Resultado_final.this, Menu.class);
        startActivity(i);
    }
    public void goDeudores(View v){
        Intent i = new Intent(Resultado_final.this, Lista_deudores.class);
        startActivity(i);
    }
    public void goEscanear(View v){
        Intent i = new Intent(Resultado_final.this, Detalle_escanear.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        /*
        Intent i = new Intent(Principal.this, Detalle_escanear.class);
        startActivity(i);
        */
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
