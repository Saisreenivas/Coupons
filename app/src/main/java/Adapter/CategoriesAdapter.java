package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sai.couponduni.CategoryDetailsActivity;
import com.example.sai.couponduni.MerchantDetailsActivity;
import com.example.sai.couponduni.OfferDetailsActivity;
import com.example.sai.couponduni.R;

import java.util.ArrayList;

import Model.CategoryData;
import Model.OfferData;

import static android.view.View.GONE;

/**
 * Created by sai on 1/3/18.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<CategoryData> CategoryDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTitle, subCat1, subCat2, subCat3, subCat4, subOff1, subOff2, subOff3, subOff4;
        public LinearLayout cardFull, cardOffs, cardSubs;
        public ImageView cardMore;

        public ViewHolder(View itemViewm) {
            super(itemViewm);
            categoryTitle = (TextView) itemViewm.findViewById(R.id.card_category_title);
            subCat1 = (TextView) itemViewm.findViewById(R.id.card_category_sub1);
            subCat2 = (TextView) itemViewm.findViewById(R.id.card_category_sub2);
            subCat3 = (TextView) itemViewm.findViewById(R.id.card_category_sub3);
            subCat4 = (TextView) itemViewm.findViewById(R.id.card_category_sub4);
            subOff1 = (TextView) itemViewm.findViewById(R.id.card_category_offer1);
            subOff2 = (TextView) itemViewm.findViewById(R.id.card_category_offer2);
            subOff3 = (TextView) itemViewm.findViewById(R.id.card_category_offer3);
            subOff4 = (TextView) itemViewm.findViewById(R.id.card_category_offer4);
            cardFull = (LinearLayout) itemViewm.findViewById(R.id.card_category_basic_data);
            cardMore = (ImageView) itemViewm.findViewById(R.id.card_category_more);
            cardOffs = (LinearLayout) itemViewm.findViewById(R.id.cardcategory_offs);
            cardSubs = (LinearLayout) itemViewm.findViewById(R.id.card_category_subs);
        }

    }

    public CategoriesAdapter(Context context, ArrayList<CategoryData> CategoryDataList) {
        this.context = context;
        this.CategoryDataList = CategoryDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewm = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_categories_list, parent, false);

        return new CategoriesAdapter.ViewHolder(itemViewm);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CategoryData CategoryData = CategoryDataList.get(position);
                String[] subCats = CategoryData.getSubCategories();
        holder.categoryTitle.setText(CategoryData.getCategory() + " ");
                holder.subCat1.setText(subCats[0]);
                holder.subCat2.setText(subCats[1]);
                holder.subCat3.setText(subCats[2]);
                holder.subCat4.setText(subCats[3]);
                String[] subOffers = CategoryData.getSubCategoryOffers();
                holder.subOff1.setText(subOffers[0]);
                holder.subOff2.setText(subOffers[1]);
                holder.subOff3.setText(subOffers[2]);
                holder.subOff4.setText(subOffers[3]);

        //*
        holder.cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cardOffs.getVisibility() == GONE) {
                    holder.cardMore.setTag(R.drawable.card_expand_more);
                    holder.cardOffs.setVisibility(View.VISIBLE);
                    holder.cardSubs.setVisibility(View.VISIBLE);
                } else {
                    holder.cardMore.setTag(R.drawable.card_expand_less);
                    holder.cardOffs.setVisibility(GONE);
                    holder.cardSubs.setVisibility(GONE);
                }

            }
        });
        holder.cardFull.setOnClickListener(this);
        holder.subCat1.setOnClickListener(this);
        holder.subCat2.setOnClickListener(this);
        holder.subCat3.setOnClickListener(this);
        holder.subCat4.setOnClickListener(this);
    }


    /*
        @Override
        public void onBindViewHolder(@NonNull final CategoriesAdapter.ViewHolder holder, final int position) {
            CategoryData CategoryData = CategoryDataList.get(position);
    //        String[] subCats = CategoryData.getSubCategories();
            holder.categoryTitle.setText(CategoryData.getCategory() + " ");
    //        holder.subCat1.setText(subCats[0]);
    //        holder.subCat2.setText(subCats[1]);
    //        holder.subCat3.setText(subCats[2]);
    //        holder.subCat4.setText(subCats[3]);
    //        String[] subOffers = CategoryData.getSubCategoryOffers();
    //        holder.subOff1.setText(subOffers[0]);
    //        holder.subOff2.setText(subOffers[1]);
    //        holder.subOff3.setText(subOffers[2]);
    //        holder.subOff4.setText(subOffers[3]);

    *//*
        holder.cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cardOffs.getVisibility() == GONE) {
                    holder.cardOffs.setVisibility(View.VISIBLE);
                    holder.cardSubs.setVisibility(View.VISIBLE);
                }else{
                    holder.cardOffs.setVisibility(GONE);
                    holder.cardSubs.setVisibility(GONE);
                }

            }
        });
        holder.cardFull.setOnClickListener(this);
        holder.subCat1.setOnClickListener(this);
        holder.subCat2.setOnClickListener(this);
        holder.subCat3.setOnClickListener(this);
        holder.subCat4.setOnClickListener(this);*//*
    }*/
    @Override
    public void onClick(View view) {

        context.startActivity(new Intent(context, CategoryDetailsActivity.class));
    }

    @Override
    public int getItemCount() {
        return CategoryDataList.size();
    }
}
