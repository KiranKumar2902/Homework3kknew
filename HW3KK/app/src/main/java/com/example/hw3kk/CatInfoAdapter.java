package com.example.hw3kk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatInfoAdapter extends RecyclerView.Adapter<CatInfoAdapter.StatsViewHolder> {
    private ArrayList<CatInfo> catStat;

    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        public TextView statsValue;
        public TextView statsKey;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            statsValue = itemView.findViewById(R.id.value_stats);
            statsKey = itemView.findViewById(R.id.key_stats);
        }
    }

    public CatInfoAdapter(ArrayList<CatInfo> stat) {
        catStat = stat;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_catinfo, parent, false);
        StatsViewHolder statsViewHolder = new StatsViewHolder(v);
        return statsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        CatInfo currentItem = catStat.get(position);

        holder.statsValue.setText(currentItem.getValue());
        holder.statsKey.setText(currentItem.getKey());
    }

    @Override
    public int getItemCount() {
        return catStat.size();
    }
}
