package com.inclutec.louis;

import android.util.Log;

/**
 * Created by martin on 9/14/15.
 */
public class Globals {
    public static final String TAG = "Louis"; //default app tag

    public static final int SQLITE_DB_VERSION = 1;
    public static final String DATABASE_NAME = "louis.db";
    public static final String PREFS_NAME = "louisPrefs";
    public static final String APP_DIR = "louis";

    public static void logDebug(String className, String message){
        Log.d(Globals.TAG, String.format("[%s]: %s", className, message));
    }

    public static void logError(String className, String message, Throwable e){
        Log.e(Globals.TAG, String.format("[%s]: %s", className, message, e));
    }

    public static void logInfo(String className, String message){
        Log.i(Globals.TAG, String.format("[%s]: %s", className, message));
    }
}
