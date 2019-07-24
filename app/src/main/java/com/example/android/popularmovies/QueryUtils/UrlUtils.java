package com.example.android.popularmovies.QueryUtils;

import android.net.Uri;

import com.example.android.popularmovies.BuildConfig;

public class UrlUtils {

    private static final String LOG_TAG = UrlUtils.class.getSimpleName();
    private static final String URL_BASE_POPULAR = "http://api.themoviedb.org/3/movie/popular/";
    private static final String URL_BASE_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String POPULARITY = "popularity.desc";
    private static final String RATING = "vote_average.desc";

    public static String createSortByPopularityUrl() {
        Uri uri = Uri.parse(URL_BASE_POPULAR).buildUpon()
                  .appendQueryParameter(PARAM_API_KEY, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                  .appendQueryParameter(PARAM_SORT_BY, POPULARITY).build();
        return uri.toString();
    }

    public static String createSortByRatingUrl() {
        Uri uri = Uri.parse(URL_BASE_TOP_RATED).buildUpon()
                  .appendQueryParameter(PARAM_API_KEY, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                  .appendQueryParameter(PARAM_SORT_BY, RATING).build();
        return uri.toString();
    }
}
