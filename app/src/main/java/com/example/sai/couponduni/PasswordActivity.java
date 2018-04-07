package com.example.sai.couponduni;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import Model.User;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;
import static android.view.View.GONE;
import static com.example.sai.couponduni.MainActivity.CONSTANT_INITIAL_URL;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    EditText txtPassword;
    ImageButton passwordShowHide;
    Button signInBtn;
    String txtUsername;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;
//    FirebaseAuth auth;
    TextInputLayout passwordLayout;
    ProgressBar progressBar;
    String referral_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        txtPassword = (EditText) findViewById(R.id.pass_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_password);
        progressBar.setVisibility(GONE);
        passwordShowHide = (ImageButton) findViewById(R.id.pass_show_hide);
        signInBtn = (Button) findViewById(R.id.pass_act_signin);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);


        passwordLayout = (TextInputLayout) findViewById(R.id.login_input_layout_password);
        passwordShowHide.setImageResource(R.drawable.password_view);
        passwordShowHide.setTag(R.drawable.password_view);
        passwordShowHide.setOnClickListener(this);
        signInBtn.setOnClickListener(this);

//        auth = FirebaseAuth.getInstance();

        txtUsername = getIntent().getStringExtra("name");
        if(txtUsername != null){
            setCollapsedToolbarTitle(txtUsername);
        }

        // Session Manager
        session = new SessionManager(getApplicationContext());

//        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();



    }

    private void setCollapsedToolbarTitle(String title) {
//        Snackbar.make(getWindow().findViewById(R.id.main_content), title, Snackbar.LENGTH_LONG)
//                .setAction("Action", null);
        mCollapsingToolbarLayout.setTitle(title);
//        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
//        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
//        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
//        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

    }


  @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pass_show_hide:
                Integer pass = (Integer)passwordShowHide.getTag();
                if(pass == R.drawable.password_view){
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordShowHide.setImageResource(R.drawable.password_hide);
                    passwordShowHide.setTag(R.drawable.password_hide);
                }else{
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordShowHide.setImageResource(R.drawable.password_view);
                    passwordShowHide.setTag(R.drawable.password_view);
                }
                break;
            case R.id.pass_act_signin:
                // Get username, password from EditText
                final String username = txtUsername;
                final String password = txtPassword.getText().toString();


                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                    Log.v("LoginSession", username +" " + password);
