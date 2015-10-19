package com.inclutec.louis.exercises;

import com.inclutec.louis.interfaces.BrailleExercise;

/**
 * Created by martin on 9/12/15.
 */
public class ExerciseAprestamiento implements BrailleExercise {

    int MAX_LEVEL_PROGRESS = 12;
    private int studiedLevelProgress;

    @Override
    public void initializeExercise() {

    }

    @Override
    public ExerciseType getExerciseType() {
        return ExerciseType.APRESTAMIENTO;
    }

    @Override
    public String getNextChar() {
        String nextChar = "";
        this.studiedLevelProgress = this.studiedLevelProgress + 1;

        if (this.studiedLevelProgress <= this.MAX_LEVEL_PROGRESS){

            switch (this.studiedLevelProgress){
                case 1:
                    nextChar = "1"; //this is not the "one" char but the first dot
                    break;
                case 2:
                    nextChar = "2";
                    break;
                case 3:
                    nextChar = "3";
                    break;
                case 4:
                    nextChar = "4";
                    break;
                case 5:
                    nextChar = "5";
                    break;
                case 6:
                    nextChar = "6";
                    break;
                case 7:
                    nextChar = "12";
                    break;
                case 8:
                    nextChar = "34";
                    break;
                case 9:
                    nextChar = "56";
                    break;
                case 10:
                    nextChar = "123";
                    break;
                case 11:
                    nextChar = "456";
                    break;
                case 12:
                    nextChar = "123456";
                    break;
                default:
                    nextChar = "0";
                    this.studiedLevelProgress = 0;

            }
        }
        else{
            return "\n";
        }

        return nextChar;
    }

    @Override
    public String getExerciseTitle() {
        return "Aprestamiento";
    }

    @Override
    public String getExerciseDescription(){
        return "Breve descripcion del modulo de APRESTAMIENTO";
    }

    public int getStudiedLevelProgress() {
        return studiedLevelProgress;
    }

    //in the "aprestamiento" the level is always 1.
    @Override
    public void loadProgress(int level, int studiedLevelProgress){

        this.studiedLevelProgress = studiedLevelProgress -1; //So when the getNextChar is called, the correct one is returned
    }


}
