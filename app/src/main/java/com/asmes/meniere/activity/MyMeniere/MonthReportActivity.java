package com.asmes.meniere.activity.MyMeniere;

import android.app.usage.UsageEvents;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.asmes.meniere.R;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.asmes.meniere.utils.DayAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MonthReportActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    boolean activitySwitchFlag = false;
    protected BarChart mChart;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

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

        mChart = findViewById(R.id.barChart);


        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setAxisMinimum(1f);
        xAxis.setCenterAxisLabels(true);
        //xAxis.setValueFormatter(xAxisFormatter);

        //Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(10, false);
        leftAxis.setGranularity(1f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        setData();

    }

    private void setData() {

        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.2f;

        // Get the number of days in that month
        int iYear = MyMeniereFragment.mCalendarV.getSelectedDate().getYear();
        int iMonth = MyMeniereFragment.mCalendarV.getSelectedDate().getMonth(); // 1 (months begin with 0)
        int iDay = MyMeniereFragment.mCalendarV.getSelectedDate().getDay();

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

        ArrayList xVals = new ArrayList();

        for(int i = 0; i < daysInMonth; i++) {
            xVals.add(String.valueOf(i));
        }

        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();

        ArrayList<EventModel> eventModelArrayList = getEventMonthStatistics();
        // fill the lists
        for(int j = 0; j < daysInMonth; j++ ){

            for (int i = 0; i < eventModelArrayList.size(); i++ ){
                Integer dayOfMonth = Integer.valueOf(eventModelArrayList.get(i).getDate().split("-")[2].split("\\}")[0]);
                if(dayOfMonth == j) {

                    int migrainteIntensity=0;
                    if (eventModelArrayList.get(i).getHeadacheProperties1() != null){
                        if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Mild")){
                            migrainteIntensity = 1;
                        }else if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Moderate")) {
                            migrainteIntensity = 2;
                        }else if(eventModelArrayList.get(i).getHeadacheProperties1().equalsIgnoreCase("Severe")) {
                            migrainteIntensity = 3;
                        }
                    }

                    entriesGroup1.add(new BarEntry(j, Integer.valueOf(eventModelArrayList.get(i).getVertigoIntensity())));
                    entriesGroup2.add(new BarEntry(j, migrainteIntensity));
                    break;
                }
            }

            if (entriesGroup1.size() < j){
                entriesGroup1.add(new BarEntry(j, 0));
                entriesGroup2.add(new BarEntry(j, 0));
            }
        }


        BarDataSet set1, set2;
        set1 = new BarDataSet(entriesGroup1, "Vertigo");
        set1.setColors(new int[] { R.color.colorPrimary}, this);
        set2 = new BarDataSet(entriesGroup2, "Migraine");
        set2.setColors(new int[] { R.color.colorPrimaryDarker}, this);
        BarData data = new BarData(set1, set2);
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        //mChart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        mChart.groupBars(1, groupSpace, barSpace);
        mChart.getData().setHighlightEnabled(false);
        mChart.invalidate();

    }

    public ArrayList<EventModel> getEventMonthStatistics(){
        String query = String.format("SELECT * FROM EVENT WHERE DATE LIKE '%s'", "%-" + MyMeniereFragment.mCalendarV.getSelectedDate().getMonth() + "-%");
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<EventModel> eventModelsArray = new ArrayList<EventModel>();
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                EventModel eventModel = new EventModel();
                eventModel.setVertigoIntensity(cursor.getString(cursor.getColumnIndex("intensity")));
                eventModel.setHeadacheProperties1(cursor.getString(cursor.getColumnIndex("migraine_type1")));
                eventModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
                eventModelsArray.add(eventModel);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return eventModelsArray;
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
