package com.example.android.popularmovies.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
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

public class FragmentReview extends Fragment {

    private static final String LOG_TAG = FragmentReview.class.getSimpleName();
    private List<String> mReviewList;
    private int mMovieId;
    private ReviewAdapter mAdapter;
    private RecyclerView mReviewRv;
    private static TextView mEmptyView;

    public FragmentReview(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMovieId();
        mEmptyView = getActivity().findViewById(R.id.tv_review_empty);
        mReviewRv = getActivity().findViewById(R.id.rv_review_list);
        mReviewRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReviewAdapter(getContext(), mReviewList,
                new TrailerAdapter.OnListItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent webIntent =
                                new Intent(Intent.ACTION_VIEW, Uri.parse(mReviewList.get(position)));
                        startActivity(webIntent);
                    }
                });
        mReviewRv.setAdapter(mAdapter);
        mReviewRv.setHasFixedSize(true);
        mReviewRv.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fetchReviewData(UrlUtils.createGetReviewUrl(mMovieId));
    }

    public static void setmEmptyView(boolean isEmpty) {
        if(isEmpty) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
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

    private void fetchReviewData(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mReviewList = JsonUtils.parseReviewJson(response);
                        mAdapter.updateDataSet(mReviewList);
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
