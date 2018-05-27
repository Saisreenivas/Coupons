package Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sai.couponduni.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import Adapter.BestOffersAdapter;
import Adapter.CategoriesAdapter;
import Model.CategoryData;
import Model.OfferData;

import static android.view.View.GONE;
import static com.example.sai.couponduni.MainActivity.CONSTANT_INITIAL_URL;

/**
 * Created by sai on 1/3/18.
 */

public class CategoriesFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<CategoryData> categoriesDataList = new ArrayList<>();
    ArrayList<Integer> catImageLists = new ArrayList<>();

    private View itemView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_categories, container, false);
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment2_recycler);
        mRecyclerView.setHasFixedSize(true);

//        categoriesDataList = prepareData();

        progressBar = (ProgressBar) itemView.findViewById(R.id.prog_bar_categories);

        if(categoriesDataList.size()>=1){
            setUpData(categoriesDataList);
            categoriesAdapter.notifyDataSetChanged();
        }else {
            progressBar.setVisibility(View.VISIBLE);
//            setUpData(categoriesDataList);
            new GetDataForContent(getActivity(), progressBar, mRecyclerView).execute();
//            categoriesAdapter.notifyDataSetChanged();
        }

//        setUpData(categoriesDataList);

        return itemView;
    }

    private ArrayList<Integer> catLists(){
//            ArrayList<int> catImagesList = new ArrayList<>();
        catImageLists = new ArrayList<>();
        catImageLists.add(R.drawable.a1_fashion);
        catImageLists.add(R.drawable.a2_food_and_dining);
        catImageLists.add(R.drawable.a3_travel);
        catImageLists.add(R.drawable.a4_mobile_and_tablet);
        catImageLists.add(R.drawable.a5_beauty_and_health);
        catImageLists.add(R.drawable.a6_recharge);
        catImageLists.add(R.drawable.a7_computers_laptops_and_gaming);
        catImageLists.add(R.drawable.a8_appliances);
        catImageLists.add(R.drawable.a9_home_furnishing_and_decor);
        catImageLists.add(R.drawable.a10_computer_accessories);
        catImageLists.add(R.drawable.a11_flowers_gifts_and_jewellary);
        catImageLists.add(R.drawable.a12_miscellaneous);
        return catImageLists;
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

    private void setUpData(ArrayList<CategoryData> categoriesDataList) {

//
//        // The number of Columns
//        mLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Integer> imageicons = catLists();
        categoriesAdapter = new CategoriesAdapter(getContext(), categoriesDataList, imageicons);
        mRecyclerView.setAdapter(categoriesAdapter);


    }


    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final RecyclerView mRecyclerView;
//        private final int page[];


        public GetDataForContent(Activity parent, ProgressBar progress, RecyclerView mRecyclerView) {
            this.parent = parent;
            this.progress = progress;
//            this.page = page;
            this.mRecyclerView = mRecyclerView;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progress.incrementProgressBy(progr[index]);
            ++index;
        }

        @Override
        protected void onPreExecute() {
            int max = 0;
            for (final int p : progr) {
                max += p;
            }
            progress.setMax(max);
            index = 0;
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            try{
//                URL url = new URL("http://assets.icubeswire.com/dealscoupons/api/getcoupon.php?API_KEY=2f27e0f4118efff145aeecd8367fbb37");
                URL url = new URL(CONSTANT_INITIAL_URL +
                        "/Coupons/categories_my_api.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(45000  /*milliseconds*/ );
                conn.setConnectTimeout(45000  /*milliseconds */);
                conn.setRequestMethod("GET");

                int responseCode=conn.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type "+ "GET");

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    publishProgress();
                    categoriesDataList = convertStreamToString(conn.getInputStream());
                    publishProgress();
                    return  "containsData";
/*
                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();
*/

                }
                else {
                    Log.v("False3" , "false : " + responseCode);
                    return "false3 : " + responseCode;
                }
            }
            catch(Exception e){
                Log.v("Exception-try/catch3" , "false : " + e.getMessage());
                return "try/catch3 : " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(final String result) {
            progress.setVisibility(GONE);
            if(result.equals("containsData")){
//                runResultsOnUi(fullData);
                setUpData(categoriesDataList);

                categoriesAdapter.notifyDataSetChanged();
            }
//            mIsLoading = false;
            Log.v("onPostExecute3", result);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ArrayList<CategoryData> convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 *
		 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
		 */
//        Log.v("StreamtoString", " Hello");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

//        bestOffersDataList = new ArrayList<>();
        ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();
        JSONArray listOfOffers = new JSONArray();
        String line = null;
        JSONObject ja;
//        OfferData offerData = new OfferData();
        Bitmap bmp;
        try {
            while ((line = reader.readLine()) != null) {
//                line =line + reader.readLine();
                Log.v("TopStories3", line);
//                line =line.replace("},]", "}]");

                listOfOffers = new JSONArray(line);

                Log.v("jsonArrayLine3", listOfOffers.getJSONObject(1).toString());

//                ja = new JSONObject(line);
//                Log.v("jsonArrayLine", ja.getJSONArray("allOffersList").toString());
//                listOfOffers = ja.getJSONArray();
                for(int i = 0; i< listOfOffers.length(); i++){
                    OfferData offerData = new OfferData();
                    CategoryData categoryData = new CategoryData();
                    Log.v("categoryOffers", listOfOffers.getJSONObject(i).getString("offer_category")
                            +" Name1: "+ listOfOffers.getJSONObject(i).getJSONObject("data").getString("name1"));

                    categoryData.setCategory(listOfOffers.getJSONObject(i).getString("offer_category"));
                    categoryData.setSubCategories(new String[]{
                            listOfOffers.getJSONObject(i).getJSONObject("data").getString("name"),
                            listOfOffers.getJSONObject(i).getJSONObject("data").getString("name1"),
                            listOfOffers.getJSONObject(i).getJSONObject("data").getString("name2"),
                            listOfOffers.getJSONObject(i).getJSONObject("data").getString("name3"),
                            listOfOffers.getJSONObject(i).getJSONObject("data").getString("name4")});
                    categoryData.setSubCategoryOffers(new String[]{
                            listOfOffers.getJSONObject(i).getJSONObject("data").getInt("data1")+ " Offers",
                            listOfOffers.getJSONObject(i).getJSONObject("data").getInt("data2")+ " Offers",
                            listOfOffers.getJSONObject(i).getJSONObject("data").getInt("data3")+ " Offers",
                            listOfOffers.getJSONObject(i).getJSONObject("data").getInt("data4")+ " Offers"});
//                    categoryData.setSubCategories(new String[]);
//                    categoryData.setCategoryOffers(String.valueOf(listOfOffers.getJSONObject(i).getInt("no_of_offers")));
//                    Log.v("dataSet3", "merchant and descriptionset");
//                    offerData.setCategory(listOfOffers.getJSONObject(i).getString("Category"));
//                    offerData.setCashBackPercentage(listOfOffers.getJSONObject(i).getString("Title"));
//                    offerData.setActivateUrl(listOfOffers.getJSONObject(i).getString("Tracking_URL"));
//                    Log.v("offerData", listOfOffers.getJSONObject(i).getString("store_image"));
//                    URL url = new URL("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAAAyCAMAAAAuj2TTAAAAM1BMVEX////50s7teW3nTDzzpZ32vLbpV0j86ef63drqYlTsbmH3x8L99PPwj4Xym5LvhHn0sKn6vq2WAAADRklEQVR4Xt2Z2Y6DOBBFa/G6sPz/1w4uSJcbC8Ioo1E756HJ7RRWDnaZoMA1eaaKyTA4Mx+4ycPIeFYMjMxqEbMhrqQCw+ONY2YXYFwCYhAVGntOaO903MLEzAuMyso7c9lNMgyKT0Tp1emJOcLI+DoXBIAyJcPiPUB2zKtMyQTDQrQFy+wAzGILDIuZRUcafWisrQHnAINDBF/BGqOBb4A34Bsw9A0z4jEv0S0wNmGKvEP2tg49PKTgBih+i+FN9ad44oaY4YJcbRGegaeOM1ukN9UfYh3/ZrqoY/7TIpaVOxPPf1skcA9n6DHM7LIN/1IEjTH2QxEd5gbiHk4XhbPGpyIi8KGInn6N555IdCFi/q5IoMpkhIyIBZRrEY+6EfcRsVyJhLYw1MJWxGM47cmdyKcgsza7iTVE0epimWqizBu6clFEZAOP4ahzvJHyj0io77rXqDZxZfJtAyC8x+NGNsYsROlWhNrd7Rx9ZMH1IuxYcF7rNqyI6NskmsQHzj4S8awo053ILKPjsvfMOSZWziJCU5dyrgcUEWUGgEmjC09EYOIeV3phWT9kjC+vqxjlmfgUM+9e5hAJdfyEWERkwbUe6Kjz+4VcRKQ5rfzkRcqbYeCSkrjD3jW7fATcWOrxHKVBAgCsfbNzlAsneRa/jShl+Os0C/Mri0l51OzIHdOdiOEGc470uguVVqQ/EjcgoJ7WDnMY4iORlX9B1jGXz0XgfxeZmUMw0clURBJ5cyNim3tXH3Vp3YtMEg/+m6VF7OpfmU6YI0CJHC9FtLuL2SjnqM1+J6LNHowx69tmfyQSpSUsVnlYeb/I+VpExnSz7GKpj4mVGxEZLprZbXEWEcXITCjhmcixSUktFNwniW5EdMd2oY8lHeGNiO6WqRwiiYWpuyE+Egk/rW2ZdcVi30tEdCjbqN8eulhEjJA2ai0Rzf0RoBhxdaa8vvKVRTJ0o+ppN1hO2nDaD/T2iTxcR0QPT/DnQt+P+phJHzJUJDOvMBiOVxXx+l8OMBS2aYfmJfFgc1LiLxHTPsewH+ynXKsiCYRjG0cYhsIbi3Y4B3091Iz49ilq4WZKTEwZBoKaFZRG/lmhrAbbvojwBYTIMcAY/AP/aSKqpfNKWwAAAABJRU5ErkJggg==");
//                    bmp = BitmapFactory.decodeResource(getResources().getDrawable(card_each_item));
//                    offerData.setImg(icon);
//                    offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getInt("url"));
//                    for(int j=0; i< listOfOffers.getJSONObject(i).getJSONObject("imageUrls").length();i++) {
//                        if (Objects.equals(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("resolutionType"), "low")) {
//                            offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("url"));
//                        }
//                    }

                    categoryDataArrayList.add(categoryData);
                    Log.v("listofOffers3FinalCheck", categoryDataArrayList.size() + " ");
                }

               /* for (int i = 0; i < ja.length(); i++) {
//                    JSONObject jo = (JSONObject) ja.get(i);
//                    Log.v("jsonObject", jo.toString());
                }*/

//                sb.append(line + "\n");
//                Log.v("StreamtoString", line+" Hello");
            }
        } catch (IOException e) {
//            Toast.makeText(parent, "Data Overloaded3", Toast.LENGTH_LONG).show();
            Log.v("StreamtoString3", e.getMessage());
        } catch (JSONException e) {
            Log.v("JsonError3",  e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.v("StreamtoString3", e.getMessage());
            }
        }
        Log.v("StreamtoString3",categoryDataArrayList.get(1).getCategory()) ;
        Log.v("DataAdded3", categoryDataArrayList.size() + " ");
        return categoryDataArrayList;
    }


}
