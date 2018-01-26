package com.symbel.meniere.activity.MyMeniere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.symbel.meniere.R;
import com.symbel.meniere.activity.HelpMeniere.AuthorsActivity;
import com.symbel.meniere.activity.TabsActivity;

import java.util.Date;


public class OneFragment extends Fragment {

    private View rootView;
    private CalendarView mCalendarV;
    private CardView mFocusTodayCv;
    private CardView mNewEventCv;

    public OneFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            mFocusTodayCv = (CardView) rootView.findViewById(R.id.cvFocusToday);
            mNewEventCv = (CardView) rootView.findViewById(R.id.cvNewEvent);

            mFocusTodayCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    focusToday();
                }
            });

            mNewEventCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabsActivity.activitySwitchFlag = true;
                    startActivity(new Intent(getActivity(), NewEventActivity.class));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void focusToday(){
        mCalendarV = (CalendarView) rootView.findViewById(R.id.calendarView);
        mCalendarV.setDate(new Date().getTime());

    }

}
