package com.asmes.meniereapp.activity.UtilitiesMeniere;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.BaseActivity;
import com.asmes.meniereapp.activity.Login.LoginFingerTipActivity;
import com.asmes.meniereapp.adapter.DatabaseHelper;
import com.asmes.meniereapp.adapter.HearingDiaryAdapter;
import com.asmes.meniereapp.models.HearingDiaryModel;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asmes.meniereapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HearingDiaryActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<HearingDiaryModel> arrayHearingEntries = new ArrayList<>();
    private HearingDiaryAdapter adapter;
    private Toolbar toolbar;
    private Toast toast;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Activity activity;
    private static String TAG;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Recupero mediciones de audio de sqlite
        arrayHearingEntries = getHearingEntries("select * from audio order by date desc");

        // 4. create and set adapter
        adapter = new HearingDiaryAdapter(arrayHearingEntries, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.hearingTestApp:
                //Go to playStore
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.amw.hearingtest"));
                try{
                    startActivity(intent);
                }
                catch(Exception e){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.amw.hearingtest&hl=es"));
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hearing_diary);
        activity = this;

        TAG = getString(R.string.hearingDiary);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(TAG);

        //
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAudio);
        fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_add_white_48px));
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showCustomView();
            }
        });

        // 1. get a reference to recyclerView
        mRecyclerView = findViewById(R.id.hearingDiary_RecyclerView);
        // 2. set layoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 3. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Recupero mediciones de audio de sqlite
        arrayHearingEntries = getHearingEntries("select * from audio order by date desc");

        // 4. create and set adapter
        adapter = new HearingDiaryAdapter(arrayHearingEntries, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

        //Mato activity de login
        if (LoginFingerTipActivity.getInstance() != null){
            LoginFingerTipActivity.getInstance().finish();
        }
    }

    public void refreshData(){
        //Recupero mediciones de audio de sqlite
        arrayHearingEntries = getHearingEntries("select * from audio order by date desc");
        // 4. create and set adapter
        adapter = new HearingDiaryAdapter(arrayHearingEntries, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    public ArrayList<HearingDiaryModel> getHearingEntries(String sql){

        ArrayList<HearingDiaryModel> arrayHearingEntriesAux = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        //Inicializo array
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            HearingDiaryModel audio = new HearingDiaryModel();
            audio.date = cursor.getString(cursor.getColumnIndex("date"));
            audio.left05_a = cursor.getString(cursor.getColumnIndex("left05_a"));
            audio.left1_a = cursor.getString(cursor.getColumnIndex("left1_a"));
            audio.left2_a = cursor.getString(cursor.getColumnIndex("left2_a"));
            audio.left4_a = cursor.getString(cursor.getColumnIndex("left4_a"));
            audio.rigth05_a = cursor.getString(cursor.getColumnIndex("rigth05_a"));
            audio.rigth1_a = cursor.getString(cursor.getColumnIndex("rigth1_a"));
            audio.rigth2_a = cursor.getString(cursor.getColumnIndex("rigth2_a"));
            audio.rigth4_a = cursor.getString(cursor.getColumnIndex("rigth4_a"));
            audio.crisis = cursor.getString(cursor.getColumnIndex("crisis"));

            arrayHearingEntriesAux.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayHearingEntriesAux;
    }

    public void showCustomView() {
        boolean wrapInScrollView = true;

        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title(R.string.titleNewEntry)
                        .customView(R.layout.dialog_hearing_diary, wrapInScrollView)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                try{
                                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                    ContentValues cv = new ContentValues();
                                    EditText left05_a = dialog.getCustomView().findViewById(R.id.etOidoIzq05_a);
                                    EditText left1_a = dialog.getCustomView().findViewById(R.id.etOidoIzq1_a);
                                    EditText left2_a = dialog.getCustomView().findViewById(R.id.etOidoIzq2_a);
                                    EditText left4_a =  dialog.getCustomView().findViewById(R.id.etOidoIzq4_a);
                                    EditText rigth05_a = dialog.getCustomView().findViewById(R.id.etOidoDer05_a);
                                    EditText rigth1_a = dialog.getCustomView().findViewById(R.id.etOidoDer1_a);
                                    EditText rigth2_a = dialog.getCustomView().findViewById(R.id.etOidoDer2_a);
                                    EditText rigth4_a = dialog.getCustomView().findViewById(R.id.etOidoDer4_a);
                                    CheckBox crisis = dialog.getCustomView().findViewById(R.id.crisisCheckBox);

                                    cv.put("date", date);
                                    cv.put("left05_a",  left05_a.getText().toString());
                                    cv.put("left1_a", left1_a.getText().toString());
                                    cv.put("left2_a", left2_a.getText().toString());
                                    cv.put("left4_a", left4_a.getText().toString());
                                    cv.put("rigth05_a", rigth05_a.getText().toString());
                                    cv.put("rigth1_a", rigth1_a.getText().toString());
                                    cv.put("rigth2_a", rigth2_a.getText().toString());
                                    cv.put("rigth4_a", rigth4_a.getText().toString());
                                    cv.put("crisis", crisis.isChecked()?1:0);

                                    db.insert("audio", null, cv);

                                    Utils.showToast(activity, getString(R.string.toast_save_hearing_test_ok));
                                }catch (Exception e){
                                    Utils.showToast(activity, getString(R.string.toast_save_hearing_test_ko));
                                }

                            }
                        })
                        .positiveText(R.string.dialogoGuardar)
                        .negativeText(android.R.string.cancel)
                        .positiveColor(getResources().getColor(R.color.colorBlack))
                        .negativeColor(getResources().getColor(R.color.colorBlack))
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .build();
        dialog.show();
    }
}