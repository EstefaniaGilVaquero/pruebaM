package com.asmes.meniereapp.activity.UtilitiesMeniere;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmes.meniereapp.R;
import com.asmes.meniereapp.activity.Login.LoginFingerTipActivity;
import com.asmes.meniereapp.activity.Login.RegisterActivity;
import com.asmes.meniereapp.activity.TabsActivity;
import com.asmes.meniereapp.prefs.UserSession;


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
        cvHealthyHabits = (CardView) rootView.findViewById(R.id.CVAuthors);
        cvRateMe = (CardView) rootView.findViewById(R.id.CVwebsite);
        //cvVerticalSensivity = (CardView) rootView.findViewById(R.id.CVverticalSensivity);
        cvHearingDiary = (CardView) rootView.findViewById(R.id.CVGuide);

        cvHearingDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                //Si no hay password vamos al registro
                if (UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals("")) {
                    startActivity(new Intent(view.getContext(), RegisterActivity.class));
                }//Si no está logueado voy a login
                else if (!UserSession.getInstance(getContext()).ismIsLoggedIn()) {
                    startActivity(new Intent(view.getContext(), LoginFingerTipActivity.class));
                } else {//Voy a myMeniere
                    startActivity(new Intent(view.getContext(), HearingDiaryActivity.class));
                }
            }
        });

        cvHealthyHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(view.getContext(), HealthyHabitsActivity.class));
            }
        });

        cvRateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to playStore
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.asmes.meniereapp"));
                try {
                    TabsActivity.activitySwitchFlag = true;
                    startActivity(intent);
                } catch (Exception e) {
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.asmes.meniereapp&hl=es"));
                }
            }
        });
    }
}
