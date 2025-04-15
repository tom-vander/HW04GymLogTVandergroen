package com.example.hw04gymlogtvandergroen.viewHolders;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.hw04gymlogtvandergroen.Database.GymLogRepository;
import com.example.hw04gymlogtvandergroen.Database.entities.GymLog;
import java.util.List;

public class GymLogViewModel extends AndroidViewModel {
    private final GymLogRepository repository;
    public GymLogViewModel (Application application){
        super(application);
        repository = GymLogRepository.getRepository(application);
    }

    public LiveData<List<GymLog>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIDLiveData(userId);
    }

    public void insert(GymLog log){
        repository.insertGymLog(log);
    }
}
