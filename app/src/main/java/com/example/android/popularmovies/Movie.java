package com.example.android.popularmovies;

public class Movie {

    private String mName;
    private String mPoster;

    public Movie(String name, String poster) {
        mName = name;
        mPoster = poster;
    }

    public String getName() {
        return mName;
    }

    public String getPoster() {
        return mPoster;
    }
}
