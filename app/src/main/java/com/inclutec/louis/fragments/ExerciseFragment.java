package com.inclutec.louis.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inclutec.louis.R;
import com.inclutec.louis.lib.BrailleCellHandler;
import com.inclutec.louis.util.ExerciseType;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExerciseFragment extends Fragment {

     BrailleCellHandler brailleCellHandler;

    public ExerciseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_exercise, container, false);

        setListeners(inflatedView);

        return inflatedView;
    }

    public void setListeners(View container){
        View.OnClickListener buttonClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnNext:

                        break;
                    case R.id.btnResend:

                        break;

                }
            }
        };

        container.findViewById(R.id.btnNext).setOnClickListener(buttonClickListener);
        container.findViewById(R.id.btnResend).setOnClickListener(buttonClickListener);

    }
}
