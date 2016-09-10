package com.paulo.minhas_series.ui.resources;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.paulo.minhas_series.R;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final String FRAGMENT_TITLE_RESOURCE_ID = "fragment_title";

    private final List<Fragment> mFragmentList;
    private AppCompatActivity mActivity;
    private Fragment mCurrentFragment;

    public SectionsPagerAdapter(AppCompatActivity activity, List<Fragment> mFragmentList) {
        super(activity.getSupportFragmentManager());
        this.mFragmentList = mFragmentList;
        this.mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try{
            Bundle bundle = mFragmentList.get(position).getArguments();
            return mActivity.getString(bundle.getInt(FRAGMENT_TITLE_RESOURCE_ID));
        }
        catch (Resources.NotFoundException e){
            return mActivity.getString(R.string.empty);
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        Fragment selectedFragment = mFragmentList.get(position);
        if (selectedFragment != mCurrentFragment) {
            mCurrentFragment = selectedFragment;
        }
    }
}
