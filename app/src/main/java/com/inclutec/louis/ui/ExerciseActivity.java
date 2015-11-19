package com.inclutec.louis.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.inclutec.louis.db.models.Statistic;
import com.inclutec.louis.db.models.User;
import com.inclutec.louis.db.models.UserLevel;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.inclutec.louis.lib.BrailleExerciseManager;
import com.inclutec.louis.ui.fragments.ExerciseFragment;
import com.inclutec.louis.ui.fragments.ExercisePreFragment;
import com.inclutec.louis.ui.fragments.ExerciseResultFragment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

public class ExerciseActivity extends LouisActivity implements
        ExercisePreFragment.OnFragmentInteractionListener,
        ExerciseFragment.OnFragmentInteractionListener, ExerciseResultFragment.ExerciseResultListener {

    private String userName = "";
    private int userId = 0;
    private int level = 1;

    private BrailleExercise selectedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        super.loadToolbar();

        SharedPreferences prefs = getSharedPreferences(Globals.PREFS_NAME, Context.MODE_PRIVATE);
        userName = prefs.getString(Globals.PREFS_KEY_USER_NAME, "User");
        userId = prefs.getInt(Globals.PREFS_KEY_USER_ID, 0);

        Bundle bundle = getIntent().getExtras();
        ExerciseType selectedType = (ExerciseType) bundle.get("type");
        selectedExercise = BrailleExerciseManager.getBrailleExercise(selectedType);

        ExercisePreFragment firstFragment = new ExercisePreFragment();
        firstFragment.setArguments(bundle);
        firstFragment.setListener(this);

        getFragmentManager().beginTransaction()
                .add(R.id.exercise_fragment_container, firstFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_ejercitacion, menu);
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
    public void onExerciseLoad(BrailleExercise exerciseType) {
        setTitle(exerciseType.getExerciseTitle());
    }

    @Override
    public void onExerciseStart(BrailleExercise type) {

        // Create a new Fragment to be placed in the activity layout
        ExerciseFragment nextFragment = new ExerciseFragment();

        //Bundle savedInstanceState = new Bundle();

        //savedInstanceState.putSerializable("selectedType", type.getExerciseType());

        Bundle extras = getIntent().getExtras();
        extras.putInt("level", getHelper().getCurrentExerciseLevel(userId, selectedExercise));
        nextFragment.setArguments(extras);
        nextFragment.setListener(this);

        // Add the fragment to the 'fragment_container' FrameLayout
        getFragmentManager().beginTransaction()
                .replace(R.id.exercise_fragment_container, nextFragment).commit();
    }

    @Override
    public void onCharacterHit(String character) {
        getHelper().saveCharacterHit(userId, character);
    }

    @Override
    public void onCharacterMiss(String character) {
        getHelper().saveCharacterMiss(userId, character);
    }

    @Override
    public void onExerciseFinish(ExerciseType type, int level, int counterHit, int counterMiss, int seconds) {

        //Save user progress
        getHelper().saveExerciseProgress(userId, selectedExercise, level, counterHit, counterMiss, seconds);

        ExerciseResultFragment nextFragment = new ExerciseResultFragment();
        nextFragment.setListener(this);
        Bundle args = new Bundle();
        args.putInt("counterHit", counterHit);
        args.putInt("counterMiss", counterMiss);
        args.putInt("seconds", seconds);
        args.putString("userName", userName);
        args.putInt("level", level);
        nextFragment.setArguments(args);

        nextFragment.setListener(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.exercise_fragment_container, nextFragment).commit();

    }

    @Override
    public void finishLevel() {
        this.finish();
    }

    @Override
    public void repeatLevel() {
        this.onExerciseStart(selectedExercise);
    }

    @Override
    public void nextLevel() {
        getHelper().setLevelPassed(userId, selectedExercise);
        this.onExerciseStart(selectedExercise);
    }




}
