package com.inclutec.louis.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.widget.DatePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

/**
 * Created by martin on 11/10/2015.
 */
public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener listener;
    private DateTime defaultDate;

    public DatePickerFragment(){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) - 15;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        this.defaultDate = new DateTime(year, month - 1, day, 0, 0);
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = defaultDate.getYear();
        int month = defaultDate.getMonthOfYear();
        int day = defaultDate.getDayOfMonth();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    }

    public void setDefaultDate(String defaultDate) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.defaultDate = DateTime.parse(defaultDate, dtf);
    }

    public DateTime getDefaultDate() {
        return defaultDate;
    }
}
