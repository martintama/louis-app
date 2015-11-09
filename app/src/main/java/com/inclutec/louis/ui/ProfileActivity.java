package com.inclutec.louis.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.inclutec.louis.db.models.User;
import com.inclutec.louis.ui.fragments.ProfileMainFragment;
import com.inclutec.louis.ui.fragments.ProfileNewFragment;
import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.SQLException;

public class ProfileActivity extends LouisActivity {

    private String CLASSNAME = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        super.loadToolbar();

        ProfileMainFragment firstFragment = new ProfileMainFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getFragmentManager().beginTransaction()
                .add(R.id.profile_fragment_container, firstFragment).commit();


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_user_new: {
                ProfileNewFragment newFragment = new ProfileNewFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Add the fragment to the 'fragment_container' FrameLayout
                transaction.replace(R.id.profile_fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;
            }
            case R.id.action_user_add: {
                saveNewUser();
                ProfileMainFragment newFragment = new ProfileMainFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Add the fragment to the 'fragment_container' FrameLayout
                transaction.replace(R.id.profile_fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNewUser() {

        //hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(
                (null == getCurrentFocus()) ?
                        null
                        :
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS
                );

        TextView nameView = (TextView)findViewById(R.id.txtName);

        RadioGroup rgroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        RadioButton genderView = (RadioButton)findViewById(rgroup.getCheckedRadioButtonId());

        TextView dniView = (TextView)findViewById(R.id.txtDni);
        TextView dobView = (TextView)findViewById(R.id.txtDob);

        User newUser = new User();

        if (nameView.getText().length() > 0) {
            newUser.setName(nameView.getText().toString());
        }
        if (genderView != null && genderView.getText().length() > 0) {
            newUser.setGender(genderView.getText().charAt(0));
        }
        if (dniView.getText().length() > 0) {
            newUser.setDni(dniView.getText().toString());
        }
        if (dobView.getText().length() > 0) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime dayOfBirth = DateTime.parse(dobView.getText().toString(), dtf);
            newUser.setDayOfBirth(new java.sql.Date(dayOfBirth.getMillis()));
        }

        newUser.setActive(true);

        try {
            Dao userDao = getHelper().getUserDao();
            userDao.create(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
