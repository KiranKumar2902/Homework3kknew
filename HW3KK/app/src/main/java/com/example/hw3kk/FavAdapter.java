package com.example.hw3kk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavouritesViewHolder> {
    private ArrayList<BreedClass> favouritesList;
    private Context favouritesContext;
    private OnItemClickListener favouritesListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        favouritesListener = listener;
    }

    public FavAdapter(Context context, ArrayList<BreedClass> list) {
        favouritesList = list;
        favouritesContext = context;

    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder {
        public TextView favouritesName;
        public Button deleteFavouritesItem;

        public FavouritesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            favouritesName = itemView.findViewById(R.id.favourite_name);
            deleteFavouritesItem = itemView.findViewById(R.id.favourite_delete);

            deleteFavouritesItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }


    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_item, parent, false);
        return new FavouritesViewHolder(v, favouritesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        BreedClass favouritesItem = favouritesList.get(position);
        String favouriteName = favouritesItem.getName();


        holder.favouritesName.setText(favouriteName);

    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

}