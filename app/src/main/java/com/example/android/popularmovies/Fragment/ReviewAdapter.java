package com.example.android.popularmovies.Fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import com.example.android.popularmovies.Fragment.TrailerAdapter.OnListItemClickListener;
import com.example.android.popularmovies.R;

public class ReviewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> mReviewList;
    private OnListItemClickListener mListener;

    public ReviewAdapter(Context context, List<String> reviewList, OnListItemClickListener listener) {
        mContext = context;
        mReviewList = reviewList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_review, viewGroup, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ReviewViewHolder) viewHolder).reviewTv
                .setText(mContext.getResources().getString(R.string.review_text));
        ((ReviewViewHolder) viewHolder).reviewTv.append("" + (i + 1));
    }

    @Override
    public int getItemCount() {
        if(mReviewList == null || mReviewList.isEmpty()) {
            return 0;
        } else {
            return mReviewList.size();
        }
    }

    public void updateDataSet(List<String> newReviewList) {
        mReviewList = newReviewList;
        FragmentReview.setEmptyView(mReviewList == null || mReviewList.isEmpty());
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends ViewHolder implements View.OnClickListener {

        private TextView reviewTv;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewTv = itemView.findViewById(R.id.tv_review_item);
            reviewTv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}
