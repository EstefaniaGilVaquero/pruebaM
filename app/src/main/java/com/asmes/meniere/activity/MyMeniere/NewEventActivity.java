package com.asmes.meniere.activity.MyMeniere;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniere.R;
import com.asmes.meniere.activity.BaseActivity;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NewEventActivity extends BaseActivity {

    private ScrollView mScrollView;
    private BubbleSeekBar mDurationBubble, mVertigoBubble, mLimitationBubble, mStressBubble, mInstabilityIntenBubble, mDizzinessDisBubble, mInstabilityDisBubble, mVisualBlurDisBubble, mHeadPresureDisBubble;
    private SwitchCompat mHearingLossSwitch, mTinnitusSwitch, mEarFullnessSwitch, mHeadacheSwitch, mPhotophobiaSwitch, mPhonophobiaSwitch, mVisualSymSwitch, mTumarkinSwitch, mMenstruationSwitch, mNauseaSwitch, mVomitingSwitch, mInstabilitySwitch;
    private TextView mEpisodesTxt, mDurationTxt, mVertigoTxt, mLimitationTxt, mStressTxt, mInstabilityIntenTxt, mDizzinessDisTxt, mInstabilityDisTxt, mVisualBlurDisTxt, mHeadPresureDisTxt,
                        mHeadProp_1a_CardView, mHeadProp_1b_CardView, mHeadProp_1c_CardView, mHeadProp_1d_CardView, mHeadProp_2a_CardView, mHeadProp_2b_CardView, mHeadProp_2c_CardView, mHeadProp_3a_CardView, mHeadProp_3b_CardView, mHeadProp_3c_CardView,
                        mWeather_1a_CardView, mWeather_1b_CardView, mWeather_1c_CardView, mWeather_1d_CardView, mSleep_1a_CardView, mSleep_1b_CardView, mSleep_1c_CardView, mSleep_1d_CardView, mPhysical_1a_CardView, mPhysical_1b_CardView, mPhysical_1c_CardView,
                        mPhysical_1d_CardView, mHabit_1a_CardView, mHabit_1b_CardView, mHabit_1c_CardView, mHabit_1d_CardView, mHabit_1e_CardView, mHabit_1f_CardView, mNotes;

    private LinearLayout mHeadProp_1a_LayOut, mHeadProp_1b_LayOut, mHeadProp_1c_LayOut, mHeadProp_1d_LayOut, mHeadProp_2a_LayOut, mHeadProp_2b_LayOut, mHeadProp_2c_LayOut, mHeadProp_3a_LayOut, mHeadProp_3b_LayOut, mHeadProp_3c_LayOut,
            mWeather_1a_LayOut, mWeather_1b_LayOut, mWeather_1c_LayOut, mWeather_1d_LayOut, mSleep_1a_LayOut, mSleep_1b_LayOut, mSleep_1c_LayOut, mSleep_1d_LayOut, mPhysical_1a_LayOut, mPhysical_1b_LayOut, mPhysical_1c_LayOut,
            mPhysical_1d_LayOut, mHabit_1a_LayOut, mHabit_1b_LayOut, mHabit_1c_LayOut, mHabit_1d_LayOut, mHabit_1e_LayOut, mHabit_1f_LayOut, mHeadache_LayOut, mWeatherLayout, mSleepLayout , mPhisicLayout , mExccessLayout;

    private String mMigraineType1, mMigraineType2, mMigraineType3, mWeather, mSleep, mPhysical, mHabit, mHearingLoss;
    private ImageView mAddEpisodeBtn, mInfoVisualBtn;
    private CardView mInstavilityIntenCardView;
    private int mEpisode = 1, mHearingLossIndex;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<EventModel> arrayEventEntries = new ArrayList<>();
    private Toast toast;
    private SimpleDateFormat sdf;
    private EventModel event;
    private CalendarDay selectedDate;
    private Boolean disableTouch;




    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        mAddEpisodeBtn = findViewById(R.id.addEpisodeBtn);
        mInfoVisualBtn = findViewById(R.id.infoVisualBtn);

        mEpisodesTxt = findViewById(R.id.episodesTxt);
        mEpisodesTxt.setText(getString(R.string.episodes) + " " + mEpisode);
        mScrollView = findViewById(R.id.newEventScrollView);
        mDurationBubble = findViewById(R.id.durationBubble);
        mVertigoBubble = findViewById(R.id.vertigoIntensityBubble);
        mLimitationBubble = findViewById(R.id.intensityLimitationBubble);
        mStressBubble =  findViewById(R.id.stressLevelBubble);
        mInstabilityIntenBubble = findViewById(R.id.instabilityIntensityBubble);
        mDizzinessDisBubble = findViewById(R.id.dizzinessBubble);
        mInstabilityDisBubble = findViewById(R.id.inestabilityDisBubble);
        mVisualBlurDisBubble = findViewById(R.id.visualBlurDisBubble);
        mHeadPresureDisBubble = findViewById(R.id.headPressureDisBubble);

        mHearingLossSwitch = findViewById(R.id.hearingLossSwitch);
        mTinnitusSwitch = findViewById(R.id.tinnitusSwitch);
        mEarFullnessSwitch = findViewById(R.id.earFullnessSwitch);
        mHeadacheSwitch = findViewById(R.id.headacheSwitch);
        mPhotophobiaSwitch = findViewById(R.id.photophobiaSwitch);
        mPhonophobiaSwitch = findViewById(R.id.phonophobiawitch);
        mVisualSymSwitch = findViewById(R.id.visualShymSwitch);
        mTumarkinSwitch = findViewById(R.id.tumarkinSwitch);
        mMenstruationSwitch = findViewById(R.id.menstruationSwitch);
        mNauseaSwitch = findViewById(R.id.nauseaSwitch);
        mVomitingSwitch = findViewById(R.id.vomitingSwitch);
        mInstabilitySwitch = findViewById(R.id.instabilitySwitch);

        mDurationTxt = findViewById(R.id.durationTxt);
        mVertigoTxt = findViewById(R.id.vertigoIntensityTxt);
        mLimitationTxt = findViewById(R.id.intensityLimitationTxt);
        mStressTxt = findViewById(R.id.stressLevelTxt);
        mInstabilityIntenTxt = findViewById(R.id.inestavilityIntensityTxt);
        mDizzinessDisTxt = findViewById(R.id.dizzinessDisTxt);
        mInstabilityDisTxt = findViewById(R.id.instabilityDisTxt);
        mVisualBlurDisTxt = findViewById(R.id.visualBlurDisTxt);
        mHeadPresureDisTxt = findViewById(R.id.headPresureDisTxt);

        mHeadProp_1a_CardView = findViewById(R.id.headProp_1a);
        mHeadProp_1b_CardView = findViewById(R.id.headProp_1b);
        mHeadProp_1c_CardView = findViewById(R.id.headProp_1c);
        mHeadProp_1d_CardView = findViewById(R.id.headProp_1d);
        mHeadProp_2a_CardView = findViewById(R.id.headProp_2a);
        mHeadProp_2b_CardView = findViewById(R.id.headProp_2b);
        mHeadProp_2c_CardView = findViewById(R.id.headProp_2c);
        mHeadProp_3a_CardView = findViewById(R.id.headProp_3a);
        mHeadProp_3b_CardView = findViewById(R.id.headProp_3b);
        mHeadProp_3c_CardView = findViewById(R.id.headProp_3c);
        mWeather_1a_CardView = findViewById(R.id.Weather_1a);
        mWeather_1b_CardView = findViewById(R.id.Weather_1b);
        mWeather_1c_CardView = findViewById(R.id.Weather_1c);
        mWeather_1d_CardView = findViewById(R.id.Weather_1d);
        mSleep_1a_CardView = findViewById(R.id.Sleep_1a);
        mSleep_1b_CardView = findViewById(R.id.Sleep_1b);
        mSleep_1c_CardView = findViewById(R.id.Sleep_1c);
        mSleep_1d_CardView = findViewById(R.id.Sleep_1d);
        mPhysical_1a_CardView = findViewById(R.id.Physical_1a);
        mPhysical_1b_CardView = findViewById(R.id.Physical_1b);
        mPhysical_1c_CardView = findViewById(R.id.Physical_1c);
        mPhysical_1d_CardView = findViewById(R.id.Physical_1d);
        mHabit_1a_CardView = findViewById(R.id.Habit_1a);
        mHabit_1b_CardView = findViewById(R.id.Habit_1b);
        mHabit_1c_CardView = findViewById(R.id.Habit_1c);
        mHabit_1d_CardView = findViewById(R.id.Habit_1d);
        mHabit_1e_CardView = findViewById(R.id.Habit_1e);
        mHabit_1f_CardView = findViewById(R.id.Habit_1f);

        //Layouts
        mHeadProp_1a_LayOut = findViewById(R.id.headProp_1a_layout);
        mHeadProp_1b_LayOut =  findViewById(R.id.headProp_1b_layout);
        mHeadProp_1c_LayOut =  findViewById(R.id.headProp_1c_layout);
        mHeadProp_1d_LayOut =  findViewById(R.id.headProp_1d_layout);
        mHeadProp_2a_LayOut = findViewById(R.id.headProp_2a_layout);
        mHeadProp_2b_LayOut = findViewById(R.id.headProp_2b_layout);
        mHeadProp_2c_LayOut = findViewById(R.id.headProp_2c_layout);
        mHeadProp_3a_LayOut = findViewById(R.id.headProp_3a_layout);
        mHeadProp_3b_LayOut = findViewById(R.id.headProp_3b_layout);
        mHeadProp_3c_LayOut = findViewById(R.id.headProp_3c_layout);
        mWeather_1a_LayOut = findViewById(R.id.Weather_1a_layout);
        mWeather_1b_LayOut = findViewById(R.id.Weather_1b_layout);
        mWeather_1c_LayOut = findViewById(R.id.Weather_1c_layout);
        mWeather_1d_LayOut = findViewById(R.id.Weather_1d_layout);
        mSleep_1a_LayOut = findViewById(R.id.Sleep_1a_layout);
        mSleep_1b_LayOut = findViewById(R.id.Sleep_1b_layout);
        mSleep_1c_LayOut = findViewById(R.id.Sleep_1c_layout);
        mSleep_1d_LayOut = findViewById(R.id.Sleep_1d_layout);
        mPhysical_1a_LayOut = findViewById(R.id.Physical_1a_layout);
        mPhysical_1b_LayOut = findViewById(R.id.Physical_1b_layout);
        mPhysical_1c_LayOut = findViewById(R.id.Physical_1c_layout);
        mPhysical_1d_LayOut = findViewById(R.id.Physical_1d_layout);
        mHabit_1a_LayOut = findViewById(R.id.Habit_1a_layout);
        mHabit_1b_LayOut = findViewById(R.id.Habit_1b_layout);
        mHabit_1c_LayOut = findViewById(R.id.Habit_1c_layout);
        mHabit_1d_LayOut = findViewById(R.id.Habit_1d_layout);
        mHabit_1e_LayOut = findViewById(R.id.Habit_1e_layout);
        mHabit_1f_LayOut = findViewById(R.id.Habit_1f_layout);
        mHeadache_LayOut = findViewById(R.id.headachePropsLayout);
        mInstavilityIntenCardView = findViewById(R.id.instabilityIntensityCardView);

        mWeatherLayout = findViewById(R.id.weatherLayout);
        mSleepLayout = findViewById(R.id.sleepLayout);
        mPhisicLayout = findViewById(R.id.phisicLayout);
        mExccessLayout = findViewById(R.id.exccessLayout);

        mNotes = findViewById(R.id.notesTxt);

        FloatingActionButton fab = findViewById(R.id.fabAudio);

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



        //Si no es nuevo pillo el evento que me llega
        Intent i = getIntent();
        selectedDate = i.getParcelableExtra("selectedDate");
        if (!i.getBooleanExtra("isNew",true)){
            disableTouch = true;
            event = (EventModel) i.getSerializableExtra("event");
            //Deshailitar y ocultar floating
            fab.setVisibility(View.GONE);
            //Cargar todos los datos en la vista
            setEventData();
        }else{
            disableTouch = false;
            fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_add_white_48px));
            fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(validateEvent()){
                        saveEvent();
                        finish();
                    }
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

    public Boolean validateEvent(){
        Boolean result = true;
        //Duration must be >20
        //vertigoIntensity must be >0 or tinnutus or hearingloss o earfulness or headache or photophobia or phonophobia or aura or tumarkin
        //TODO cambiar valor de vertigobubble debe ser <20??? pq???
        if (mVertigoBubble.getProgress()<0 || (mVertigoBubble.getProgress()<1 && !mTinnitusSwitch.isChecked() && !mHearingLossSwitch.isChecked() && !mEarFullnessSwitch.isChecked()
        && !mHeadacheSwitch.isChecked() && !mPhonophobiaSwitch.isChecked() && !mPhotophobiaSwitch.isChecked() && !mVisualSymSwitch.isChecked() && !mTumarkinSwitch.isChecked())){
            result = false;
            new MaterialDialog.Builder(this)
                    .content(R.string.mandatoryFieldsContent)
                    .positiveText(R.string.positiveTxtOk)
                    .show();
        }
        return result;
    }

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

    private void disableEnableControls(boolean enable){

        mAddEpisodeBtn.setEnabled(enable);
        mDurationBubble.setEnabled(enable);
        mVertigoBubble.setEnabled(enable);
        mLimitationBubble.setEnabled(enable);
        mStressBubble.setEnabled(enable);
        mHearingLossSwitch.setEnabled(enable);
        mTinnitusSwitch.setEnabled(enable);
        mEarFullnessSwitch.setEnabled(enable);
        mHeadacheSwitch.setEnabled(enable);
        mPhotophobiaSwitch.setEnabled(enable);
        mPhonophobiaSwitch.setEnabled(enable);
        mVisualSymSwitch.setEnabled(enable);
        mTumarkinSwitch.setEnabled(enable);
        mMenstruationSwitch.setEnabled(enable);
        mNauseaSwitch.setEnabled(enable);
        mVomitingSwitch.setEnabled(enable);
        mInstabilitySwitch.setEnabled(enable);
        mWeatherLayout.setEnabled(enable);
        mSleepLayout.setEnabled(enable);
        mPhisicLayout.setEnabled(enable);
        mExccessLayout.setEnabled(enable);
        mNotes.setEnabled(enable);
        mDizzinessDisBubble.setEnabled(enable);
        mInstabilityDisBubble.setEnabled(enable);
        mVisualBlurDisBubble.setEnabled(enable);
        mHeadPresureDisBubble.setEnabled(enable);
    }

    public void setEventData(){

        LinearLayout eventLayout = findViewById(R.id.layoutToDisable);
        disableEnableControls(false);

        if (event.instability != null && event.instability.equals("1")) mInstavilityIntenCardView.setVisibility(View.VISIBLE);
        if (event.headache != null && event.headache.equals("1")) mHeadache_LayOut.setVisibility(View.VISIBLE);

        mEpisodesTxt.setText(event.episodes!=null?getString(R.string.episodes) + event.episodes:getString(R.string.episodes) + "0");
        mDurationBubble.setProgress(event.duration!=null?Float.valueOf(event.duration):0);
        mVertigoBubble.setProgress(event.vertigoIntensity!=null?Float.valueOf(event.vertigoIntensity):0);
        mLimitationBubble.setProgress(event.limitation!=null?Float.valueOf(event.limitation):0);
        mStressBubble.setProgress(event.stress!=null?Float.valueOf(event.stress):0);

        mHearingLossSwitch.setChecked(event.hearingLoss!=null?true:false);
        mTinnitusSwitch.setChecked(event.tinnitus.equals("1")?true:false);
        mEarFullnessSwitch.setChecked(event.earFullness.equals("1")?true:false);
        mHeadacheSwitch.setChecked(event.headache.equals("1")?true:false);
        mPhotophobiaSwitch.setChecked(event.photophobia.equals("1")?true:false);
        mPhonophobiaSwitch.setChecked(event.equals("1")?true:false);
        mVisualSymSwitch.setChecked(event.visualSymptoms.equals("1")?true:false);
        mTumarkinSwitch.setChecked(event.tumarkin.equals("1")?true:false);
        mMenstruationSwitch.setChecked(event.menstruation.equals("1")?true:false);
        mNauseaSwitch.setChecked(event.nausea.equals("1")?true:false);
        mVomitingSwitch.setChecked(event.vomiting.equals("1")?true:false);
        mInstabilitySwitch.setChecked(event.instability.equals("1")?true:false);

        //Mostrar instavility booble
        if (mInstabilitySwitch.isChecked()){
            mInstavilityIntenCardView.setVisibility(View.VISIBLE);
            mInstabilityIntenBubble.setProgress(event.instabilityIntensity!=null?Float.valueOf(event.instabilityIntensity):0);
        }

        if (mHeadacheSwitch.isChecked()){
            mHeadache_LayOut.setVisibility(View.VISIBLE);

            if(mHeadProp_1b_CardView.getText().equals(event.headacheProperties1)){
                mHeadProp_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }else if(mWeather_1c_CardView.getText().equals(event.headacheProperties1)){
                mHeadProp_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }else if(mHeadProp_1d_CardView.getText().equals(event.headacheProperties1)){
                mHeadProp_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }

            if(mHeadProp_2b_CardView.getText().equals(event.headacheProperties2)){
                mHeadProp_2b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }else if(mHeadProp_2c_CardView.getText().equals(event.headacheProperties2)){
                mHeadProp_2c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_2c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_2a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_2a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }

            if(mHeadProp_3b_CardView.getText().equals(event.headacheProperties3)){
                mHeadProp_3b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }else if(mHeadProp_3c_CardView.getText().equals(event.headacheProperties3)){
                mHeadProp_3c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
                mHeadProp_3c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
                mHeadProp_3a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
                mHeadProp_3a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
            }

        }

        if(mWeather_1b_CardView.getText().equals(event.weather)){
            mWeather_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mWeather_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mWeather_1c_CardView.getText().equals(event.weather)){
            mWeather_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mWeather_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mWeather_1d_CardView.getText().equals(event.weather)){
            mWeather_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mWeather_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mWeather_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mWeather_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }

        if(mSleep_1b_CardView.getText().equals(event.sleep)){
            mSleep_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mSleep_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mSleep_1c_CardView.getText().equals(event.sleep)){
            mSleep_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mSleep_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mSleep_1d_CardView.getText().equals(event.sleep)){
            mSleep_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mSleep_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mSleep_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mSleep_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }

        if(mPhysical_1b_CardView.getText().equals(event.physicalActivity)){
            mPhysical_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mPhysical_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mPhysical_1c_CardView.getText().equals(event.physicalActivity)){
            mPhysical_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mPhysical_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mPhysical_1d_CardView.getText().equals(event.physicalActivity)){
            mPhysical_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mPhysical_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mPhysical_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mPhysical_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }

        if(mHabit_1b_CardView.getText().equals(event.habitExcess)){
            mHabit_1b_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mHabit_1b_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mHabit_1c_CardView.getText().equals(event.habitExcess)){
            mHabit_1c_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mHabit_1c_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mHabit_1d_CardView.getText().equals(event.habitExcess)){
            mHabit_1d_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mHabit_1d_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mHabit_1e_CardView.getText().equals(event.habitExcess)){
            mHabit_1e_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mHabit_1e_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }else if(mHabit_1f_CardView.getText().equals(event.habitExcess)){
            mHabit_1f_LayOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarker));
            mHabit_1f_CardView.setTextColor(getResources().getColor(R.color.colorWhite));
            mHabit_1a_LayOut.setBackground(getResources().getDrawable(R.drawable.card_edge));
            mHabit_1a_CardView.setTextColor(getResources().getColor(R.color.colorBlackTranslucent));
        }

        mNotes.setText(event.myNotes);

        mDizzinessDisBubble.setProgress(Float.valueOf(event.getDizzinessDis()));
        mInstabilityDisBubble.setProgress(Float.valueOf(event.inestabilityDis));
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

        cv.put("date", selectedDate.toString());
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
        cv.put("tinnitus", mTinnitusSwitch.isChecked()?1:0);
        cv.put("plenitude", mEarFullnessSwitch.isChecked()?1:0);
        cv.put("migraine", mHeadacheSwitch.isChecked()?1:0);
        cv.put("photophobia", mPhotophobiaSwitch.isChecked()?1:0);
        cv.put("phonophobia", mPhonophobiaSwitch.isChecked()?1:0);
        cv.put("visual_symp", mVisualSymSwitch.isChecked()?1:0);
        cv.put("tumarkin", mTumarkinSwitch.isChecked()?1:0);
        cv.put("menstruation", mMenstruationSwitch.isChecked()?1:0);
        cv.put("nausea", mNauseaSwitch.isChecked()?1:0);
        cv.put("vomit", mVomitingSwitch.isChecked()?1:0);
        cv.put("inestability", mInstabilitySwitch.isChecked()?1:0);

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
    }
}
