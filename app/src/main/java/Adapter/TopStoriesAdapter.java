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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sai.couponduni.MerchantDetailsActivity;
import com.example.sai.couponduni.OfferDetailsActivity;
import com.example.sai.couponduni.R;

import java.util.ArrayList;

import Model.CategoryData;
import Model.OfferData;

/**
 * Created by sai on 1/3/18.
 */

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<CategoryData> categoryDataArrayList ;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryTitle, subCat1, subCat2, subCat3, subCat4, subOff1, subOff2, subOff3, subOff4;
        public LinearLayout cardFull;
        public ImageButton cardMore;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.card_category_title);
            subCat1 = (TextView) itemView.findViewById(R.id.card_category_sub1);
            subCat2 = (TextView) itemView.findViewById(R.id.card_category_sub2);
            subCat3 = (TextView) itemView.findViewById(R.id.card_category_sub3);
            subCat4 = (TextView) itemView.findViewById(R.id.card_category_sub4);
            subOff1 = (TextView) itemView.findViewById(R.id.card_category_offer1);
            subOff2 = (TextView) itemView.findViewById(R.id.card_category_offer2);
            subOff3 = (TextView) itemView.findViewById(R.id.card_category_offer3);
            subOff4 = (TextView) itemView.findViewById(R.id.card_category_offer4);
            cardFull = (LinearLayout) itemView.findViewById(R.id.card_category_basic_data);
            cardMore = (ImageButton) itemView.findViewById(R.id.card_category_more);
        }
    }

    public TopStoriesAdapter(Context context, ArrayList<CategoryData> categoryDataArrayList) {
        this.context = context;
        this.categoryDataArrayList =  categoryDataArrayList;
    }

    @Override
    public TopStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_merchant_simple, parent, false);

        return new TopStoriesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopStoriesAdapter.ViewHolder holder, final int position) {
        /*OfferData offerData = offerDataList.get(position);
        holder.merchantName.setText(offerData.getMerchant());
        holder.categoryName.setText(offerData.getCategory());
        holder.description.setText(offerData.getBasicDescription());
        holder.cashBackPercent.setText(offerData.getCashBackPercentage());

        holder.cardFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfferDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        holder.cardMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.cardMore, position);
            }
        });*/
    }

    private void showPopupMenu(View view, int position) {
        /*// inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_offers_more, popup.getMenu());
        popup.setOnMenuItemClickListener(new CategoriesAdapter.MyMenuItemClickListener(position));
        popup.show();*/
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            /*switch (menuItem.getItemId()) {
                case R.id.card_offers_share:
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            categoryDataArrayList.get(position).getMerchant() + " "
                                    +categoryDataArrayList.get(position).getBasicDescription());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this" +
                            "amazing offer");
                    context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.card_offers_show_all:
                    context.startActivity(new Intent(context, MerchantDetailsActivity.class));
                    ((Activity)context).finish();
//                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }*/
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }
}
