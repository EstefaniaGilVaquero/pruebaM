package com.example.stefy83.meniere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.activity.HealthyHabits;
import com.example.stefy83.meniere.activity.HearingDiaryActivity;
import com.example.stefy83.meniere.activity.LoginActivity;
import com.example.stefy83.meniere.activity.TabsActivity;
import com.example.stefy83.meniere.adapter.FaqAdapter;


public class ThreeFragment extends Fragment {

    private Activity activity;
    private View rootView;
    private CardView cvHealthyHabits;
    private CardView cvRateMe;
    private CardView cvVerticalSensivity;
    private CardView cvHearingDiary;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_three, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            setCardViews();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCardViews() {
        cvHealthyHabits = (CardView) rootView.findViewById(R.id.CVhealthyHabits);
        cvRateMe = (CardView) rootView.findViewById(R.id.CVrateMe);
        //cvVerticalSensivity = (CardView) rootView.findViewById(R.id.CVverticalSensivity);
        cvHearingDiary = (CardView) rootView.findViewById(R.id.CVhearingDiary);

        cvHearingDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(view.getContext(), HearingDiaryActivity.class));
            }
        });

        cvHealthyHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(view.getContext(), HealthyHabits.class));
            }
        });

//        cvVerticalSensivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Go to playStore
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.sindromedemeniereespana.com/"));
//                startActivity(intent);
//
//            }
//        });

        cvRateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to playStore
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.app"));
                try{
                    TabsActivity.activitySwitchFlag = true;
                    startActivity(intent);
                }
                catch(Exception e){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.android.app"));
                }
            }
        });
    }
}
