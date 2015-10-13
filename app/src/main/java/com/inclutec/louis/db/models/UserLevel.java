package com.inclutec.louis.db.models;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by martin on 12/10/2015.
 */
public class UserLevel {
    public static final String ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String EXERCISE = "exercise";
    public static final String LEVEL = "level";
    public static final String ACTIVE = "active";

    @DatabaseField(generatedId = true, columnName = ID)
    private int userLevelId;

    @DatabaseField(canBeNull = false, foreignAutoRefresh=true, foreign = true, columnName = USER_ID)
    private User user;

    @DatabaseField(columnName = EXERCISE)
    private String exercise;

    @DatabaseField(columnName = LEVEL)
    private Integer level;

    @DatabaseField(columnName = ACTIVE)
    private Boolean active;

    public int getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(int userLevelId) {
        this.userLevelId = userLevelId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
