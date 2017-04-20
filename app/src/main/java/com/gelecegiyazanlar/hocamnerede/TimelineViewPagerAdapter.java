package com.gelecegiyazanlar.hocamnerede;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TimelineViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentsList = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments,String titles){
        fragmentsList.add(fragments);
        tabTitles.add(titles);
    }


    public TimelineViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserProfile();
            case 1:
                return new Timeline();
            case 2:
                return new Search();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        //return tabTitles.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
