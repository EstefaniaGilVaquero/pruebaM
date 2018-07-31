package com.asmes.meniere.activity.UtilitiesMeniere;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.BaseActivity;
import com.asmes.meniere.activity.Login.LoginFingerTipActivity;
import com.asmes.meniere.activity.TabsActivity;
import com.asmes.meniere.adapter.FaqAdapter;

public class HealthyHabits extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String[] arrayTituloHabitos;
    private String[] arrayDescripcionHabitos;
    private FaqAdapter adapter;
    private Toolbar toolbar;
    boolean activitySwitchFlag = false;
    private static String TAG;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_healthy_habits);

        TAG = getString(R.string.healthyHabits);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);


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
