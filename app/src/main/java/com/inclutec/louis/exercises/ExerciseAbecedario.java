package com.inclutec.louis.exercises;

import com.inclutec.louis.R;
import com.inclutec.louis.interfaces.BrailleExercise;

/**
 * Created by martin on 9/12/15.
 */
public class ExerciseAbecedario implements BrailleExercise {

    private int SEQ_START=7;
    private int MAX_LEVEL=26;

    private int level;
    private int nextCharIndex = 1;
    private String sequence;
    private String studiedSequence;
    private int studiedLevelProgress = 1;

    public ExerciseAbecedario(){
        sequence = "abclgmproetsunqivjdyñkháxíwfózúüé";
    }

    @Override
    public void initializeExercise() {

    }


    @Override
    public String getNextChar() {

        String nextChar;
        nextCharIndex = nextCharIndex + 1;

        if (nextCharIndex < this.getStudiedSequence().length()){
            nextChar = this.getStudiedSequence().substring(nextCharIndex,nextCharIndex+1);
        }
        else{
            nextChar = "\n"; //This is the "end of level" signal, by now...
        }

        return nextChar;
    }

    public static String getExerciseTitle() {
        return "Abecedario";
    }

    public static String getExerciseDescription(){
        return "Breve descripcion del modulo de ABECEDARIO";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {

        if (level > MAX_LEVEL){
            level = MAX_LEVEL;
        }

        this.level = level;

        studiedSequence = sequence.substring(0,SEQ_START + level);

    }

    public String getStudiedSequence() {
        return studiedSequence;
    }

    public void setStudiedSequence(String studiedSequence) {
        this.studiedSequence = studiedSequence;
    }

    public int getStudiedLevelProgress() {
        return studiedLevelProgress;
    }

    public void setStudiedLevelProgress(int studiedLevelProgress) {

        this.studiedLevelProgress = studiedLevelProgress;
    }

    public void loadProgress(int level, int studiedLevelProgress){
        this.setLevel(level);
        this.setStudiedLevelProgress(studiedLevelProgress);
    }


}
