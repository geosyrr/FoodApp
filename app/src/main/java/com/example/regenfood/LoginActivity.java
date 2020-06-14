package com.example.regenfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.widget.TextView;
import android.widget.Toast;

import com.example.regenfood.Database.User;
import com.example.regenfood.Database.UserDao;
import com.example.regenfood.Database.UserDataBase;
import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout editTextEmail, editTextPassword;
    TextView textViewRegister;
    UserDao db;
    UserDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        CircularProgressButton buttonLogin = (CircularProgressButton) findViewById(R.id.btn_id);
        textViewRegister = findViewById(R.id.textViewRegister);
        dataBase = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getUserDao();
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogin.startAnimation();
                String username = editTextEmail.getEditText().getText().toString().trim();
                String password = editTextPassword.getEditText().getText().toString().trim();
                User user = db.getUser(username, password);
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
                    i.putExtra("User", user);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Unregistered user, or incorrect credentials", Toast.LENGTH_SHORT).show();
                    buttonLogin.revertAnimation();
                }
            }
        });
    }
}