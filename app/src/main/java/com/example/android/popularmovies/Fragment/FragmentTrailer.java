package com.example.android.popularmovies.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.popularmovies.MovieDetailActivity;
import com.example.android.popularmovies.QueryUtils.JsonUtils;
import com.example.android.popularmovies.QueryUtils.UrlUtils;
import com.example.android.popularmovies.R;

import org.json.JSONObject;

import java.util.List;

public class FragmentTrailer extends Fragment {

    private static final String LOG_TAG = FragmentTrailer.class.getSimpleName();
    private List<String> mTrailerList;
    private int mMovieId;
    private TrailerAdapter mAdapter;
    private RecyclerView mTrailerRv;

    public FragmentTrailer(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMovieId();
        mTrailerRv = getActivity().findViewById(R.id.rv_trailer_list);
        mTrailerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TrailerAdapter(getContext(), mTrailerList,
                new TrailerAdapter.OnListItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
        });
        mTrailerRv.setAdapter(mAdapter);
        mTrailerRv.setHasFixedSize(true);
        mTrailerRv.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fetchTrailerData(UrlUtils.createGetTrailerUrl(mMovieId));
    }

    private void getMovieId() {
        Intent intent = getActivity().getIntent();
        Bundle bundle = new Bundle();
        if(intent != null) {
            bundle = intent.getExtras();
        }
        if(bundle != null) {
            mMovieId = bundle.getInt(MovieDetailActivity.MOVIE_ID);
        }
    }

    private void fetchTrailerData(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       mTrailerList = JsonUtils.parseTrailerJson(response);
                       mAdapter.updateDataSet(mTrailerList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, error.getMessage(), error);
                    }
                });
        queue.add(jsonRequest);
        queue.start();
    }
}
