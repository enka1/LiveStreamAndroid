package com.vcsp.livestream.room;

import android.content.Context;
import androidx.room.Room;

public class  DatabaseInstance {
    private static AppDatabase appDatabase;

    public static AppDatabase getDatabaseInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "live_stream").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

    public static void destroyDatabaseInstance() {
        appDatabase = null;
    }
}
