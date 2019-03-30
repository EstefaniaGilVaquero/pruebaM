package com.asmes.meniereapp.activity.MyMeniere;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;
import com.asmes.meniereapp.adapter.DatabaseHelper;
import com.asmes.meniereapp.models.EventModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import lib.kingja.switchbutton.SwitchMultiButton;

public class NewEventActivity extends BaseActivity {

    private ScrollView mScrollView;
    private BubbleSeekBar mDurationBubble, mVertigoBubble, mLimitationBubble, mStressBubble, mInstabilityIntenBubble, mDizzinessDisBubble, mInstabilityDisBubble, mVisualBlurDisBubble, mHeadPresureDisBubble;
    private SwitchCompat mHearingLossSwitch, mTinnitusSwitch, mEarFullnessSwitch, mHeadacheSwitch, mPhotophobiaSwitch, mPhonophobiaSwitch, mVisualSymSwitch, mTumarkinSwitch, mMenstruationSwitch, mNauseaSwitch, mVomitingSwitch, mInstabilitySwitch;
    private TextView mEpisodesTxt, mDurationTxt, mVertigoTxt, mLimitationTxt, mStressTxt, mInstabilityIntenTxt, mDizzinessDisTxt, mInstabilityDisTxt, mVisualBlurDisTxt, mHeadPresureDisTxt,
                        mHeadProp_1a_CardView, mHeadProp_1b_CardView, mHeadProp_1c_CardView, mHeadProp_1d_CardView, mHeadProp_2a_CardView, mHeadProp_2b_CardView, mHeadProp_2c_CardView, mHeadProp_3a_CardView, mHeadProp_3b_CardView, mHeadProp_3c_CardView, mNotes;

    private LinearLayout mHeadProp_1a_LayOut, mHeadProp_1b_LayOut, mHeadProp_1c_LayOut, mHeadProp_1d_LayOut, mHeadProp_2a_LayOut, mHeadProp_2b_LayOut, mHeadProp_2c_LayOut, mHeadProp_3a_LayOut, mHeadProp_3b_LayOut, mHeadProp_3c_LayOut, mHeadache_LayOut;

    SwitchMultiButton mSwitchMultiButtonClima , mSwitchMultiButtonSueno , mSwitchMultiButtonActividad , mSwitchMultiButtonExcesos, getmSwitchMultiButtonJaqueca1, getmSwitchMultiButtonJaqueca2, getmSwitchMultiButtonJaqueca3;

    private String mMigraineType1, mMigraineType2, mMigraineType3, mHearingLoss;
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
    private static String TAG;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        TAG = getString(R.string.newEvent);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

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

        getmSwitchMultiButtonJaqueca1 = findViewById(R.id.multiButtonJaqueca1);
        getmSwitchMultiButtonJaqueca2 = findViewById(R.id.multiButtonJaqueca2);
        getmSwitchMultiButtonJaqueca3 = findViewById(R.id.multiButtonJaqueca3);
        mSwitchMultiButtonClima = findViewById(R.id.multiButtonClima);
        mSwitchMultiButtonSueno = findViewById(R.id.multiButtonSueno);
        mSwitchMultiButtonActividad = findViewById(R.id.multiButtonActividad);
        mSwitchMultiButtonExcesos = findViewById(R.id.multiButtonExcesos);

