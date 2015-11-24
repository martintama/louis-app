package com.inclutec.louis.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inclutec.louis.Globals;
import com.inclutec.louis.R;
import com.inclutec.louis.db.models.User;

/**
 * Created by martin on 9/22/15.
 */
public class UserListDataAdapter extends CursorAdapter {

    Context theContext;

    public UserListDataAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View inflatedView;

        // Create a new view into the list.
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflatedView = inflater.inflate(R.layout.item_userlist, parent, false);

        return inflatedView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        User item = null;

        theContext = context;

        SharedPreferences prefs = context.getSharedPreferences(Globals.PREFS_NAME, Context.MODE_PRIVATE);
        Integer currentUserId = prefs.getInt(Globals.PREFS_KEY_USER_ID, 0);


        TextView tvUsername = (TextView) view.findViewById(R.id.txtUsername);

        Bundle userBundle = new Bundle();
        userBundle.putInt("user_id", cursor.getInt(0));
        userBundle.putString("name", cursor.getString(1));
        view.setTag(userBundle);

        if (currentUserId == cursor.getInt(0)){
            tvUsername.setText(cursor.getString(1) + " (Actual)");
        }
        else {
            tvUsername.setText(cursor.getString(1));
        }
    }
}
