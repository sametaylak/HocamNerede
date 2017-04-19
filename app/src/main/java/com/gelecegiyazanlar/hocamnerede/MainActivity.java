package com.gelecegiyazanlar.hocamnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    TimelineViewPagerAdapter mViewPagerAdapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private User user;

    public static Intent newIntent(Activity callerActivity){
        return new Intent(callerActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            Intent intent = SignActivity.newIntent(MainActivity.this);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mToolbar = (Toolbar)findViewById(R.id.toolbar_layout);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        setSupportActionBar(mToolbar);

        mViewPagerAdapter = new TimelineViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.addFragments(new UserProfile(),"Profile");
        mViewPagerAdapter.addFragments(new Timeline(),"Timeline");
        mViewPagerAdapter.addFragments(new Search(),"Search");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
