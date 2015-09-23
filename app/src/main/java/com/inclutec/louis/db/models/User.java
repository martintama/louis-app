package com.inclutec.louis.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by martin on 9/14/15.
 */
@DatabaseTable(tableName = "user")
public class User {
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String DNI = "dni";
    public static final String DOB = "dob";
    public static final String ACTIVE = "active";

    @DatabaseField(generatedId = true, columnName = ID)
    private int userId;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = GENDER)
    private char gender;

    @DatabaseField(columnName = DNI)
    private String dni;

    @DatabaseField(columnName = DOB)
    private Date dayOfBirth;

    @DatabaseField(columnName = ACTIVE)
    private Boolean active;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
