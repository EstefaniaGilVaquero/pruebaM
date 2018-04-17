package com.asmes.meniere.activity.MyMeniere;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniere.R;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.utils.Utils;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity {

    private View mScrollView;
    private BubbleSeekBar mDurationBubble, mVertigoBubble, mLimitationBubble, mStressBubble, mInstabilityIntenBubble, mDizzinessDisBubble, mInstabilityDisBubble, mVisualBlurDisBubble, mHeadPresureDisBubble;
    private SwitchCompat mHearingLossSwitch, mTinnitusSwitch, mEarFullnessSwitch, mHeadacheSwitch, mPhotophobiaSwitch, mPhonophobiaSwitch, mVisualSymSwitch, mTumarkinSwitch, mMenstruationSwitch, mNauseaSwitch, mVomitingSwitch, mInstabilitySwitch;
    private TextView mEpisodesTxt, mDurationTxt, mVertigoTxt, mLimitationTxt, mStressTxt, mInstabilityIntenTxt, mDizzinessDisTxt, mInstabilityDisTxt, mVisualBlurDisTxt, mHeadPresureDisTxt,
                        mHeadProp_1a_CardView, mHeadProp_1b_CardView, mHeadProp_1c_CardView, mHeadProp_1d_CardView, mHeadProp_2a_CardView, mHeadProp_2b_CardView, mHeadProp_2c_CardView, mHeadProp_3a_CardView, mHeadProp_3b_CardView, mHeadProp_3c_CardView,
                        mWeather_1a_CardView, mWeather_1b_CardView, mWeather_1c_CardView, mWeather_1d_CardView, mSleep_1a_CardView, mSleep_1b_CardView, mSleep_1c_CardView, mSleep_1d_CardView, mPhysical_1a_CardView, mPhysical_1b_CardView, mPhysical_1c_CardView,
                        mPhysical_1d_CardView, mHabit_1a_CardView, mHabit_1b_CardView, mHabit_1c_CardView, mHabit_1d_CardView, mHabit_1e_CardView, mHabit_1f_CardView, mNotes;

    private LinearLayout mHeadProp_1a_LayOut, mHeadProp_1b_LayOut, mHeadProp_1c_LayOut, mHeadProp_1d_LayOut, mHeadProp_2a_LayOut, mHeadProp_2b_LayOut, mHeadProp_2c_LayOut, mHeadProp_3a_LayOut, mHeadProp_3b_LayOut, mHeadProp_3c_LayOut,
            mWeather_1a_LayOut, mWeather_1b_LayOut, mWeather_1c_LayOut, mWeather_1d_LayOut, mSleep_1a_LayOut, mSleep_1b_LayOut, mSleep_1c_LayOut, mSleep_1d_LayOut, mPhysical_1a_LayOut, mPhysical_1b_LayOut, mPhysical_1c_LayOut,
            mPhysical_1d_LayOut, mHabit_1a_LayOut, mHabit_1b_LayOut, mHabit_1c_LayOut, mHabit_1d_LayOut, mHabit_1e_LayOut, mHabit_1f_LayOut, mHeadache_LayOut;

    private String mMigraineType1, mMigraineType2, mMigraineType3, mWeather, mSleep, mPhysical, mHabit, mHearingLoss;
    private ImageButton mAddEpisodeBtn, mInfoVisualBtn;
    private CardView mInstavilityIntenCardView;
    private int mEpisode = 1, mHearingLossIndex;

    boolean activitySwitchFlag = false;
    private Toolbar toolbar;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<EventModel> arrayEventEntries = new ArrayList<>();
    private Toast toast;
    private SimpleDateFormat sdf;
    private EventModel event;
    private String selectedDate;

    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu, menu);
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
            case android.R.id.home:
                activitySwitchFlag = true;
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sdf = new SimpleDateFormat("dd/MM/yyyy");




        mAddEpisodeBtn = (ImageButton) findViewById(R.id.addEpisodeBtn);
        mInfoVisualBtn = (ImageButton) findViewById(R.id.infoVisualBtn);

        mEpisodesTxt = (TextView) findViewById(R.id.episodesTxt);
        mEpisodesTxt.setText(getString(R.string.episodes) + " " + mEpisode);
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

        //Layouts
        mHeadProp_1a_LayOut = (LinearLayout) findViewById(R.id.headProp_1a_layout);
        mHeadProp_1b_LayOut = (LinearLayout) findViewById(R.id.headProp_1b_layout);
        mHeadProp_1c_LayOut = (LinearLayout) findViewById(R.id.headProp_1c_layout);
        mHeadProp_1d_LayOut = (LinearLayout) findViewById(R.id.headProp_1d_layout);
        mHeadProp_2a_LayOut = (LinearLayout) findViewById(R.id.headProp_2a_layout);
        mHeadProp_2b_LayOut = (LinearLayout) findViewById(R.id.headProp_2b_layout);
        mHeadProp_2c_LayOut = (LinearLayout) findViewById(R.id.headProp_2c_layout);
        mHeadProp_3a_LayOut = (LinearLayout) findViewById(R.id.headProp_3a_layout);
        mHeadProp_3b_LayOut = (LinearLayout) findViewById(R.id.headProp_3b_layout);
        mHeadProp_3c_LayOut = (LinearLayout) findViewById(R.id.headProp_3c_layout);
        mWeather_1a_LayOut = (LinearLayout) findViewById(R.id.Weather_1a_layout);
        mWeather_1b_LayOut = (LinearLayout) findViewById(R.id.Weather_1b_layout);
        mWeather_1c_LayOut = (LinearLayout) findViewById(R.id.Weather_1c_layout);
        mWeather_1d_LayOut = (LinearLayout) findViewById(R.id.Weather_1d_layout);
        mSleep_1a_LayOut = (LinearLayout) findViewById(R.id.Sleep_1a_layout);
        mSleep_1b_LayOut = (LinearLayout) findViewById(R.id.Sleep_1b_layout);
        mSleep_1c_LayOut = (LinearLayout) findViewById(R.id.Sleep_1c_layout);
        mSleep_1d_LayOut = (LinearLayout) findViewById(R.id.Sleep_1d_layout);
        mPhysical_1a_LayOut = (LinearLayout) findViewById(R.id.Physical_1a_layout);
        mPhysical_1b_LayOut = (LinearLayout) findViewById(R.id.Physical_1b_layout);
        mPhysical_1c_LayOut = (LinearLayout) findViewById(R.id.Physical_1c_layout);
        mPhysical_1d_LayOut = (LinearLayout) findViewById(R.id.Physical_1d_layout);
        mHabit_1a_LayOut = (LinearLayout) findViewById(R.id.Habit_1a_layout);
        mHabit_1b_LayOut = (LinearLayout) findViewById(R.id.Habit_1b_layout);
        mHabit_1c_LayOut = (LinearLayout) findViewById(R.id.Habit_1c_layout);
        mHabit_1d_LayOut = (LinearLayout) findViewById(R.id.Habit_1d_layout);
        mHabit_1e_LayOut = (LinearLayout) findViewById(R.id.Habit_1e_layout);
        mHabit_1f_LayOut = (LinearLayout) findViewById(R.id.Habit_1f_layout);
        mHeadache_LayOut = (LinearLayout) findViewById(R.id.headachePropsLayout);
        mInstavilityIntenCardView = (CardView) findViewById(R.id.instabilityIntensityCardView);

        mNotes = (TextView) findViewById(R.id.notesTxt);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAudio);

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SET BACK BUTTON
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        //Si no es nuevo pillo el evento que me llega
        Intent i = getIntent();
        selectedDate = i.getStringExtra("selectedDate");
        if (!i.getBooleanExtra("isNew",true)){
            event = (EventModel) i.getSerializableExtra("event");
            //Deshailitar y ocultar floating
            fab.setVisibility(View.GONE);
            //Cargar todos los datos en la vista
            setEventData();
        }else{


            fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_add_white_48px));
            fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Snackbar.make(view, "Se ha guardado el evento", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    saveEvent();
                    finish();
                    //disableEnableEvent(false ,(LinearLayout) findViewById(R.id.layoutToDisable));
                /*View fondoTransparente = findViewById(R.id.backgroundDisable);
                fondoTransparente.setLayoutParams(new RelativeLayout.LayoutParams(mScrollView.getWidth(), mScrollView.getMeasuredHeight()));
                fondoTransparente.setVisibility(View.VISIBLE);
                fondoTransparente.setEnabled(false);*/

                }
            });

            //Set listeners for bubbles
            setBubblesListeners();

            //Set listeners for CardViews
            setCardViewsListeners();

            //set info listeners
            setInfoListeners();
        }



    }

