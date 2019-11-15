package com.example.hw3kk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private ImageClass catImage;
    private BreedClass clickedCat;
    private ArrayList<ImageClass> catImageAll;

    private RecyclerView.LayoutManager statsLayoutManager;
    private ArrayList<CatInfo> statsList;
    private RecyclerView statsRecyclerView;
    private CatInfoAdapter statsAdapter;

    private String catImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        clickedCat = (BreedClass) intent.getSerializableExtra("clickedCat");
        loadUI();

        //Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        catImageURL = "https://api.thecatapi.com/v1/images/search?breed_id=" + clickedCat.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, catImageURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type catImageType = new TypeToken<ArrayList<ImageClass>>() {
                }.getType();
                catImageAll = gson.fromJson(response, catImageType);
                catImage = catImageAll.get(0);
                loadImage();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> apiKey = new HashMap<String, String>();
                apiKey.put("x-api-key", "99c5c080-ad1d-481e-b5c3-f5e3572732a0");
                return apiKey;
            }
        };

        queue.add(stringRequest);
    }

    public void loadUI() {
        TextView catNameView = findViewById(R.id.name_detail);
        TextView temperamentView = findViewById(R.id.temperament_detail);
        TextView catDescriptionView = findViewById(R.id.description_detail);

        catNameView.setText(clickedCat.getName());
        temperamentView.setText(clickedCat.getTemperament());
        catDescriptionView.setText(clickedCat.getDescription());

        statsRecyclerView = findViewById(R.id.stats_recyclerView);
        statsRecyclerView.setHasFixedSize(true);
        statsLayoutManager = new LinearLayoutManager(this);

        statsList = addStats(clickedCat);
        statsAdapter = new CatInfoAdapter(statsList);

        statsRecyclerView.setLayoutManager(statsLayoutManager);
        statsRecyclerView.setAdapter(statsAdapter);

        ImageButton favButton = findViewById(R.id.favoriteButton_detail);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
    }

    public void loadImage() {
        ImageView catImageView = findViewById(R.id.image_detail);
        Picasso.get().load(catImage.getUrl()).fit().centerInside().into(catImageView); //load from API instead?
    }

    private void add() {
        ArrayList<BreedClass> favouritesList;
        BreedClass favourite = clickedCat;

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("favourites", null);
        Gson gson = new Gson();

        Type favouritesJson = new TypeToken<ArrayList<BreedClass>>() {
        }.getType();
        favouritesList = gson.fromJson(json, favouritesJson);

        if (favouritesList == null) {
            favouritesList = new ArrayList<>();
        }

        favouritesList.add(favourite);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(favouritesList);
        editor.putString("favourites", json);
        editor.apply();
    }

    public ArrayList<CatInfo> addStats(BreedClass cat) {
        ArrayList<CatInfo> catStats = new ArrayList<>();

        catStats.add(new CatInfo("Origin", cat.getOrigin()));
        catStats.add(new CatInfo("Life Span (years)", cat.getLife_span()));
        catStats.add(new CatInfo("Weight (kg)", cat.getWeight().getMetric()));
        catStats.add(new CatInfo("Dog Friendly (out of 5)", cat.getDog_friendly()));
        catStats.add(new CatInfo("Wikipedia URL", cat.getWikipedia_url()));

        return catStats;
    }

}
