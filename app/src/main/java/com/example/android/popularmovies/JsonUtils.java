package com.example.android.popularmovies;

import android.util.Log;
import org.json.JSONObject;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static final String RESULT = "results";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185/";

    public static Movie parseMovieJson(String json) {
        if(json == null || json.isEmpty())
            return null;

        try {
            JSONObject jsonMovie = new JSONObject(json);
            JSONObject resultObj = jsonMovie.getJSONObject(RESULT);

            String title = resultObj.getString(TITLE);
            String poster = POSTER_BASE_URL + POSTER_SIZE + resultObj.getString(POSTER_PATH);

            Movie movie = new Movie(title, poster);
            return  movie;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Can't parse JSON", e);
            return null;
        }
    }
}
