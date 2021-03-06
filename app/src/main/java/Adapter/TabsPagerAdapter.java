package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import Fragments.BestOffersFragment;
import Fragments.CategoriesFragment;
import Fragments.TopStories2Fragment;
import Fragments.TopStoriesFragment;

/**
 * Created by sai on 1/3/18.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = { "FlipKart", /*"Categories", */ "Stores", "icubes"};

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
/*            case 1:
                return new CategoriesFragment();*/
            case 1:
                return new TopStoriesFragment();
            case 2:
                return new TopStories2Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}

