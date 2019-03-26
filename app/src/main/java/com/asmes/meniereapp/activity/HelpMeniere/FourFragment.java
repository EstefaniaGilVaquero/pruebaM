package com.asmes.meniereapp.activity.HelpMeniere;

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
import com.asmes.meniereapp.activity.UtilitiesMeniere.HealthyHabitsActivity;
import com.asmes.meniereapp.activity.TabsActivity;
import com.asmes.meniereapp.models.User;


public class FourFragment extends Fragment {

    private Activity activity;
    private View rootView;
    private CardView cvGuide;
    private CardView cvAuthors;
    private CardView cvFrameworks;
    private CardView cvWebsite;

    public FourFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_four, container, false);

        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            setCardViews();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCardViews() {
        cvGuide = (CardView) rootView.findViewById(R.id.CVGuide);
        cvAuthors = (CardView) rootView.findViewById(R.id.CVAuthors);
        cvFrameworks = (CardView) rootView.findViewById(R.id.CVFrameworks);
        cvWebsite = (CardView) rootView.findViewById(R.id.CVwebsite);

        cvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(getActivity(), UserGuideActivity.class));
            }
        });

        cvAuthors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(getActivity(), AuthorsActivity.class));
            }
        });

        cvFrameworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabsActivity.activitySwitchFlag = true;
                startActivity(new Intent(getActivity(), FrameworksActivity.class));
            }
        });

        cvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to playStore
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.sindromedemeniereespana.com/"));
                startActivity(intent);

            }
        });
    }
}

