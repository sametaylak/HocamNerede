package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.gelecegiyazanlar.hocamnerede.model.User;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {


    private SearchRecyclerAdapter searcAdapter;
    private RecyclerView recyclerView;


    private FloatingSearchView mSearchView;
    private MenuItem searchMenuItem;

    private List<User> userTeacherList = new ArrayList<>();

    public Search(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchView = (FloatingSearchView)mView.findViewById(R.id.search);




        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //mSearchView.swapSuggestions();
            }
        });


        return mView;
    }
}
