package com.asmes.meniere.activity.HelpMeniere;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.BaseActivity;

public class FrameworksActivity extends BaseActivity {

    private Toolbar toolbar;
    private static String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frameworks);

        TAG = getString(R.string.frameworks);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

    }
}
