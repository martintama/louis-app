package com.inclutec.louis.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
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

    private int userId;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        super.loadToolbar();

        setTitle(R.string.title_activity_estadisticas);


        SharedPreferences prefs = getSharedPreferences(Globals.PREFS_NAME, MODE_PRIVATE);
        userId = prefs.getInt(Globals.PREFS_KEY_USER_ID, 0);
        name = prefs.getString(Globals.PREFS_KEY_USER_NAME, "");

        loadStatistics();
        loadCharacterStatistics();
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

    private void loadStatistics() {
        try {


            Dao statsDao = getHelper().getStatisticDao();


            TextView txtName = (TextView) findViewById(R.id.txtName);
            txtName.setText(name);


            String query = "Select exercise, sum(time_elapsed) time, sum(qty_ok), sum(qty_miss) " +
                    "from statistics where active = 1 and user_id = " + userId + " group by exercise order by exercise";

            Log.d(Globals.TAG, String.format("Query is %s", query));

            GenericRawResults<String[]> rawResults = statsDao.queryRaw(query);

            Log.d(Globals.TAG, String.format("Results: %s", rawResults.toString()));

            List<String[]> results = rawResults.getResults();

            TextView txtExercise = null;
            TextView txtTime = null;
            TextView txtHits = null;
            TextView txtMiss = null;

            for (int i = 0; i < results.size(); i++) {
                String[] fila = results.get(i);

                switch (fila[0].toLowerCase()) {
                    case "aprestamiento":
                        txtExercise = (TextView) findViewById(R.id.exercise1_title);
                        txtExercise.setText("Aprestamiento");
                        txtTime = (TextView) findViewById(R.id.exercise1_time);
                        txtHits = (TextView) findViewById(R.id.exercise1_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise1_miss);

                        break;
                    case "abecedario":
                        txtExercise = (TextView) findViewById(R.id.exercise2_title);
                        txtExercise.setText("Abecedario");
                        txtTime = (TextView) findViewById(R.id.exercise2_time);
                        txtHits = (TextView) findViewById(R.id.exercise2_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise2_miss);

                        break;
                    case "libre":
                        txtExercise = (TextView) findViewById(R.id.exercise3_title);
                        txtExercise.setText("Libre");
                        txtTime = (TextView) findViewById(R.id.exercise3_time);
                        txtHits = (TextView) findViewById(R.id.exercise3_hit);
                        txtMiss = (TextView) findViewById(R.id.exercise3_miss);

                        break;

                }

                int minutes = Integer.parseInt(fila[1]) / 60;
                int seconds = Integer.parseInt(fila[1]) - minutes*60;

                txtTime.setText(String.format("%02d:%02d", minutes, seconds));
                txtHits.setText(fila[2]);
                txtMiss.setText(fila[3]);
            }


        } catch (SQLException e) {
            Log.e(Globals.TAG, String.format("Error loading statistics: %s", e.getMessage()), e);
        }
    }
    private void loadCharacterStatistics(){

        // Get the TableLayout
        TableLayout tl = (TableLayout) findViewById(R.id.mostMissedCharacterTable);
        tl.removeAllViews();

        List<String[]> resultList = getHelper().getMostMissedCharacters(this.userId);

        int counter = 1;
        if (resultList != null && resultList.size() > 0){

            // Create a TableRow and give it an ID
            TableRow trTitles = new TableRow(this);
            trTitles.setId(100+counter);
            trTitles.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
            );

            TextView labelPositionTitle = new TextView(this);
            labelPositionTitle.setId(150 + counter);
            labelPositionTitle.setText("Pos.");
            trTitles.addView(labelPositionTitle);

            TextView labelCharTitle = new TextView(this);
            labelCharTitle.setId(200 + counter);
            labelCharTitle.setText("Letra");
            trTitles.addView(labelCharTitle);

            TextView labelMissesTitle = new TextView(this);
            labelMissesTitle.setId(250 + counter);
            labelMissesTitle.setText("Errores");

            trTitles.addView(labelMissesTitle);

            TextView labelTotalsTitle = new TextView(this);
            labelTotalsTitle.setId(300 + counter);
            labelTotalsTitle.setText("Total");

            trTitles.addView(labelTotalsTitle);

            TextView labelPercentageTitle = new TextView(this);
            labelPercentageTitle.setId(350 + counter);
            labelPercentageTitle.setText("Porcentaje");

            trTitles.addView(labelPercentageTitle);

            // Add the TableRow to the TableLayout
            tl.addView(trTitles, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));


            for(String[] row : resultList){
                // Create a TableRow and give it an ID
                TableRow tr = new TableRow(this);
                tr.setId(1000 + counter);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView labelPosition = new TextView(this);
                labelPosition.setId(1500 + counter);
                labelPosition.setText(String.valueOf(counter));

                tr.addView(labelPosition);

                TextView labelChar = new TextView(this);
                labelChar.setId(2000 + counter);
                labelChar.setText(row[0]);

                tr.addView(labelChar);

                TextView labelMisses = new TextView(this);
                labelMisses.setId(3000 + counter);
                labelMisses.setText(row[1]);

                tr.addView(labelMisses);

                TextView labelTotal = new TextView(this);
                labelTotal.setId(4000 + counter);
                labelTotal.setText(row[2]);

                tr.addView(labelTotal);

                TextView labelPercentage = new TextView(this);
                labelPercentage.setId(5000 + counter);
                labelPercentage.setText(row[3] + "%");

                tr.addView(labelPercentage);

                // Add the TableRow to the TableLayout
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                counter++;
            }
        }
        else{
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(1000 + counter);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView labelChar = new TextView(this);
            labelChar.setId(1000 + counter);
            labelChar.setText("No se han encotrado suficientes datos");

            tr.addView(labelChar);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }

    }

}
