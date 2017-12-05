package com.ascend.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Inicio extends AppCompatActivity {

    private PrefManager prefManager;

    public static final String idu = "idu";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private int valueID = 0;

    String _urlGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Intent para la siguiente version

        Intent i = new Intent(Home.this, Lista_contratos.class);
        startActivity(i);


        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        */



        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        valueID = sharedpreferences.getInt("idu", 0);

    }

    public void goMenu(View v){
        Intent i = new Intent(Inicio.this, Menu.class);
        startActivity(i);
    }
    public void goNotificaciones(View v){
        /*
        Intent i = new Intent(Home.this, Notificaciones.class);
        startActivity(i);
        */
    }

    public void goPerfil(View v){
        /*
        //Intent i = new Intent(Home.this, Cuenta.class);
        Intent i = new Intent(Home.this, Promocionar_producto.class);
        startActivity(i);
        */
    }

    public void goCitas(View v){
        /*
        Intent i = new Intent(Home.this, Citas.class);
        startActivity(i);
        */
    }

    public void goPoliticas(View v){
        /*
        Intent i = new Intent(Home.this, Politicas_de_privacidad.class);
        startActivity(i);
        */
    }

    /**
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Inicio.this, MainActivity.class));
        finish();
    }




     * Making notification bar transparent
     */
    /*
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    */

}
