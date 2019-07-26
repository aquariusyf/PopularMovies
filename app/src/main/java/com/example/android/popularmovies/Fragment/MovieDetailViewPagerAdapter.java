package com.example.android.popularmovies.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MovieDetailViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MovieDetailViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList == null ? null : mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
