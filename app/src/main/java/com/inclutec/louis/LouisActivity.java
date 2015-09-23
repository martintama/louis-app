package com.inclutec.louis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.inclutec.louis.db.SQLiteHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by martin on 9/23/15.
 */
public class LouisActivity extends AppCompatActivity {
    private SQLiteHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper != null) {
            // Log.d(Globals.TAG, "Releasing DB Helper");
            OpenHelperManager.releaseHelper();
            mDbHelper = null;
        }
    }

    public SQLiteHelper getHelper(){
        return ((LouisApplication)getApplication()).getHelper();
    }
}
