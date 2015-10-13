package com.inclutec.louis.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inclutec.louis.Globals;
import com.inclutec.louis.R;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.lib.BrailleCellImageHandler;
import com.inclutec.louis.lib.BrailleManager;

public class ExerciseResultFragment extends Fragment {

    View inflatedView;

    private String userName = "User";
    private int userId = 0;
    private int seconds = 0;
    private int counterHit = 0;
    private int counterMiss = 0;

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


        inflatedView.findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        loadResults();

        return inflatedView;
    }

    public void loadResults(){

        Bundle bundle = getArguments();
        userName =  bundle.getString("userName");
        counterHit = bundle.getInt("counterHit");
        counterMiss = bundle.getInt("counterMiss");
        seconds = bundle.getInt("seconds");


        TextView txtThumbsUpCounter = (TextView) inflatedView.findViewById(R.id.txtThumbsUpCounter);
        TextView txtThumbsDownCounter = (TextView) inflatedView.findViewById(R.id.txtThumbsDownCounter);
        TextView txtName = (TextView) inflatedView.findViewById(R.id.txtName);
        TextView txtTime = (TextView) inflatedView.findViewById(R.id.txtTime);

        txtThumbsUpCounter.setText(String.valueOf(counterHit));
        txtThumbsDownCounter.setText(String.valueOf(counterMiss));
        txtName.setText(userName);
        txtTime.setText(String.valueOf(seconds));


    }

}
