package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER_ID_SORT_BY_MOST_POPULAR = 1;
    private static final int MOVIE_LOADER_ID_SORT_BY_TOP_RATED = 2;
    private ProgressBar mLoadingProgressBar;
    private RecyclerView mMovieListRv;
    private MovieListAdapter mAdapter;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgressBar = findViewById(R.id.pb_loading);
        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mAdapter = new MovieListAdapter(this, mMovieList, new MovieListAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO: Go to movie details;
                Intent movieDetail = new Intent(MainActivity.this, MovieDetailActivity.class);
                Bundle movieDetailBundle = new Bundle();
                movieDetailBundle.putString(MovieDetailActivity.TITLE, mMovieList.get(position).getName());
                movieDetailBundle.putString(MovieDetailActivity.POSTER, mMovieList.get(position).getPoster());
                movieDetailBundle.putString(MovieDetailActivity.RELEASE_DATE, mMovieList.get(position).getReleaseDate());
                movieDetailBundle.putString(MovieDetailActivity.OVERVIEW, mMovieList.get(position).getOverView());
                movieDetailBundle.putString(MovieDetailActivity.USER_RATING, mMovieList.get(position).getUserRating());
                movieDetail.putExtras(movieDetailBundle);
                startActivity(movieDetail);
            }
        });
        mMovieListRv.setAdapter(mAdapter);
        if(checkNetworkConnection()) {
            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID_SORT_BY_MOST_POPULAR, null, this);
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID_SORT_BY_MOST_POPULAR, null, this);
                break;
            case R.id.action_sort_by_top_rated:
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID_SORT_BY_TOP_RATED, null, this);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        if(i == MOVIE_LOADER_ID_SORT_BY_MOST_POPULAR)
            return new MovieLoader(this, QueryUtils.createSortByPopularityUrl());
        else
            return new MovieLoader(this, QueryUtils.createSortByRatingUrl());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mMovieList = movies;
        mAdapter.updateDataSet(mMovieList);
        Log.v(LOG_TAG, "Adapter updated: " + loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.updateDataSet(null);
    }
}
