package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.popularmovies.utils.JsonUtils;
import com.example.android.popularmovies.utils.UrlUtils;

import org.json.JSONObject;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ProgressBar mLoadingProgressBar;
    private RecyclerView mMovieListRv;
    private MovieListAdapter mAdapter;
    private List<Movie> mMovieList;
    private RequestQueue mQueue;
    private JsonObjectRequest mJsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgressBar = findViewById(R.id.pb_loading);
        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setLayoutManager(new GridLayoutManager(this,
                2,
                GridLayoutManager.VERTICAL,
                false));
        mAdapter = new MovieListAdapter(this, mMovieList, new MovieListAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent movieDetail = new Intent(MainActivity.this,
                        MovieDetailActivity.class);
                Bundle movieDetailBundle = new Bundle();
                movieDetailBundle.putString(MovieDetailActivity.TITLE,
                        mMovieList.get(position).getName());
                movieDetailBundle.putString(MovieDetailActivity.POSTER,
                        mMovieList.get(position).getPoster());
                movieDetailBundle.putString(MovieDetailActivity.RELEASE_DATE,
                        mMovieList.get(position).getReleaseDate());
                movieDetailBundle.putString(MovieDetailActivity.OVERVIEW,
                        mMovieList.get(position).getOverView());
                movieDetailBundle.putString(MovieDetailActivity.USER_RATING,
                        mMovieList.get(position).getUserRating());
                movieDetail.putExtras(movieDetailBundle);
                startActivity(movieDetail);
            }
        });
        mMovieListRv.setAdapter(mAdapter);
        if(checkNetworkConnection()) {
            fetchMovieData(UrlUtils.createSortByPopularityUrl());
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnected();
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_most_popular:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                fetchMovieData(UrlUtils.createSortByPopularityUrl());
                break;
            case R.id.action_sort_by_top_rated:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                fetchMovieData(UrlUtils.createSortByRatingUrl());
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovieData(String url) {
        mJsonObjectRequest = new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mMovieList = JsonUtils.parseMovieJson(response);
                        mAdapter.updateDataSet(mMovieList);
                        mLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, error.getMessage(), error);
                    }
                });
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(mJsonObjectRequest);
        mQueue.start();
    }
}