        mHeadache_LayOut = findViewById(R.id.headachePropsLayout);
        mInstavilityIntenCardView = findViewById(R.id.instabilityIntensityCardView);

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
                        onBackPressed();
                    }
                }
            });

            //Set listeners for bubbles
            setBubblesListeners();

            //set info listeners
            setInfoListeners();
        }
    }

    public Boolean validateEvent(){
        Boolean result = true;
        //Duration must be >20
        //vertigoIntensity must be >0 or tinnutus or hearingloss o earfulness or headache or photophobia or phonophobia or aura or tumarkin
        //TODO cambiar valor de vertigobubble debe ser <20??? pq???
        if (mDurationBubble.getProgressFloat()<0.21 || mVertigoBubble.getProgress()<0 || (mVertigoBubble.getProgress()<1 && !mTinnitusSwitch.isChecked() && !mHearingLossSwitch.isChecked() && !mEarFullnessSwitch.isChecked()
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
                            .title(R.string.hearingLossSwitchTitle)
                            .content(R.string.hearingLossSwitchContent)
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
        mSwitchMultiButtonClima.setEnabled(enable);
        mSwitchMultiButtonSueno.setEnabled(enable);
        mSwitchMultiButtonActividad.setEnabled(enable);
        mSwitchMultiButtonExcesos.setEnabled(enable);
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

        String switchSelectedName;

        if (mHeadacheSwitch.isChecked()){
            mHeadache_LayOut.setVisibility(View.VISIBLE);

            for (int i=0; i<getResources().getStringArray(R.array.arrayJaqueca1).length; i++)
            {
                switchSelectedName = getResources().getStringArray(R.array.arrayJaqueca1)[i];
                if (switchSelectedName.equalsIgnoreCase(event.headacheProperties1)) mSwitchMultiButtonClima.setSelectedTab(i);
            }

            for (int i=0; i<getResources().getStringArray(R.array.arrayJaqueca2).length; i++)
            {
                switchSelectedName = getResources().getStringArray(R.array.arrayJaqueca2)[i];
                if (switchSelectedName.equalsIgnoreCase(event.headacheProperties2)) mSwitchMultiButtonClima.setSelectedTab(i);
            }

            for (int i=0; i<getResources().getStringArray(R.array.arrayJaqueca3).length; i++)
            {
                switchSelectedName = getResources().getStringArray(R.array.arrayJaqueca3)[i];
                if (switchSelectedName.equalsIgnoreCase(event.headacheProperties3)) mSwitchMultiButtonClima.setSelectedTab(i);
            }
        }

        for (int i=0; i<getResources().getStringArray(R.array.arrayTriggersClima).length; i++)
        {
            switchSelectedName = getResources().getStringArray(R.array.arrayTriggersClima)[i];
            if (switchSelectedName.equalsIgnoreCase(event.weather)) mSwitchMultiButtonClima.setSelectedTab(i);
        }

        for (int i=0; i<getResources().getStringArray(R.array.arrayTriggersSueño).length; i++)
        {
            switchSelectedName = getResources().getStringArray(R.array.arrayTriggersSueño)[i];
            if (switchSelectedName.equalsIgnoreCase(event.sleep)) mSwitchMultiButtonSueno.setSelectedTab(i);
        }

        for (int i=0; i<getResources().getStringArray(R.array.arrayTriggersActividad).length; i++)
        {
            switchSelectedName = getResources().getStringArray(R.array.arrayTriggersActividad)[i];
            if (switchSelectedName.equalsIgnoreCase(event.physicalActivity)) mSwitchMultiButtonActividad.setSelectedTab(i);
        }

        for (int i=0; i<getResources().getStringArray(R.array.arrayTriggersExcesos).length; i++)
        {
            switchSelectedName = getResources().getStringArray(R.array.arrayTriggersExcesos)[i];
            if (switchSelectedName.equalsIgnoreCase(event.habitExcess)) mSwitchMultiButtonExcesos.setSelectedTab(i);
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
        cv.put("migraine_type1", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):getResources().getStringArray(R.array.arrayJaqueca1)[mSwitchMultiButtonClima.getSelectedTab()]);
        cv.put("migraine_type2", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):getResources().getStringArray(R.array.arrayJaqueca2)[mSwitchMultiButtonClima.getSelectedTab()]);
        cv.put("migraine_type3", mHeadacheSwitch.getVisibility()==View.GONE?getResources().getString(R.string.NA):getResources().getStringArray(R.array.arrayJaqueca3)[mSwitchMultiButtonClima.getSelectedTab()]);
        cv.put("triggers_climate", getResources().getStringArray(R.array.arrayTriggersClima)[mSwitchMultiButtonClima.getSelectedTab()]);
        cv.put("triggers_sleep", getResources().getStringArray(R.array.arrayTriggersSueño)[mSwitchMultiButtonSueno.getSelectedTab()]);
        cv.put("triggers_phisic", getResources().getStringArray(R.array.arrayTriggersActividad)[mSwitchMultiButtonActividad.getSelectedTab()]);
        cv.put("triggers_excesses", getResources().getStringArray(R.array.arrayTriggersExcesos)[mSwitchMultiButtonExcesos.getSelectedTab()]);
        cv.put("triggers_notes", notes);

        db.insert("event", null, cv);
    }
}
