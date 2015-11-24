package com.inclutec.louis.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.inclutec.louis.Globals;
import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.LouisApplication;
import com.inclutec.louis.R;
import com.inclutec.louis.db.models.User;
import com.inclutec.louis.db.models.UserLevel;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.inclutec.louis.lib.BrailleCellImageHandler;
import com.inclutec.louis.lib.BrailleExerciseManager;
import com.j256.ormlite.dao.Dao;

public class ExercisePreFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BrailleExercise brailleExercise;

    private int exerciseLevel = 1;

    View inflatedView = null;

    public ExercisePreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        ExerciseType selectedType = (ExerciseType) bundle.get("type");

        BrailleExerciseManager brailleManager = ((LouisApplication)getActivity().getApplication()).getBrailleExerciseManager();

        brailleManager.setExerciseType(selectedType);

        brailleExercise = brailleManager.getBrailleExercise();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.fragment_exercise_pre, container, false);

        Button btnStart = (Button) inflatedView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    //The level is extracted in the other activity
                    mListener.onExerciseStart(brailleExercise);
                }
            }
        });

        if (mListener != null){
            mListener.onExerciseLoad(brailleExercise);
        }

        loadLevels(brailleExercise);
        loadExerciseDescription(inflatedView);

        // Inflate the layout for this fragment
        return inflatedView;
    }

    private void loadLevels(BrailleExercise brailleExercise) {



        View levelRow = inflatedView.findViewById(R.id.levelSelectorRow);
        if (brailleExercise.getExerciseType() == ExerciseType.ABECEDARIO){

            SharedPreferences prefs = getActivity().getSharedPreferences(Globals.PREFS_NAME, Context.MODE_PRIVATE);
            Integer userId = prefs.getInt(Globals.PREFS_KEY_USER_ID, 0);

            Integer currentLevel = ((LouisActivity)getActivity()).getHelper().getCurrentExerciseLevel(userId, brailleExercise);

            String[] levels= {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26"};
            ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, levels);

            Spinner levelSelector = (Spinner)inflatedView.findViewById(R.id.levelSelector);
            levelSelector.setAdapter(adapter_state);
            levelSelector.setSelection(adapter_state.getPosition(String.valueOf(currentLevel)));

            levelRow.setVisibility(View.VISIBLE);
        }
        else{

            levelRow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
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
