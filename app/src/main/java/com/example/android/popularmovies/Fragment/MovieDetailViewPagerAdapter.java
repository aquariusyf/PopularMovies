package com.example.android.popularmovies.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.popularmovies.R;

import java.util.List;

public class MovieDetailViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private Context mContext;

    public MovieDetailViewPagerAdapter(
            FragmentManager fm,
            List<Fragment> fragmentList,
            Context context) {
        super(fm);
        mFragmentList = fragmentList;
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList == null ? null : mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.tab_title_trailers);
            case 1:
                return mContext.getResources().getString(R.string.tab_title_reviews);
            default:
                return null;
        }
    }
}
