package com.gelecegiyazanlar.hocamnerede;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ASUS-PC on 19.4.2017.
 */

public class TimelineViewPagerAdapter extends FragmentPagerAdapter{

    ArrayList<Fragment> fragmentsList = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments,String titles){
        fragmentsList.add(fragments);
        tabTitles.add(titles);
    }


    public TimelineViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
/*
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }*/
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Timeline();
            case 1:
                return new UserProfile();
            case 2:
                return new Search();
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
