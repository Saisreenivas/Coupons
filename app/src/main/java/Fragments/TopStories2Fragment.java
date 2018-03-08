package Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

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
import Model.OfferData;

import static android.view.View.GONE;

/**
 * Created by sai on 1/3/18.
 */

public class TopStories2Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BestOffersAdapter bestOffersAdapter;

    private ArrayList<OfferData> bestOffersDataList = new ArrayList<>();
    private View itemView;
    private ProgressBar progress;
    private ArrayList<OfferData> fullData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_best_offers, container, false);
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment1_recycler);
        mRecyclerView.setHasFixedSize(true);

        progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

//        bestOffersDataList = prepareData();

        if(bestOffersDataList.size()>=1){
            setUpData(bestOffersDataList);
            bestOffersAdapter.notifyDataSetChanged();
        }else {
            progress.setVisibility(View.VISIBLE);
            new GetDataForContent(getActivity(), progress, mRecyclerView).execute();
        }
//        setUpData(bestOffersDataList);

//        bestOffersAdapter.notifyDataSetChanged();
        return itemView;
    }


    private ArrayList<OfferData> prepareData() {
        bestOffersDataList =new ArrayList<>();

        OfferData offerData = new OfferData("Amazon", "Fashion & Tech",
                "Super Value Day: Save Up to Rs. 2400" +
                        " as Amazon Pay Balance on your monthly shopping " +
                        "(ICICI Card Holders, 1st-3rd Mar)",
                "UPTO 25%CD Voucher rewards");
        bestOffersDataList.add(offerData);

        offerData = new OfferData("Flipkart", "Fashion & Tech",
                "Super Value Day: Save Up to Rs. 2400" +
                        " as Amazon Pay Balance on your monthly shopping " +
                        "(ICICI Card Holders, 1st-3rd Mar)",
                "UPTO 25%CD Voucher rewards");
        bestOffersDataList.add(offerData);

        offerData = new OfferData("Minthra", "Fashion & Tech",
                "Super Value Day: Save Up to Rs. 2400" +
                        " as Amazon Pay Balance on your monthly shopping " +
                        "(ICICI Card Holders, 1st-3rd Mar)",
                "UPTO 25%CD Voucher rewards");
        bestOffersDataList.add(offerData);

        offerData = new OfferData("SmartBuy", "Fashion & Tech",
                "Super Value Day: Save Up to Rs. 2400" +
                        " as Amazon Pay Balance on your monthly shopping " +
                        "(ICICI Card Holders, 1st-3rd Mar)",
                "UPTO 25%CD Voucher rewards");
        bestOffersDataList.add(offerData);
        return bestOffersDataList;
    }

    private void setUpData(ArrayList<OfferData> bestOffersDataList) {

//
//        // The number of Columns
//        mLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bestOffersAdapter = new BestOffersAdapter(getContext(), bestOffersDataList);
        mRecyclerView.setAdapter(bestOffersAdapter);


    }


    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final RecyclerView mRecyclerView;


        public GetDataForContent(Activity parent, ProgressBar progress, RecyclerView mRecyclerView) {
            this.parent = parent;
            this.progress = progress;
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
                URL url = new URL("http://192.168.1.105/Coupons/icubes.php");
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
                    fullData = convertStreamToString(conn.getInputStream());
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
                setUpData(fullData);
                bestOffersAdapter.notifyDataSetChanged();
            }
            Log.v("onPostExecute3", result);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ArrayList<OfferData> convertStreamToString(InputStream is) {
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

        ArrayList<OfferData> bestOffersDataList = new ArrayList<>();
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
                for(int i = 0; i< 100; i++){
                    OfferData offerData = new OfferData();
                    Log.v("listOfOffers", listOfOffers.getJSONObject(i).getString("Campaign_Name") +
                            listOfOffers.getJSONObject(i).getString("Category"));

                    offerData.setMerchant(listOfOffers.getJSONObject(i).getString("Campaign_Name"));
                    offerData.setBasicDescription(listOfOffers.getJSONObject(i).getString("Description"));
                    Log.v("dataSet3", "merchant and descriptionset");
                    offerData.setCategory(listOfOffers.getJSONObject(i).getString("Category"));
                    offerData.setCashBackPercentage(listOfOffers.getJSONObject(i).getString("Title"));
//                    Log.v("offerData", listOfOffers.getJSONObject(i).getString("store_image"));
//                    URL url = new URL("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAAAyCAMAAAAuj2TTAAAAM1BMVEX////50s7teW3nTDzzpZ32vLbpV0j86ef63drqYlTsbmH3x8L99PPwj4Xym5LvhHn0sKn6vq2WAAADRklEQVR4Xt2Z2Y6DOBBFa/G6sPz/1w4uSJcbC8Ioo1E756HJ7RRWDnaZoMA1eaaKyTA4Mx+4ycPIeFYMjMxqEbMhrqQCw+ONY2YXYFwCYhAVGntOaO903MLEzAuMyso7c9lNMgyKT0Tp1emJOcLI+DoXBIAyJcPiPUB2zKtMyQTDQrQFy+wAzGILDIuZRUcafWisrQHnAINDBF/BGqOBb4A34Bsw9A0z4jEv0S0wNmGKvEP2tg49PKTgBih+i+FN9ad44oaY4YJcbRGegaeOM1ukN9UfYh3/ZrqoY/7TIpaVOxPPf1skcA9n6DHM7LIN/1IEjTH2QxEd5gbiHk4XhbPGpyIi8KGInn6N555IdCFi/q5IoMpkhIyIBZRrEY+6EfcRsVyJhLYw1MJWxGM47cmdyKcgsza7iTVE0epimWqizBu6clFEZAOP4ahzvJHyj0io77rXqDZxZfJtAyC8x+NGNsYsROlWhNrd7Rx9ZMH1IuxYcF7rNqyI6NskmsQHzj4S8awo053ILKPjsvfMOSZWziJCU5dyrgcUEWUGgEmjC09EYOIeV3phWT9kjC+vqxjlmfgUM+9e5hAJdfyEWERkwbUe6Kjz+4VcRKQ5rfzkRcqbYeCSkrjD3jW7fATcWOrxHKVBAgCsfbNzlAsneRa/jShl+Os0C/Mri0l51OzIHdOdiOEGc470uguVVqQ/EjcgoJ7WDnMY4iORlX9B1jGXz0XgfxeZmUMw0clURBJ5cyNim3tXH3Vp3YtMEg/+m6VF7OpfmU6YI0CJHC9FtLuL2SjnqM1+J6LNHowx69tmfyQSpSUsVnlYeb/I+VpExnSz7GKpj4mVGxEZLprZbXEWEcXITCjhmcixSUktFNwniW5EdMd2oY8lHeGNiO6WqRwiiYWpuyE+Egk/rW2ZdcVi30tEdCjbqN8eulhEjJA2ai0Rzf0RoBhxdaa8vvKVRTJ0o+ppN1hO2nDaD/T2iTxcR0QPT/DnQt+P+phJHzJUJDOvMBiOVxXx+l8OMBS2aYfmJfFgc1LiLxHTPsewH+ynXKsiCYRjG0cYhsIbi3Y4B3091Iz49ilq4WZKTEwZBoKaFZRG/lmhrAbbvojwBYTIMcAY/AP/aSKqpfNKWwAAAABJRU5ErkJggg==");
//                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    offerData.setImg(bmp);
                    offerData.setActivateUrl(listOfOffers.getJSONObject(i).getString("Tracking_URL"));
//                    offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getInt("url"));
//                    for(int j=0; i< listOfOffers.getJSONObject(i).getJSONObject("imageUrls").length();i++) {
//                        if (Objects.equals(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("resolutionType"), "low")) {
//                            offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("url"));
//                        }
//                    }

                    bestOffersDataList.add(offerData);
                    Log.v("listofOffers3FinalCheck", bestOffersDataList.size() + " ");
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
        Log.v("StreamtoString3",bestOffersDataList.get(1).getMerchant()) ;
        return bestOffersDataList;
    }


}
