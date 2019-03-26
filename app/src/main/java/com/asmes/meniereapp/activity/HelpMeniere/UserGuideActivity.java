package com.asmes.meniereapp.activity.HelpMeniere;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;
import com.asmes.meniereapp.adapter.FaqAdapter;

public class UserGuideActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String[] arrayTituloUserGuide;
    private String[] arrayDescripcionUserGuide;
    private FaqAdapter adapter;
    private static String TAG;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_healthy_habits);

        TAG = getString(R.string.userGuide);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);


        arrayTituloUserGuide = getResources().getStringArray(R.array.arrayTituloUserGuide);
        arrayDescripcionUserGuide = getResources().getStringArray(R.array.arrayDescripcioUserGuide);


        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.HealthyHabits_RecyclerView);
        // 2. set layoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 3. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 4. create and set adapter
        adapter = new FaqAdapter(arrayTituloUserGuide, arrayDescripcionUserGuide, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);


    }
}
