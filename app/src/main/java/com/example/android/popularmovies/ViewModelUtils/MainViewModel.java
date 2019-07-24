package com.example.android.popularmovies.ViewModelUtils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.popularmovies.RoomUtils.AppDataBase;
import com.example.android.popularmovies.RoomUtils.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<Movie>> mMovies;

    public MainViewModel(Application application) {
        super(application);
        AppDataBase db = AppDataBase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Actively retrieving data from database");
        mMovies = db.movieDao().getAll();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return mMovies;
    }

}