/*    public void disableEnableEvent(boolean enable, LinearLayout layoutToDisable){
        for (int i = 0; i < layoutToDisable.getChildCount(); i++) {
            View child = layoutToDisable.getChildAt(i);
            child.setEnabled(enable);
        }
    }*/

    /*private void disableEnableEvent(boolean enable, ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                for (int j = 0; j < group.getChildCount(); j++) {
                    group.getChildAt(j).setEnabled(enable);
                }
            }
        }
    }*/



    public void setInfoListeners(){
        mAddEpisodeBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {

                int index = mEpisode - 1;
                new MaterialDialog.Builder(v.getContext())
                        .title(R.string.addEpisodeTitle)
                        .content(R.string.addEpisodeContent)
                        .items(R.array.episodes)
                        .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                mEpisodesTxt.setText(getString(R.string.episodes) + " " + (which + 1));
                                mEpisode = which + 1;
                                return true;
                            }
                        })
                        .positiveText(R.string.positiveTxtOk)
                        .show();
            }
        } );

        mInfoVisualBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(R.string.infoVisualTitle)
                        .content(R.string.infoVisualContent)
                        .positiveText(R.string.positiveTxtOk)
                        .show();
            }
        } );

        mHearingLossSwitch.setOnClickListener(new SwitchCompat.OnClickListener(){
            @Override
            public void onClick(View v) {
        //TODO: Poner textos de verdad
                if (mHearingLossSwitch.isChecked()) {
                    new MaterialDialog.Builder(v.getContext())
                            .title(R.string.addEpisodeTitle)
                            .content(R.string.addEpisodeContent)
                            .items(R.array.hearingLoss)
                            .itemsCallbackSingleChoice(mHearingLossIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    mHearingLoss = text.toString();
                                    mHearingLossIndex = which;
                                    return true;
                                }
                            })
                            .positiveText(R.string.positiveTxtOk)
                            .show();
                }else{
                    mHearingLoss = "";
                    mHearingLossIndex = 0;
                }
            }
        } );
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
                mHeadProp_1a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType1 = mHeadProp_1a_CardView.getText().toString();
            }
        });

        mHeadProp_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType1 = mHeadProp_1b_CardView.getText().toString();
            }
        });

        mHeadProp_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType1 = mHeadProp_1c_CardView.getText().toString();
            }
        });

        mHeadProp_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType1 = mHeadProp_1d_CardView.getText().toString();
            }
        });

        mHeadProp_2a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_2c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType2 = mHeadProp_2a_CardView.getText().toString();
            }
        });

        mHeadProp_2b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_2b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType2 = mHeadProp_2b_CardView.getText().toString();
            }
        });

        mHeadProp_2c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_2a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_2b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_2c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType2 = mHeadProp_2c_CardView.getText().toString();
                }
        });

        mHeadProp_3a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_3c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType3 = mHeadProp_3a_CardView.getText().toString();
            }
        });

        mHeadProp_3b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_3b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mMigraineType3 = mHeadProp_3b_CardView.getText().toString();
            }
        });

        mHeadProp_3c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadProp_3a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_3b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHeadProp_3c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mMigraineType3 = mHeadProp_3c_CardView.getText().toString();
            }
        });

        mWeather_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather = mWeather_1a_CardView.getText().toString();
            }
        });

        mWeather_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather = mWeather_1b_CardView.getText().toString();
            }
        });

        mWeather_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather = mWeather_1c_CardView.getText().toString();
            }
        });

        mWeather_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mWeather_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mWeather = mWeather_1d_CardView.getText().toString();
            }
        });

        mSleep_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep = mSleep_1a_CardView.getText().toString();
            }
        });

        mSleep_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep = mSleep_1b_CardView.getText().toString();
            }
        });

        mSleep_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep = mSleep_1c_CardView.getText().toString();
            }
        });

        mSleep_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mSleep_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mSleep = mSleep_1d_CardView.getText().toString();
            }
        });

        mPhysical_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical = mPhysical_1a_CardView.getText().toString();
            }
        });

        mPhysical_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical = mPhysical_1b_CardView.getText().toString();
            }
        });

        mPhysical_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical = mPhysical_1c_CardView.getText().toString();
            }
        });

        mPhysical_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mPhysical_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mPhysical = mPhysical_1d_CardView.getText().toString();
            }
        });

        mHabit_1a_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1e_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1f_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit = mHabit_1a_CardView.getText().toString();
            }
        });

        mHabit_1b_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1e_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1f_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit = mHabit_1b_CardView.getText().toString();
            }
        });

        mHabit_1c_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1e_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1f_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit = mHabit_1c_CardView.getText().toString();
            }
        });

        mHabit_1d_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1e_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1f_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit = mHabit_1d_CardView.getText().toString();
            }
        });

        mHabit_1e_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1e_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit_1f_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit = mHabit_1e_CardView.getText().toString();
            }
        });

        mHabit_1f_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1b_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1c_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1d_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1e_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
                mHabit_1f_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHabit = mHabit_1f_CardView.getText().toString();
            }
        });

        mInstabilitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInstabilitySwitch.isChecked()){
                    mInstavilityIntenCardView.setVisibility(View.VISIBLE);
                }else{
                    mInstavilityIntenCardView.setVisibility(View.GONE);
                }
            }
        });

        mHeadacheSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeadacheSwitch.isChecked()){
                    mHeadache_LayOut.setVisibility(View.VISIBLE);
                }else{
                    mHeadache_LayOut.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setEventData(){

        if (event.instability != null && event.instability) mInstavilityIntenCardView.setVisibility(View.VISIBLE);
        if (event.headache != null && event.headache) mHeadache_LayOut.setVisibility(View.VISIBLE);

        mEpisodesTxt.setText(event.episodes!=null?getString(R.string.episodes) + event.episodes:getString(R.string.episodes) + "0");
        mDurationBubble.setProgress(event.duration!=null?Float.valueOf(event.duration):0);
        mVertigoBubble.setProgress(event.vertigoIntensity!=null?Float.valueOf(event.vertigoIntensity):0);
        mLimitationBubble.setProgress(event.limitation!=null?Float.valueOf(event.limitation):0);
        mStressBubble.setProgress(event.stress!=null?Float.valueOf(event.stress):0);
        mInstabilityIntenBubble.setProgress(event.instabilityIntensity!=null?Float.valueOf(event.instabilityIntensity):0);

        mHearingLossSwitch.setChecked(event.hearingLoss);
        mTinnitusSwitch.setChecked(event.tinnitus);
        mEarFullnessSwitch.setChecked(event.earFullness);
        mHeadacheSwitch.setChecked(event.headache);
        mPhotophobiaSwitch.setChecked(event.photophobia);
        mPhonophobiaSwitch.setChecked(event.phonophobia);
        mVisualSymSwitch.setChecked(event.visualSymptoms);
        mTumarkinSwitch.setChecked(event.tumarkin);
        mMenstruationSwitch.setChecked(event.menstruation);
        mNauseaSwitch.setChecked(event.nausea);
        mVomitingSwitch.setChecked(event.vomiting);
        mInstabilitySwitch.setChecked(event.instability);

        //TODO cardviews, pensar como hacerlo mejor

        mNotes.setText(event.myNotes);

        mDizzinessDisBubble.setProgress(Float.valueOf(event.getDizzinessDis()));
        mDizzinessDisBubble.setProgress(Float.valueOf(event.inestabilityDis));
        mVisualBlurDisBubble.setProgress(Float.valueOf(event.visualBlurDis));
        mHeadPresureDisBubble.setProgress(Float.valueOf(event.headPressure));

    }

    public void saveEvent(){

        String duration = String.valueOf(mDurationBubble.getProgress());
        String vertigoIntensity = String.valueOf(mVertigoBubble.getProgress());
        String limitation = String.valueOf(mLimitationBubble.getProgress());
        String stress = String.valueOf(mStressBubble.getProgress());
        String instabilityIntensity = mInstavilityIntenCardView.getVisibility()==View.GONE?"0":String.valueOf(mInstabilityIntenBubble.getProgress());
        String dizzinessDis = String.valueOf(mDizzinessDisBubble.getProgress());
        String inestabilityDis = String.valueOf(mDizzinessDisBubble.getProgress());
        String visualBlurDis = String.valueOf(mVisualBlurDisBubble.getProgress());
        String headPressure = String.valueOf(mHeadPresureDisBubble.getProgress());
        String notes = mNotes.getText().toString();

        ContentValues cv = new ContentValues();

        cv.put("date", selectedDate);
        cv.put("episodes", mEpisode);
        cv.put("duration", duration);
        cv.put("intensity", vertigoIntensity);
        cv.put("limitation", limitation);
        cv.put("stress", stress);
        cv.put("inestabilityInten", instabilityIntensity);
        cv.put("residual_type1", dizzinessDis);
        cv.put("residual_type2", inestabilityDis);
        cv.put("residual_type3", visualBlurDis);
        cv.put("residual_type4", headPressure);

        //Switchs
        cv.put("hearingLoss", mHearingLoss);
        cv.put("tinnitus", mTinnitusSwitch.isActivated());
        cv.put("plenitude", mEarFullnessSwitch.isActivated());
        cv.put("migraine", mHeadacheSwitch.isActivated());
        cv.put("photophobia", mPhotophobiaSwitch.isActivated());
        cv.put("phonophobia", mPhonophobiaSwitch.isActivated());
        cv.put("visual_symp", mVisualSymSwitch.isActivated());
        cv.put("tumarkin", mTumarkinSwitch.isActivated());
        cv.put("menstruation", mMenstruationSwitch.isActivated());
        cv.put("nausea", mNauseaSwitch.isActivated());
        cv.put("vomit", mVomitingSwitch.isActivated());
        cv.put("inestability", mInstabilitySwitch.isActivated());

        //Cards
        cv.put("migraine_type1", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):mMigraineType1);
        cv.put("migraine_type2", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):mMigraineType2);
        cv.put("migraine_type3", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):mMigraineType3);
        cv.put("triggers_climate", mWeather);
        cv.put("triggers_sleep", mSleep);
        cv.put("triggers_phisic", mPhysical);
        cv.put("triggers_excesses", mHabit);
        cv.put("triggers_notes", notes);

        db.insert("event", null, cv);
        Utils.showToast(this,"Evento guardado con exito");
    }


}
