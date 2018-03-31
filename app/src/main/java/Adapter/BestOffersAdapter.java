package Adapter;

import
        android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.couponduni.MerchantDetailsActivity;
import com.example.sai.couponduni.OfferDetailsActivity;
import com.example.sai.couponduni.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Fragments.BestOffersFragment;
import Model.OfferData;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by sai on 1/3/18.
 */

public class BestOffersAdapter extends RecyclerView.Adapter<BestOffersAdapter.ViewHolder>{



    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;
    private Context context;
    private ArrayList<OfferData> offerDataList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView merchantName, categoryName, description, cashBackPercent;
        public RelativeLayout cardFull;
        public ImageButton cardMore;
        public ImageView merchantImg;

        public ViewHolder(View itemView) {
            super(itemView);
            merchantName = (TextView) itemView.findViewById(R.id.card_offers_title);
            categoryName = (TextView) itemView.findViewById(R.id.card_offers_category);
            description = (TextView) itemView.findViewById(R.id.card_offers_description);
            cashBackPercent = (TextView) itemView.findViewById(R.id.card_offers_cashback_percent);
            cardFull = (RelativeLayout) itemView.findViewById(R.id.card_offers_full);
            cardMore = (ImageButton) itemView.findViewById(R.id.card_offers_more);
            merchantImg = (ImageView) itemView.findViewById(R.id.card_merchant_img);
        }
    }

    public BestOffersAdapter(Context context, ArrayList<OfferData> offerDataList) {
        this.context = context;
        this.offerDataList = offerDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;

//        if(viewType == VIEW_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_merchant_complicated, parent, false);
            vh = new ViewHolder(itemView);
//        }
        /*else{
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar_item,parent, false);
            vh = new ViewHolder(itemView);
        }*/

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final OfferData offerData = offerDataList.get(position);
        holder.merchantName.setText(offerData.getMerchant());
        holder.categoryName.setText(offerData.getCategory());
        holder.description.setText(offerData.getBasicDescription());
        holder.cashBackPercent.setText(offerData.getCashBackPercentage());
        if(offerData.getImg() == null){

        }else{
            holder.merchantImg.setImageBitmap(offerData.getImg());
        }
//        holder.merchantImg.setImageResource(offerData.getImgUrl());
//        try {
//            URL url = null;
//            url = new URL(offerData.getImgUrl());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.merchantImg.setImageBitmap(bmp);
//        } catch (java.io.IOException e) {
//            Log.v("IOException: Adapter", e.getMessage());
//        }
//
//        holder.merchantImg.setImageResource();
//        GlideApp.with(this).load(offerData.getImgUrl()).into(holder.merchantImg);

        holder.cardFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfferDetailsActivity.class);
                intent.putExtra("merchantName", offerData.getMerchant());
                intent.putExtra("categoryName", offerData.getCategory());
                intent.putExtra("description", offerData.getBasicDescription());
                intent.putExtra("cashBackPercent", offerData.getCashBackPercentage());
                intent.putExtra("merchantImg", offerData.getImg());
                intent.putExtra("activateUrl", offerData.getActivateUrl());
                context.startActivity(intent);
            }
        });
        holder.cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        offerDataList.get(position).getMerchant() + " "
                                +offerDataList.get(position).getBasicDescription());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this" +
                        "amazing offer");
                context.startActivity(Intent.createChooser(sharingIntent, "Share using"));

//                showPopupMenu(holder.cardMore, position);
            }
        });

    }

    /*private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_offers_more, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int position;
        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.card_offers_share:
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            offerDataList.get(position).getMerchant() + " "
                                    +offerDataList.get(position).getBasicDescription());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this" +
                            "amazing offer");
                    context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
*//*                case R.id.card_offers_show_all:
                    context.startActivity(new Intent(context, MerchantDetailsActivity.class));
//                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;*//*
                default:
            }
            return false;
        }
    }*/

    @Override
    public int getItemCount() {
        return offerDataList.size();
    }
}
