package com.inclutec.louis.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.List;

public class StatsActivity extends LouisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        super.loadToolbar();

        setTitle(R.string.title_activity_estadisticas);
        loadStatistics();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadStatistics(){
        try {

            SharedPreferences prefs = getSharedPreferences(Globals.PREFS_NAME, MODE_PRIVATE);

            Dao statsDao = getHelper().getStatisticDao();

            String user_id = String.valueOf(prefs.getInt(Globals.PREFS_KEY_USER_ID, 0));
            String name = prefs.getString(Globals.PREFS_KEY_USER_NAME, "");

            TextView txtName = (TextView) findViewById(R.id.txtName);
            txtName.setText(name);


            String query = "Select exercise, sum(time_elapsed) time, sum(qty_ok), sum(qty_miss) " +
                    "from statistics where active = 1 and user_id = " + user_id + " group by exercise order by exercise";

            Log.d(Globals.TAG, String.format("Query is %s", query));

            GenericRawResults<String[]> rawResults = statsDao.queryRaw(query);

            Log.d(Globals.TAG, String.format("Results: %s", rawResults.toString()));

            List<String[]> results = rawResults.getResults();

            TextView txtExercise = null;
            TextView txtTime = null;
            TextView txtHits = null;
            TextView txtMiss = null;

            for (int i = 0; i<results.size();i++){
                String[] fila = results.get(i);

                switch(fila[0].toLowerCase()){
                    case "aprestamiento":
                        txtExercise = (TextView) findViewById(R.id.exercise1_title);
                        txtTime = (TextView) findViewById(R.id.exercise1_time);
                        txtHits = (TextView) findViewById(R.id.exercise1_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise1_miss);

                        break;
                    case "abecedario":
                        txtExercise = (TextView) findViewById(R.id.exercise2_title);
                        txtTime = (TextView) findViewById(R.id.exercise2_time);
                        txtHits = (TextView) findViewById(R.id.exercise2_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise2_miss);

                        break;
                    case "libre":
                        txtExercise = (TextView) findViewById(R.id.exercise3_title);
                        txtTime = (TextView) findViewById(R.id.exercise3_time);
                        txtHits = (TextView) findViewById(R.id.exercise3_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise3_miss);

                        break;

                }
                txtExercise.setText(fila[0]);
                txtTime.setText(fila[1]);
                txtHits.setText(fila[2]);
                txtMiss.setText(fila[3]);
            }



        } catch (SQLException e) {
            Log.e(Globals.TAG, String.format("Error loading statistics: %s", e.getMessage()),e);
        }

    }
}
