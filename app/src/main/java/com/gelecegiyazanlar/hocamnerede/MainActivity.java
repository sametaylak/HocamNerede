package com.gelecegiyazanlar.hocamnerede;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    CustomTextView mUserFullName;
    CustomTextView mUserUniversity;
    CircleImageView mUserSmallAvatar;

    TimelineViewPagerAdapter mViewPagerAdapter;

    private FirebaseAuth firebaseAuth;

    public static Intent newIntent(Activity callerActivity) {
        return new Intent(callerActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            Intent intent = SignActivity.newIntent(MainActivity.this);
            startActivity(intent);
            finish();
        } else {
            FirebaseHelper.getFirebaseUserDetail(new FirebaseHelper.FirebaseCallback() {
                @Override
                public void onSuccess(Object result) {
                    User user = (User) result;
                    mUserFullName.setText(user.getFullname());
                    mUserUniversity.setText(user.getUniversity());
                    if (user.getAvatar() != null) {
                        loadSmallAvatar(user);
                    }
                }
            });
        }

        setContentView(R.layout.activity_main);

        bindViews();
        setupViewPager();
    }

    private void bindViews() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar_layout);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mUserFullName = (CustomTextView)findViewById(R.id.userFullName);
        mUserUniversity = (CustomTextView)findViewById(R.id.userUniversity);
        mUserSmallAvatar = (CircleImageView) findViewById(R.id.userSmallAvatar);
        setSupportActionBar(mToolbar);
    }

    private void setupViewPager() {
        mViewPagerAdapter = new TimelineViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.addFragments(new Timeline(),"Timeline");
        mViewPagerAdapter.addFragments(new UserProfile(),"Profile");
        mViewPagerAdapter.addFragments(new Search(),"Search");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void loadSmallAvatar(User user) {
        Glide.with(MainActivity.this)
                .using(new FirebaseImageLoader())
                .load(FirebaseHelper.getUserAvatarRef(user.getAvatar()))
                .into(mUserSmallAvatar);
    }

}
