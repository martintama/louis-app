package com.inclutec.louis.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.inclutec.louis.R;

import org.joda.time.DateTime;

public class ProfileNewFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    View inflatedView;

    public ProfileNewFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_profile_new, container, false);

        final EditText dobTextView = (EditText) inflatedView.findViewById(R.id.txtDob);

        final ProfileNewFragment thisFragment = this;
        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setListener(thisFragment);

                String defaultDate = "01/01/2000";
                if (!dobTextView.getText().toString().equals("")){
                    defaultDate = dobTextView.getText().toString();
                }

                newFragment.setDefaultDate(defaultDate);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        return inflatedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText dobText = (EditText) inflatedView.findViewById(R.id.txtDob);
        DateTime dobTextString = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
        dobText.setText(dobTextString.toString("dd/MM/yyyy"));
    }
}
