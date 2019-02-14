package com.asmes.meniere.adapter;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmes.meniere.R;

/**
 * Created by stefy83 on 20/03/2017.
 */

public class FaqAdapter extends RecyclerView.Adapter  {

    private Activity activity;
    String[] arrayQuestions;
    String[] arrayAnswers;
    private boolean isShowing;


    public FaqAdapter(String[] arrayQuestions, String[] arrayAnswers, Activity activity) {
        this.arrayQuestions = arrayQuestions;
        this.arrayAnswers = arrayAnswers;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemLayoutView;
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_faq, parent, false);

        // create ViewHolder
        return new FaqAdapter.ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {
            final FaqAdapter.ViewHolder viewHolder = (FaqAdapter.ViewHolder) holder;

            viewHolder.txtQuestion.setText(arrayQuestions[position]);
            viewHolder.txtAnswer.setText(arrayAnswers[position]);
            //Onclicklistener

            viewHolder.constraintLayoutDesplegable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewHolder.txtAnswer.getVisibility()==view.GONE) {
                        viewHolder.txtAnswer.setVisibility(view.VISIBLE);
                        viewHolder.txtAnswer.setText(arrayAnswers[position]);
                        viewHolder.arrow.setImageResource(R.drawable.ic_arrow_up);
                    } else {
                        viewHolder.txtAnswer.setVisibility(view.GONE);
                        viewHolder.arrow.setImageResource(R.drawable.ic_arrow_down);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayQuestions.length;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtQuestion, txtAnswer;
        public ImageView arrow;
        public CardView cvRow;
        public ConstraintLayout constraintLayoutDesplegable;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            try {
                txtQuestion = itemLayoutView.findViewById(R.id.txtQuestion);
                txtAnswer = itemLayoutView.findViewById(R.id.txtAnswer);
                cvRow = itemLayoutView.findViewById(R.id.cvRow);
                arrow = itemLayoutView.findViewById(R.id.imageView);
                constraintLayoutDesplegable = itemLayoutView.findViewById(R.id.constraintLayoutDesplegable);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}
