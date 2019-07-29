package com.example.android.popularmovies.RoomUtils;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movies")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "movie_id")
    private int mMovieId;
    @ColumnInfo(name = "movie_name")
    private String mName;
    @ColumnInfo(name = "movie_poster")
    private String mPoster;
    @ColumnInfo(name = "movie_release_date")
    private String mReleaseDate;
    @ColumnInfo(name = "movie_overview")
    private String mOverView;
    @ColumnInfo(name = "movie_user_rating")
    private String mUserRating;

    @Ignore
    public Movie(
            int movieId,
            String name,
            String poster,
            String releaseDate,
            String overView,
            String userRating) {
        mMovieId = movieId;
        mName = name;
        mPoster = poster;
        mReleaseDate = releaseDate;
        mOverView = overView;
        mUserRating = userRating;
    }

    public Movie(
            int id,
            int movieId,
            String name,
            String poster,
            String releaseDate,
            String overView,
            String userRating) {
        mId = id;
        mMovieId = movieId;
        mName = name;
        mPoster = poster;
        mReleaseDate = releaseDate;
        mOverView = overView;
        mUserRating = userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mMovieId);
        dest.writeString(mName);
        dest.writeString(mPoster);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverView);
        dest.writeString(mUserRating);
    }

    @Ignore
    protected Movie(Parcel in) {
        mId = in.readInt();
        mMovieId = in.readInt();
        mName = in.readString();
        mPoster = in.readString();
        mReleaseDate = in.readString();
        mOverView = in.readString();
        mUserRating = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() { return mId; }

    public void setId(int id) { mId = id; }

    public int getMovieId() { return mMovieId; }

    public String getName() {
        return mName;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getReleaseDate() { return mReleaseDate; }

    public String getOverView() { return mOverView; }

    public String getUserRating() { return mUserRating; }

}