//                    if(username.equals("test") && password.equals("test")){

                    if(!checkPassword()) {
                        return;
                    }



                    if(!password.isEmpty()){
                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
//                        session.createLoginSession("Android Hive", "anroidhive@gmail.com");
                        progressBar.setVisibility(View.VISIBLE);
//                        simpleSignInOption(getParent(), txtUsername, password);
                        new GetDataForContent(getParent(), progressBar ,txtUsername, password).executeOnExecutor(THREAD_POOL_EXECUTOR);


                        /*auth.signInWithEmailAndPassword(username, password)
                               .addOnCompleteListener(PasswordActivity.this, new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (!task.isSuccessful()) {
                                           // there was an error
                                           Toast.makeText(PasswordActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                       } else {
                                           Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                                           session.createLoginSession(username, password);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
                               });*/



                        // Staring MainActivity
//                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(i);
//                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(PasswordActivity.this, "Login failed..", "Username/Password is incorrect", false);
                        progressBar.setVisibility(GONE);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(PasswordActivity.this, "Login failed..", "Please enter username and password", false);
                    progressBar.setVisibility(GONE);
                }
                break;

        }
    }

    /*
    private void simpleSignInOption(Activity parent, String txtUsername, String password) {
        try{
            Log.v("inputData", txtUsername + " " + password);

            URL url = new URL("http://18.217.104.249/Coupons/sign_in.php?username=" + txtUsername +"&password=" + password);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000*//*milliseconds*//*);
            conn.setConnectTimeout(15000*//*milliseconds *//*);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode=conn.getResponseCode();

            final StringBuilder output = new StringBuilder("Request URL " + url);
            output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
            output.append(System.getProperty("line.separator") + "Type "+ "GET");

            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                    publishProgress();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                User user = new User();
                JSONArray listOfOffers = new JSONArray();
                String line = null;
                JSONObject ja;

//        OfferData offerData = new OfferData();
                Bitmap bmp;
                try {
                    while ((line = reader.readLine()) != null) {
                        ja = new JSONObject(line);
                        Log.v("jsonObject", ja.getString("success"));
//                            Log.v("jsonArrayLine", ja.getJSONArray("wallet_balance").toString());
//                            listOfOffers = ja.getJSONArray("wallet_balance");

                        if(ja.getBoolean("success") && (ja.getString("wallet_balance")+" ") !=null){
//                                progress.setVisibility(GONE);
                            String referral_code = ja.getString("referral_code");
                            Log.v("LogInReferral", referral_code);
                            session.createLoginSession(txtUsername, password, referral_code);
                            Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
//                intent.putExtra("wallet_balance", result + " ");

                            Toast.makeText(PasswordActivity.this, "Wallet Balance: " + ja.getString("wallet_balance"), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                            Log.v("onPostExecute", ja.getString("wallet_balance") + " Wallet Balance ");


                        }
                        else if(ja.getBoolean("false")){
//                            Toast.makeText(PasswordActivity.this, "sign in failed..." + "invalid email or password", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(GONE);
                        }
                            *//*for(int i=0;i< 100; i++){
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
                                offerData.setActivateUrl(listOfOffers.getJSONObject(i).getString("url"));
//                    offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getInt("url"));
//                    for(int j=0; i< listOfOffers.getJSONObject(i).getJSONObject("imageUrls").length();i++) {
//                        if (Objects.equals(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("resolutionType"), "low")) {
//                            offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("url"));
//                        }
//                    }

                                bestOffersDataList.add(offerData);



                                for (int i = 0; i < ja.length(); i++) {*//*
//                    JSONObject jo = (JSONObject) ja.get(i);
//                    Log.v("jsonObject", jo.toString());
//                                }


//                sb.append(line + "\n");
//                Log.v("StreamtoString", line+" Hello");
                    }
                } catch (IOException e) {
                    Log.v("LogInIO", e.getMessage());
//                    Toast.makeText(PasswordActivity.this, "sign in failed..." + e.getMessage() , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(GONE);
                } catch (JSONException e) {
                    Log.v("LogINJSON",  e.getMessage());
//                    Toast.makeText(PasswordActivity.this, "sign in failed..." + e.getMessage() , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(GONE);
                } finally {
                    try {
                        conn.getInputStream().close();
                    } catch (IOException e) {
                        Log.v("FinallyLogSess", e.getMessage());
//                        Toast.makeText(PasswordActivity.this, "sign in failed..." + e.getMessage() , Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(GONE);
                    }
                }
                Log.v("StreamtoString","Data Unknown") ;
//                Toast.makeText(PasswordActivity.this, "sign in failed..." + "Error Unknown", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(GONE);

//                    publishProgress();
*//*
                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
*//*
*//*

                    in.close();
                    return sb.toString();
*//*



            }
            else {
                Log.v("LogInHttpNotOK" , "false : " + responseCode);
//                Toast.makeText(PasswordActivity.this, "sign in failed..." + responseCode, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(GONE);
            }
        }
        catch(Exception e){
            Log.v("LogIntryURL" , "false : " + e.getMessage());
//            Toast.makeText(PasswordActivity.this, "sign in failed..." + e.getMessage() , Toast.LENGTH_LONG).show();
            progressBar.setVisibility(GONE);
        }

    }

    */
    private boolean checkPassword() {

        String password = txtPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            passwordLayout.setError(getString(R.string.err_msg_password));
            txtPassword.setError(getString(R.string.err_msg_required));
            requestFocus(txtPassword);
            return false;
        }
        passwordLayout.setErrorEnabled(false);
        return true;
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
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
        private final String email, password;


        public GetDataForContent(Activity parent, ProgressBar progress, String emailId, String password) {
            this.parent = parent;
            this.progress = progress;

            this.email = emailId;
            this.password = password;
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



        protected String doInBackground(String... strings) {

            try{

//                18.217.104.249
                URL url = new URL( CONSTANT_INITIAL_URL + "Coupons/sign_in.php?username=" + email +"&password=" + password);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000/*milliseconds*/);
                conn.setConnectTimeout(15000/*milliseconds */);
                conn.setRequestMethod("GET");

                int responseCode=conn.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type "+ "GET");

                if (responseCode == HttpsURLConnection.HTTP_OK) {
//                    publishProgress();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();

                    User user = new User();
                    JSONArray listOfOffers = new JSONArray();
                    String line = null;
                    JSONObject ja;

//        OfferData offerData = new OfferData();
                    Bitmap bmp;
                    try {
                        while ((line = reader.readLine()) != null) {
                            ja = new JSONObject(line);
                            Log.v("jsonObject", ja.getString("success"));
//                            Log.v("jsonArrayLine", ja.getJSONArray("wallet_balance").toString());
//                            listOfOffers = ja.getJSONArray("wallet_balance");

                            if(ja.getBoolean("success") && (ja.getString("wallet_balance")+" ") !=null){
//                                progress.setVisibility(GONE);
                                referral_code = ja.getString("referral_code");
                                Log.v("LogInReferral", referral_code);

//                                String wallet = ja.getString("wallet_balance");
//                                Toast.makeText(PasswordActivity.this, "Wallet Balance: " + wallet, Toast.LENGTH_LONG).show();


                                return ja.getString("wallet_balance");
                            }
                            else{
                                return String.valueOf(0);
                            }
                            /*for(int i=0;i< 100; i++){
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
                                offerData.setActivateUrl(listOfOffers.getJSONObject(i).getString("url"));
//                    offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONArray("imageUrls").getJSONObject(1).getInt("url"));
//                    for(int j=0; i< listOfOffers.getJSONObject(i).getJSONObject("imageUrls").length();i++) {
//                        if (Objects.equals(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("resolutionType"), "low")) {
//                            offerData.setImgUrl(listOfOffers.getJSONObject(i).getJSONObject("imageUrls").getString("url"));
//                        }
//                    }

                                bestOffersDataList.add(offerData);



                                for (int i = 0; i < ja.length(); i++) {*/
//                    JSONObject jo = (JSONObject) ja.get(i);
//                    Log.v("jsonObject", jo.toString());
//                                }


//                sb.append(line + "\n");
//                Log.v("StreamtoString", line+" Hello");
                            }
                        } catch (IOException e) {
                        Log.v("LogInIO", e.getMessage());
                    } catch (JSONException e) {
                        Log.v("LogINJSON",  e.getMessage());
                    } finally {
                        try {
                            conn.getInputStream().close();
                        } catch (IOException e) {
                            Log.v("FinallyLogSess", e.getMessage());
                        }
                    }
                    Log.v("StreamtoString","Data Unknown") ;


//                    publishProgress();
                    return String.valueOf(0);
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
*/
/*

                    in.close();
                    return sb.toString();
*/



                }
                else {
                    Log.v("LogInHttpNotOK" , "false : " + responseCode);
                    return String.valueOf(0);
                }
            }
            catch(Exception e){
                Log.v("LogIntryURL" , "false : " + e.getMessage());
                return String.valueOf(0);
            }

        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(final String result) {
//            progress.setVisibility(GONE);

            int finalres;
            if(result != String.valueOf(0)) {
                progress.setVisibility(GONE);
//                runResultsOnUi(fullData);
                Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                if(result == "null" || result == null){
                    finalres = 0;
                    intent.putExtra("wallet_balance", finalres);
                    session.createLoginSession(email, password, referral_code, String.valueOf(finalres));
                    Toast.makeText(PasswordActivity.this, "Wallet Balance: " + finalres, Toast.LENGTH_LONG).show();
                }else{
                    finalres = Integer.parseInt(result);
                    intent.putExtra("wallet_balance", finalres);
                    session.createLoginSession(email, password, referral_code, String.valueOf(finalres));
                    Log.v("Wallet", finalres + " : Wallet Balance");
                    Toast.makeText(PasswordActivity.this, "Wallet Balance: " + finalres, Toast.LENGTH_LONG).show();
                }
//                intent.putExtra("wallet_balance", result + " ");

                startActivity(intent);
                finish();
                Log.v("onPostExecute", result + " Wallet Balance ");

//                setUpData(fullData);
//                bestOffersAdapter.notifyDataSetChanged();
            } else{
                progress.setVisibility(GONE);
                Toast.makeText(PasswordActivity.this, "sign in failed...", Toast.LENGTH_LONG).show();
            }

        }

    }


}
