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

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String URL_BASE = "http://api.themoviedb.org/3/movie/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String KEY = "4f2511f01aba7a30079a4c8874ad7f8f";
    private static final String POPULARITY = "popularity.desc";
    private static final String RATING = "vote_average.desc";


    public QueryUtils(){}

    public static URL createSortByPopularityUrl() {
        Uri uri = Uri.parse(URL_BASE).buildUpon()
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
        Uri uri = Uri.parse(URL_BASE).buildUpon()
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

    public static String makeHTTPRequest(URL url) throws IOException {
        String JSONRespose = "";
        if(url == null)
            return JSONRespose;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JSONRespose = readFromInputStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "HTTP Response Code: " + urlConnection.getResponseCode());
                JSONRespose = "";
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOEXCEPTION: " + e.toString());
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return JSONRespose;
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
}
