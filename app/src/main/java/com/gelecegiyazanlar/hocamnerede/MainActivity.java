package com.gelecegiyazanlar.hocamnerede;

<<<<<<< Updated upstream
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
=======
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity{




    //For Timeline
    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;


    TimelineViewPagerAdapter mViewPagerAdapter;
>>>>>>> Stashed changes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


<<<<<<< Updated upstream
        Toast.makeText(MainActivity.this,"Naber",Toast.LENGTH_SHORT).show();
=======
        mToolbar = (Toolbar)findViewById(R.id.toolbar_layout);
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mViewPagerAdapter = new TimelineViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.addFragments(new UserProfile(),"Profile");
        mViewPagerAdapter.addFragments(new Timeline(),"Timeline");
        mViewPagerAdapter.addFragments(new Search(),"Search");


        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);



>>>>>>> Stashed changes

    }

}
