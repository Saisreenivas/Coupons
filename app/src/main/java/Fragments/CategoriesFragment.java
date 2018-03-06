package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sai.couponduni.R;

import java.util.ArrayList;

import Adapter.BestOffersAdapter;
import Adapter.CategoriesAdapter;
import Model.CategoryData;
import Model.OfferData;

/**
 * Created by sai on 1/3/18.
 */

public class CategoriesFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<CategoryData> categoriesDataList;
    private View itemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_categories, container, false);
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment2_recycler);
        mRecyclerView.setHasFixedSize(true);

        categoriesDataList = prepareData();

        setUpData(categoriesDataList);

        categoriesAdapter.notifyDataSetChanged();
        return itemView;
    }


    private ArrayList<CategoryData> prepareData() {
        categoriesDataList =new ArrayList<>();

        CategoryData categoryData = new CategoryData("Fashion",
                new String[]{"Clothing", "Footwear","Bags and Accessories", "Watch and Sunglasses"},
                new String[]{"1850 Offers", "500 Offers", "80 Offers", "90 Offers"});
        categoriesDataList.add(categoryData);

        categoryData = new CategoryData("Model",
                new String[]{"Clothing", "Footwear","Bags and Accessories", "Watch and Sunglasses"},
                new String[]{"1850 Offers", "500 Offers", "80 Offers", "90 Offers"});
        categoriesDataList.add(categoryData);
        categoryData = new CategoryData("Electronics",
                new String[]{"Clothing", "Footwear","Bags and Accessories", "Watch and Sunglasses"},
                new String[]{"1850 Offers", "500 Offers", "80 Offers", "90 Offers"});
        categoriesDataList.add(categoryData);
        categoryData = new CategoryData("Laptops",
                new String[]{"Clothing", "Footwear","Bags and Accessories", "Watch and Sunglasses"},
                new String[]{"1850 Offers", "500 Offers", "80 Offers", "90 Offers"});
        categoriesDataList.add(categoryData);
        categoryData = new CategoryData("Mobile Accessories",
                new String[]{"Clothing", "Footwear","Bags and Accessories", "Watch and Sunglasses"},
                new String[]{"1850 Offers", "500 Offers", "80 Offers", "90 Offers"});
        categoriesDataList.add(categoryData);

        return categoriesDataList;
    }

    private void setUpData(ArrayList<CategoryData> bestOffersDataList) {

//
//        // The number of Columns
//        mLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesAdapter = new CategoriesAdapter(getContext(), categoriesDataList);
        mRecyclerView.setAdapter(categoriesAdapter);


    }
}
