package com.example.android.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String URL_BASE_POPULAR = "http://api.themoviedb.org/3/movie/popular/";
    private static final String URL_BASE_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String KEY = ""; //Put the API key here
    private static final String POPULARITY = "popularity.desc";
    private static final String RATING = "vote_average.desc";

    public static URL createSortByPopularityUrl() {
        Uri uri = Uri.parse(URL_BASE_POPULAR).buildUpon()
                  .appendQueryParameter(PARAM_API_KEY, KEY)
                  .appendQueryParameter(PARAM_SORT_BY, POPULARITY).build();
        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Failed to create URL", e);
            return null;
        }
        return url;
    }

    public static URL createSortByRatingUrl() {
        Uri uri = Uri.parse(URL_BASE_TOP_RATED).buildUpon()
                  .appendQueryParameter(PARAM_API_KEY, KEY)
                  .appendQueryParameter(PARAM_SORT_BY, RATING).build();
        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Failed to create URL", e);
            return null;
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String JSONResponse = "";
        if(url == null)
            return JSONResponse;
        Log.v(LOG_TAG, "Making request: " + url.toString());
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromInputStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "HTTP Response Code: " + urlConnection.getResponseCode());
                JSONResponse = "";
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOEXCEPTION: " + e.toString());
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return JSONResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movie> fetchMovieData(URL url) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to make HTTP request", e);

        }
        List<Movie> movieList = JsonUtils.parseMovieJson(jsonResponse);
        return movieList;
    }
}
