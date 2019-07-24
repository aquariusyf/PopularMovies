package com.example.android.popularmovies.RoomUtils;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "movie_id")
    private int mMovieId;
    @ColumnInfo(name = "movie_name")
    private String mName;
    @ColumnInfo(name = "movie_poster")
    private String mPoster;
    @ColumnInfo(name = "movie_release_date")
    private String mReleaseDate;
    @ColumnInfo(name = "movie_overview")
    private String mOverView;
    @ColumnInfo(name = "movie_user_rating")
    private String mUserRating;

    @Ignore
    public Movie(
            int movieId,
            String name,
            String poster,
            String releaseDate,
            String overView,
            String userRating) {
        mMovieId = movieId;
        mName = name;
        mPoster = poster;
        mReleaseDate = releaseDate;
        mOverView = overView;
        mUserRating = userRating;
    }

    public Movie(
            int id,
            int movieId,
            String name,
            String poster,
            String releaseDate,
            String overView,
            String userRating) {
        mId = id;
        mMovieId = movieId;
        mName = name;
        mPoster = poster;
        mReleaseDate = releaseDate;
        mOverView = overView;
        mUserRating = userRating;
    }

    public int getId() { return mId; }

    public void setId(int id) { mId = id; }

    public int getMovieId() { return mMovieId; }

    public String getName() {
        return mName;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getReleaseDate() { return mReleaseDate; }

    public String getOverView() { return mOverView; }

    public String getUserRating() { return mUserRating; }

}
