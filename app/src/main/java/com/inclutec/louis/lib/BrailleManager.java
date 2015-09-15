package com.inclutec.louis.lib;

import android.content.Context;

import com.inclutec.louis.exercises.ExerciseAbecedario;
import com.inclutec.louis.exercises.ExerciseAprestamiento;
import com.inclutec.louis.exercises.ExerciseLibre;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.BrailleExercise;

/**
 * Created by martin on 9/10/15.
 */
public class BrailleManager {
    Context context;
    ExerciseType exerciseType;
    BrailleExercise brailleExercise;

    public BrailleManager(Context aContext){
        context = aContext;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;

        switch(this.exerciseType){
            case ABECEDARIO:
                brailleExercise = new ExerciseAbecedario();
                break;
            case APRESTAMIENTO:
                brailleExercise = new ExerciseAprestamiento();
                break;
            case LIBRE:
                brailleExercise = new ExerciseLibre();
                break;
        }
    }

    public BrailleExercise getBrailleExercise() {
        return brailleExercise;
    }

}
