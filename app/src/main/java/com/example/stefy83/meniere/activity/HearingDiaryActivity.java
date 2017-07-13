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
    private ArrayList<HearingDiaryModel> arrayHearingEntries = new ArrayList<>();
    private HearingDiaryAdapter adapter;
    private Toolbar toolbar;
    boolean activitySwitchFlag = false;
    private Toast toast;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onResume() {
        super.onResume();
        //Recupero mediciones de audio de sqlite
        arrayHearingEntries = getHearingEntries("SELECT * FROM " + "audio");

        // 4. create and set adapter
        adapter = new HearingDiaryAdapter(arrayHearingEntries, this);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
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

        ArrayList<HearingDiaryModel> arrayHearingEntriesAux = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        //Inicializo array
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            HearingDiaryModel audio = new HearingDiaryModel();
            audio.date = cursor.getString(cursor.getColumnIndex("date"));
            audio.left05_a = cursor.getString(cursor.getColumnIndex("left05_a"));
            audio.left05_b = cursor.getString(cursor.getColumnIndex("left05_b"));
            audio.left1_a = cursor.getString(cursor.getColumnIndex("left1_a"));
            audio.left1_b = cursor.getString(cursor.getColumnIndex("left1_b"));
            audio.left2_a = cursor.getString(cursor.getColumnIndex("left2_a"));
            audio.left2_b = cursor.getString(cursor.getColumnIndex("left2_b"));
            audio.left4_a = cursor.getString(cursor.getColumnIndex("left4_a"));
            audio.left4_b = cursor.getString(cursor.getColumnIndex("left4_b"));
            audio.rigth05_a = cursor.getString(cursor.getColumnIndex("rigth05_a"));
            audio.rigth05_b = cursor.getString(cursor.getColumnIndex("rigth05_b"));
            audio.rigth1_a = cursor.getString(cursor.getColumnIndex("rigth1_a"));
            audio.rigth1_b = cursor.getString(cursor.getColumnIndex("rigth1_b"));
            audio.rigth2_a = cursor.getString(cursor.getColumnIndex("rigth2_a"));
            audio.rigth2_a = cursor.getString(cursor.getColumnIndex("rigth2_b"));
            audio.rigth4_a = cursor.getString(cursor.getColumnIndex("rigth4_a"));
            audio.rigth4_b = cursor.getString(cursor.getColumnIndex("rigth4_b"));

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
                                showToast("creo audicion");
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                ContentValues cv = new ContentValues();
                                EditText left05_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq05_a);
                                EditText left05_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq05_b);
                                EditText left1_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq1_a);
                                EditText left1_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq1_b);
                                EditText left2_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq2_a);
                                EditText left2_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq2_b);
                                EditText left4_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq4_a);
                                EditText left4_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoIzq4_b);
                                EditText rigth05_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer05_a);
                                EditText rigth05_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer05_b);
                                EditText rigth1_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer1_a);
                                EditText rigth1_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer1_b);
                                EditText rigth2_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer2_a);
                                EditText rigth2_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer2_b);
                                EditText rigth4_a = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer4_a);
                                EditText rigth4_b = (EditText) dialog.getCustomView().findViewById(R.id.etOidoDer4_b);

                                cv.put("date", date);
                                cv.put("left05_a",  left05_a.getText().toString());
                                cv.put("left05_b",  left05_b.getText().toString());
                                cv.put("left1_a", left1_a.getText().toString());
                                cv.put("left1_b", left1_b.getText().toString());
                                cv.put("left2_a", left2_a.getText().toString());
                                cv.put("left2_b", left2_b.getText().toString());
                                cv.put("left4_a", left4_a.getText().toString());
                                cv.put("left4_b", left4_b.getText().toString());
                                cv.put("rigth05_a", rigth05_a.getText().toString());
                                cv.put("rigth05_b", rigth05_b.getText().toString());
                                cv.put("rigth1_a", rigth1_a.getText().toString());
                                cv.put("rigth1_b", rigth1_b.getText().toString());
                                cv.put("rigth2_a", rigth2_a.getText().toString());
                                cv.put("rigth2_b", rigth2_b.getText().toString());
                                cv.put("rigth4_a", rigth4_a.getText().toString());
                                cv.put("rigth4_b", rigth4_b.getText().toString());

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
}
