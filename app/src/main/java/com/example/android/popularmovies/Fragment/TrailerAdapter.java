package com.example.android.popularmovies.Fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> mTrailerList;
    private OnListItemClickListener mListener;

    public TrailerAdapter(Context context, List<String> trailerList, OnListItemClickListener listener) {
        mContext = context;
        mTrailerList = trailerList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_trailer, viewGroup, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((TrailerViewHolder) viewHolder).trailerTv
                .setText(mContext.getResources().getString(R.string.trailer_text));
        ((TrailerViewHolder) viewHolder).trailerTv.append(" " + (i + 1));
    }

    @Override
    public int getItemCount() {
        if(mTrailerList == null || mTrailerList.size() == 0) {
            return 0;
        } else {
            return mTrailerList.size();
        }
    }

    public void updateDataSet(List<String> newTrailerList) {
        mTrailerList = newTrailerList;
        FragmentTrailer.setEmptyView(mTrailerList == null || mTrailerList.isEmpty());
        notifyDataSetChanged();
    }

    public interface OnListItemClickListener {
        void onItemClick(int position);
    }

    public class TrailerViewHolder extends ViewHolder implements View.OnClickListener {

        private TextView trailerTv;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerTv = itemView.findViewById(R.id.tv_trailer_item);
            trailerTv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}
