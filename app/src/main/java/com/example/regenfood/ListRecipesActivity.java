package com.example.regenfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.regenfood.Database.User;
import com.example.regenfood.json.HitsJsonModel;
import com.example.regenfood.json.JsonModel;
import com.example.regenfood.json.RecipeJsonModel;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class ListRecipesActivity extends AppCompatActivity {
    private static final String TAG = "ListRecipesActivity";
    private String RecipeSearch;
    private List<String> Images;
    private List<String> Labels;
    public Context thisContext;
    private JsonModel Recipies;
    private String url;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.thisContext = this;
        Images = new ArrayList<>();
        Labels = new ArrayList<>();
        setContentView(R.layout.activity_list_recipes);
        RecipeSearch = "Chicken";
        Bundle b = getIntent().getExtras();
        url = "https://api.edamam.com/search?app_id=5476cbdc&app_key=173291640885e0b1116a2f18f1adaa74&q=strawberries";
        assert b != null;
        if (b.getString("url") != null) {
            url = b.getString("url");
            Log.d(TAG, "onResume: " + url);
            callApi(url);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ImageButton backbutton = findViewById(R.id.backbutton);
        ImageButton searchbutton = findViewById(R.id.searchbutton);
        ImageButton profilebutton = findViewById(R.id.profilebutton);
        backbutton.setOnClickListener(view -> {
            MenuOptions(1);
        });
        searchbutton.setOnClickListener(view -> {
            MenuOptions(2);
        });

        profilebutton.setOnClickListener(view -> {
            MenuOptions(3);
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void fillRecycler() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerRecipes);
        ListRecipeAdapter adapter = new ListRecipeAdapter(thisContext, Images, Labels, Recipies, "https://api.edamam.com/search?app_id=5476cbdc&app_key=173291640885e0b1116a2f18f1adaa74&r=");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(thisContext));
    }

    private void callApi(String url) {
        Log.d(TAG, "callApi: pre-Api Call");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        JsonModel model = new Gson().fromJson(response, JsonModel.class);
                        if (model.getCount() != 0) {
                            Recipies = model;
                            RecipeSearch = model.getQ();
                            for (HitsJsonModel i : model.getHits()) {
                                Images.add(i.getRecipe().getImage());
                                Labels.add(i.getRecipe().getLabel());
                            }
                            TextView q = findViewById(R.id.imageView1);
                            q.setText(RecipeSearch);

                            AppCompatImageView image = findViewById(R.id.loader);      //image loader
                            image.setVisibility(View.GONE);

                            fillRecycler();
                        } else {
                            Toast.makeText(ListRecipesActivity.this, "We found 0 results...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onApiCall: line94 ERROR");
                Toast.makeText(ListRecipesActivity.this, "Try again...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);
    }

    public void MenuOptions(int i) {
        switch (i) {

            case 1:
                finish();
                break;

            case 2:
                Intent intentSearch = new Intent(ListRecipesActivity.this, CategoriesActivity.class);
                user = (User) getIntent().getSerializableExtra("User");
                intentSearch.putExtra("User", user);
                startActivity(intentSearch);
                break;


            case 3:
                Intent intentProfile = new Intent(ListRecipesActivity.this, ProfileActivity.class);
                user = (User) getIntent().getSerializableExtra("User");
                intentProfile.putExtra("User", user);
                startActivity(intentProfile);
                break;

        }
    }

    public void showRecipe(String recipe, int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("url", recipe);
        user = (User) getIntent().getSerializableExtra("User");
        intent.putExtra("User", user);

        //String r =  Recipies.getHits().get(position).getRecipe().toString();
        //intent.putExtra("recipe",  r);
        this.startActivity(intent);
    }
}
