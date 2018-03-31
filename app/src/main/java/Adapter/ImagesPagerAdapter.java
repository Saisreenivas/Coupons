package Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sai.couponduni.MainActivity;
import com.example.sai.couponduni.R;

import Fragments.AmazonFragment;
import Fragments.BestOffersFragment;
import Fragments.CategoriesFragment;
import Fragments.TopStories2Fragment;
import Fragments.TopStoriesFragment;

/**
 * Created by sai on 29/3/18.
 */

public class ImagesPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = { "  ", "  ",  "  "};

    public ImagesPagerAdapter(FragmentManager fm) {
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
                return new ImageFragment(position);
            case 1:
                return new ImageFragment(position);
            case 2:
                return new ImageFragment(position);
//            case 3:
//                return new TopStories2Fragment();
//            case 4:
//                return new AmazonFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @SuppressLint("ValidFragment")
    public static class ImageFragment extends Fragment {
        int position;
        View itemView;
        ImageView imageView;
//        Activity activity = getActivity();


        public ImageFragment(int position) {
            this.position = position;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            itemView = inflater.inflate(R.layout.fragment_single_image, container, false);

            imageView = (ImageView) itemView.findViewById(R.id.single_image);
            Drawable drawable = itemView.getResources().getDrawable(R.drawable.icon);

            switch (position){
                case 0:
                    drawable = getResources().getDrawable(R.drawable.banner1);
                    break;
                case 1:
                    drawable = getResources().getDrawable(R.drawable.banner2);
                    break;
                case 2:
                    drawable = getResources().getDrawable(R.drawable.banner3);
                    break;
            }

            imageView.setImageDrawable(drawable);
            imageView.setFitsSystemWindows(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return itemView;
        }
    }
}

