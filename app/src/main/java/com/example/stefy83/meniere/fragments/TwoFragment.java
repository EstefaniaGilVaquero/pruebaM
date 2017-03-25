package com.example.stefy83.meniere.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.adapter.FaqAdapter;

import java.util.ArrayList;


public class TwoFragment extends Fragment {

    private Activity activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View rootView;
//    private String[] arrayQuestions = activity.getResources().getStringArray(R.array.arrayQuestions);
//    private String[] arrayAnswers = activity.getResources().getStringArray(R.array.arrayAnswers);
//    private String[] arrayQuestions = {"pregunta1"};
//    private String[] arrayAnswers = {"respuesta1"};
    private String[] arrayQuestions;
    private String[] arrayAnswers;
    private FaqAdapter adapter;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = getActivity();
        arrayQuestions = activity.getResources().getStringArray(R.array.arrayQuestions);
        arrayAnswers = activity.getResources().getStringArray(R.array.arrayAnswers);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_two, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            // 1. get a reference to recyclerView
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.FAQ_RecyclerView);
            // 2. set layoutManger
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            // 3. set item animator to DefaultAnimator
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            // 4. create and set adapter
            adapter = new FaqAdapter(arrayQuestions, arrayAnswers, activity);
            adapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
