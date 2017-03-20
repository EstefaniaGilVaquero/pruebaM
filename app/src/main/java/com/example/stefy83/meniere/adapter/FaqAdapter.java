package com.example.stefy83.meniere.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stefy83.meniere.R;

import java.util.ArrayList;

/**
 * Created by stefy83 on 20/03/2017.
 */

public class FaqAdapter extends RecyclerView.Adapter  {

    private ArrayList<String> listaClasif;
    private Activity activity;

    public FaqAdapter(ArrayList<String> listaClasif, Activity activity) {
        this.listaClasif = listaClasif;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemLayoutView;
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_clasif, parent, false);

        // create ViewHolder
        return new FaqAdapter.ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // Get data from your itemsData at this position
        try {
            final FaqAdapter.ViewHolder viewHolder = (FaqAdapter.ViewHolder) holder;
            final Clasificacion clasificacion = listaClasif.get(position);

            // Replace the contents of the view with that itemsData
            if (clasificacion.getUrl() != null) {
                BitManage.loadBitmap(clasificacion.getUrl(), viewHolder.ivPerfil, activity);
            }else {
                viewHolder.ivPerfil.setImageResource(R.drawable.baliza);
            }

            if (clasificacion.getNombreUsuario().isEmpty()) {
                viewHolder.tvNombre.setVisibility(View.GONE);
            }else {
                viewHolder.tvNombre.setVisibility(View.VISIBLE);
                viewHolder.tvNombre.setText(clasificacion.getNombreUsuario());
            }
            viewHolder.tvClub.setText(clasificacion.getClub());
            String punt = String.valueOf(clasificacion.getPuntuacion());
            viewHolder.tvPunt.setText(punt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaClasif.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFaq, tvClub, tvPunt;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            try {
                tvFaq = (TextView) itemLayoutView.findViewById(R.id.tvNombre);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
