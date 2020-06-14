package com.example.regenfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.regenfood.Database.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    private static final String TAG = "CategoriesActivity";
    private ArrayList<String> categories1;
    private ArrayList<String> categories2;
    private ArrayList<String> mImageUrl1;
    private ArrayList<String> mImageUrl2;
    private ArrayList<String> clicked;
    private String q;
    String diet = "", health = "", calories = "";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        diet = "";
        health = "";
        calories = "";
        clicked = new ArrayList<>();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        View v = findViewById(R.id.search);
        v.setEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SearchView searchView = (SearchView) findViewById(R.id.search);
        initImages(1);
        ImageButton backbutton = findViewById(R.id.backbutton);
        ImageButton searchbutton = findViewById(R.id.searchbutton);
        searchbutton.setColorFilter(Color.rgb(0, 0, 0));
        ImageButton profilebutton = findViewById(R.id.profilebutton);
        backbutton.setOnClickListener(view -> {
            MenuOptions(1);
        });
        profilebutton.setOnClickListener(view -> {
            MenuOptions(3);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String calories = "";
                EditText minCalories =findViewById(R.id.caloriesMin);
                EditText maxCalories = findViewById(R.id.caloriesMax);
                boolean found = false;
                if ((minCalories.getText() != null || maxCalories.getText() != null)) {
                    if(!minCalories.getText().toString().trim().equals("") || !maxCalories.getText().toString().trim().equals("") ){
                        calories = "&calories=";
                        found = true;
                    }
                }
                if (found) {
                    if(!minCalories.getText().toString().trim().equals("") && !maxCalories.getText().toString().trim().equals(""))
                    if (Integer.parseInt(minCalories.getText().toString()) >= Integer.parseInt(maxCalories.getText().toString())) {
                        Toast.makeText(CategoriesActivity.this, "Minimum calories cannot be greater than Maximum", Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        calories += minCalories.getText().toString() + "%2D" + maxCalories.getText().toString();
                    }
                    if ((minCalories.getText().toString().trim().equals("")) && (!maxCalories.getText().toString().trim().equals(""))) {
                        calories += String.valueOf(maxCalories.getText().toString());
                    }
                    if ((maxCalories.getText().toString().trim().equals("")) && (!minCalories.getText().toString().trim().equals(""))) {
                        calories += minCalories.getText().toString() + "%2B";
                    }
                }

                //q = "https://api.edamam.com/search?app_id=5476cbdc&app_key=173291640885e0b1116a2f18f1adaa74&q=" + query + getFilters() + calories;
                q = "https://api.edamam.com/search?app_id=ccbcf7ca&app_key=e14438ed975fafb38752cbdd559e3d77&q=" + query + getFilters() + calories;
                Intent i = new Intent(CategoriesActivity.this, ListRecipesActivity.class);
                i.putExtra("url", q);
                user = (User) getIntent().getSerializableExtra("User");
                i.putExtra("User", user);
                CategoriesActivity.this.startActivity(i);
                View v = findViewById(R.id.search);
                v.setEnabled(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initImages(int type) {
        mImageUrl1 = new ArrayList<>();
        mImageUrl2 = new ArrayList<>();
        categories1 = new ArrayList<>();
        categories2 = new ArrayList<>();
        Log.d(TAG, "initImages: preparing images.");
        //Diet
        categories1.add("balanced");
        categories1.add("high-fiber");
        categories1.add("high-protein");
        categories1.add("low-carb");
        categories1.add("low-fat");
        categories1.add("low-sodium");
        for (int i = 0; i < 6; i++)
            mImageUrl1.add("https://img.icons8.com/officel/40/000000/apple.png");
        //Health
        categories2.add("alcohol-free");
        categories2.add("immuno-supportive");
        categories2.add("celery-free");
        categories2.add("crustacean-free");
        categories2.add("dairy-free");
        categories2.add("egg-free");
        categories2.add("fish-free");
        categories2.add("fodmap-free");
        categories2.add("gluten-free");
        categories2.add("keto-friendly");
        categories2.add("kidney-friendly");
        categories2.add("kosher");
        categories2.add("low-potassium");
        categories2.add("lupine-free");
        categories2.add("mustard-free");
        categories2.add("low-fat-abs");
        categories2.add("No-oil-added");
        categories2.add("low-sugar");
        categories2.add("paleo");
        categories2.add("peanut-free");
        categories2.add("pecatarian");
        categories2.add("pork-free");
        categories2.add("red-meat-free");
        categories2.add("sesame-free");
        categories2.add("shellfish-free");
        categories2.add("soy-free");
        categories2.add("sugar-conscious");
        categories2.add("tree-nut-free");
        categories2.add("vegan");
        categories2.add("vegetarian");
        categories2.add("wheat-free");
        for (int i = 0; i < 31; i++) {
            mImageUrl2.add("https://img.icons8.com/office/40/000000/paleo-diet.png");
        }
        Log.d(TAG, "initImages: added images and texts.");
        this.initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: initialize.");
        RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        CategoriesAdapter adapter1 = new CategoriesAdapter(categories1, mImageUrl1, this);
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Log.d(TAG, "initRecyclerView: initialize.");
        recyclerView = findViewById(R.id.recyclerview2);
        CategoriesAdapter adapter2 = new CategoriesAdapter(categories2, mImageUrl2, this);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void MenuOptions(int i) {
        switch (i) {
            case 1:
                finish();
                break;
            case 2:
                Intent intentSearch = new Intent(CategoriesActivity.this, CategoriesActivity.class);
                user = (User) getIntent().getSerializableExtra("User");
                intentSearch.putExtra("User", user);
                startActivity(intentSearch);
                break;
            case 3:
                Intent intentProfile = new Intent(CategoriesActivity.this, ProfileActivity.class);
                user = (User) getIntent().getSerializableExtra("User");
                intentProfile.putExtra("User", user);
                startActivity(intentProfile);
                break;
        }
    }

    public String getFilters() {
        String result = "";
        if (!diet.equals("")) {
            result += diet;
        }
        if (!calories.equals("")) {
            result += calories;
        }
        if (!health.equals("")) {
            result += "&" + health;
        }
        return result;
    }

    public void dietOrHealth(String input, View v, String position) {
        if (categories1.contains(input)) {
            setDiet(input, v, position);
        } else {
            setHealth(input, v, position);
        }
    }

    public void setDiet(String input, View v, String position) {
        if (!clicked.contains(position)) {
            diet += "&diet=" + input;
            clicked.add(position);
            v.setBackgroundResource(R.drawable.gradient_clicked_background);
        } else {
            diet.replaceAll("&diet=" + input, "");
            clicked.remove(position);
            v.setBackgroundResource(R.drawable.white_border);
        }
    }

    public void setHealth(String input, View v, String position) {
        if (!clicked.contains(position)) {
            health += "&health=" + input;
            clicked.add(position);
            v.setBackgroundResource(R.drawable.gradient_clicked_background);
        } else {
            health.replaceAll("&health=" + input, "");
            clicked.remove(position);
            v.setBackgroundResource(R.drawable.white_border);
        }
    }
}
