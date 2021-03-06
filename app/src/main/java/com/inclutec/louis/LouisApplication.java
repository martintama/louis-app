package com.inclutec.louis;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.inclutec.louis.db.SQLiteHelper;
import com.inclutec.louis.interfaces.ArduinoDeviceConnector;
import com.inclutec.louis.lib.BrailleExerciseManager;
import com.inclutec.louis.lib.LouisDeviceConnector;
import com.inclutec.louis.mocks.LouisDeviceMock;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by martin on 9/22/15.
 */
public class LouisApplication extends Application {

    private String CLASSNAME = "LouisApplicationClass";
    private SQLiteHelper dbHelper;
    private ArduinoDeviceConnector deviceConnector;
    private BrailleExerciseManager brailleExerciseManager;

    public void setDbHelper(SQLiteHelper helper){
        this.dbHelper = helper;
    }

    public SQLiteHelper getHelper() {
        if (this.dbHelper == null || !this.dbHelper.isOpen()) {
            setDbHelper(OpenHelperManager.getHelper(this, SQLiteHelper.class));
        }

        return dbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Globals.logInfo(CLASSNAME, "Started Louis Application");
        initalizeDependencies(false);

        JodaTimeAndroid.init(this);

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        Globals.logError(CLASSNAME, e.getMessage(), e);

        Intent intent = new Intent ();
        intent.setAction ("com.inclutec.SEND_LOG"); // see step 5.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);

        System.exit(1); // kill off the crashed app
    }

    public void initalizeDependencies(boolean useMocks) {

        if (!useMocks) {
            this.setDbHelper(OpenHelperManager.getHelper(this, SQLiteHelper.class));
            this.setBrailleExerciseManager(new BrailleExerciseManager(this));
            this.setDeviceConnector(new LouisDeviceMock());
        }
    }

    public ArduinoDeviceConnector getDeviceConnector() {
        return deviceConnector;
    }

    public void setDeviceConnector(ArduinoDeviceConnector deviceConnector) {
        this.deviceConnector = deviceConnector;
    }

    public BrailleExerciseManager getBrailleExerciseManager() {
        return brailleExerciseManager;
    }

    public void setBrailleExerciseManager(BrailleExerciseManager brailleExerciseManager) {
        this.brailleExerciseManager = brailleExerciseManager;
    }
}
