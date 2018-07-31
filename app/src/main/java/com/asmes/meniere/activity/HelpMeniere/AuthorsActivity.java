package com.asmes.meniere.activity.HelpMeniere;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.BaseActivity;

public class AuthorsActivity extends BaseActivity {

    private static String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        TAG = getString(R.string.authors);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

    }
}
