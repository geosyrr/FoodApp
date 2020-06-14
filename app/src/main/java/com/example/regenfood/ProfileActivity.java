package com.example.regenfood;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.regenfood.Database.User;
import com.example.regenfood.Database.UserDao;
import com.example.regenfood.Database.UserDataBase;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ProfileActivity extends AppCompatActivity {
    User user;
    String username;
    String email;
    String favourites;
    UserDataBase dataBase;
    UserDao userDao;
    ArrayList<String> favorites;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        favorites = new ArrayList<>();
        updateFromDB();
        Button logoutButton = findViewById(R.id.logoutButton);
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
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PopUp.class);
            this.startActivity(intent);
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        favorites = new ArrayList<>();
        updateFromDB();
        TextView textViewUsername = findViewById(R.id.greetingsTextUsername);
        EditText textViewEmail = findViewById(R.id.usernamEmail);
        user = (User) getIntent().getSerializableExtra("User");
        if (user != null) {
            username = user.getUserName();
            email = user.getEmail();
            favourites = user.getFavourites();
            textViewUsername.setText(username);
            textViewEmail.setText(email);
        }
        textViewEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    user.setEmail(v.getText().toString());
                    userDao.update(user);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel =
                                new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(ProfileActivity.this, "MyNotifications")
                            .setContentTitle("RegenFood: Email change")
                            .setSmallIcon(R.drawable.ic_profile_icon)
                            .setAutoCancel(true)
                            .setContentText("Your email was changed to : " + v.getText().toString());
                    NotificationManagerCompat manager = NotificationManagerCompat.from(ProfileActivity.this);
                    manager.notify(99, builder.build());
                    return true;
                }
                return false;
            }
        });
    }

    public void MenuOptions(int i) {
        switch (i) {
            case 1:
                finish();
                break;
            case 2:
                Intent intentSearch = new Intent(ProfileActivity.this, CategoriesActivity.class);
                user = (User) getIntent().getSerializableExtra("User");
                intentSearch.putExtra("User", user);
                startActivity(intentSearch);
                break;
            case 3:
//                Intent intentProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
//                user = (User) getIntent().getSerializableExtra("User");
//                intentProfile.putExtra("User", user);
//                startActivity(intentProfile);
                break;
        }
    }

    public void updateFromDB() {
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
        initFavorites(user.getFavourites());
    }

    public void initFavorites(String i) {
        String[] list = i.split(",");
        for (int j = 0; j < list.length; j++) {
            favorites.add(list[j]);
        }
        fillRecycler();
    }

    private void fillRecycler() {
        RecyclerView recyclerView = findViewById(R.id.profileRecycler);
        ProfileAdapter adapter = new ProfileAdapter(favorites, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void showRecipe(String recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("url", recipe);
        user = (User) getIntent().getSerializableExtra("User");
        intent.putExtra("User", user);
        this.startActivity(intent);
    }
}








