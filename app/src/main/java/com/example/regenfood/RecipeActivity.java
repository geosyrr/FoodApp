package com.example.regenfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.regenfood.Database.User;
import com.example.regenfood.Database.UserDao;
import com.example.regenfood.Database.UserDataBase;
import com.example.regenfood.json.HitsJsonModel;
import com.example.regenfood.json.RecipeJsonModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class RecipeActivity extends AppCompatActivity {
    private static final String TAG = "RecipeActivity";
    RecyclerView recyclerView2;
    RecyclerView.Adapter adapter2;
    RecyclerView.LayoutManager layoutManager2;
    ArrayList<String> ingredients;
    ArrayList<String> tags;
    Context mContext = this;
    RecipeJsonModel model;
    ToggleButton favoriteButton;
    String shareUrl;
    User user;
    ArrayList<String> favorites;
    private UserDao userDao;
    private UserDataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        updateFromDB();
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
        String url = "";
        Bundle b = getIntent().getExtras();
        assert b != null;
        if (b.getString("url") != null) {
            url = b.getString("url");
            Log.d(TAG, "onResume: " + url);
            callApi(url);
        }
    }

    private void fillRecyclers() {

        Log.d(TAG, "fillRecyclers: " + ingredients.size());
        RecyclerView recyclerView = findViewById(R.id.ingredientsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecipeAdapter adapter = new RecipeAdapter(ingredients);
        recyclerView.setAdapter(adapter);

        recyclerView2 = findViewById(R.id.buttonRecycler);
        recyclerView2.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        adapter2 = new ButtonsRecyclerAdapter(tags);
        recyclerView2.setAdapter(adapter2);


        Button b = findViewById(R.id.enjoy);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareUrl));
                startActivity(browserIntent);
            }
        });

        favoriteButton = (ToggleButton) findViewById(R.id.likebutton);
        favorites = new ArrayList<>();
        updateFromDB();
        //CHECK IN DATABASE IF ITS LIKED:
        initFavorites(user.getFavourites());
        if (favorites.contains(model.getUri())) {
            favoriteButton.setChecked(true);
            favoriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_red_24dp));
        } else {
            favoriteButton.setChecked(false);
            favoriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_24dp));
        }


        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFromDB();
                if (!favorites.contains(model.getUri())) {
                    favoriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_red_24dp));
                    String favs = user.getFavourites();
                    favs += "," + model.getUri() + "," + model.getLabel();
                    user.setFavourites(favs);
                    initFavorites(favs);
                    removeEmpty();
                } else {
                    favoriteButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_24dp));
                    favorites.clear();
                    initFavorites(user.getFavourites());
                    removeEmpty();
                    String test = makeFavorite().replaceAll(model.getUri() + "," + model.getLabel(), "");
                    user.setFavourites(test);
                    initFavorites(user.getFavourites());
                }
                removeEmpty();
                userDao.update(user);
            }
        });
        ImageButton share = findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                TextView t = findViewById(R.id.description);
                String shareBody = "Share this amazing recipe: '" + t.getText() + "'\nas shown here: " + shareUrl;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "RegenFood: " + t.getText());
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share it!"));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateFromDB();
    }

    private void callApi(String url) {

        //OPTIMIZATION
        String recipe = "";
        Bundle b = getIntent().getExtras();
        assert b != null;
        if (b.getString("recipe") != null) {
            String h1 = b.getString("recipe");
            model = new Gson().fromJson(h1, RecipeJsonModel.class);
            //Display Label
            TextView label = findViewById(R.id.description);
            label.setText(model.getLabel());
            //Display Image
            AppCompatImageView image = findViewById(R.id.imageView);
            Glide.with(mContext).asBitmap().load(model.getImage()).into(image);
            //Fill Ingredients and tags
            ingredients = new ArrayList<>();
            ingredients.addAll(model.getIngredientLines());
            tags = new ArrayList<>();
            tags.addAll(model.getDietLabels());
            tags.addAll(model.getHealthLabels());
            tags.addAll(model.getCautions());
            //Fill Calories
            TextView time = findViewById(R.id.time);
            time.setText(countTime(Math.round(model.getTotalTime())));
            shareUrl = model.getUrl();
            TextView calories = findViewById(R.id.calories);
            calories.setText("Calories: " + Math.round(model.getCalories()) / (int) model.getYield() + " per serving");
            //Now Fill Recycler Views
            fillRecyclers();
            return;
        }

        Log.d(TAG, "callApi: pre-Api Call");
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        // Convert Response to GSON
                        model = new Gson().fromJson(response.substring(1, response.length() - 1), RecipeJsonModel.class);
                        //Display Label
                        TextView label = findViewById(R.id.description);
                        label.setText(model.getLabel());
                        //Display Image
                        AppCompatImageView image = findViewById(R.id.imageView);
                        Glide.with(mContext).asBitmap().load(model.getImage()).into(image);
                        //Fill Ingredients and tags
                        ingredients = new ArrayList<>();
                        ingredients.addAll(model.getIngredientLines());
                        tags = new ArrayList<>();
                        tags.addAll(model.getDietLabels());
                        tags.addAll(model.getHealthLabels());
                        tags.addAll(model.getCautions());
                        //Fill Calories
                        TextView time = findViewById(R.id.time);
                        time.setText(countTime(Math.round(model.getTotalTime())));
                        shareUrl = model.getUrl();
                        TextView calories = findViewById(R.id.calories);
                        calories.setText("Calories: " + Math.round(model.getCalories() / model.getYield()) + " x " + String.valueOf((int) model.getYield()));
                        TextView servings = findViewById(R.id.titlecalories);
                        servings.setText(getString(R.string.time_difficulty) + " (" + String.valueOf((int) model.getYield()) + " servings)");
                        //Now Fill Recycler Views
                        fillRecyclers();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onApiCall: line154 ERROR");
            }
        });
        queue.add(stringRequest);
    }

    public String countTime(Long time) {
        String total;
        if (time > 60) {
            total = time / 60 + "h:" + time % 60 + "m ";
        } else {
            total = String.valueOf(time) + "m ";
        }
        return total;
    }

    public void MenuOptions(int i) {
        switch (i) {
            case 1:
                finish();
                break;
            case 2:
                Intent intentSearch = new Intent(RecipeActivity.this, CategoriesActivity.class);
                intentSearch.putExtra("User", user);
                startActivity(intentSearch);
                break;
            case 3:
                Intent intentProfile = new Intent(RecipeActivity.this, ProfileActivity.class);
                intentProfile.putExtra("User", user);
                startActivity(intentProfile);
                break;

        }
    }

    public void initFavorites(String i) {
        String[] list = i.split(",");
        for (int j = 0; j < list.length; j++) {
            favorites.add(list[j]);
        }
    }

    public String makeFavorite() {
        String result = "";
        for (String i : favorites) {
            result += i + ",";
        }
        return result;
    }


    public void updateFromDB() {
        //UPDATE FROM DATABASE
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                    .allowMainThreadQueries()
                    .build();
        }
        userDao = dataBase.getUserDao();
        user = (User) getIntent().getSerializableExtra("User");
        String userName = user.getUserName();
        String password = user.getPassword();
        user = userDao.getUser(userName, password);
    }


    public void removeEmpty() {
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).equals("")) {
                favorites.remove(i);
            }
        }
    }


}
