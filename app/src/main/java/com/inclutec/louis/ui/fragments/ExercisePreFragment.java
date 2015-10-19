package com.inclutec.louis.ui.fragments;

import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inclutec.louis.R;
import com.inclutec.louis.interfaces.BrailleExercise;

public class ExercisePreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BrailleExercise brailleExercise;

    public ExercisePreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_exercise_pre, container, false);

        Button btnStart = (Button) inflatedView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onExerciseStart(brailleExercise);
                }
            }
        });

        if (mListener != null){
            mListener.onExerciseLoad(brailleExercise);
        }

        loadExerciseDescription(inflatedView);

        // Inflate the layout for this fragment
        return inflatedView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onExerciseLoad(BrailleExercise exerciseType);

        void onExerciseStart(BrailleExercise exerciseType);
    }

    public void loadExerciseDescription(View inflatedView){

        TextView description = (TextView) inflatedView.findViewById(R.id.txtDescription);
        description.setText(brailleExercise.getExerciseDescription());

    }
}
