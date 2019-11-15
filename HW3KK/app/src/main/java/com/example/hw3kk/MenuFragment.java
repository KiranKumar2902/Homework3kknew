package com.example.hw3kk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    public static String catBreedURL = "catBreedURL";

    private RecyclerView catRv;
    private MenuAdapter catAdapter;
    private ArrayList<BreedClass> catArrayList;

    private String storeResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        catRv = view.findViewById(R.id.cat_recyclerView);
        catRv.setHasFixedSize(true);
        catRv.setLayoutManager(new LinearLayoutManager(getContext()));

        setHasOptionsMenu(true);

        if (catArrayList == null) {

            getCatBreeds();

        } else {

            parseResponse();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        menu.clear();


        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);


        android.view.MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        BreedClass clickedItem = catArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("clickedCat", clickedItem);

        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }

    public void getCatBreeds() {
        RequestQueue queue = Volley.newRequestQueue(this.getContext()); //rather than this.getActivity() - which may have been null
        catBreedURL = "https://api.thecatapi.com/v1/breeds";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, catBreedURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type menuJson = new TypeToken<ArrayList<BreedClass>>() {
                }.getType();
                catArrayList = gson.fromJson(response, menuJson);
                parseResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-key", getString(R.string.x_api_key));
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    private void parseResponse() {
        catAdapter = new MenuAdapter(getActivity(), catArrayList);
        catRv.setAdapter(catAdapter);
        catAdapter.setOnItemClickListener(MenuFragment.this);
    }
}
