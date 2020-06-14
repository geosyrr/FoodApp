package com.example.regenfood.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User where email= :mail and password= :password")
    User getUser1(String mail, String password);

    @Query("SELECT * FROM User where userName= :username and password= :password")
    User getUser(String username, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
