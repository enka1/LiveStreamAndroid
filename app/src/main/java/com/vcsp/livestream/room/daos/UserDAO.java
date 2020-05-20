package com.vcsp.livestream.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.vcsp.livestream.room.entities.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Query("DELETE FROM User")
    void deleteAll();
}
