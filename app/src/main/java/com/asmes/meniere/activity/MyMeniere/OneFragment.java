package com.asmes.meniere.activity.MyMeniere;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.LoginFingerTipFragment;
import com.asmes.meniere.activity.RegisterFragment;
import com.asmes.meniere.activity.TabsActivity;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.prefs.UserSession;
import com.asmes.meniere.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

public class OneFragment extends Fragment {

    private View rootView;
    private MaterialCalendarView mCalendarV;
    private CardView mFocusTodayCv, mNewEventCv, mSeeSelection;
    private Activity activity;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private EventModel event;
    private DayDecorator dayDecorator;
    private EventDecorator eventDecorator;
    private Collection<CalendarDay> calendarDayCollection = new ArrayList<>();

    public OneFragment() {
        // Required empty public constructor
    }

    public static OneFragment newInstance() {
        /*Bundle bundle = new Bundle();
        bundle.putString(Constants.ITEM_TYPE, itemType);*/
        OneFragment fragment = new OneFragment();
        //fragment.setArguments(bundle);

        return fragment;
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

        //Si no hay password vamos al registro
        if(UserSession.getInstance(getContext()).getPreferences().getString(UserSession.PREFERENCES_PASS, "").equals("")){
            Fragment fragment = RegisterFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.constraintOneFragment, fragment);
            transaction.commit();

        }//Si no está logueado voy a login
        else if(!UserSession.getInstance(getContext()).ismIsLoggedIn()){
            Fragment fragment = LoginFingerTipFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.constraintOneFragment, fragment);
            transaction.commit();
        }

        mCalendarV = rootView.findViewById(R.id.calendarView);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            dbHelper = new DatabaseHelper(activity);
            db = dbHelper.getWritableDatabase();

            mCalendarV.setDateSelected(CalendarDay.today(), true);
            dayDecorator = new DayDecorator(CalendarDay.today());
            mCalendarV.addDecorator(dayDecorator);
            setDaysWithEntries();

            mCalendarV.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    //mCalendarV.removeDecorator(new DayDecorator(previousSelectedDay));

                    mCalendarV.removeDecorator(dayDecorator);
                    //mCalendarV.invalidateDecorators();
                    dayDecorator = new DayDecorator(mCalendarV.getSelectedDate());
                    mCalendarV.addDecorator(dayDecorator);
                }
            });

            mFocusTodayCv = rootView.findViewById(R.id.cvFocusToday);
            mNewEventCv = rootView.findViewById(R.id.cvNewEvent);
            mSeeSelection = rootView.findViewById(R.id.cvSeeSelection);

            mSeeSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), NewEventActivity.class));
                }
            });

            mFocusTodayCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCalendarV.setCurrentDate(CalendarDay.today());
                }
            });

            mNewEventCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Solo crear eventos en el dia de hoy o ayer y solo un evento por dia
                    if(isValidDay() && getEventEntriesCount()==0) {
                        callNewEvent(true);
                    }else if(!isValidDay()){
                        Utils.OkDialog(activity, getString(R.string.selectionErrorTitle), getString(R.string.selectionErrorMessagePassDay));
                    }else if(getEventEntriesCount()>0){
                        Utils.OkDialog(activity, getString(R.string.newEventErrorTitle), getString(R.string.newEventErrorMessageDayHasEvent));
                    }
                }
            });

            mSeeSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Recupero datos de eventos de sqlite
                    //Eventos del dia
                    String query = String.format("SELECT * FROM EVENT WHERE DATE='%s'", mCalendarV.getSelectedDate().toString());
                    event = getEventEntries(query);

                    //Si hay eventos para el dia
                    if (event != null) callNewEvent(false);
                    else Utils.showToast(activity,"Este dia no contiene eventos");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callNewEvent(Boolean isNew){
        TabsActivity.activitySwitchFlag = true;
        Intent intent = new Intent(getActivity(), NewEventActivity.class);
        intent.putExtra("selectedDate", mCalendarV.getSelectedDate());
        intent.putExtra("isNew", isNew);
        if (!isNew) {
            intent.putExtra("event", event);
        }
        startActivity(intent);
    }

    public int getEventEntriesCount(){

        String query = String.format("SELECT * FROM EVENT WHERE DATE='%s'", mCalendarV.getSelectedDate().toString());
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public void setDaysWithEntries() {
        String query = String.format("SELECT DATE FROM EVENT");
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() != 0){

            String date = "";
            int day = 0;
            int month = 0;
            int year= 0;

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                date = cursor.getString(i).substring(12,cursor.getString(i).toString().length()-1);
                day = Integer.valueOf(date.split("-")[2]);
                month = Integer.valueOf(date.split("-")[1]);
                year = Integer.valueOf(date.split("-")[0]);

                calendarDayCollection.add(CalendarDay.from(year,month,day));
            }

            cursor.close();
            eventDecorator = new EventDecorator(calendarDayCollection);
            mCalendarV.addDecorator(eventDecorator);
        }
    }

    public EventModel getEventEntries(String sql){
        EventModel event = null;
        Cursor cursor = db.rawQuery(sql, null);

        //Inicializo array
        if (cursor.getCount()!= 0) {
            event = new EventModel();
            cursor.moveToFirst();
            event.id = cursor.getString(cursor.getColumnIndex("id"));
            event.episodes = cursor.getString(cursor.getColumnIndex("episodes"));
            event.date = cursor.getString(cursor.getColumnIndex("date"));
            event.duration = cursor.getString(cursor.getColumnIndex("duration"));
            event.vertigoIntensity = cursor.getString(cursor.getColumnIndex("intensity"));
            event.limitation = cursor.getString(cursor.getColumnIndex("limitation"));
            event.stress = cursor.getString(cursor.getColumnIndex("stress"));
            event.hearingLoss = cursor.getString(cursor.getColumnIndex("hearingLoss"));
            event.tinnitus = cursor.getString(cursor.getColumnIndex("tinnitus"));
            event.earFullness = cursor.getString(cursor.getColumnIndex("plenitude"));
            event.headache = cursor.getString(cursor.getColumnIndex("migraine"));
            event.photophobia = cursor.getString(cursor.getColumnIndex("photophobia"));
            event.phonophobia = cursor.getString(cursor.getColumnIndex("phonophobia"));
            event.visualSymptoms = cursor.getString(cursor.getColumnIndex("visual_symp"));
            event.tumarkin = cursor.getString(cursor.getColumnIndex("tumarkin"));
            event.menstruation = cursor.getString(cursor.getColumnIndex("menstruation"));
            event.nausea = cursor.getString(cursor.getColumnIndex("nausea"));
            event.vomiting = cursor.getString(cursor.getColumnIndex("vomit"));
            event.instability = cursor.getString(cursor.getColumnIndex("inestability"));
            event.instabilityIntensity = cursor.getString(cursor.getColumnIndex("inestabilityInten"));
            event.headacheProperties1 = cursor.getString(cursor.getColumnIndex("migraine_type1"));
            event.headacheProperties2 = cursor.getString(cursor.getColumnIndex("migraine_type2"));
            event.headacheProperties3 = cursor.getString(cursor.getColumnIndex("migraine_type3"));
            event.weather = cursor.getString(cursor.getColumnIndex("triggers_climate"));
            event.sleep = cursor.getString(cursor.getColumnIndex("triggers_sleep"));
            event.physicalActivity = cursor.getString(cursor.getColumnIndex("triggers_phisic"));
            event.habitExcess = cursor.getString(cursor.getColumnIndex("triggers_excesses"));
            event.myNotes = cursor.getString(cursor.getColumnIndex("triggers_notes"));
            event.dizzinessDis = cursor.getString(cursor.getColumnIndex("residual_type1"));
            event.inestabilityDis = cursor.getString(cursor.getColumnIndex("residual_type2"));
            event.visualBlurDis = cursor.getString(cursor.getColumnIndex("residual_type3"));
            event.headPressure = cursor.getString(cursor.getColumnIndex("residual_type4"));
        }

        cursor.close();
         return event;
    }

    public Boolean isValidDay(){
        Boolean result = false;
        //Calculate yesterday
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        CalendarDay yesterday = CalendarDay.from(cal);

        //Si no es el dia de hoy o ayer, no se puede crear un nuevo evento
        if(mCalendarV.getSelectedDate().equals(CalendarDay.today()) || mCalendarV.getSelectedDate().isInRange(yesterday, CalendarDay.today())){
            result = true;
        }
        return result;
    }

    private class DayDecorator implements DayViewDecorator {

        private final CalendarDay calendarDay;
        private final Drawable backgroundDrawable;


        public DayDecorator(CalendarDay calendarDay) {
            this.calendarDay = calendarDay;
            backgroundDrawable = getResources().getDrawable(R.drawable.today_circle_background);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return this.calendarDay.equals(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(backgroundDrawable);
        }
    }

    public class EventDecorator implements DayViewDecorator {

        private final HashSet<CalendarDay> dates;

        public EventDecorator(Collection<CalendarDay> dates) {
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, R.color.colorPrimaryDarker));
        }
    }


}
