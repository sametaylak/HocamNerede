package com.gelecegiyazanlar.hocamnerede;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.gelecegiyazanlar.hocamnerede.model.LocationPost;
import com.gelecegiyazanlar.hocamnerede.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;


public class Search extends Fragment {

    @BindView(R.id.searchView) EditText searchView;
    @BindView(R.id.userRecyclerView) RecyclerView userRecyclerView;

    List<User> userList = new ArrayList<>();
    List<User> tempList = new ArrayList<>();

    private UserRecyclerAdapter userSearchAdapter;

    public Search() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);

        setAdapter();

        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        userList.add(dataSnapshot.getValue(User.class));
                        tempList.add(dataSnapshot.getValue(User.class));
                        userSearchAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

        return rootView;
    }

    private void setAdapter() {
        userSearchAdapter = new UserRecyclerAdapter(getContext(), tempList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        userRecyclerView.setLayoutManager(mLayoutManager);
        userRecyclerView.setItemAnimator(new DefaultItemAnimator());
        userRecyclerView.setAdapter(userSearchAdapter);
    }

    @OnTextChanged(R.id.searchView)
    public void onSearchTextChanged(CharSequence s) {
        getFilteredUser(s.toString());
    }

    private void getFilteredUser(String start) {
        tempList.clear();
        for (User u : userList) {
            if (u.getFullname().toLowerCase().startsWith(start.toLowerCase())) {
                tempList.add(u);
            }
        }
        userSearchAdapter.notifyDataSetChanged();
    }
}
