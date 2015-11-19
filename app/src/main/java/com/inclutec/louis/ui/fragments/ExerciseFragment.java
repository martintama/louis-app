package com.inclutec.louis.ui.fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisApplication;
import com.inclutec.louis.R;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.ArduinoDeviceConnector;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.inclutec.louis.lib.BrailleCellImageHandler;
import com.inclutec.louis.lib.BrailleExerciseManager;
import com.inclutec.louis.lib.LouisDeviceConnector;
import com.inclutec.louis.lib.TimeCounterTask;
import com.inclutec.louis.mocks.LouisDeviceMock;

import java.security.PrivilegedAction;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExerciseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private BrailleCellImageHandler brailleCellImageHandler;
    private BrailleExerciseManager brailleManager;
    private ArduinoDeviceConnector louisDeviceConnector;
    private BrailleExercise exercise;
    private ExerciseType selectedType;

    private View inflatedView;

    private TimeCounterTask timeCounterTask;
    
    private int counterHit = 0;
    private int counterMiss = 0;
    private int seconds = 0;
    private int totalSeconds = 0;
    private int maxSeconds = 30;

    private int userLevel = 1;

    private String lastChar;

    private boolean soundsEnabled = false;

    public ExerciseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.loadPreferences();
        Bundle bundle = getArguments();
        selectedType = (ExerciseType) bundle.get("type");
        userLevel = bundle.getInt("level",1);

        brailleCellImageHandler = new BrailleCellImageHandler(getActivity());
        brailleManager = ((LouisApplication)getActivity().getApplication()).getBrailleExerciseManager();
        louisDeviceConnector = ((LouisApplication)getActivity().getApplication()).getDeviceConnector();

        //this should not be necessary but... well...
        louisDeviceConnector.setContext(getActivity());

        brailleManager.setExerciseType(selectedType);

        exercise = brailleManager.getBrailleExercise();

    }

    private void loadPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Globals.PREFS_NAME, Context.MODE_PRIVATE);
        this.soundsEnabled = prefs.getBoolean(getActivity().getString(R.string.preference_sounds), false);
        String maxSecondsString = prefs.getString(getActivity().getString(R.string.preference_answer_timeout), "30");
        this.maxSeconds = Integer.parseInt(maxSecondsString);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_exercise, container, false);

        setListeners(inflatedView);

        this.initializeExercise();

        return inflatedView;
    }

    @Override
    public void onStop(){
        super.onStop();
        this.stopTimer();

    }

    private void setListeners(View container){
        View.OnClickListener buttonClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnFinish:
                        stopTimer();
                        if (mListener != null){
                            mListener.onExerciseFinish(selectedType, userLevel, counterHit, counterMiss, seconds);
                        }
                        break;
                    case R.id.btnResend:
                        louisDeviceConnector.write(lastChar);
                        break;
                    case R.id.btnThumbsUp:
                        counterHit += 1;
                        stopTimer();
                        loadNextChar();
                        break;

                    case R.id.btnThumbsDown:
                        counterMiss += 1;
                        stopTimer();
                        loadNextChar();
                        break;

                }
            }
        };

        container.findViewById(R.id.btnFinish).setOnClickListener(buttonClickListener);
        container.findViewById(R.id.btnResend).setOnClickListener(buttonClickListener);
        container.findViewById(R.id.btnThumbsUp).setOnClickListener(buttonClickListener);
        container.findViewById(R.id.btnThumbsDown).setOnClickListener(buttonClickListener);

    }

    public void initializeExercise(){
        exercise.loadProgress(userLevel, 1);

        this.loadNextChar();
    }

    private void loadNextChar(){
        String nextChar = exercise.getNextChar();

        //If we have reached the finish
        if (nextChar == "\n"){
            if (mListener != null){
                mListener.onExerciseFinish(selectedType, userLevel, counterHit, counterMiss, totalSeconds);
            }
        }else {
            setBrailleCharImage(nextChar);
            setCharText(nextChar);
            louisDeviceConnector.write(nextChar);

            startTimer();
            if (soundsEnabled){
                this.playSound();
            }


            lastChar = nextChar;
        }
    }
    private void setBrailleCharImage(String character){
        ImageView image = (ImageView) inflatedView.findViewById(R.id.imgBraille);

        Drawable cellImage = brailleCellImageHandler.getCellPicture(character);
        image.setImageDrawable(cellImage);

    }

    private void setCharText(String character){
        TextView text = (TextView) inflatedView.findViewById(R.id.txtCaracter);
        text.setText(character);
    }

    private void playSound(){
        //Play sound to notify the user the recording stopped
        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),defaultRingtoneUri);
        mediaPlayer.start();
    }


    private void startTimer() {

        Log.d(Globals.TAG, "startTimer called");
        setTimer(this.maxSeconds);
        seconds = this.maxSeconds;

        timeCounterTask = new TimeCounterTask(new TimeCounterTask.TaskListener() {
            @Override
            public void onIntervalElapsed(Long timeLong) {

                seconds--;
                totalSeconds++;

                setTimer(seconds);

                if (seconds <= 0){
                    stopTimer();
                    showTimeoutDialog();

                }

                Log.d(Globals.TAG, "Seconds elapsed: " + String.valueOf(seconds));

            }
        });

        Log.d(Globals.TAG, "Executing timeCounterTask");
        timeCounterTask.execute();
        Log.d(Globals.TAG, "Returned");
    }

    private void setTimer(Integer seconds) {
        TextView tvCountdown = (TextView)inflatedView.findViewById(R.id.tvTimer);

        int minutes = seconds / 60;
        seconds = seconds - minutes*60;

        tvCountdown.setText(String.format("%02d:%02d", minutes, seconds));

    }

    private void stopTimer() {

        if (timeCounterTask != null && !timeCounterTask.isCancelled()) {
            Log.d(Globals.TAG, "Timer stopped");
            timeCounterTask.cancel(true);
            timeCounterTask = null;
        }
    }

    private void showTimeoutDialog(){
        FragmentManager fm = getActivity().getFragmentManager();

        DialogFragment newFragment = new ExerciseTimeoutFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(fm, "timeout");
    }

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        void onExerciseFinish(ExerciseType type, int level, int counterHit, int counterMiss, int seconds);
    }
}
