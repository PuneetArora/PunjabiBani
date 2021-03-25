package com.digihelper.punjabibani.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.digihelper.punjabibani.Fragments.AudioFragment;
import com.digihelper.punjabibani.Fragments.LiveFragment;
import com.digihelper.punjabibani.Fragments.VideoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_PAGES = 3;
    LiveFragment liveFragment= null;
    AudioFragment audioFragment = null;
    VideoFragment videoFragment = null;
    public ViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
        }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;

        if(position == 0)
        fragment =  new LiveFragment();
        else if(position == 1)
        fragment =  new VideoFragment();
        else if(position == 2)
        fragment =  new AudioFragment();

        return fragment;

    }

    @Override
    public int getCount()
    {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {

        String title = null;
        if (position == 0)
            title = "Live";
        else if (position == 1)
            title = "Videos";
        else if (position == 2)
            title = "Audios";

        return title;
    }
}

