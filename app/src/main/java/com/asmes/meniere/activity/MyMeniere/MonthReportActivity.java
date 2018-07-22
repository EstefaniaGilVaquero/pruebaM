package com.asmes.meniere.activity.MyMeniere;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.asmes.meniere.R;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.models.TriggerModel;
import com.asmes.meniere.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthReportActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    boolean activitySwitchFlag = false;
    protected BarChart chart;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private int eventsInMonth;
    private TextView meanData1, meanData2, meanData3, meanData4, meanData5, meanData6,trigger1, trigger2, trigger3, trigger4;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                activitySwitchFlag = true;
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_report);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        chart = findViewById(R.id.barChart);
        trigger1 = findViewById(R.id.trigger1);
        trigger2 = findViewById(R.id.trigger2);
        trigger3 = findViewById(R.id.trigger3);
        trigger4 = findViewById(R.id.trigger4);

        meanData1 = findViewById(R.id.meanData1);
        meanData2 = findViewById(R.id.meanData2);
        meanData3 = findViewById(R.id.meanData3);
        meanData4 = findViewById(R.id.meanData4);
        meanData5 = findViewById(R.id.meanData5);
        meanData6 = findViewById(R.id.meanData6);

        setChart();
        setMeanData();
        setTriggers();
    }

    private void setChart() {

        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;

        //Set the chart setting with the below following code
        chart = findViewById(R.id.barChart);
        chart.setDescription(null);
        chart.setPinchZoom(true);
        chart.setScaleEnabled(true);
        chart.setDrawBarShadow(false);

        // fill the lists//

        // Get the number of days in that month
        int iYear = MyMeniereFragment.mCalendarV.getSelectedDate().getYear();
        int iMonth = MyMeniereFragment.mCalendarV.getSelectedDate().getMonth(); // 1 (months begin with 0)
        int iDay = MyMeniereFragment.mCalendarV.getSelectedDate().getDay();

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

        ArrayList xVals = new ArrayList();

        for(int i = 1; i <= daysInMonth; i++) {
            xVals.add(String.valueOf(i));
        }

        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();

        ArrayList<EventModel> eventModelArrayList = getEventMonthChartData();

        for(int j = 1; j <= daysInMonth; j++ ){
            Boolean dateAsigned = false;

            for (int i = 0; i < eventModelArrayList.size(); i++ ){
                Integer dayOfMonth = Integer.valueOf(eventModelArrayList.get(i).getDate().split("-")[2].split("\\}")[0]);
                if(dayOfMonth == j) {
                    int vertigoIntensity = Integer.valueOf(eventModelArrayList.get(i).getVertigoIntensity());
                    int migrainteIntensity=0;
                    if (eventModelArrayList.get(i).getHeadacheProperties1() != null){
                        if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Mild")){
                            migrainteIntensity = vertigoIntensity;
                        }else if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Moderate")) {
                            migrainteIntensity = vertigoIntensity/2;
                        }else if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Severe")) {
                            migrainteIntensity = vertigoIntensity/3;
                        }
                    }else if(eventModelArrayList.get(i).getPhonophobia() != null || eventModelArrayList.get(i).getPhotophobia() != null
                            || eventModelArrayList.get(i).getVisualSymptoms() != null){
                        migrainteIntensity = 1;
                    }

                    entriesGroup1.add(new BarEntry(j, vertigoIntensity));
                    entriesGroup2.add(new BarEntry(j, migrainteIntensity));
                    dateAsigned = true;
                    break;
                }
            }

            if (!dateAsigned){
                entriesGroup1.add(new BarEntry(j, 0));
                entriesGroup2.add(new BarEntry(j, 0));
            }
        }


        //draw the graph
        BarDataSet set1, set2;
        set1 = new BarDataSet(entriesGroup1, getString(R.string.txtChartLabelVertigo));
        set1.setColors(new int[] { R.color.colorPrimary}, this);
        set2 = new BarDataSet(entriesGroup2, getString(R.string.txtChartLabelMigraine));
        set2.setColors(new int[] { R.color.colorPrimaryDarker}, this);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * daysInMonth);
        //chart.setScaleMinima((float) daysInMonth / 17f, 1f);
        //chart.zoom(-15f,0f,0,0);
        chart.setPinchZoom(false);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();

        //Draw the indicator
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        //Draw the X-Axis and Y-Axis
        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(30);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setValueFormatter(new YValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(10);
        leftAxis.setAxisMinimum(0);

    }

    public ArrayList<EventModel> getEventMonthChartData(){
        String query = String.format("SELECT * FROM EVENT WHERE DATE LIKE '%s'", "%-" + MyMeniereFragment.mCalendarV.getSelectedDate().getMonth() + "-%");
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EventModel> eventModelsArray = new ArrayList<EventModel>();
        eventsInMonth = cursor.getCount();
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                EventModel eventModel = new EventModel();
                eventModel.setVertigoIntensity(cursor.getString(cursor.getColumnIndex("intensity")));
                eventModel.setHeadacheProperties1(cursor.getString(cursor.getColumnIndex("migraine_type1")));
                eventModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
                eventModel.setPhonophobia(cursor.getString(cursor.getColumnIndex("phonophobia")));
                eventModel.setPhotophobia(cursor.getString(cursor.getColumnIndex("photophobia")));
                eventModel.setVisualSymptoms(cursor.getString(cursor.getColumnIndex("visual_symp")));
                eventModelsArray.add(eventModel);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return eventModelsArray;
    }

    public void setMeanData(){

        //Eventos con migraña o vertigo
        String query2 = "select * from event where intensity != 0 or migraine != 0";
        Cursor cursor2 = db.rawQuery(query2, null);
        meanData2.setText(getString(R.string.txtVertigoMigraineEvents).concat(String.valueOf(cursor2.getCount()).concat("/").concat(String.valueOf(eventsInMonth))));
        cursor2.close();

        //Media de duracion y maximo y minimo
        String query3 = "select SUM(duration) suma, count(*) contador, min(duration) minimo, max(duration) maximo from event where duration != 0";
        Cursor cursor3 = db.rawQuery(query3, null);
        if(cursor3.getCount() != 0){
            cursor3.moveToFirst();
            int suma = cursor3.getInt(cursor3.getColumnIndex("suma"));
            int contador = cursor3.getInt(cursor3.getColumnIndex("contador"));
            int minimo = cursor3.getInt(cursor3.getColumnIndex("minimo"));
            int maximo = cursor3.getInt(cursor3.getColumnIndex("maximo"));

            meanData3.setText(getString(R.string.txtDurationMean).concat(String.valueOf(suma/contador)).concat("[max:").concat(String.valueOf(maximo)).concat("-min").concat(String.valueOf(minimo)).concat("]"));
            cursor3.close();
        }

        //Media intensidad y minimo y maximo
        String query4 = "select SUM(intensity) suma, count(*) contador, min(intensity) minimo, max(intensity) maximo from event where intensity != 0";
        Cursor cursor4 = db.rawQuery(query4, null);
        if(cursor4.getCount() != 0){
            cursor4.moveToFirst();
            int suma = cursor4.getInt(cursor4.getColumnIndex("suma"));
            int contador = cursor4.getInt(cursor4.getColumnIndex("contador"));
            int minimo = cursor4.getInt(cursor4.getColumnIndex("minimo"));
            int maximo = cursor4.getInt(cursor4.getColumnIndex("maximo"));

            meanData4.setText(getString(R.string.txtIntensityMean).concat(String.valueOf(suma/contador)).concat("[max:").concat(String.valueOf(maximo)).concat("-min").concat(String.valueOf(minimo)).concat("]"));
            cursor4.close();
        }

        //Media limitation y minimo y maximo
        String query5 = "select SUM(limitation) suma, count(*) contador, min(limitation) minimo, max(limitation) maximo from event where limitation != 0";
        Cursor cursor5 = db.rawQuery(query5, null);
        if(cursor5.getCount() != 0){
            cursor5.moveToFirst();
            int suma = cursor5.getInt(cursor5.getColumnIndex("suma"));
            int contador = cursor5.getInt(cursor5.getColumnIndex("contador"));
            int minimo = cursor5.getInt(cursor5.getColumnIndex("minimo"));
            int maximo = cursor5.getInt(cursor5.getColumnIndex("maximo"));

            meanData5.setText(getString(R.string.txtActivityMean).concat(String.valueOf(suma/contador)).concat("[max:").concat(String.valueOf(maximo)).concat("-min").concat(String.valueOf(minimo)).concat("]"));
            cursor5.close();
        }

        //Media stress y minimo y maximo
        String query6 = "select SUM(stress) suma, count(*) contador, min(stress) minimo, max(stress) maximo from event where stress != 0";
        Cursor cursor6 = db.rawQuery(query6, null);
        if(cursor6.getCount() != 0){
            cursor6.moveToFirst();
            int suma = cursor6.getInt(cursor6.getColumnIndex("suma"));
            int contador = cursor6.getInt(cursor6.getColumnIndex("contador"));
            int minimo = cursor6.getInt(cursor6.getColumnIndex("minimo"));
            int maximo = cursor6.getInt(cursor6.getColumnIndex("maximo"));

            meanData6.setText(getString(R.string.txtStressMean).concat(String.valueOf(suma/contador)).concat("[max:").concat(String.valueOf(maximo)).concat("-min").concat(String.valueOf(minimo)).concat("]"));
            cursor6.close();
        }
    }

    public void setTriggers(){
        String query = "select distinct(triggers_climate) name, count(*) contador, 'climate' parent\n" +
                "from event\n" +
                "where triggers_climate not null\n" +
                "group by triggers_climate\n" +
                "union\n" +
                "select distinct(triggers_sleep) name, count(*) contador, 'sleep' parent\n" +
                "from event\n" +
                "where triggers_sleep not null\n" +
                "group by triggers_sleep\n" +
                "union\n" +
                "select distinct(triggers_phisic) name, count(*) contador, 'phisic' parent\n" +
                "from event\n" +
                "where triggers_phisic not null\n" +
                "group by triggers_phisic\n" +
                "union\n" +
                "select distinct(triggers_excesses) name, count(*) contador, 'excesses' parent\n" +
                "from event\n" +
                "where triggers_excesses not null\n" +
                "group by triggers_excesses\n" +
                "union\n" +
                "select distinct(menstruation) name, count(*) contador, 'menstruation' parent\n" +
                "from event\n" +
                "where menstruation = 1\n" +
                "group by menstruation";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            Map arrayTriggers = new HashMap();
            while(!cursor.isAfterLast()) {
                arrayTriggers.put(cursor.getString(cursor.getColumnIndex("name")),cursor.getInt(cursor.getColumnIndex("contador")));
                cursor.moveToNext();
            }
            cursor.close();

            //ordenar array
            arrayTriggers = Utils.sortByComparator(arrayTriggers,false);

            List<String> arrayTriggersNames = new ArrayList<String>(arrayTriggers.keySet());
            List<String> arrayTriggersValues = new ArrayList<String>(arrayTriggers.values());

            trigger1.setText("1. " + arrayTriggersNames.get(0) + " (" + String.valueOf(arrayTriggersValues.get(0)) + ")");
            trigger2.setText("2. " + arrayTriggersNames.get(1) + " (" + String.valueOf(arrayTriggersValues.get(1)) + ")");
            trigger3.setText("3. " + arrayTriggersNames.get(2) + " (" + String.valueOf(arrayTriggersValues.get(2)) + ")");
            trigger4.setText("4. " + arrayTriggersNames.get(3) + " (" + String.valueOf(arrayTriggersValues.get(3)) + ")");

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}
