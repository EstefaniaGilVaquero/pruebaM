package com.asmes.meniere.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmes.meniere.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDataFragment extends Fragment {


    public EditUserDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_data, container, false);
    }

}