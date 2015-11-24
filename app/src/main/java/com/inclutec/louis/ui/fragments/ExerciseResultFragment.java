package com.inclutec.louis.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inclutec.louis.R;
import com.inclutec.louis.exercises.ExerciseType;

public class ExerciseResultFragment extends Fragment {

    private ExerciseResultListener mListener;

    View inflatedView;

    private String userName = "User";
    private int userId = 0;
    private int seconds = 0;
    private int counterHit = 0;
    private int counterMiss = 0;
    private int level = 0;
    private ExerciseType exerciseType;

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
        inflatedView.findViewById(R.id.btnNext).setOnClickListener(new ExerciseFinishButtonClickListener());
        inflatedView.findViewById(R.id.btnRepeat).setOnClickListener(new ExerciseFinishButtonClickListener());

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
        ExerciseType selectedType = (ExerciseType) bundle.getSerializable("type");

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

        int totalChars = counterMiss + counterHit;
        //If the user had some error or did not complete the level
        if (selectedType == ExerciseType.ABECEDARIO && counterMiss > 1 && totalChars < level + 7){
            Button nextButton = (Button) inflatedView.findViewById(R.id.btnNext);
            nextButton.setEnabled(false);

            nextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast aToast = Toast.makeText(getActivity(), "Para pasar al próximo nivel no debes tener errores", Toast.LENGTH_LONG);
                    aToast.show();;
                }
            });
        }

        if (selectedType == ExerciseType.APRESTAMIENTO || selectedType == ExerciseType.LIBRE){
            Button nextButton = (Button) inflatedView.findViewById(R.id.btnNext);
            nextButton.setVisibility(View.GONE);

            Button repeatButton = (Button) inflatedView.findViewById(R.id.btnRepeat);
            repeatButton.setVisibility(View.GONE);
        }else{
            Button nextButton = (Button) inflatedView.findViewById(R.id.btnNext);
            nextButton.setVisibility(View.VISIBLE);

            Button repeatButton = (Button) inflatedView.findViewById(R.id.btnRepeat);
            repeatButton.setVisibility(View.VISIBLE);
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
