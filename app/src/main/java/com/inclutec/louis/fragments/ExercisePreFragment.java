package com.inclutec.louis.fragments;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inclutec.louis.R;
import com.inclutec.louis.exercises.ExerciseType;

public class ExercisePreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ExerciseType selectedType;

    public ExercisePreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        selectedType = (ExerciseType) bundle.get("type");
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
                    mListener.onExerciseStart(selectedType);
                }
            }
        });


        loadExerciseDescription(inflatedView);

        // Inflate the layout for this fragment
        return inflatedView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onExerciseStart(ExerciseType type);
    }

    public void loadExerciseDescription(View inflatedView){

        TextView description = (TextView) inflatedView.findViewById(R.id.txtDescription);
        switch(selectedType){
            case ABECEDARIO:
                description.setText(R.string.abecedario_description);
                break;
            case APRESTAMIENTO:
                description.setText(R.string.aprestamiento_description);
                break;
            case LIBRE:
                description.setText(R.string.free_description);
                break;
        }
    }
}
