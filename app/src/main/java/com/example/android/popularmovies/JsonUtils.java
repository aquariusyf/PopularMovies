package com.example.android.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static final String RESULT = "results";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185/";

    public static List<Movie> parseMovieJson(String json) {
        if(json == null || json.isEmpty())
            return null;

        try {
            JSONObject jsonRoot = new JSONObject(json);
            JSONArray resultArray = jsonRoot.getJSONArray(RESULT);
            List<Movie> movieList = new ArrayList<>();
            for(int i = 0; i < resultArray.length(); i++) {
                String title = resultArray.getJSONObject(i).getString(TITLE);
                String poster = POSTER_BASE_URL + POSTER_SIZE + resultArray.getJSONObject(i).getString(POSTER_PATH);
                Movie movie = new Movie(title, poster);
                movieList.add(movie);
            }
            return movieList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't parse JSON", e);
            return null;
        }
    }
}