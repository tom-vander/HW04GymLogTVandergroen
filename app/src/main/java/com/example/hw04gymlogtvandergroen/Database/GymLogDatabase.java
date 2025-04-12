package com.example.hw04gymlogtvandergroen.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hw04gymlogtvandergroen.Database.entities.GymLog;
import com.example.hw04gymlogtvandergroen.Database.typeConverters.LocalDateTypeConverter;
import com.example.hw04gymlogtvandergroen.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GymLog.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "GymLog_Database";
    public static final String gymLogTable = "gymLogTable";
    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GymLogDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (GymLogDatabase.class){
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GymLogDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract GymLogDAO gymLogDAO();
}
