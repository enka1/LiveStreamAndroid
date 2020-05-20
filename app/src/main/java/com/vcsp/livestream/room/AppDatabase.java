package com.vcsp.livestream.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.vcsp.livestream.room.daos.UserDAO;
import com.vcsp.livestream.room.entities.User;

import java.util.List;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDao();

    public User getUser() {
        User user;
        List<User> users = userDao().getAll();
        if (users.size() == 0) {
            user = null;
        } else {
            user = users.get(0);
        }
        return user;
    }

    public void addUser(User user) {
        userDao().deleteAll();
        userDao().insertUser(user);
    }

    public void deleteUser() {
        userDao().deleteAll();
    }
}
