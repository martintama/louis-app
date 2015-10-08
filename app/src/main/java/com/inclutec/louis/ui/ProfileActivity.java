package com.inclutec.louis.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.inclutec.louis.adapters.UserListDataAdapter;
import com.inclutec.louis.db.datasources.UserListDataSource;
import com.inclutec.louis.ui.fragments.ProfileMainFragment;
import com.inclutec.louis.ui.fragments.ProfileNewFragment;

import java.sql.SQLException;

public class ProfileActivity extends LouisActivity {

    private String CLASSNAME = "ProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileMainFragment firstFragment = new ProfileMainFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_fragment_container, firstFragment).commit();

        final Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(actionBarToolbar);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_user_new) {

            ProfileNewFragment newFragment = new ProfileNewFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Add the fragment to the 'fragment_container' FrameLayout
            transaction.replace(R.id.profile_fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
