package com.example.stefy83.meniere.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.adapter.DatabaseHelper;
import com.example.stefy83.meniere.adapter.FaqAdapter;
import com.example.stefy83.meniere.adapter.HearingDiaryAdapter;
import com.example.stefy83.meniere.models.HearingDiaryModel;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.util.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HearingDiaryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HearingDiaryModel[] arrayHearingEntries;
    private FaqAdapter adapter;
    private Toolbar toolbar;
    boolean activitySwitchFlag = false;
    private HearingDiaryModel hearingDiary = new HearingDiaryModel();
    private Toast toast;
    private View positiveAction;
    private EditText passwordInput;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            activitySwitchFlag = true;
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hearing_diary);

        //
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAudio);
        fab.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_add_white_48px));
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Se ha presionado FAB", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                showCustomView();
            }
        });

        // SET BACK BUTTON
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Recupero mediciones de audio de sqlite
        //arrayHearingEntries = getResources().(R.array.arrayHearingEntries);

        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.hearingDiary_RecyclerView);
        // 2. set layoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 3. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // 4. create and set adapter
        /*adapter = new HearingDiaryAdapter(arrayTituloHabitos, arrayDescripcionHabitos, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            activitySwitchFlag = true;
            onBackPressed();
            // activity switch stuff..
            return true;
        }
        return false;
    }


    @Override
    public void onPause(){
        super.onPause();

        if (!activitySwitchFlag) {
            // Cambiamos de activity y no hacemos nada
            // Hemos pulsado home, matamos la app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            finishAffinity();
        }
        activitySwitchFlag = false;
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
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
                                showToast("creo audicion");
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                ContentValues cv = new ContentValues();
                                cv.put("date", date);
                                cv.put("left_05",  dialog.getCustomView().findViewById(R.id.etOidoIzq05).toString());
                                cv.put("left_1", dialog.getCustomView().findViewById(R.id.etOidoIzq1).toString());
                                cv.put("left_2", dialog.getCustomView().findViewById(R.id.etOidoIzq2).toString());
                                cv.put("left_4", dialog.getCustomView().findViewById(R.id.etOidoIzq4).toString());
                                cv.put("rigth_05", dialog.getCustomView().findViewById(R.id.etOidoDer05).toString());
                                cv.put("rigth_1", dialog.getCustomView().findViewById(R.id.etOidoDer1).toString());
                                cv.put("rigth_2", dialog.getCustomView().findViewById(R.id.etOidoDer2).toString());
                                cv.put("rigth_4", dialog.getCustomView().findViewById(R.id.etOidoDer4).toString());

                                db.insert("users", null, cv);

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
