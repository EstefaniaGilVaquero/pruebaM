package com.example.stefy83.meniere.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stefy83.meniere.R;
import com.example.stefy83.meniere.models.HearingDiaryModel;

import java.util.ArrayList;

/**
 * Created by stefy83 on 21/06/2017.
 */

public class HearingDiaryAdapter extends RecyclerView.Adapter {

    private Activity activity;
    ArrayList<HearingDiaryModel> arrayHearingEntries;


    public HearingDiaryAdapter(ArrayList<HearingDiaryModel> arrayHearingEntries, Activity activity) {
        this.arrayHearingEntries = arrayHearingEntries;

        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HearingDiaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemLayoutView;
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_audio, parent, false);

        // create ViewHolder
        return new HearingDiaryAdapter.ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {
            final HearingDiaryAdapter.ViewHolder viewHolder = (HearingDiaryAdapter.ViewHolder) holder;
            viewHolder.txtDate.setText(arrayHearingEntries.get(position).date);
            viewHolder.txtLeft05.setText(arrayHearingEntries.get(position).left05_a);
            viewHolder.txtLeft05.setText(arrayHearingEntries.get(position).left05_b);
            viewHolder.txtLeft1.setText(arrayHearingEntries.get(position).left1_a);
            viewHolder.txtLeft1.setText(arrayHearingEntries.get(position).left1_b);
            viewHolder.txtLeft2.setText(arrayHearingEntries.get(position).left2_a);
            viewHolder.txtLeft2.setText(arrayHearingEntries.get(position).left2_b);
            viewHolder.txtLeft4.setText(arrayHearingEntries.get(position).left4_a);
            viewHolder.txtLeft4.setText(arrayHearingEntries.get(position).left4_b);
            viewHolder.txtRigth05.setText(arrayHearingEntries.get(position).rigth05_a);
            viewHolder.txtRigth05.setText(arrayHearingEntries.get(position).rigth05_b);
            viewHolder.txtRigth1.setText(arrayHearingEntries.get(position).rigth1_a);
            viewHolder.txtRigth1.setText(arrayHearingEntries.get(position).rigth1_b);
            viewHolder.txtRigth2.setText(arrayHearingEntries.get(position).rigth2_a);
            viewHolder.txtRigth2.setText(arrayHearingEntries.get(position).rigth2_b);
            viewHolder.txtRigth4.setText(arrayHearingEntries.get(position).rigth4_a);
            viewHolder.txtRigth4.setText(arrayHearingEntries.get(position).rigth4_b);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayHearingEntries.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate, txtLeft05, txtLeft1, txtLeft2, txtLeft4, txtRigth05, txtRigth1, txtRigth2, txtRigth4;
        public CardView cvRow;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            try {
                txtDate = (TextView) itemLayoutView.findViewById(R.id.tvAudioDate);
                txtLeft05 = (TextView) itemLayoutView.findViewById(R.id.tvLeft05);
                txtLeft1 = (TextView) itemLayoutView.findViewById(R.id.tvLeft1);
                txtLeft2 = (TextView) itemLayoutView.findViewById(R.id.tvLeft2);
                txtLeft4 = (TextView) itemLayoutView.findViewById(R.id.tvLeft4);
                txtRigth05 = (TextView) itemLayoutView.findViewById(R.id.tvRigth05);
                txtRigth1 = (TextView) itemLayoutView.findViewById(R.id.tvRigth1);
                txtRigth2 = (TextView) itemLayoutView.findViewById(R.id.tvRigth2);
                txtRigth4 = (TextView) itemLayoutView.findViewById(R.id.tvRigth4);
                cvRow = (CardView) itemLayoutView.findViewById(R.id.cvRow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
