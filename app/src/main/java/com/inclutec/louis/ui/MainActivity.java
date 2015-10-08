package com.inclutec.louis.ui;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.inclutec.louis.adapters.DrawerListAdapter;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.extra.DrawerItem;

import java.util.ArrayList;

public class MainActivity extends LouisActivity{

    private android.support.v7.widget.Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] drawerListItemsTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void openPracticeActivity(ExerciseType mode){
        Intent intentSettings = new Intent(this, ExerciseActivity.class);
        intentSettings.putExtra("type", mode);
        this.startActivity(intentSettings);
    }

    private void loadNavDrawer() {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");  ;//que no diga nada por ahora

        setSupportActionBar(toolbar);

        //add the header to the drawer
        View header = getLayoutInflater().inflate(R.layout.drawer_header, null);

        mDrawerList.addHeaderView(header);

        //add the options
        // Get the titles and construct the items with the icons
        drawerListItemsTag = getResources().getStringArray(R.array.drawer_options);

        ArrayList<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem(drawerListItemsTag[0], R.drawable.ic_account_circle_white_36dp));
        items.add(new DrawerItem(drawerListItemsTag[1], R.drawable.ic_assessment_white_36dp));
        items.add(new DrawerItem(drawerListItemsTag[2], R.drawable.ic_settings_white_36dp));

        mDrawerList.setAdapter(new DrawerListAdapter(this, items));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
