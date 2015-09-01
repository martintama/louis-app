package com.inclutec.louis.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inclutec.louis.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EjercitacionFragment extends Fragment {

    public EjercitacionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ejercitacion, container, false);
    }
}
