package com.masterandroid.kamino.data.api;

import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseClient {

    private static AppDatabase database;

    public static synchronized AppDatabase getDatabase(Context context) {
        if (database == null) {
            Log.d("DatabaseClient", "Creating new database instance");
            database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "kamino_db")
                    .build();
        } else {
            Log.d("DatabaseClient", "Database instance already exists");
        }
        return database;
    }

}
