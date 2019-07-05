package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMovieListRv;
    private MovieListAdapter mAdapter;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MovieListAdapter(this, mMovieList, new MovieListAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO: Go to movie details;
            }
        });
        mMovieListRv.setAdapter(mAdapter);
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnected();
        return isConnected;
    }
}
