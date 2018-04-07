package com.example.sai.couponduni;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import Model.User;
import Utils.RandomString;

import static android.view.View.GONE;
import static com.example.sai.couponduni.MainActivity.CONSTANT_INITIAL_URL;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "signInCheck";
    TextInputLayout signupInputLayoutEmail, signupInputLayoutPassword;
    Button btnSignUp, btnLinkToLogIn;
    public EditText signupInputEmail, signupInputPassword, signUpReferralCode;
    ProgressBar progressBar;
//    FirebaseAuth auth;
    SessionManager session;
    TextView resultText, signUpInviteCode;
    String random;
    String email;
    String password;
    ImageButton imageShowHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        auth = FirebaseAuth.getInstance();
        session = new SessionManager(getApplicationContext());

        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString randomString = new RandomString(6, new SecureRandom(), easy );
        random = randomString.nextString();

        signupInputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_input_layout_password);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_me);

        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        signUpReferralCode = (EditText) findViewById(R.id.signup_referral_code);
        resultText = (TextView) findViewById(R.id.resultAnnouncement);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkToLogIn = (Button) findViewById(R.id.btn_link_login);

        signUpInviteCode = (TextView) findViewById(R.id.signup_invite_code);
        signUpInviteCode.setText("Your Referral Code: " + random);

        imageShowHide = (ImageButton) findViewById(R.id.pass_show_hide_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imageShowHide.setImageResource(R.drawable.password_view);
        imageShowHide.setTag(R.drawable.password_view);
        imageShowHide.setOnClickListener(this);

    }



    private void submitForm() {

        email = signupInputEmail.getText().toString().trim();
        password = signupInputPassword.getText().toString().trim();

        if (!checkEmail()) {
            return;
        }
        if (!checkPassword()) {
            return;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        new GetDataForContent(getParent(), progressBar ,email, password, random).execute();





        /*//create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, Log the message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "Authentication failed." + task.getException());

                        } else {
//                            Toast.makeText(getApplicationContext(), user + " " + user.getUid() + " ", Toast.LENGTH_LONG).show();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users/");
                            String UploadId = auth.getCurrentUser().getUid();
                            User user1 = new User(" ", email);
//        myRef.child("users").child(UploadId).setValue(user1);
                            myRef.child(UploadId).setValue(user1);
                            session.createLoginSession(email, " ");
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });*/
//        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
    }


    private boolean checkEmail() {
        String email = signupInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputEmail.setError(getString(R.string.err_email_required));
            requestFocus(signupInputEmail);
            return false;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {

        String password = signupInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            signupInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPassword);
            return false;
            
        }
        signupInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.pass_show_hide_up){
            Integer pass = (Integer)imageShowHide.getTag();
            if(pass == R.drawable.password_view){
                signupInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageShowHide.setImageResource(R.drawable.password_hide);
                imageShowHide.setTag(R.drawable.password_hide);
            }else{
                signupInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageShowHide.setImageResource(R.drawable.password_view);
                imageShowHide.setTag(R.drawable.password_view);
            }
        }
    }


    protected class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr  = {40,65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final String email, password, random;


        public GetDataForContent(Activity parent,ProgressBar progress ,String emailId, String password, String random) {
            this.random =random;
            this.email = emailId;
            this.password = password;
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



        protected String doInBackground(String... strings) {

            try{


                URL url = new URL(CONSTANT_INITIAL_URL + "Coupons/sign_up.php?username=" + email
                        +"&password=" + password +"&referred_by=" + signUpReferralCode.getText()
                        +"&referral_code=" + random +"&wallet_balance=");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/ );
                conn.setConnectTimeout(15000  /*milliseconds */);
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

//                            Log.v("jsonObject", ja.getString("success"));
//                            Log.v("jsonArrayLine", ja.getJSONArray("wallet_balance").toString());
//                            listOfOffers = ja.getJSONArray("wallet_balance");

                            if(ja.getBoolean("success")){
                                if(ja.getBoolean("referral_activation") &&
                                        ja.getBoolean("referred_by_activation") && ja.getBoolean("referal_Code")){

//                                    String wallet = String.valueOf(0);
//                                    session.createLoginSession(email, " ", random, wallet);
                                    return "SignUp Successful\nReferral Code Activated";
                                }
                            }else{
                                if(signUpReferralCode.getText()==null) {
                                    return "Already Signed up (or) Invalid Referral";
                                }else{
                                    return "Already Signed up";
                                }
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

                                bestOffersDataList.add(offerData);*/


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
                            conn.getInputStream().close();
                        } catch (IOException e) {
                            Log.v("StreamtoString", e.getMessage());
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

                    in.close();
                    return sb.toString();
*/

                }
                else {
                    Log.v("False" , "false : " + responseCode);
                    return String.valueOf(0);
                }
            }
            catch(Exception e){
                Log.v("Exception-try/catch" , "false : " + e.getMessage());
                return String.valueOf(0);
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(final String result) {
            progress.setVisibility(GONE);
//                runResultsOnUi(fullData);

                    if(result.contains("Already Signed up") ||
                            result.contains("SignUp Successful\nReferral Code Activated")){
                        resultText.setText("SignUp Successful");
                        Log.v("Signupdetails", "SignUp Success");
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }else {
                        resultText.setText(result);
                        Log.v("Signupdetails", "SignUp Failed");
//                        Toast.makeText(SignUpActivity.this, "SignUp Failed", Toast.LENGTH_LONG).show();
                    }
//                Intent intent = new Intent(parent, MainActivity.class);
//                session.createLoginSession(email, password);
//                startActivity(intent);
//                finish();
//                Log.v("onPostExecute", result+" Wallet Balance ");


//                setUpData(fullData);
//                bestOffersAdapter.notifyDataSetChanged();


        }

    }


}
