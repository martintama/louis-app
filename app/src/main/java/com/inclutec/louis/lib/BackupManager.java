package com.inclutec.louis.lib;

import android.content.Context;
import android.util.Log;

import com.inclutec.louis.Globals;

import java.io.File;

/**
 * Created by martin on 12/10/2015.
 */
public class BackupManager {

    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String exportDB(String exportDir) {


        File dbFile = this.context.getDatabasePath(Globals.DATABASE_NAME);
        String pathDB = dbFile.getAbsolutePath();

        File fileSrc = new File(pathDB);
        String destination = exportDir + File.separator + fileSrc.getName();

        FileManager fileMgr = new FileManager();
        fileMgr.copyFile(pathDB, destination);

        Log.d(Globals.TAG, "DB copied to " + destination);

        return destination;

    }

}
