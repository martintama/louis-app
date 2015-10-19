package com.inclutec.louis.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inclutec.louis.R;

public class ExerciseResultFragment extends Fragment {

    private ExerciseResultListener mListener;

    View inflatedView;

    private String userName = "User";
    private int userId = 0;
    private int seconds = 0;
    private int counterHit = 0;
    private int counterMiss = 0;
    private int level = 0;

    public ExerciseResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_exercise_result, container, false);


        inflatedView.findViewById(R.id.btnFinish).setOnClickListener(new ExerciseFinishButtonClickListener());

        loadResults();

        return inflatedView;
    }

    private class ExerciseFinishButtonClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnFinish:{
                    if (mListener != null){
                        mListener.finishLevel();
                    }
                    break;
                }
                case R.id.btnRepeat:{
                    if (mListener != null){
                        mListener.repeatLevel();
                    }
                    break;
                }
                case R.id.btnNext:{
                    if (mListener != null){
                        mListener.nextLevel();
                    }
                    break;
                }
            }
        }
    }
    public void loadResults(){

        Bundle bundle = getArguments();
        userName =  bundle.getString("userName");
        counterHit = bundle.getInt("counterHit");
        counterMiss = bundle.getInt("counterMiss");
        seconds = bundle.getInt("seconds");
        level = bundle.getInt("level");


        TextView txtThumbsUpCounter = (TextView) inflatedView.findViewById(R.id.txtThumbsUpCounter);
        TextView txtThumbsDownCounter = (TextView) inflatedView.findViewById(R.id.txtThumbsDownCounter);
        TextView txtName = (TextView) inflatedView.findViewById(R.id.txtName);
        TextView txtTime = (TextView) inflatedView.findViewById(R.id.txtTime);
        TextView txtLevel = (TextView) inflatedView.findViewById(R.id.txtLevel);

        txtThumbsUpCounter.setText(String.valueOf(counterHit));
        txtThumbsDownCounter.setText(String.valueOf(counterMiss));
        txtName.setText(userName);
        txtTime.setText(String.valueOf(seconds));
        txtLevel.setText(String.valueOf(level));

        //If the user had some error
        if (counterMiss > 1){
            Button nextButton = (Button) inflatedView.findViewById(R.id.btnNext);
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setListener(ExerciseResultListener mListener) {
        this.mListener = mListener;
    }

    public interface ExerciseResultListener{
        void finishLevel();
        void repeatLevel();
        void nextLevel();
    }

}
