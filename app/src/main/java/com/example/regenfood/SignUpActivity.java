package com.example.regenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regenfood.Database.User;
import com.example.regenfood.Database.UserDao;
import com.example.regenfood.Database.UserDataBase;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    TextView textViewLogin;
    private UserDao userDao;
    private String favourites;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Log.d(TAG, "onCreate: SignUpWorks");
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        textViewLogin = findViewById(R.id.textViewLogin);
        CircularProgressButton buttonRegister = (CircularProgressButton) findViewById(R.id.buttonRegister);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegister.startAnimation();
                String userName = editTextUsername.getEditText().getText().toString().trim();
                String email = editTextEmail.getEditText().getText().toString().trim();
                String password = editTextPassword.getEditText().getText().toString().trim();
                String passwordConf = editTextCnfPassword.getEditText().getText().toString().trim();
                favourites = "";
                if (password.equals(passwordConf)) {
                    User user = new User(userName, password, email, favourites);
                    userDao.insert(user);
                    Intent moveToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(moveToLogin);
                } else {
                    Toast.makeText(SignUpActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    buttonRegister.revertAnimation();
                }
            }
        });
    }
}
