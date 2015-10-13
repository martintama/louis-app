package com.inclutec.louis.db.datasources;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.inclutec.louis.Globals;
import com.inclutec.louis.db.SQLiteHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by martin on 9/22/15.
 */
public class UserListDataSource {

    private String CLASSNAME = "UserListDataSource";
    private SQLiteHelper mDbHelper;
    private Context context;

    public UserListDataSource(Context mContext) {
        this.context = mContext;
        mDbHelper = this.getHelper();
    }

    public SQLiteHelper getHelper() {
        if (mDbHelper == null) {
            mDbHelper = OpenHelperManager.getHelper(context, SQLiteHelper.class);
        }
        return mDbHelper;
    }

    public Cursor getCursorForAll() throws SQLException {

        String query = "select _id, name from users " +
                "where active = 1";

        Globals.logDebug(CLASSNAME, String.format("SQLString: %s", query));
        Cursor cursor = mDbHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();


        return cursor;
    }
}
