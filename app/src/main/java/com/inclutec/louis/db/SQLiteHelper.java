package com.inclutec.louis.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.inclutec.louis.Globals;
import com.inclutec.louis.R;
import com.inclutec.louis.db.models.CharacterStatistic;
import com.inclutec.louis.db.models.Statistic;
import com.inclutec.louis.db.models.User;
import com.inclutec.louis.db.models.UserLevel;
import com.inclutec.louis.interfaces.BrailleExercise;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 1/18/15.
 */
public class SQLiteHelper extends OrmLiteSqliteOpenHelper {

    private Dao<User, Integer> userDao;
    private Dao<Statistic, Integer> statisticDao;
    private Dao<UserLevel, Integer> userLevelDao;
    private Dao<CharacterStatistic, Integer> characterStatisticDao;

    Context context;

    public SQLiteHelper(Context context) {
        super(context, Globals.DATABASE_NAME, null, Globals.SQLITE_DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Statistic.class);
            TableUtils.createTable(connectionSource, UserLevel.class);
            TableUtils.createTable(connectionSource, CharacterStatistic.class);
            Log.i(Globals.TAG, "Initialized tables");

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.w(Globals.TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(database, connectionSource);
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Statistic, Integer> getStatisticDao() throws SQLException {
        if (statisticDao == null) {
            statisticDao = getDao(Statistic.class);
        }
        return statisticDao;
    }

    public Dao<UserLevel, Integer> getUserLevelDao() throws SQLException{
        if (userLevelDao == null){
            userLevelDao = getDao(UserLevel.class);
        }

        return userLevelDao;
    }

    public Dao<CharacterStatistic, Integer> getCharacterStatisticDao() throws SQLException{
        if (characterStatisticDao == null){
            characterStatisticDao = getDao(CharacterStatistic.class);
        }

        return characterStatisticDao;
    }


    public void resetDb(SQLiteDatabase database, ConnectionSource connectionSource){

        try {
            TableUtils.dropTable(connectionSource, User.class, false);
            TableUtils.dropTable(connectionSource, Statistic.class, false);
            TableUtils.dropTable(connectionSource, CharacterStatistic.class, false);
        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
    }

    public UserLevel getUserLevel(int userId, BrailleExercise selectedExercise){
        try {

            //save the progress
            Dao userDao = getUserDao();
            Dao userLevelDao = getUserLevelDao();

            //Save the progress of the current level
            User currentUser = (User)userDao.queryForId(userId);

            QueryBuilder<UserLevel, String> queryBuilder = userLevelDao.queryBuilder();

            Where where = queryBuilder.where();
            where.and(
                    where.eq(UserLevel.USER_ID, userId),
                    where.eq(UserLevel.EXERCISE, selectedExercise.getExerciseType())
            );

            List<UserLevel> list = userLevelDao.query(queryBuilder.prepare());

            UserLevel userLevel;
            if (list.isEmpty()){
                userLevel = new UserLevel();
                userLevel.setUser(currentUser);
                userLevel.setExercise(selectedExercise.getExerciseType().toString());
                userLevel.setLevel(1);
                userLevelDao.create(userLevel);
            }
            else{
                userLevel = list.get(0);
            }

            return userLevel;

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
            return null;
        }
    }

    public void setLevelPassed(int userId, BrailleExercise exercise) {
        try {
            Dao userLevelDao = getUserLevelDao();

            UserLevel userLevel = this.getUserLevel(userId, exercise);
            userLevel.setLevel(userLevel.getLevel() + 1);
            userLevelDao.update(userLevel);

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
    }

    public int getCurrentExerciseLevel(int userId, BrailleExercise exercise){
        UserLevel userLevel = this.getUserLevel(userId, exercise);
        return userLevel.getLevel();
    }

    //save exercise statistics
    public void saveExerciseProgress(int userId, BrailleExercise exercise, int level, int counterHit, int counterMiss, int seconds) {

        try {
            Dao userDao = getUserDao();

            //Save the progress of the current level
            User currentUser = (User)userDao.queryForId(userId);


            //and save statistics
            Dao statisticsDao = getStatisticDao();
            Statistic stat = new Statistic();
            stat.setUser(currentUser);
            stat.setActive(true);
            stat.setDate(new java.sql.Date(new DateTime().getMillis()));
            stat.setHits(counterHit);
            stat.setMisses(counterMiss);
            stat.setLevel(level);
            stat.setTimeElapsed(seconds);
            stat.setExcersise(exercise.getExerciseType().toString());

            statisticsDao.create(stat);

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }

    }

    public List<String[]> loadStatistics(int userId) {
        try {

            String query = "Select exercise, sum(time_elapsed) time, sum(qty_ok), sum(qty_miss) " +
                    "from statistics where active = 1 and user_id = " + userId + " group by exercise order by exercise";

            Log.d(Globals.TAG, String.format("Query is %s", query));

            GenericRawResults<String[]> rawResults = getStatisticDao().queryRaw(query);

            Log.d(Globals.TAG, String.format("Results: %s", rawResults.toString()));

            List<String[]> results = rawResults.getResults();

            return results;

        } catch (SQLException e) {
            Log.e(Globals.TAG, String.format("Error loading statistics: %s", e.getMessage()), e);
            return null;
        }
    }
    //save character stats
    public void saveCharacterHit(int userId, String character) {

        try {
            CharacterStatistic charStat = getCharacterStatistic(userId, character);

            charStat.setHits(charStat.getHits() + 1);
            charStat.setActive(true);

            getCharacterStatisticDao().createOrUpdate(charStat);

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
    }

    //save character stats
    public void saveCharacterMiss(int userId, String character) {

        try {

            CharacterStatistic charStat = getCharacterStatistic(userId, character);

            charStat.setMisses(charStat.getMisses() + 1);
            charStat.setActive(true);

            getCharacterStatisticDao().createOrUpdate(charStat);

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }

    }

    private CharacterStatistic getCharacterStatistic(int userId, String character) throws SQLException {

        Dao userDao = getUserDao();

        //Save the progress of the current level
        User currentUser = (User)userDao.queryForId(userId);

        QueryBuilder<CharacterStatistic, Integer> queryBuilder = getCharacterStatisticDao().queryBuilder();

        Where where = queryBuilder.where();
        where.and(
                where.eq(CharacterStatistic.USER_ID, userId),
                where.eq(CharacterStatistic.CHAR, character),
                where.eq(CharacterStatistic.ACTIVE, true)
        );

        List<CharacterStatistic> list = getCharacterStatisticDao().query(queryBuilder.prepare());

        CharacterStatistic charStat;
        if (list.isEmpty()){
            charStat = new CharacterStatistic();
            charStat.setUser(currentUser);
            charStat.setCharacter(character);
            charStat.setHits(0);
            charStat.setMisses(0);
            getCharacterStatisticDao().create(charStat);
        }
        else{
            charStat = list.get(0);
        }

        return charStat;
    }

    //get character stats
    public List<String[]> getMostMissedCharacters(int userId){

        try {

            GenericRawResults<String[]> rawResults =
                    getCharacterStatisticDao().queryRaw(
                            "select character, qty_miss , qty_ok + qty_miss as total, " +
                                    " cast(cast(cast(qty_miss as float) / cast(qty_ok + qty_miss as float) as float)*100 as int) " +
                                    " from character_statistics where user_id = " + userId +
                                    " and active = 1" +
                                    " order by qty_miss / (qty_ok + qty_miss) desc, qty_miss desc, character " +
                                    " limit 10");

            List<String[]> results = rawResults.getResults();

            return results;

        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
            return null;
        }
    }


    @Override
    public void close() {
        super.close();
        userDao = null;
        statisticDao = null;
    }

}
