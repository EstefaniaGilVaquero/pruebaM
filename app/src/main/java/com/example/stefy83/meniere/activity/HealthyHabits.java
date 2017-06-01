package com.example.stefy83.meniere.activity;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.adapter.FaqAdapter;

public class HealthyHabits extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String[] arrayTituloHabitos;
    private String[] arrayDescripcionHabitos;
    private FaqAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_healthy_habits);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SET BACK BUTTON
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        arrayTituloHabitos = getResources().getStringArray(R.array.arrayTituloHabitos);
        arrayDescripcionHabitos = getResources().getStringArray(R.array.arrayDescripcionHabitos);



        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.HealthyHabits_RecyclerView);
        // 2. set layoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 3. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 4. create and set adapter
        adapter = new FaqAdapter(arrayTituloHabitos, arrayDescripcionHabitos, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }
}