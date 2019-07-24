package com.example.android.popularmovies.RoomUtils;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY mId")
    LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM movies WHERE mId = :id")
    LiveData<Movie> getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie newMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
