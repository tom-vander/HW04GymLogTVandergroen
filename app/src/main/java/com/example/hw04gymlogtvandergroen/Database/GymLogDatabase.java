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
import com.example.hw04gymlogtvandergroen.Database.entities.User;
import com.example.hw04gymlogtvandergroen.Database.typeConverters.LocalDateTypeConverter;
import com.example.hw04gymlogtvandergroen.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 3, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    public static final String USER_TABLE = "user_table";
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
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
 //               dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract GymLogDAO gymLogDAO();

    public abstract UserDAO userDAO();
}
