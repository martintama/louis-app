package com.inclutec.louis.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inclutec.louis.Globals;
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
        try {
            SQLiteHelper helper = OpenHelperManager.getHelper(context, SQLiteHelper.class);
            Dao categoryDao = helper.getCategoryDao();

            Dao rateDao = helper.getRateDao();

            Rate aRate;

            aRate = new Rate("funny");
            rateDao.create(aRate);

            aRate = new Rate("smart");
            rateDao.create(aRate);

            aRate = new Rate("rude");
            rateDao.create(aRate);


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

    public void resetDb(SQLiteDatabase database, ConnectionSource connectionSource){

        try {
            TableUtils.dropTable(connectionSource, User.class, false);
            TableUtils.dropTable(connectionSource, Plate.class, false);
            TableUtils.dropTable(connectionSource, PlateFormat.class, false);
            TableUtils.dropTable(connectionSource, Message.class, false);
            TableUtils.dropTable(connectionSource, Content.class, false);
            TableUtils.dropTable(connectionSource, Rate.class, false);
            TableUtils.dropTable(connectionSource, Feedback.class, false);
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

    public Dao<Plate, Integer> getPlateDao() throws SQLException {
        if (plateDao == null) {
            plateDao = getDao(Plate.class);
        }
        return plateDao;
    }

    public Dao<PlateFormat, Integer> getPlateFormatDao() throws SQLException {
        if (plateFormatDao == null) {
            plateFormatDao = getDao(PlateFormat.class);
        }
        return plateFormatDao;
    }

    public Dao<Message, Integer> getMessageDao() throws SQLException {
        if (messageDao == null) {
            messageDao = getDao(Message.class);
        }
        return messageDao;
    }

    public Dao<Content, Integer> getContentDao() throws SQLException {
        if (contentDao == null) {
            contentDao = getDao(Content.class);
        }

        return contentDao;
    }

    public Dao<Device, Integer> getDeviceDao() throws SQLException {
        if (deviceDao == null) {
            deviceDao = getDao(Device.class);
        }

        return deviceDao;
    }

    public Dao<Category, Integer> getCategoryDao() throws SQLException{
        if (categoryDao == null){
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    public Dao<Rate, Integer> getRateDao() throws SQLException{
        if (rateDao == null){
            rateDao = getDao(Rate.class);
        }
        return rateDao;
    }

    public Dao<Feedback, Integer> getFeedbackDao() throws SQLException{
        if (feedbackDao == null){
            feedbackDao = getDao(Feedback.class);
        }
        return feedbackDao;
    }

    @Override
    public void close() {
        super.close();
        userDao = null;
        plateDao = null;
        plateFormatDao = null;
        messageDao = null;
        deviceDao = null;
        categoryDao = null;
        rateDao = null;
        feedbackDao = null;
    }

}
