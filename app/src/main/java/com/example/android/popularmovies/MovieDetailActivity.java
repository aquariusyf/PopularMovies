package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private static final String OUT_OF_TEN = "/10";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String RELEASE_DATE = "release_date";
    public static final String OVERVIEW = "overview";
    public static final String USER_RATING = "user_rating";

    private TextView mTitleTv;
    private ImageView mPosterIv;
    private TextView mReleaseDateTv;
    private TextView mUserRatingTv;
    private ImageView mMarkFavoriteIv;
    private TextView mOverviewTv;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTv = findViewById(R.id.tv_title);
        mPosterIv = findViewById(R.id.iv_movie_detail_poster);
        mReleaseDateTv = findViewById(R.id.tv_release_date);
        mUserRatingTv = findViewById(R.id.tv_user_rating);
        mMarkFavoriteIv = findViewById(R.id.iv_mark_favorite);
        mOverviewTv = findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        if(!checkBundle(intent))
            finish();
        setMovieDetail();
    }

    private boolean checkBundle(Intent intent) {
        if(intent == null) {
            Log.e(LOG_TAG, "Failed to open movie details");
            return false;
        }
        mBundle = intent.getExtras();
        if(mBundle == null) {
            Log.e(LOG_TAG, "Failed to receive movie details");
            return false;
        }
        return true;
    }

    private void setMovieDetail() {
        String title = mBundle.getString(TITLE);
        String poster = mBundle.getString(POSTER);
        String releaseDate = mBundle.getString(RELEASE_DATE);
        String overview = mBundle.getString(OVERVIEW);
        String userRating = mBundle.getString(USER_RATING);

        mTitleTv.setText(title);
        mTitleTv.setSelected(true);
        mReleaseDateTv.setText(releaseDate);
        mOverviewTv.setText(overview);
        mUserRatingTv.setText(userRating + OUT_OF_TEN);
        Picasso.get().load(Uri.parse(poster)).into(mPosterIv);
    }
}
