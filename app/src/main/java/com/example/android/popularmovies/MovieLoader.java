package com.example.android.popularmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getSimpleName();

    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movieList = QueryUtils.fetchMovieData(QueryUtils.createSortByPopularityUrl());
        return movieList;
    }
}
