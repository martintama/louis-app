package com.inclutec.louis.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by martin on 9/14/15.
 */
@DatabaseTable(tableName = "character_statistics")
public class CharacterStatistic {
    public static final String ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String CHAR = "character";
    public static final String QTY_HIT = "qty_ok";
    public static final String QTY_MISS = "qty_miss";
    public static final String ACTIVE = "active";

    @DatabaseField(generatedId = true, columnName = ID)
    private int statId;

    @DatabaseField(canBeNull = false, foreignAutoRefresh=true, foreign = true, columnName = USER_ID)
    private User user;

    @DatabaseField(columnName = CHAR)
    private String character;

    @DatabaseField(columnName = QTY_HIT)
    private int hits;

    @DatabaseField(columnName = QTY_MISS)
    private int misses;

    @DatabaseField(columnName = ACTIVE)
    private Boolean active;

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
