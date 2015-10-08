package com.inclutec.louis.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.inclutec.louis.Globals;
import com.inclutec.louis.R;
import com.inclutec.louis.adapters.UserListDataAdapter;
import com.inclutec.louis.db.datasources.UserListDataSource;
import com.inclutec.louis.ui.ProfileActivity;

import java.sql.SQLException;

public class ProfileMainFragment extends Fragment {

    private String CLASSNAME = "ProfileMainFragment";

    private boolean destroyed;
    private ListView userList;
    private CursorAdapter cursorAdapter;
    private View inflatedView;

    public ProfileMainFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        destroyed = true;

        // calling changeCursor() will close the previous cursor
        if( cursorAdapter != null ) { cursorAdapter.changeCursor( null ); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_profile_main, container, false);

        loadUserList();

        return inflatedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadUserList(){

        this.cursorAdapter = new UserListDataAdapter(getActivity(), null, 0);

        userList = (ListView) inflatedView.findViewById(R.id.userList);

        //bint the cursoradapter to the list
        userList.setAdapter(cursorAdapter);

        //refresh the adapter with data from datasource
        this.updateMessageList(getActivity());

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                Bundle data = (Bundle) view.getTag();

                int user_id = data.getInt("user_id");
                String name = data.getString("name");

                Toast theToast = Toast.makeText(getActivity(),
                        String.format("Setting user %s of id %s", name, user_id), Toast.LENGTH_LONG);

                theToast.show();

            }
        });
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
