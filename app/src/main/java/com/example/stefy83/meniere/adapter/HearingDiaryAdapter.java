package com.example.stefy83.meniere.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by stefy83 on 21/06/2017.
 */

public class HearingDiaryAdapter extends RecyclerView.Adapter {

    private Activity activity;


    public HearingDiaryAdapter(String[] arrayQuestions, String[] arrayAnswers, Activity activity) {
        this.arrayQuestions = arrayQuestions;
        this.arrayAnswers = arrayAnswers;
        this.activity = activity;
    }


}
