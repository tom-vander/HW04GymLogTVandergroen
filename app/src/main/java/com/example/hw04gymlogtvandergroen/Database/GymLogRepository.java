package com.example.hw04gymlogtvandergroen.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.hw04gymlogtvandergroen.Database.entities.GymLog;
import com.example.hw04gymlogtvandergroen.Database.entities.User;
import com.example.hw04gymlogtvandergroen.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private ArrayList<GymLog> allLogs;
    private static GymLogRepository repository;
    private GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    public static GymLogRepository getRepository (Application application){
        if(repository != null){
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog){
        GymLogDatabase.databaseWriteExecutor.execute(()->{
            gymLogDAO.insert(gymLog);
        });
    }

    public void insertUser(User... user){
        GymLogDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<GymLog>> getAllLogsByUserIDLiveData(int loggedInUserId){
        return gymLogDAO.getRecordsByUserID(loggedInUserId);
    }

    @Deprecated
    public ArrayList<GymLog> getAllLogsByUserID(int loggedInUserId) {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getRecordsByUserId(loggedInUserId);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }
}
