package com.asmes.meniere.activity.MyMeniere;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.asmes.meniere.R;
import com.asmes.meniere.activity.LoginFragment;
import com.asmes.meniere.activity.RegisterFragment;
import com.asmes.meniere.activity.TabsActivity;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.prefs.UserSession;
import com.asmes.meniere.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OneFragment extends Fragment {

    private View rootView;
    private CalendarView mCalendarV;
    private CardView mFocusTodayCv, mNewEventCv, mSeeSelection;
    private Activity activity;
    private SimpleDateFormat sdf;
    private String selectedDate;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private EventModel event;

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
            transaction.replace(R.id.relativeOneFragment, fragment);
            transaction.commit();

        }//Si no está logueado voy a login
        else if(!UserSession.getInstance(getContext()).ismIsLoggedIn()){
            Fragment fragment = LoginFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.relativeOneFragment, fragment);
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

            sdf = new SimpleDateFormat("dd/MM/yyyy");
            selectedDate = sdf.format(new Date());

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
                    focusToday();
                }
            });

            mCalendarV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                    Calendar calendar = new GregorianCalendar( year, month, dayOfMonth );
                    selectedDate = sdf.format(calendar.getTime());
                }
            });

            mNewEventCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Solo crear eventos en el dia de hoy y solo un evento por dia
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
                    String query = String.format("SELECT * FROM EVENT WHERE DATE='%s'", selectedDate);
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
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("isNew", isNew);
        if (!isNew) {
            intent.putExtra("event", event);
        }
        startActivity(intent);
    }

    public int getEventEntriesCount(){

        String query = String.format("SELECT * FROM EVENT WHERE DATE='%s'", selectedDate);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
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
