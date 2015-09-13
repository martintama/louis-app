package com.inclutec.louis.interfaces;

/**
 * Created by martin on 9/10/15.
 */
public interface BrailleExercise {

    void initializeExercise();

    String getNextChar();

    void loadProgress(int level, int studiedLevelProgress);
}
