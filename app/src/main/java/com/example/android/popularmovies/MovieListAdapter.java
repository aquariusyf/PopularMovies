package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmovies.RoomUtils.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();
    private Context mContext;
    private List<Movie> mMovies;
    private OnListItemClickListener mOnListItemClickListener;

    public MovieListAdapter(Context context,
                            List<Movie> movies,
                            OnListItemClickListener onListItemClickListener) {
        mContext = context;
        mMovies = movies;
        mOnListItemClickListener = onListItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_movie, viewGroup, false);
        view.getLayoutParams().height = viewGroup.getMeasuredHeight()/2;
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Picasso.get().load(Uri.parse(mMovies.get(position).getPoster()))
                .placeholder(R.drawable.default_poster)
                .into(((MovieViewHolder) viewHolder).singleMovieIv);
    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.size();
        else
            return 0;
    }

    public void updateDataSet(List<Movie> newMovieList) {
        mMovies = newMovieList;
        MainActivity.setEmptyView(mMovies == null || mMovies.isEmpty());
        notifyDataSetChanged();
    }

    interface OnListItemClickListener {
        void onItemClick(int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView singleMovieIv;

        public MovieViewHolder(View itemView) {
            super(itemView);
            singleMovieIv = itemView.findViewById(R.id.iv_single_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
