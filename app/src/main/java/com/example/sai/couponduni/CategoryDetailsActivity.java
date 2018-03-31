package com.example.sai.couponduni;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import Adapter.BestOffersAdapter;
import Adapter.TabsPagerAdapter;
import Fragments.BestOffersFragment;
import Fragments.CategoriesFragment;
import Fragments.EachFragment;
import Fragments.EachFragmentSecond;
import Fragments.EachFragmentThird;
import Fragments.TopStoriesFragment;
import Model.OfferData;

public class CategoryDetailsActivity extends AppCompatActivity {

    //viewPager
    ViewPager viewPager;
    //    Toolbar actionBar;
    TabLayout tabLayout;
    protected TabsPagerAdapter mAdapter;
    TextView dataText;
    String[] subCat = new String[5];
    int startPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Bundle intentExtras = getIntent().getExtras();
        if(intentExtras != null){
            String categories = intentExtras.getString("Category");
//            subCat[0] = "All";
            subCat = intentExtras.getStringArray("subCategories");
            startPos = intentExtras.getInt("startPos");
//            subCat
            Log.v("SubCategories", Arrays.toString(subCat));
            setTitle(categories);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPagerCategoryDetails);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutCategoryDetails);
//        actionBar = (Toolbar) findViewById(R.id.toolbar);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(startPos);

    }


    protected class TabsPagerAdapter extends FragmentPagerAdapter {


        private String tabs[] = subCat;/*{ "Telecom", "Mobiles", "Electronics", "Species"};*/


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
          /*  switch (position){
                case 0:
                    return new EachFragment(subCat[position], subCat);
//                    return new Fragment();
                case 1:
                    return new EachFragment(subCat[position], subCat);
//                    return new CategoriesFragment();
                case 2:*/
          switch (position){
              case 0:
                  Bundle data = new Bundle();
                  data.putString("presentHeading", (String) getPageTitle(position));
                  data.putStringArray("allHeadings", subCat);
                  Fragment fragment = new EachFragment();
                  fragment.setArguments(data);
                  return fragment;
              case 1:
                  Bundle data1 = new Bundle();
                  data1.putString("presentHeading", (String) getPageTitle(position));
                  data1.putStringArray("allHeadings", subCat);
                  Fragment fragment1 = new EachFragmentSecond();
                  fragment1.setArguments(data1);
                  return fragment1;
              case 2:
                  Bundle data2 = new Bundle();
                  data2.putString("presentHeading", (String) getPageTitle(position));
                  data2.putStringArray("allHeadings", subCat);
                  Fragment fragment2 = new EachFragmentThird();
                  fragment2.setArguments(data2);
                  return fragment2;
              case 3:
                  Bundle data3 = new Bundle();
                  data3.putString("presentHeading", (String) getPageTitle(position));
                  data3.putStringArray("allHeadings", subCat);
                  Fragment fragment3 = new EachFragmentSecond();
                  fragment3.setArguments(data3);
                  return fragment3;
              case 4:
                  Bundle data4 = new Bundle();
                  data4.putString("presentHeading", (String) getPageTitle(position));
                  data4.putStringArray("allHeadings", subCat);
                  Fragment fragment4 = new EachFragmentSecond();
                  fragment4.setArguments(data4);
                  return fragment4;
          }
//                    return new TopStoriesFragment();
            /*    case 3:
                    return new EachFragment(subCat[position], subCat);
//                    return new CategoriesFragment();
                case 4:
                    return new EachFragment(subCat[position], subCat);
//                    return new TopStoriesFragment();
            }*/
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }



    }



}
