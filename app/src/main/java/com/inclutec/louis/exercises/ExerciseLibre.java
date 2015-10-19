package com.inclutec.louis.exercises;

import com.inclutec.louis.interfaces.BrailleExercise;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by martin on 9/12/15.
 */
public class ExerciseLibre implements BrailleExercise {

    String universe = "abcdefghijklmnñopqrstuvwxyzáéíóúü!?#";

    @Override
    public void initializeExercise() {

    }

    @Override
    public ExerciseType getExerciseType() {
        return ExerciseType.LIBRE;
    }

    @Override
    public String getNextChar() {

        String nextChar = "";
        int minimum = 0;
        int maximum = universe.length() - 1;
        int range = maximum - minimum + 1;

        Random ran = new Random();
        int randomNum = ran.nextInt(range) + minimum;

        nextChar = universe.substring(randomNum,randomNum+1);

        return nextChar;

    }

    @Override
    public String getExerciseTitle() {
        return "Libre";
    }

    @Override
    public String getExerciseDescription(){
        return "Breve descripcion del modulo de LIBRE";
    }

    //In the free mode, one cannot load progress as it's "free"
    @Override
    public void loadProgress(int level, int studiedLevelProgress) {
        //nothing to do.
    }
}
