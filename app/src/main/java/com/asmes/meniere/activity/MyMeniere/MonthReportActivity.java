package com.asmes.meniere.activity.MyMeniere;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.asmes.meniere.R;
import com.asmes.meniere.adapter.DatabaseHelper;
import com.asmes.meniere.models.EventModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MonthReportActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    boolean activitySwitchFlag = false;
    protected BarChart chart;
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

        chart = findViewById(R.id.barChart);



        setData();

    }

    private void setData() {

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

        ArrayList<EventModel> eventModelArrayList = getEventMonthStatistics();

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
