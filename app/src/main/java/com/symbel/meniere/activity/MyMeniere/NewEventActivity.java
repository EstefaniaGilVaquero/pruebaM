package com.symbel.meniere.activity.MyMeniere;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.symbel.meniere.R;
import com.symbel.meniere.adapter.DatabaseHelper;
import com.symbel.meniere.models.EventModel;
import com.symbel.meniere.models.HearingDiaryModel;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity {

    private View mScrollView;
    private BubbleSeekBar mDurationBubble, mVertigoBubble, mLimitationBubble, mStressBubble, mInstabilityIntenBubble, mDizzinessDisBubble, mInstabilityDisBubble, mVisualBlurDisBubble, mHeadPresureDisBubble;
    private SwitchCompat mHearingLossSwitch, mTinnitusSwitch, mEarFullnessSwitch, mHeadacheSwitch, mPhotophobiaSwitch, mPhonophobiaSwitch, mVisualSymSwitch, mTumarkinSwitch, mMenstruationSwitch, mNauseaSwitch, mVomitingSwitch, mInstabilitySwitch;
    private TextView mDurationTxt, mVertigoTxt, mLimitationTxt, mStressTxt, mInstabilityIntenTxt, mDizzinessDisTxt, mInstabilityDisTxt, mVisualBlurDisTxt, mHeadPresureDisTxt,
                        mHeadProp_1a_CardView, mHeadProp_1b_CardView, mHeadProp_1c_CardView, mHeadProp_1d_CardView, mHeadProp_2a_CardView, mHeadProp_2b_CardView, mHeadProp_2c_CardView, mHeadProp_3a_CardView, mHeadProp_3b_CardView, mHeadProp_3c_CardView,
                        mWeather_1a_CardView, mWeather_1b_CardView, mWeather_1c_CardView, mWeather_1d_CardView, mSleep_1a_CardView, mSleep_1b_CardView, mSleep_1c_CardView, mSleep_1d_CardView, mPhysical_1a_CardView, mPhysical_1b_CardView, mPhysical_1c_CardView,
                        mPhysical_1d_CardView, mHabit_1a_CardView, mHabit_1b_CardView, mHabit_1c_CardView, mHabit_1d_CardView, mHabit_1e_CardView, mHabit_1f_CardView, mNotes;

    private String mMigraineType1, mMigraineType2, mMigraineType3, mWeather, mSleep, mPhysical, mHabit;

    private Toolbar toolbar;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<EventModel> arrayEventEntries = new ArrayList<>();

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Action View
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {


            case R.id.showSqlite:
                Intent dbmanager = new Intent(this,DatabaseHelper.class);
                startActivity(dbmanager);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mScrollView = findViewById(R.id.newEventScrollView);
        mDurationBubble = (BubbleSeekBar) findViewById(R.id.durationBubble);
        mVertigoBubble = (BubbleSeekBar) findViewById(R.id.vertigoIntensityBubble);
        mLimitationBubble = (BubbleSeekBar) findViewById(R.id.intensityLimitationBubble);
        mStressBubble = (BubbleSeekBar) findViewById(R.id.stressLevelBubble);
        mInstabilityIntenBubble = (BubbleSeekBar) findViewById(R.id.instabilityIntensityBubble);
        mDizzinessDisBubble = (BubbleSeekBar) findViewById(R.id.dizzinessBubble);
        mInstabilityDisBubble = (BubbleSeekBar) findViewById(R.id.inestabilityDisBubble);
        mVisualBlurDisBubble = (BubbleSeekBar) findViewById(R.id.visualBlurDisBubble);
        mHeadPresureDisBubble = (BubbleSeekBar) findViewById(R.id.headPressureDisBubble);

        mHearingLossSwitch = (SwitchCompat) findViewById(R.id.hearingLossSwitch);
        mTinnitusSwitch = (SwitchCompat) findViewById(R.id.tinnitusSwitch);
        mEarFullnessSwitch = (SwitchCompat) findViewById(R.id.earFullnessSwitch);
        mHeadacheSwitch = (SwitchCompat) findViewById(R.id.headacheSwitch);
        mPhotophobiaSwitch = (SwitchCompat) findViewById(R.id.photophobiaSwitch);
        mPhonophobiaSwitch = (SwitchCompat) findViewById(R.id.phonophobiawitch);
        mVisualSymSwitch = (SwitchCompat) findViewById(R.id.visualShymSwitch);
        mTumarkinSwitch = (SwitchCompat) findViewById(R.id.tumarkinSwitch);
        mMenstruationSwitch = (SwitchCompat) findViewById(R.id.menstruationSwitch);
        mNauseaSwitch = (SwitchCompat) findViewById(R.id.nauseaSwitch);
        mVomitingSwitch = (SwitchCompat) findViewById(R.id.vomitingSwitch);
        mInstabilitySwitch = (SwitchCompat) findViewById(R.id.instabilitySwitch);

        mDurationTxt = (TextView)findViewById(R.id.durationTxt);
        mVertigoTxt = (TextView)findViewById(R.id.vertigoIntensityTxt);
        mLimitationTxt = (TextView)findViewById(R.id.intensityLimitationTxt);
        mStressTxt = (TextView)findViewById(R.id.stressLevelTxt);
        mInstabilityIntenTxt = (TextView)findViewById(R.id.inestavilityIntensityTxt);
        mDizzinessDisTxt = (TextView)findViewById(R.id.dizzinessDisTxt);
        mInstabilityDisTxt = (TextView)findViewById(R.id.instabilityDisTxt);
        mVisualBlurDisTxt = (TextView)findViewById(R.id.visualBlurDisTxt);
        mHeadPresureDisTxt = (TextView)findViewById(R.id.headPresureDisTxt);

        mHeadProp_1a_CardView = (TextView) findViewById(R.id.headProp_1a);
        mHeadProp_1b_CardView = (TextView) findViewById(R.id.headProp_1b);
        mHeadProp_1c_CardView = (TextView) findViewById(R.id.headProp_1c);
        mHeadProp_1d_CardView = (TextView) findViewById(R.id.headProp_1d);
        mHeadProp_2a_CardView = (TextView) findViewById(R.id.headProp_2a);
        mHeadProp_2b_CardView = (TextView) findViewById(R.id.headProp_2b);
        mHeadProp_2c_CardView = (TextView) findViewById(R.id.headProp_2c);
        mHeadProp_3a_CardView = (TextView) findViewById(R.id.headProp_3a);
        mHeadProp_3b_CardView = (TextView) findViewById(R.id.headProp_3b);
        mHeadProp_3c_CardView = (TextView) findViewById(R.id.headProp_3c);
        mWeather_1a_CardView = (TextView) findViewById(R.id.Weather_1a);
        mWeather_1b_CardView = (TextView) findViewById(R.id.Weather_1b);
        mWeather_1c_CardView = (TextView) findViewById(R.id.Weather_1c);
        mWeather_1d_CardView = (TextView) findViewById(R.id.Weather_1d);
        mSleep_1a_CardView = (TextView) findViewById(R.id.Sleep_1a);
        mSleep_1b_CardView = (TextView) findViewById(R.id.Sleep_1b);
        mSleep_1c_CardView = (TextView) findViewById(R.id.Sleep_1c);
        mSleep_1d_CardView = (TextView) findViewById(R.id.Sleep_1d);
        mPhysical_1a_CardView = (TextView) findViewById(R.id.Physical_1a);
        mPhysical_1b_CardView = (TextView) findViewById(R.id.Physical_1b);
        mPhysical_1c_CardView = (TextView) findViewById(R.id.Physical_1c);
        mPhysical_1d_CardView = (TextView) findViewById(R.id.Physical_1d);
        mHabit_1a_CardView = (TextView) findViewById(R.id.Habit_1a);
        mHabit_1b_CardView = (TextView) findViewById(R.id.Habit_1b);
        mHabit_1c_CardView = (TextView) findViewById(R.id.Habit_1c);
        mHabit_1d_CardView = (TextView) findViewById(R.id.Habit_1d);
        mHabit_1e_CardView = (TextView) findViewById(R.id.Habit_1e);
        mHabit_1f_CardView = (TextView) findViewById(R.id.Habit_1f);

        mNotes = (TextView) findViewById(R.id.notesTxt);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // call this method to correct offsets
                mDurationBubble.correctOffsetWhenContainerOnScrolling();
                mVertigoBubble.correctOffsetWhenContainerOnScrolling();
                mLimitationBubble.correctOffsetWhenContainerOnScrolling();
                mStressBubble.correctOffsetWhenContainerOnScrolling();
                mInstabilityIntenBubble.correctOffsetWhenContainerOnScrolling();
                mDizzinessDisBubble.correctOffsetWhenContainerOnScrolling();
                mInstabilityDisBubble.correctOffsetWhenContainerOnScrolling();
                mVisualBlurDisBubble.correctOffsetWhenContainerOnScrolling();
                mHeadPresureDisBubble.correctOffsetWhenContainerOnScrolling();
            }
        });

        //Instancio bd
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //Recupero eventos prueba una fecha
        arrayEventEntries = getEventEntries("SELECT * FROM " + "event");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SET BACK BUTTON
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAudio);
        fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_add_white_48px));
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Se ha presionado FAB", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                saveEvent();

            }
        });


        //Set listeners for bubbles
        setBubblesListeners();

        //Set listeners for CardViews
        setCardViewsListeners();

    }

    public void setBubblesListeners(){

        //mDurationBubble, mVertigoBubble, mLimitationBubble, mStressBubble, mInstabilityIntenBubble, mDizzinessDisBubble, mInstabilityDisBubble, mVisualBlurDisBubble, mHeadPresureDisBubble;
        mDurationBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.duration) +" %.1f h", progressFloat);
                mDurationTxt.setText(s);
            }
        });
        mVertigoBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.vertigoIntensity) +" %d", progress);
                mVertigoTxt.setText(s);
            }
        });
        mLimitationBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.limitation) + " %d", progress);
                mLimitationTxt.setText(s);
            }
        });
        mStressBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.stress) + " %d", progress);
                mStressTxt.setText(s);
            }
        });
        mInstabilityIntenBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.instabilityIntensity) + " %d", progress);
                mInstabilityIntenTxt.setText(s);
            }
        });
        mDizzinessDisBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.dizzinessDis) + " %d", progress);
                mDizzinessDisTxt.setText(s);
            }
        });
        mInstabilityDisBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.inestabilityDis) + " %d", progress);
                mDizzinessDisTxt.setText(s);
            }
        });
        mVisualBlurDisBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.visualBlurDis) + " %d", progress);
                mVisualBlurDisTxt.setText(s);
            }
        });
        mHeadPresureDisBubble.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.getDefault(), getResources().getString(R.string.headPressure) + " %d", progress);
                mHeadPresureDisTxt.setText(s);
            }
        });
    }

    public void setCardViewsListeners(){
        mHeadProp_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType1 = mHeadProp_1a_CardView.getText().toString();
            }
        });

        mHeadProp_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType1 = mHeadProp_1b_CardView.getText().toString();
            }
        });

        mHeadProp_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType1 = mHeadProp_1c_CardView.getText().toString();
            }
        });

        mHeadProp_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType1 = mHeadProp_1d_CardView.getText().toString();
            }
        });

        mHeadProp_2a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_2c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType2 = mHeadProp_2a_CardView.getText().toString();
            }
        });

        mHeadProp_2b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_2b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType2 = mHeadProp_2b_CardView.getText().toString();
            }
        });

        mHeadProp_2c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_2b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_2c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType2 = mHeadProp_2c_CardView.getText().toString();
                }
        });

        mHeadProp_3a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_3c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType3 = mHeadProp_3a_CardView.getText().toString();
            }
        });

        mHeadProp_3b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_3b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mMigraineType3 = mHeadProp_3b_CardView.getText().toString();
            }
        });

        mHeadProp_3c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_3b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHeadProp_3c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType3 = mHeadProp_3c_CardView.getText().toString();
            }
        });

        mWeather_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather = mWeather_1a_CardView.getText().toString();
            }
        });

        mWeather_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather = mWeather_1b_CardView.getText().toString();
            }
        });

        mWeather_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather = mWeather_1c_CardView.getText().toString();
            }
        });

        mWeather_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mWeather_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather = mWeather_1d_CardView.getText().toString();
            }
        });

        mSleep_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep = mSleep_1a_CardView.getText().toString();
            }
        });

        mSleep_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep = mSleep_1b_CardView.getText().toString();
            }
        });

        mSleep_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep = mSleep_1c_CardView.getText().toString();
            }
        });

        mSleep_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mSleep_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep = mSleep_1d_CardView.getText().toString();
            }
        });

        mPhysical_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical = mPhysical_1a_CardView.getText().toString();
            }
        });

        mPhysical_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical = mPhysical_1b_CardView.getText().toString();
            }
        });

        mPhysical_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical = mPhysical_1c_CardView.getText().toString();
            }
        });

        mPhysical_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mPhysical_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical = mPhysical_1d_CardView.getText().toString();
            }
        });

        mHabit_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit = mHabit_1a_CardView.getText().toString();
            }
        });

        mHabit_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit = mHabit_1b_CardView.getText().toString();
            }
        });

        mHabit_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit = mHabit_1c_CardView.getText().toString();
            }
        });

        mHabit_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit = mHabit_1d_CardView.getText().toString();
            }
        });

        mHabit_1e_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit = mHabit_1e_CardView.getText().toString();
            }
        });

        mHabit_1f_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1b_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1c_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1d_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1e_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.black_overlay));
                mHabit_1f_CardView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit = mHabit_1f_CardView.getText().toString();
            }
        });
}

    public void saveEvent(){

        String duration = String.valueOf(mDurationBubble.getProgress());
        String vertigoIntensity = String.valueOf(mVertigoBubble.getProgress());
        String limitation = String.valueOf(mLimitationBubble.getProgress());
        String stress = String.valueOf(mStressBubble.getProgress());
        String instabilityIntensity = String.valueOf(mInstabilityIntenBubble.getProgress());
        String dizzinessDis = String.valueOf(mDizzinessDisBubble.getProgress());
        String inestabilityDis = String.valueOf(mDizzinessDisBubble.getProgress());
        String visualBlurDis = String.valueOf(mVisualBlurDisBubble.getProgress());
        String headPressure = String.valueOf(mHeadPresureDisBubble.getProgress());
        String notes = mNotes.getText().toString();


        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ContentValues cv = new ContentValues();

        cv.put("date", date);
        cv.put("duration", duration);
        cv.put("intensity", vertigoIntensity);
        cv.put("limitation", limitation);
        cv.put("stress", stress);
        cv.put("inestability", instabilityIntensity);
        cv.put("residual_type1", dizzinessDis);
        cv.put("residual_type2", inestabilityDis);
        cv.put("residual_type3", visualBlurDis);
        cv.put("residual_type4", headPressure);

        //Switchs
        cv.put("hearingLoss", mHearingLossSwitch.isActivated());
        cv.put("tinnitus", mTinnitusSwitch.isActivated());
        cv.put("plenitude", mEarFullnessSwitch.isActivated());
        cv.put("migraine", mHeadacheSwitch.isActivated());
        cv.put("photophobia", mPhotophobiaSwitch.isActivated());
        cv.put("phonophobia", mPhonophobiaSwitch.isActivated());
        cv.put("visual_symp", mVisualSymSwitch.isActivated());
        cv.put("tumerkin", mTumarkinSwitch.isActivated());
        cv.put("menstruation", mMenstruationSwitch.isActivated());
        cv.put("nausea", mNauseaSwitch.isActivated());
        cv.put("vomit", mVomitingSwitch.isActivated());
        cv.put("inestability", mInstabilitySwitch.isActivated());

        //Cards
        cv.put("migraine_type1", mMigraineType1);
        cv.put("migraine_type2", mMigraineType2);
        cv.put("migraine_type3", mMigraineType3);
        cv.put("triggers_climate", mWeather);
        cv.put("triggers_sleep", mSleep);
        cv.put("triggers_phisic", mPhysical);
        cv.put("triggers_excesses", mHabit);
        cv.put("triggers_notes", notes);

        db.insert("event", null, cv);
    }

    public ArrayList<EventModel> getEventEntries(String sql){

        ArrayList<EventModel> arrayEventEntriesAux = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        //Inicializo array
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            EventModel event = new EventModel();
            event.duration = cursor.getString(cursor.getColumnIndex("duration"));


            arrayEventEntriesAux.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayEventEntriesAux;
    }


}
