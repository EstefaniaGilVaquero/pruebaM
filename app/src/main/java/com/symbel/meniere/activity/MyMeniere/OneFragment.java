package com.symbel.meniere.activity.MyMeniere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.symbel.meniere.R;
import com.symbel.meniere.activity.HelpMeniere.AuthorsActivity;
import com.symbel.meniere.activity.TabsActivity;
import com.symbel.meniere.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class OneFragment extends Fragment {

    private View rootView;
    private CalendarView mCalendarV;
    private CardView mFocusTodayCv;
    private CardView mNewEventCv;
    private Activity activity;
    private SimpleDateFormat sdf;
    private String selectedDate;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        mCalendarV = (CalendarView) rootView.findViewById(R.id.calendarView);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            sdf = new SimpleDateFormat("dd/MM/yyyy");
            selectedDate = sdf.format(new Date());

            mFocusTodayCv = (CardView) rootView.findViewById(R.id.cvFocusToday);
            mNewEventCv = (CardView) rootView.findViewById(R.id.cvNewEvent);

            mFocusTodayCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    focusToday();
                }
            });

            mCalendarV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth) {

                    Calendar calendar = new GregorianCalendar( year, month, dayOfMonth );
                    selectedDate = sdf.format(calendar.getTime());
                }
            });

            mNewEventCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isValidDay()) {
                        TabsActivity.activitySwitchFlag = true;
                        startActivity(new Intent(getActivity(), NewEventActivity.class));
                    }else{
                        Utils.OkDialog(activity, getString(R.string.selectionErrorTitle), getString(R.string.selectionErrorMessage));
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void focusToday(){

        mCalendarV.setDate(new Date().getTime());
        selectedDate = sdf.format(new Date().getTime());

    }

    public Boolean isValidDay(){
        Boolean result = false;
        String today = sdf.format(new Date());
        Date lDate = new Date(mCalendarV.getDate());
        //Si no es el dia de hoy, no se puede crear un nuevo evento
        if(selectedDate.equals(today)){
            result = true;
        }
        return result;
    }
}
