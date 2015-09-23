package com.inclutec.louis.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.sql.SQLException;

public class ProfileActivity extends LouisActivity {

    private String CLASSNAME = "ProfileActivity";

    private boolean destroyed;
    private ListView userList;
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.cursorAdapter = new UserListDataAdapter(this, null, 0);

        final ListView lv = (ListView) findViewById(R.id.userList);

        //bint the cursoradapter to the list
        lv.setAdapter(cursorAdapter);

        //refresh the adapter with data from datasource
        this.updateMessageList(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                Bundle data = (Bundle) view.getTag();

                int user_id = data.getInt("user_id");
                String name = data.getString("name");

                Toast theToast = Toast.makeText(getApplicationContext(),
                        String.format("Setting user %s of id %s", name, user_id), Toast.LENGTH_LONG);

                theToast.show();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        destroyed = true;

        // calling changeCursor() will close the previous cursor
        if( cursorAdapter != null ) { cursorAdapter.changeCursor( null ); }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
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

    public void updateMessageList(final Context context) {

        new AsyncTask<Void, Void, Cursor>() {
            /**
             * Run the query in the background so we don't cause ANRs
             */
            @Override
            protected Cursor doInBackground(Void... params){

                UserListDataSource dataSource = new UserListDataSource(context);

                try {
                    return dataSource.getCursorForAll();
                } catch (SQLException e) {
                    Globals.logError(CLASSNAME, e.getMessage(), e);

                    return null;
                }

            }

            /**
             * After doInBackground() returns, call onCursorLoaded() to
             * change cursorAdapter's Cursor
             */
            @Override
            protected void onPostExecute (Cursor result){
                Log.d(Globals.TAG, "[MESSAGELIST FRAGMENT] OnPostExecute of reloading cursor");
                onCursorLoaded(result);
            }
        }.execute(null, null, null) ;
    }

    private void onCursorLoaded( Cursor cur ) {
        // This is the tricky part. Since the activity could've been
        // destroyed during the time CursorTask spent in the background
        // we have to make sure we haven't been destoyed and that
        // this.cursorAdapter is stil valid. We should be extra
        // careful here because  we don't want to leak DB Cursors.
        if( !destroyed ) {

            cursorAdapter.changeCursor( cur );
        }
        else {
            cur.close();
        }
    }

}
