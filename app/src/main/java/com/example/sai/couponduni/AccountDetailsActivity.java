package com.example.sai.couponduni;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Model.OfferData;

import static android.view.View.GONE;

public class AccountDetailsActivity extends AppCompatActivity {

    EditText accHolder, accIfsc, accNo;
    Button accBtn;
    ProgressBar progress;
    TextInputLayout accHolderLayout, accIfscLayout,accNoLayout;
    String username;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        session = new SessionManager(getApplicationContext());
        username = session.getUserDetails().get(SessionManager.KEY_NAME);

        accHolder =(EditText) findViewById(R.id.acc_holder_text);
        accIfsc = (EditText) findViewById(R.id.acc_ifsc_text);
        accNo = (EditText) findViewById(R.id.acc_no_text);
        accBtn = (Button) findViewById(R.id.acc_save_btn);
        progress = (ProgressBar) findViewById(R.id.progress_acc);
        accHolderLayout = (TextInputLayout) findViewById(R.id.acc_holder_text_layout);
        accIfscLayout = (TextInputLayout) findViewById(R.id.acc_ifsc_text_layout);
        accNoLayout = (TextInputLayout) findViewById(R.id.acc_no_text_layout);

        progress.setVisibility(GONE);
        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAccHolder()){
                    return;
                }
                if(!checkAccIfsc()){
                    return;
                }
                if(!checkAccNo()){
                    return;
                }

                String accHolderme = (accHolder.getText().toString()).replace(" ", "%20");
                
                new GetDataForContent(getParent(), progress, accHolderme, accIfsc.getText().toString(), accNo.getText().toString(), username).execute();
            }
        });
    }

    private boolean checkAccNo() {
        String password = accNo.getText().toString().trim();
        if (password.isEmpty()) {

            accNoLayout.setError("Account holder field is empty");
            accNo.setError("Account Holder Name");
            requestFocus(accNo);
            return false;
        }
        accNoLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkAccIfsc() {
        String password = accIfsc.getText().toString().trim();
        if (password.isEmpty()) {

            accIfscLayout.setError("Account holder field is empty");
            accIfsc.setError("Account Holder Name");
            requestFocus(accIfsc);
            return false;
        }
        accIfscLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkAccHolder() {
        String password = accHolder.getText().toString().trim();
        if (password.isEmpty()) {

            accHolderLayout.setError("Account holder field is empty");
            accHolder.setError("Account Holder Name");
            requestFocus(accHolder);
            return false;
        }
        accHolderLayout.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }




    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final String accHolder, accIfsc, accNo, username;

        public GetDataForContent(Activity parent, ProgressBar progress, String accHolder, String accIfsc, String accNo, String username) {
            this.parent = parent;
            this.progress = progress;
            this.accHolder = accHolder;
            this.accIfsc = accIfsc;
            this.accNo = accNo;
            this.username =username;
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
            /*18.217.104.249*/
            try{
                URL url = new URL("http://couponkhajana.com/android/Coupons/updating_acc_details_api.php?accName="
                        + accHolder + "&accIfsc=" + accIfsc + "&accNo=" + accNo + "&username=" + username + "&redeem="+ "1"
                        + "&accBal=" + session.getUserDetails().get(SessionManager.KEY_WALLET_BAL));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  );
                conn.setConnectTimeout(15000  );
                conn.setRequestMethod("GET");

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    JSONObject listOfOffers = new JSONObject();
                    String line = null;
                    JSONObject ja;
                    try {
                        while ((line = reader.readLine()) != null) {
                            ja = new JSONObject(line);
                            listOfOffers = ja;
                            if(ja.getBoolean("success")){
                                return "containsData";
                            }else{
                                return "data is not saved";
                            }
                            /*for(int i=0;i< listOfOffers.length(); i++){
                                OfferData offerData = new OfferData();
                                Log.v("listOfOffers", listOfOffers.getJSONObject(i).getString("title") +
                                        listOfOffers.getJSONObject(i).getString("description"));

                                offerData.setMerchant(listOfOffers.getJSONObject(i).getString("title"));

                            }*/

                        }
                    } catch (IOException e) {
                        Log.v("StreamtoString", e.getMessage());
                        return "error";
                    } catch (JSONException e) {
                        Log.v("JsonError",  e.getMessage());
                        return "error";
                    } finally {
                        try {
                            conn.getInputStream().close();
                        } catch (IOException e) {
                            Log.v("StreamtoString", e.getMessage());
                            return "error";
                        }
                    }

                    return "error";


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
            progress.setVisibility(GONE);

            if(result.equals("containsData")){
                Toast.makeText(AccountDetailsActivity.this,
                        "Account Details Successfully Added.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AccountDetailsActivity.this, MainActivity.class));
                finish();
            }
            Log.v("onPostExecute", result);

        }

    }


}
