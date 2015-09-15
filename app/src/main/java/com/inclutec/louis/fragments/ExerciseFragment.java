package com.inclutec.louis.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inclutec.louis.R;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.ArduinoDeviceConnector;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.inclutec.louis.lib.BrailleCellImageHandler;
import com.inclutec.louis.lib.BrailleManager;
import com.inclutec.louis.lib.LouisDeviceConnector;
import com.inclutec.louis.mocks.LouisDeviceMock;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExerciseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private BrailleCellImageHandler brailleCellImageHandler;
    private BrailleManager brailleManager;
    private BrailleExercise exercise;
    private ExerciseType selectedType;
    private String lastChar;

    private View inflatedView;


    public ExerciseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        selectedType = (ExerciseType) bundle.get("type");

        brailleCellImageHandler = new BrailleCellImageHandler(getActivity());
        brailleManager = new BrailleManager(getActivity());

        brailleManager.setExerciseType(selectedType);

        exercise = brailleManager.getBrailleExercise();




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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void setListeners(View container){
        View.OnClickListener buttonClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnNext:

                        loadNextChar();
                        break;
                    case R.id.btnResend:

                        //TODO find a way to make this configurable at startup
                        LouisDeviceMock mock = new LouisDeviceMock();
                        mock.setContext(getActivity());
                        mock.write(lastChar);


                        break;

                }
            }
        };

        container.findViewById(R.id.btnNext).setOnClickListener(buttonClickListener);
        container.findViewById(R.id.btnResend).setOnClickListener(buttonClickListener);

    }

    public void initializeExercise(){
        exercise.loadProgress(1, 1);

        this.loadNextChar();
    }

    private void loadNextChar(){
        String nextChar = exercise.getNextChar();

        //If we have reached the finish
        if (nextChar == "\n"){
            if (mListener != null){
                mListener.onExerciseFinish(selectedType);
            }
        }else {
            setBrailleCharImage(nextChar);
            setCharText(nextChar);

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

    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        void onExerciseFinish(ExerciseType type);
    }
}
