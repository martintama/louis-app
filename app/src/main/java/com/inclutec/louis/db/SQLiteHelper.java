package com.inclutec.louis.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inclutec.louis.Globals;
import com.inclutec.louis.db.models.Statistic;
import com.inclutec.louis.db.models.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by martin on 1/18/15.
 */
public class SQLiteHelper extends OrmLiteSqliteOpenHelper {

    private Dao<User, Integer> userDao;
    private Dao<Statistic, Integer> statisticDao;

    Context context;

    public SQLiteHelper(Context context) {
        super(context, Globals.DATABASE_NAME, null, Globals.SQLITE_DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);

            this.initializeTables();
        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
    }

    private void initializeTables() {

    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.w(Globals.TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(database, connectionSource);
    }

    public void resetDb(SQLiteDatabase database, ConnectionSource connectionSource){

        try {
            TableUtils.dropTable(connectionSource, User.class, false);
            TableUtils.dropTable(connectionSource, Statistic.class, false);
        } catch (SQLException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
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


    @Override
    public void close() {
        super.close();
        userDao = null;
        statisticDao = null;
    }

}
