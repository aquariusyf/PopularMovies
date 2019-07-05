package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER_ID = 1;
    private RecyclerView mMovieListRv;
    private MovieListAdapter mAdapter;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mAdapter = new MovieListAdapter(this, mMovieList, new MovieListAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO: Go to movie details;
            }
        });
        mMovieListRv.setAdapter(mAdapter);
        if(checkNetworkConnection()) {
            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnected();
        return isConnected;
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mMovieList = movies;
        mAdapter.updateDataSet(mMovieList);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.updateDataSet(null);
    }
}
