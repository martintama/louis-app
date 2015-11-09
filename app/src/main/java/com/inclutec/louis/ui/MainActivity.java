package com.inclutec.louis.ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.LouisApplication;
import com.inclutec.louis.R;
import com.inclutec.louis.adapters.DrawerListAdapter;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.extra.DrawerItem;
import com.inclutec.louis.lib.BackupManager;
import com.inclutec.louis.lib.FileManager;
import com.inclutec.louis.ui.fragments.ProfileMainFragment;
import com.inclutec.louis.ui.fragments.ProfileNewFragment;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends LouisActivity{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] drawerListItemsTag;

    private final Handler mDrawerActionHandler = new Handler();
    private int mNavItemId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.loadToolbar();
        loadNavDrawer();

        View.OnClickListener activityButtonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnAbecedario:
                        openPracticeActivity(ExerciseType.ABECEDARIO);
                        break;
                    case R.id.btnAprestamiento:
                        openPracticeActivity(ExerciseType.APRESTAMIENTO);
                        break;
                    case R.id.btnLibre:
                        openPracticeActivity(ExerciseType.LIBRE);
                        break;

                }
            }
        };

        findViewById(R.id.btnAbecedario).setOnClickListener(activityButtonListener);
        findViewById(R.id.btnAprestamiento).setOnClickListener(activityButtonListener);
        findViewById(R.id.btnLibre).setOnClickListener(activityButtonListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_export: {

                BackupManager bkMgr = new BackupManager();
                bkMgr.setContext(getApplicationContext());

                bkMgr.exportDB(this.getExternalFilesDir(null).getAbsolutePath());

                break;
            }
            case R.id.action_connect:{

                LouisApplication app = (LouisApplication) getApplication();
                int status = app.getDeviceConnector().connect();

                if (status == 0){
                    Toast aToast = Toast.makeText(this, "Conectado", Toast.LENGTH_LONG);
                    aToast.show();
                }
                else{
                    Toast aToast = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
                    aToast.show();
                }

                break;
            }
            case R.id.action_disconnect:{

                LouisApplication app = (LouisApplication) getApplication();
                int status = app.getDeviceConnector().disconnect();

                if (status == 0){
                    Toast aToast = Toast.makeText(this, "Desconectado", Toast.LENGTH_LONG);
                    aToast.show();
                }
                else{
                    Toast aToast = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
                    aToast.show();
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPracticeActivity(ExerciseType mode){

        //first check if there's any user loaded
        int userId = this.getSharedPreferences(Globals.PREFS_NAME, MODE_PRIVATE).getInt(Globals.PREFS_KEY_USER_ID, 0);

        if (userId > 0) {
            Intent intentSettings = new Intent(this, ExerciseActivity.class);
            intentSettings.putExtra("type", mode);
            this.startActivity(intentSettings);
        }
        else{
            Toast aToast = Toast.makeText(this, "Debe seleccionar un usuario primero", Toast.LENGTH_LONG);
            aToast.show();
        }
    }

    private void loadNavDrawer() {

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //add the header to the drawer
        View header = getLayoutInflater().inflate(R.layout.drawer_header, null);

        mDrawerList.addHeaderView(header);

        //add the options
        // Get the titles and construct the items with the icons
        drawerListItemsTag = getResources().getStringArray(R.array.drawer_options);

        ArrayList<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem(drawerListItemsTag[0], R.drawable.ic_account_circle_black_48dp));
        items.add(new DrawerItem(drawerListItemsTag[1], R.drawable.ic_assessment_black_48dp));
            items.add(new DrawerItem(drawerListItemsTag[2], R.drawable.ic_settings_black_48dp));

        mDrawerList.setAdapter(new DrawerListAdapter(this, items));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Toolbar toolbar = super.getToolbar();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intentSettings = null;

            //The zero is the header
            switch(position){
                case 1: //Alumno
                    intentSettings = new Intent(getApplication(), ProfileActivity.class);

                    startActivity(intentSettings);
                    break;
                case 2: //Estadisticas
                    intentSettings = new Intent(getApplication(), StatsActivity.class);

                    startActivity(intentSettings);
                    break;
                case 3: //Configuracion
                    intentSettings = new Intent(getApplication(), SettingsActivity.class);

                    startActivity(intentSettings);
                    break;
            }

        }
    }

}
