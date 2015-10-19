package com.inclutec.louis.interfaces;

import com.inclutec.louis.exercises.ExerciseType;

/**
 * Created by martin on 9/10/15.
 */
public interface BrailleExercise {

    void initializeExercise();

    ExerciseType getExerciseType();

    String getNextChar();

    String getExerciseTitle();

    String getExerciseDescription();

    void loadProgress(int level, int studiedLevelProgress);
}
