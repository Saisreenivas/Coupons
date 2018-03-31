package Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import Adapter.TabsPagerAdapter;
import Model.OfferData;

import static android.view.View.GONE;

/**
 * Created by sai on 12/3/18.
 */


public class EachFragmentThird extends Fragment {
    String presentHeading;
    String[] allHeadings;
    View itemView;
    RecyclerView mRecyclerView;
    ProgressBar progress;
    ArrayList<OfferData> bestOffersDataList = new ArrayList<>();
    BestOffersAdapter bestOffersAdapter;
    final int page[] = {1};
    int pageLimit = 50;
    private boolean mIsLoading =false;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<OfferData> fullData = new ArrayList<>();
//
//    public EachFragment(String presentHeading, String[] allHeadings) {
//        this.allHeadings = allHeadings;
//        this.presentHeading =presentHeading;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_best_offers, container, false);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment1_recycler);
        mRecyclerView.setHasFixedSize(true);
        progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

//        tabs = getArguments().get("Title");
        if(getArguments() != null){
            Bundle data = getArguments();
            allHeadings = data.getStringArray("allHeadings");
            presentHeading = data.getString("presentHeading");
        }
        if(bestOffersDataList.size()>=1){
            setUpData(bestOffersDataList);
            bestOffersAdapter.notifyDataSetChanged();
        }else {
            progress.setVisibility(View.VISIBLE);
            allHeadings[1] = allHeadings[1].replace(" ", "%20");
            allHeadings[2] = allHeadings[2].replace(" ", "%20");
            allHeadings[3] = allHeadings[3].replace(" ", "%20");
            allHeadings[4] = allHeadings[4].replace(" ", "%20");
            new GetDataForContent(getActivity(), progress, mRecyclerView, page, allHeadings, presentHeading).execute();
        }

        return itemView;
    }


    private void setUpData(ArrayList<OfferData> bestOffersDataList) {

//        bestOffersDataList.clear();
        Log.v("dataCountEach", bestOffersDataList.size()+ " ");
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        bestOffersAdapter = new BestOffersAdapter(getContext(), bestOffersDataList);
        bestOffersAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(bestOffersAdapter);

/*

        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading)
                    return;
                int visibleItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getChildCount();
                int totalItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount();
                int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount-pageLimit/2) {
                    //End of list
                    new GetDataForContent(getActivity(), progress, recyclerView, page, allHeadings, presentHeading).execute();
                    mIsLoading = true;

                }
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
*/


    }

    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final RecyclerView mRecyclerView;
        private final int[] page;
        private final String presentHeading;
        private final String[] allHeadings;

        public GetDataForContent(Activity parent, ProgressBar progress, RecyclerView mRecyclerView,
                                 int[] page, String[] allHeadings, String presentHeading) {
            this.parent = parent;
            this.progress = progress;
            this.mRecyclerView = mRecyclerView;
            this.page= page;
            this.allHeadings = allHeadings;
            this.presentHeading = presentHeading;
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


                Log.v("eachFragment",allHeadings[1] + " " + allHeadings[2] + " " + allHeadings[3]
                        + " " + allHeadings[4] + " " + presentHeading);

//                URL url = new URL("https://affiliate-api.flipkart.net/affiliate/offers/v1/all/json");
                URL url = new URL("http://couponkhajana.com/android/Coupons/category_each_api.php?para1=" + allHeadings[1]+ "&para2=" + allHeadings[2] + "&para3=" + allHeadings[3] + "&para4=" + allHeadings[4] + "&present=" + presentHeading );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/ );
                conn.setConnectTimeout(15000  /*milliseconds */);
                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Fk-Affiliate-Id", "komalvash");
//                conn.setRequestProperty("Fk-Affiliate-Token", "7c6e0e21eac043039163b4c5fb07a052");

                int responseCode=conn.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type "+ "GET");

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    publishProgress();
                    Log.v("ContainsDataOf", presentHeading);
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
                    return "false : " + responseCode + presentHeading;
                }
            }
            catch(Exception e){
                Log.v("Exception-try/catch" , "false : " + e.getMessage());
                return "try/catch : " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(final String result) {
            progress.setVisibility(GONE);
            if(result.equals("containsData")){
//                runResultsOnUi(fullData);
//                setUpData(fullData);
//                page[0]++;
//                Log.v("pageNumber", page[0]+" ");
//                if(page[0] == 2) {
                bestOffersDataList.clear();
                setUpData(fullData);
//                fullData.clear();

//                }
                bestOffersAdapter.notifyDataSetChanged();
            }
//            mIsLoading = false;
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
        JSONArray ja;
//        OfferData offerData = new OfferData();
        Bitmap bmp;
        try {
            while ((line = reader.readLine()) != null) {
                ja = new JSONArray(line);

                Log.v("jsonArrayLine", ja.toString());
//                listOfOffers = ja.getJSONArray();
                listOfOffers = ja;
                for(int i=0;i< listOfOffers.length(); i++){
                    OfferData offerData = new OfferData();
                    Log.v("eachFragment"+i, listOfOffers.getJSONObject(i).getString("title") +
                            listOfOffers.getJSONObject(i).getString("description"));

                    offerData.setMerchant(listOfOffers.getJSONObject(i).getString("title"));
                    offerData.setBasicDescription(listOfOffers.getJSONObject(i).getString("description"));
                    offerData.setCategory(listOfOffers.getJSONObject(i).getString("category"));
                    offerData.setCashBackPercentage(listOfOffers.getJSONObject(i).getString("availability"));
//                    URL url = new URL(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getString("url"));
//                    URL url = new URL(listOfOffers.getJSONObject(i).getString("image_url"));
//                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    offerData.setImg(bmp);
                    offerData.setActivateUrl(listOfOffers.getJSONObject(i).getString("url"));
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
        Log.v("DataAdded1", bestOffersDataList.size()+ " ");
        return bestOffersDataList;
    }





}
