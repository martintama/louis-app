package com.inclutec.louis.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;

import com.inclutec.louis.LouisActivity;
import com.inclutec.louis.R;
import com.inclutec.louis.ui.fragments.SettingsFragment;

import java.util.List;


public class SettingsActivity extends LouisActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        super.loadToolbar();
         // Display the settings fragment as the content of the activity
        getFragmentManager().beginTransaction()
                .replace(R.id.preference_fragment, new SettingsFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
