package com.gelecegiyazanlar.hocamnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gelecegiyazanlar.hocamnerede.helper.FirebaseHelper;
import com.gelecegiyazanlar.hocamnerede.model.User;
import com.gelecegiyazanlar.hocamnerede.views.CustomTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    CustomTextView mUserFullName;
    CustomTextView mUserUniversity;
    CircleImageView mUserSmallAvatar;
    SwitchCompat mUserStatusButton;
    RelativeLayout mStatusLayout;
    TextView mTextView;
    boolean busy;

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

                    if (user.getStatus())
                        mUserStatusButton.setChecked(false);
                    else
                        mUserStatusButton.setChecked(true);

                    if (user.isStudent()) {
                        mStatusLayout.setVisibility(View.GONE);
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
        mUserStatusButton = (SwitchCompat) findViewById(R.id.userStatusButton);
        mStatusLayout = (RelativeLayout) findViewById(R.id.statusLayout);
        mTextView = (TextView)findViewById(R.id.switchText);
        busy = true;
        setSupportActionBar(mToolbar);

        mUserStatusButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(busy){
                    mTextView.setText("Uygun");
                    busy = false;
                }
                else{
                    mTextView.setText("Meşgul");
                    busy = true;
                }
                FirebaseHelper.updateUserStatus(b);
            }
        });
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
