package com.example.android.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.popularmovies.RoomUtils.Movie;
import com.example.android.popularmovies.QueryUtils.JsonUtils;
import com.example.android.popularmovies.QueryUtils.UrlUtils;
import com.example.android.popularmovies.ViewModelUtils.MainViewModel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MOVIE_LIST_KEY = "movie_list";
    private ProgressBar mLoadingProgressBar;
    private static TextView mEmptyView;
    private RecyclerView mMovieListRv;
    private MovieListAdapter mAdapter;
    private List<Movie> mMovieList;
    private RequestQueue mQueue;
    private JsonObjectRequest mJsonObjectRequest;
    private static boolean sIsFavoriteSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgressBar = findViewById(R.id.pb_loading);
        mEmptyView = findViewById(R.id.tv_movie_list_empty);
        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setLayoutManager(new GridLayoutManager(this,
                2,
                GridLayoutManager.VERTICAL,
                false));
        mAdapter = new MovieListAdapter(this, mMovieList,
                new MovieListAdapter.OnListItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent movieDetail = new Intent(MainActivity.this,
                                MovieDetailActivity.class);
                        Bundle movieDetailBundle = new Bundle();
                        movieDetailBundle.putInt(MovieDetailActivity.MOVIE_ID,
                                mMovieList.get(position).getMovieId());
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
        setupViewModel();
        if(!restoreUserData(savedInstanceState) && checkNetworkConnection()) {
            fetchMovieData(UrlUtils.createSortByPopularityUrl());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_LIST_KEY, (ArrayList)mMovieList);
    }

    private boolean restoreUserData(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            return false;
        }
        mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
        mAdapter.updateDataSet(mMovieList);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        Log.v(LOG_TAG, "User data restored");
        return true;
    }

    public static void setEmptyView(boolean isEmpty) {
        if(isEmpty) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(sIsFavoriteSelected) {
                    mMovieList = movies;
                    mAdapter.updateDataSet(movies);
                    mLoadingProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
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
                sIsFavoriteSelected = false;
                fetchMovieData(UrlUtils.createSortByPopularityUrl());
                break;
            case R.id.action_sort_by_top_rated:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                sIsFavoriteSelected = false;
                fetchMovieData(UrlUtils.createSortByRatingUrl());
                break;
            case R.id.action_show_favorite:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                sIsFavoriteSelected = true;
                setupViewModel();
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