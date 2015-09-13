package com.inclutec.louis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.inclutec.louis.exercises.ExerciseType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void openPracticeActivity(ExerciseType mode){
        Intent intentSettings = new Intent(this, ExerciseActivity.class);
        intentSettings.putExtra("type", mode);
        this.startActivity(intentSettings);
    }

}
