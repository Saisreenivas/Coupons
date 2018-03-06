package com.example.sai.couponduni;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

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

import Model.OfferData;

public class EmptyLoadingActivity extends AppCompatActivity {

    ProgressBar progress;
    ArrayList<OfferData> fullData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_loading);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        Drawable draw = getResources().getDrawable(R.drawable.coupon);
        progress.setProgressDrawable(draw);

        new GetDataForContent(this, progress).execute();

    }


    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;


        public GetDataForContent(Activity parent, ProgressBar progress) {
            this.parent = parent;
            this.progress = progress;
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
                URL url = new URL("https://affiliate-api.flipkart.net/affiliate/offers/v1/all/json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/ );
                conn.setConnectTimeout(15000  /*milliseconds */);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Fk-Affiliate-Id", "komalvash");
                conn.setRequestProperty("Fk-Affiliate-Token", "7c6e0e21eac043039163b4c5fb07a052");

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
                    Log.v("False" , "false : " + responseCode);
                    return "false : " + responseCode;
                }
            }
            catch(Exception e){
                Log.v("Exception-try/catch" , "false : " + e.getMessage());
                return "try/catch : " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(final String result) {
         /*   progress.setVisibility(GONE);
            if(result.equals("containsData")){
//                runResultsOnUi(fullData);
                setUpData(fullData);
                bestOffersAdapter.notifyDataSetChanged();
            }
*/
            Intent intent = new Intent(EmptyLoadingActivity.this, MainActivity.class);
            intent.putExtra("AllData", fullData);
            startActivity(intent);
            finish();
            Log.v("onPostExecute", result);

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
                ja = new JSONObject(line);

                Log.v("jsonArrayLine", ja.getJSONArray("allOffersList").toString());
                listOfOffers = ja.getJSONArray("allOffersList");
                for(int i=0;i< 20; i++){
                    OfferData offerData = new OfferData();
                    Log.v("listOfOffers", listOfOffers.getJSONObject(i).getString("title") +
                            listOfOffers.getJSONObject(i).getString("description"));
                    offerData.setMerchant(listOfOffers.getJSONObject(i).getString("title"));
                    offerData.setBasicDescription(listOfOffers.getJSONObject(i).getString("description"));
                    offerData.setCategory(listOfOffers.getJSONObject(i).getString("category"));
                    offerData.setCashBackPercentage(listOfOffers.getJSONObject(i).getString("availability"));
                    URL url = new URL(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getString("url"));
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    offerData.setImg(bmp);
//                    offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getInt("url"));
//                    for(int j=0; i< listOfOffers.getJSONObject(i).getJSONObject("imageUrls").length();i++) {
//                        if (Objects.equals(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("resolutionType"), "low")) {
//                            offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("url"));
//                        }
//                    }

                    bestOffersDataList.add(offerData);
                }

               /* for (int i = 0; i < ja.length(); i++) {
//                    JSONObject jo = (JSONObject) ja.get(i);
//                    Log.v("jsonObject", jo.toString());
                }*/

//                sb.append(line + "\n");
//                Log.v("StreamtoString", line+" Hello");
            }
        } catch (IOException e) {
            Log.v("StreamtoString", e.getMessage());
        } catch (JSONException e) {
            Log.v("JsonError",  e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.v("StreamtoString", e.getMessage());
            }
        }
        Log.v("StreamtoString",bestOffersDataList.get(1).getMerchant()) ;
        return bestOffersDataList;
    }

}
