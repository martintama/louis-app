package com.inclutec.louis.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inclutec.louis.R;

public class ExerciseTimeoutFragment extends DialogFragment {

    View inflatedView;
    public ExerciseTimeoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        inflatedView = inflater.inflate(R.layout.dialog_exercise_timeout, container, false);
        View containerView = inflatedView.findViewById(R.id.container);
        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return inflatedView;
    }

}
