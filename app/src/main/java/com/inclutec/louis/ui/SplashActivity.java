package com.inclutec.louis.ui;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisApplication;
import com.inclutec.louis.R;
import com.inclutec.louis.interfaces.ArduinoDeviceConnector;
import com.inclutec.louis.lib.LouisDeviceConnector;
import com.inclutec.louis.mocks.LouisDeviceMock;
import com.inclutec.louis.util.SystemUiHider;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashActivity extends Activity {

    private interface ChecksCompletedCallback{
        public void onChecksCompletedOk();

        public void onChecksCompletedWithError(String message);

    }

    private ChecksCompletedCallback mChecksCompletedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                checkDeviceConnection();

            }
        }, 2000);


    }

    private void checkDeviceConnection() {

        boolean connected = false;
        int status = -1; //not connected
        boolean retry = true;

        ArduinoDeviceConnector deviceConnector = null;

        try {
            //first try with the real device
            deviceConnector = new LouisDeviceConnector();
            deviceConnector.setContext(this);
            tryConnectDevice(deviceConnector);


        }
        catch(Exception ex){

            Toast aToast = Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            aToast.show();
        }




    }

    private void tryConnectDevice(ArduinoDeviceConnector deviceConnector){

        try {
            int status = 0;
            deviceConnector.initialize();
            status = deviceConnector.connect();

            if (status == 0){
                ((LouisApplication) getApplication()).setDeviceConnector(deviceConnector);
                goToMainActivity();
                finish();
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No se detectó el interprete Louis conectado a tu dispositivo.")
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                LouisDeviceMock deviceConnector = new LouisDeviceMock();
                                deviceConnector.initialize();
                                deviceConnector.setContext(getApplicationContext());
                                deviceConnector.connect();
                                ((LouisApplication) getApplication()).setDeviceConnector(deviceConnector);

                                goToMainActivity();
                                finish();
                            }
                        })
                        .setNeutralButton("Reintentar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                checkDeviceConnection();
                            }
                        })
                        .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                finish();
                            }
                        });

                // Create the AlertDialog object and return it

                builder.create().show();

            }
        }
        catch(Exception ex){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Error conectando con el interprete Louis")
                    .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            checkDeviceConnection();
                        }
                    })
                    .setNeutralButton("Omitir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LouisDeviceMock deviceConnector = new LouisDeviceMock();
                            deviceConnector.initialize();
                            deviceConnector.connect();
                            ((LouisApplication)

                                    getApplication()

                            ).

                                    setDeviceConnector(deviceConnector);

                            goToMainActivity();

                            finish();

                        }


                    })
                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            finish();
                        }
                    });

            // Create the AlertDialog object and return it

            builder.create().show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void goToMainActivity() {

        Intent intentMain = new Intent(SplashActivity.this, MainActivity.class);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intentMain);

    }

    public void initializeAppData(){

    }


}
