package com.symbel.meniere.activity.MyMeniere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.symbel.meniere.R;

public class NewEventActivity extends AppCompatActivity {

    private View mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mScrollView = findViewById(R.id.newEventScrollView);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // call this method to correct offsets
                //mBubbleSeekBar.correctOffsetWhenContainerOnScrolling();
            }
        });
    }


}
