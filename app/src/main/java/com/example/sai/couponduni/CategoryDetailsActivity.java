package com.example.sai.couponduni;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import Adapter.TabsPagerAdapter;
import Fragments.BestOffersFragment;
import Fragments.CategoriesFragment;
import Fragments.TopStoriesFragment;

public class CategoryDetailsActivity extends AppCompatActivity {

    //viewPager
    ViewPager viewPager;
    //    Toolbar actionBar;
    TabLayout tabLayout;
    protected TabsPagerAdapter mAdapter;
    TextView dataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        viewPager = (ViewPager) findViewById(R.id.viewPagerCategoryDetails);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutCategoryDetails);
//        actionBar = (Toolbar) findViewById(R.id.toolbar);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    protected class TabsPagerAdapter extends FragmentPagerAdapter {

        private String[] tabs = { "Telecom", "Mobiles", "Electronics", "Species", "Micelleneus" };

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // overriding getPageTitle()
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BestOffersFragment();
//                    return new Fragment();
                case 1:
                    return new BestOffersFragment();
//                    return new CategoriesFragment();
                case 2:
                    return new BestOffersFragment();
//                    return new TopStoriesFragment();
                case 3:

                    return new BestOffersFragment();
//                    return new CategoriesFragment();
                case 4:
                    return new BestOffersFragment();
//                    return new TopStoriesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }


    }
}
