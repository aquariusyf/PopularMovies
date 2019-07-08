package com.example.android.popularmovies;

public class Movie {

    private String mName;
    private String mPoster;
    private String mReleaseDate;
    private String mOverView;
    private String mUserRating;

    public Movie(String name, String poster, String releaseDate, String overView, String userRating) {
        mName = name;
        mPoster = poster;
        mReleaseDate = releaseDate;
        mOverView = overView;
        mUserRating = userRating;
    }

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
