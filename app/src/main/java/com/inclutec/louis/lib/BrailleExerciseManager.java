package com.inclutec.louis.lib;

import android.content.Context;

import com.inclutec.louis.LouisApplication;
import com.inclutec.louis.db.models.UserLevel;
import com.inclutec.louis.exercises.ExerciseAbecedario;
import com.inclutec.louis.exercises.ExerciseAprestamiento;
import com.inclutec.louis.exercises.ExerciseLibre;
import com.inclutec.louis.exercises.ExerciseType;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by martin on 9/10/15.
 */
public class BrailleExerciseManager {
    Context context;
    ExerciseType exerciseType;
    BrailleExercise brailleExercise;
    LouisApplication louisApp;

    public BrailleExerciseManager(LouisApplication app){
        louisApp = app;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;

        this.brailleExercise = getBrailleExercise(exerciseType);

    }

    public BrailleExercise getBrailleExercise() {
        return brailleExercise;
    }

    public static BrailleExercise getBrailleExercise(ExerciseType exerciseType){

        BrailleExercise brailleExercise = null;

        switch(exerciseType){
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

        return brailleExercise;
    }

    public int getCurrentLevel(BrailleExercise selectedExercise, int userId) {
        int level = 0;
        try {
            Dao userLevelDao = louisApp.getHelper().getUserLevelDao();
            QueryBuilder<UserLevel, String> userLevelQb = userLevelDao.queryBuilder();
            Where where = userLevelQb.where();

            where.and(
                    where.eq(UserLevel.USER_ID, userId),
                    where.eq(UserLevel.EXERCISE, selectedExercise.getExerciseType())
            );

            userLevelQb.setWhere(where);
            userLevelQb.orderBy(UserLevel.LEVEL, false);

            List<UserLevel> list = userLevelDao.query(userLevelQb.prepare());

            if (!list.isEmpty()) {
                UserLevel lastLevel = (UserLevel) list.get(0);
                level = lastLevel.getLevel();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return level;
    }
}
