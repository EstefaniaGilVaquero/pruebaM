package com.example.stefy83.meniere.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.stefy83.meniere.AndroidDatabaseManager;
import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.adapter.DatabaseHelper;
import com.example.stefy83.meniere.adapter.HearingDiaryAdapter;
import com.example.stefy83.meniere.models.HearingDiaryModel;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HearingDiaryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<HearingDiaryModel> arrayHearingEntries = new ArrayList<HearingDiaryModel>();
    private HearingDiaryAdapter adapter;
    private Toolbar toolbar;
    boolean activitySwitchFlag = false;
    private Toast toast;
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

            case android.R.id.home:
                activitySwitchFlag = true;
                onBackPressed();
                return true;

            case R.id.showSqlite:
                Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
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

        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.hearingDiary_RecyclerView);
        // 2. set layoutManger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 3. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Recupero mediciones de audio de sqlite
        arrayHearingEntries = getHearingEntries("SELECT * FROM " + "audio");

        // 4. create and set adapter
        adapter = new HearingDiaryAdapter(arrayHearingEntries, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
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

    public ArrayList<HearingDiaryModel> getHearingEntries(String sql){

        Cursor cursor = db.rawQuery(sql, null);

        //Inicializo array
        HearingDiaryModel audio = new HearingDiaryModel();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            audio.date = cursor.getString(cursor.getColumnIndex("date"));
            audio.left05 = cursor.getString(cursor.getColumnIndex("left05"));
            audio.left1 = cursor.getString(cursor.getColumnIndex("left1"));
            audio.left2 = cursor.getString(cursor.getColumnIndex("left2"));
            audio.left4 = cursor.getString(cursor.getColumnIndex("left4"));
            audio.rigth05 = cursor.getString(cursor.getColumnIndex("rigth05"));
            audio.rigth1 = cursor.getString(cursor.getColumnIndex("rigth1"));
            audio.rigth2 = cursor.getString(cursor.getColumnIndex("rigth2"));
            audio.rigth4 = cursor.getString(cursor.getColumnIndex("rigth4"));

            arrayHearingEntries.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayHearingEntries;
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
                                EditText left05 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq05);
                                EditText left1 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq1);
                                EditText left2 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq2);
                                EditText left4 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq4);
                                EditText rigth05 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer05);
                                EditText rigth1 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer1);
                                EditText rigth2 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer2);
                                EditText rigth4 = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer4);

                                cv.put("date", date);
                                cv.put("left05",  left05.getText().toString());
                                cv.put("left1", left1.getText().toString());
                                cv.put("left2", left2.getText().toString());
                                cv.put("left4", left4.getText().toString());
                                cv.put("rigth05", rigth05.getText().toString());
                                cv.put("rigth1", rigth1.getText().toString());
                                cv.put("rigth2", rigth2.getText().toString());
                                cv.put("rigth4", rigth4.getText().toString());

                                db.insert("audio", null, cv);

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

    public void SqlQuery(String sql) {

    }
}
