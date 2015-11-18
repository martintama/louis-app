package com.inclutec.louis.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.inclutec.louis.R;

public class ExerciseTimeoutDialog extends DialogFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseTimeoutDialog newInstance(String param1, String param2) {
        ExerciseTimeoutDialog fragment = new ExerciseTimeoutDialog();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ExerciseTimeoutDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View inflattedView = inflater.inflate(R.layout.dialog_exercise_timeout, container, false);

        return inflattedView;
    }

}
