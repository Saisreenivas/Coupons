package com.example.sai.couponduni;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.view.View.GONE;

public class OfferDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton cardOffersMore;
    ImageView cardExpandMore;
    TextView offerDetailsData;
    Bundle extras;
    Button activateDeal;
    String categoryName, descriptionData, cashbackPercentage, companyName, activateUrlLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        extras = getIntent().getExtras();
        if(extras!=null){
            setUp();
        }
    }

    private void setUp() {
        cardOffersMore = (ImageButton) findViewById(R.id.card_offers_more);
        cardOffersMore.setVisibility(GONE);

        cardExpandMore = (ImageView) findViewById(R.id.offer_details_more);
        cardExpandMore.setImageResource(R.drawable.card_expand_more);
        cardExpandMore.setTag(R.drawable.card_expand_more);
        cardExpandMore.setVisibility(GONE);
//        cardExpandMore.setOnClickListener(this);

        offerDetailsData = (TextView) findViewById(R.id.offer_details_data);
        ImageView cardMerchantImg = (ImageView) findViewById(R.id.card_merchant_img);
        TextView merchantName = (TextView) findViewById(R.id.card_offers_title);
        TextView category = (TextView) findViewById(R.id.card_offers_category);
        TextView description = (TextView) findViewById(R.id.card_offers_description);
        TextView cashBackPercent = (TextView) findViewById(R.id.card_offers_cashback_percent);

        categoryName = extras.getString("categoryName");
        descriptionData = extras.getString("description");
        cashbackPercentage = extras.getString("cashBackPercent");
        companyName =extras.getString("merchantName");
        activateUrlLink = extras.getString("activateUrl");

        cardMerchantImg.setImageBitmap((Bitmap) extras.get("merchantImg"));
        merchantName.setText(extras.getString("merchantName"));
        category.setText(extras.getString("categoryName"));
        description.setText(extras.getString("description"));
        cashBackPercent.setText(extras.getString("cashBackPercent"));
        if (categoryName.length() > descriptionData.length()) {
            offerDetailsData.setText(categoryName);
        }else{
            offerDetailsData.setText(descriptionData);
        }

        activateDeal = (Button) findViewById(R.id.activateDeal);
        activateDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("activateUrl", extras.getString("activateUrl"));
                Uri uri = Uri.parse(extras.getString("activateUrl")); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offers_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.card_offers_share){
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Check out this amazing offer: " + companyName + " "+ categoryName
                                + " "  +descriptionData + " " + activateUrlLink);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this" +
                        " amazing offer");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                return true;
            }else
               return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.offer_details_more:
                if(cardExpandMore.getResources().getDrawable(R.drawable.card_expand_more).isVisible()){
                    cardExpandMore.setTag(R.drawable.card_expand_less);
                    cardExpandMore.setImageResource(R.drawable.card_expand_less);
                }else{
                    cardExpandMore.setTag(R.drawable.card_expand_more);
                    cardExpandMore.setImageResource(R.drawable.card_expand_more);
                }
                break;
        }*/
    }
}
