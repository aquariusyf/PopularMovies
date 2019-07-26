package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Fragment.FragmentTrailer;
import com.example.android.popularmovies.Fragment.MovieDetailViewPagerAdapter;
import com.example.android.popularmovies.RoomUtils.AppDataBase;
import com.example.android.popularmovies.RoomUtils.AppExecutors;
import com.example.android.popularmovies.RoomUtils.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private static final String OUT_OF_TEN = "/10";
    public static final String MOVIE_ID = "id";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String RELEASE_DATE = "release_date";
    public static final String OVERVIEW = "overview";
    public static final String USER_RATING = "user_rating";

    private TextView mTitleTv;
    private ImageView mPosterIv;
    private TextView mReleaseDateTv;
    private TextView mUserRatingTv;
    private Button mMarkFavoriteIv;
    private TextView mOverviewTv;
    private Bundle mBundle;
    private AppDataBase mDb;

    private FragmentTrailer mTrailerFragment;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPagerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mDb = AppDataBase.getInstance(getApplicationContext());
        mTitleTv = findViewById(R.id.tv_title);
        mPosterIv = findViewById(R.id.iv_movie_detail_poster);
        mReleaseDateTv = findViewById(R.id.tv_release_date);
        mUserRatingTv = findViewById(R.id.tv_user_rating);
        mMarkFavoriteIv = findViewById(R.id.btn_mark_favorite);
        mOverviewTv = findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        if(!checkBundle(intent))
            finish();
        setupViewPager();
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

        mMarkFavoriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteClick();
            }
        });
    }

    private void onFavoriteClick() {
        final int movieId = mBundle.getInt(MOVIE_ID);
        String title = mBundle.getString(TITLE);
        String poster = mBundle.getString(POSTER);
        String releaseDate = mBundle.getString(RELEASE_DATE);
        String overview = mBundle.getString(OVERVIEW);
        String userRating = mBundle.getString(USER_RATING);

        final Movie newFavoriteMovie =
                new Movie(movieId, title, poster, releaseDate, overview, userRating);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Movie movieInDb = mDb.movieDao().getMovieByMovieId(movieId);
                if(movieInDb == null) {
                    mDb.movieDao().insertMovie(newFavoriteMovie);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.add_favorite_toast),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mDb.movieDao().deleteMovie(movieInDb);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.remove_favorite_toast),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void setupViewPager() {
        mViewPagerContainer = findViewById(R.id.vp_fragment_container);
        mTrailerFragment = new FragmentTrailer();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mTrailerFragment);
        mViewPagerContainer.setAdapter(
                new MovieDetailViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
    }

}
