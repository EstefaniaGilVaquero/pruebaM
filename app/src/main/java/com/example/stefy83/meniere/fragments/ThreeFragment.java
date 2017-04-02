package com.example.stefy83.meniere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.activity.HealthyHabits;
import com.example.stefy83.meniere.adapter.FaqAdapter;


public class ThreeFragment extends Fragment {

    private Activity activity;
    private View rootView;
    private ImageView cvHealthyHabits;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_three, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            setCardViews();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCardViews() {
        cvHealthyHabits = (ImageView) rootView.findViewById(R.id.healthyHabits);

        cvHealthyHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HealthyHabits.class));
            }
        });


    }



}
