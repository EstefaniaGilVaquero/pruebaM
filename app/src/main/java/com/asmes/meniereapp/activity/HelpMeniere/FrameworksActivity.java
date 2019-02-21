package com.asmes.meniereapp.activity.HelpMeniere;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;

public class FrameworksActivity extends BaseActivity {

    private static String TAG;
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frameworks);

        TAG = getString(R.string.frameworks);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

        text = findViewById(R.id.editText);
        Spanned sp = Html.fromHtml( getString(R.string.text_frameworks));
        text.setText(sp);

    }
}
