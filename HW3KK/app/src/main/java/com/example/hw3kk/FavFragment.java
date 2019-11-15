package com.example.hw3kk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavFragment extends Fragment {
    private RecyclerView favouritesRv;
    private FavAdapter favouritesAdapter;
    private RecyclerView.LayoutManager favouritesLayoutManager;
    private ArrayList<BreedClass> favouritesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        favouritesList = load("favourites", favouritesList);
        view = buildRecyclerView(view);

        return view;
    }

    private ArrayList<BreedClass> load(String key, ArrayList<BreedClass> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);

        Type menuJson = new TypeToken<ArrayList<BreedClass>>() {
        }.getType();

        list = gson.fromJson(json, menuJson);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    public View buildRecyclerView(View view) {
        favouritesRv = view.findViewById(R.id.favourites_recyclerView);
        favouritesRv.setHasFixedSize(true);
        favouritesLayoutManager = new LinearLayoutManager(view.getContext());
        favouritesAdapter = new FavAdapter(view.getContext(), favouritesList);

        favouritesRv.setLayoutManager(favouritesLayoutManager);
        favouritesRv.setAdapter(favouritesAdapter);

        favouritesAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
        return view;
    }

    private void delete(String key, ArrayList<BreedClass> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("favourites", gson.toJson(list));
        editor.apply();
    }

    public void removeItem(int position) {
        favouritesList.remove(position);
        favouritesAdapter.notifyItemRemoved(position);
        delete("favourites", favouritesList);
    }
}
