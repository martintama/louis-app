package com.inclutec.louis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.inclutec.louis.fragments.ExerciseFragment;
import com.inclutec.louis.fragments.ExercisePreFragment;
import com.inclutec.louis.exercises.ExerciseType;

public class ExerciseActivity extends AppCompatActivity implements
        ExercisePreFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Bundle bundle = getIntent().getExtras();

        // Create a new Fragment to be placed in the activity layout
        ExercisePreFragment firstFragment = new ExercisePreFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(bundle);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exercise_fragment_container, firstFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ejercitacion, menu);
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

    @Override
    public void onExerciseStart(ExerciseType type) {
        // Create a new Fragment to be placed in the activity layout
        ExerciseFragment nextFragment = new ExerciseFragment();

        Bundle savedInstanceState = new Bundle();

        savedInstanceState.putSerializable("selectedType", type);
        nextFragment.setArguments(savedInstanceState);

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        nextFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.exercise_fragment_container, nextFragment).commit();
    }

}
