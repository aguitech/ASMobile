package com.ascend.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Menu extends AppCompatActivity implements ZXingScannerView.ResultHandler  {

    private ZXingScannerView mScannerView;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //int value = sharedpreferences.getInt("idu", 0);

        //TextView txtNombre = (TextView)findViewById(R.id.txtNombre);
        //txtNombre.setText(String.valueOf(value));
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


        Intent i = new Intent(Menu.this, Resultado_escanear.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("resultado_qr", rawResult.getText());

        startActivity(i);
        // If you would like to resume scanning, call this method below:<br />
        // mScannerView.resumeCameraPreview(this);<br />
    }


/*
    @Override
    public void handleResult(Result rawResult) {
        //Hold result
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        mScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
        mScannerView.stopCamera(); //<- then stop the camera
        setContentView(R.layout.activity_menu); //<- and set the View again.
        final String vString = rawResult.getText();

        Intent i = new Intent(Menu.this, Resultado_escanear.class);
        //i.putExtra("idcliente", _listaIdVeterinarios.get(i));
        //i.putExtra("idcliente", idString);
        i.putExtra("resultado_qr", rawResult.getText());

        startActivity(i);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(mContext,vString,Toast.LENGTH_LONG).show();



            }
        });
        // to resume scanning
        // mScannerView.resumeCameraPreview(this);<br />
    }
    */

    public void cerrar(View v){
        finish();
    }

    public void goHome(View v){
        Intent i = new Intent(Menu.this, Home.class);
        startActivity(i);
    }
    public void goClientes(View v){
        Intent i = new Intent(Menu.this, Lista_contratos.class);
        startActivity(i);
    }
    public void goEscanear(View v){
        Intent i = new Intent(Menu.this, Detalle_escanear.class);
        startActivity(i);
    }

    public void goCrearEvento(View v){
        Intent i = new Intent(Menu.this, Lista_contratos.class);
        startActivity(i);
    }
    public void goDeudores(View v){
        Intent i = new Intent(Menu.this, Lista_deudores.class);
        startActivity(i);
    }
    public void goEventos(View v){
        Intent i = new Intent(Menu.this, Lista_contratos.class);
        startActivity(i);
    }
    public void cerrarS(View v){
        Intent i = new Intent(Menu.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    /*
    public void goHome(View v){
        Intent i = new Intent(Menu.this, Home.class);
        startActivity(i);
    }

    public void goPerfil(View v){
        Intent i = new Intent(Menu.this, Principal.class);
        startActivity(i);
    }

    public void goServicios(View v){
        Intent i = new Intent(Menu.this, Servicio.class);
        startActivity(i);
    }

    public void goAgenda(View v){
        Intent i = new Intent(Menu.this, Citas_calendario.class);
        startActivity(i);
    }

    public void goCuenta(View v){
        Intent i = new Intent(Menu.this, Cuenta.class);
        startActivity(i);
    }

    public void goTienda(View v){
        Intent i = new Intent(Menu.this, Tienda.class);
        startActivity(i);
    }

    public void goInventario(View v){
        Intent i = new Intent(Menu.this, Inventario.class);
        startActivity(i);
    }

    public void goEstadisticas(View v){
        Intent i = new Intent(Menu.this, Estadisticas.class);
        startActivity(i);
    }

    public void goAnuncios(View v){
        Intent i = new Intent(Menu.this, Anuncios.class);
        startActivity(i);
    }

    public void goGastos(View v){
        Intent i = new Intent(Menu.this, Gastos.class);
        startActivity(i);
    }

    public void goFormas(View v){
        Intent i = new Intent(Menu.this, Cuenta_pago.class);
        startActivity(i);
    }

    public void cerrarS(View v){
        Intent i = new Intent(Menu.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    */
}
