package com.example.sai.couponduni; /**
 * Created by sai on 28/2/18.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.auth.UserProfileChangeRequest;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import Model.User;
import Utils.RandomString;

import static android.content.ContentValues.TAG;
import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;
import static android.view.View.GONE;
import static com.example.sai.couponduni.MainActivity.CONSTANT_INITIAL_URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // Email, password edittext
    EditText txtUsername, txtPassword;

    GoogleApiClient mGoogleApiClient;

    // login button
    Button btnLogin, btnSkip, passLogin, btx, btnSignUp/*, unnecessaryLogout*/;

    SessionManager session;
    ProgressBar progressBarAuth;

    RelativeLayout userNameLayout, passwordLayout;
    TextInputLayout emailIdLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView collapseImage;
    SignInButton googleSignIn;
    private static final int RC_SIGN_IN = 20;
//    FirebaseAuth auth;
    GoogleSignInOptions options;
    private GoogleSignInApi mGoogleSignInClient;
    String idToken, name, email;
    private static final String specialPassword = "z6Wnf456Ond67GNEl114kjd45Fg";
    boolean special_referral = false;
    String referred_by = "";

    //Facebook Login details
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignUp = (Button) findViewById(R.id.btn_signup_open);
        btnSignUp.setOnClickListener(this);

        googleSignIn = (SignInButton) findViewById(R.id.google_signin);
        TextView textView = (TextView) googleSignIn.getChildAt(0);
        textView.setText("Sign In");
        googleSignIn.setOnClickListener(this);
//        auth  = FirebaseAuth.getInstance();
        configureSignIn();
        facebookSignIn();

        progressBarAuth = (ProgressBar) findViewById(R.id.auth_log_in_progress);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/").push();

        String UploadId = "3iwzC3NSS7gPnmyAGgWgmONA35k2";

        User user1 = new User("Sai Sreenivsa", "saisreenivas222@gmail.com");
//        myRef.child("users").child(UploadId).setValue(user1);
        myRef.child(UploadId).setValue(user1);
        myRef.setValue(user1);*/


        emailIdLayout = (TextInputLayout) findViewById(R.id.signin_input_layout);

//        passLogin = (Button) findViewById(R.id.pass_signin);
//        btx = (Button) findViewById(R.id.btx);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
//        txtPassword = (EditText) findViewById(R.id.txtPassword);

//        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        btnLogin.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        session = new SessionManager(getApplicationContext());

//        unnecessaryLogout= (Button) findViewById(R.id.unnecessary_logout);
//        unnecessaryLogout.setOnClickListener(this);
    }

    private void facebookSignIn() {
        loginButton = (LoginButton) findViewById(R.id.facebook_signin);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        callbackManager = CallbackManager.Factory.create();
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");
                                    progressBarAuth.setVisibility(View.VISIBLE);
                                    new GetDataForContent(getParent(), progressBarAuth ,email, specialPassword).executeOnExecutor(THREAD_POOL_EXECUTOR);
//                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                // App code
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(…);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
// Google Sign In was successful, save oken and a state then authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                idToken = account.getIdToken();
                name = account.getDisplayName();
                email = account.getEmail();

//                Log.v("MainLogIn", name + " " + email);
                progressBarAuth.setVisibility(View.VISIBLE);
                new GetDataForContent(getParent(), progressBarAuth ,email, specialPassword).executeOnExecutor(THREAD_POOL_EXECUTOR);

//                session.createLoginSession(name, email, "GoogleS", "49");
//                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("wallet_balance", "49"));
//                finish();


                /*idToken = account.getIdToken();
                name = account.getDisplayName();
                email = account.getEmail();
                photoUri = account.getPhotoUrl();
                photo = photoUri.toString();
// Save Data to SharedPreference
                sharedPrefManager = new SharedPrefManager(mContext);
                sharedPrefManager.saveIsLoggedIn(mContext, true);
                sharedPrefManager.saveEmail(mContext, email);
                sharedPrefManager.saveName(mContext, name);
                sharedPrefManager.savePhoto(mContext, photo);
                sharedPrefManager.saveToken(mContext, idToken);*/
//sharedPrefManager.saveIsLoggedIn(mContext, true);
//                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//                firebaseAuthWithGoogle(credential);

//                firebaseAuthWithGoogle(credential);
            } else {
// Google Sign In failed, update UI appropriately
                Log.e(TAG, "Login Unsuccessful. ");
//                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
//                        .show();
            }
        }


    }


    private class GetDataForContent extends AsyncTask<String, Void, String> {


        private final int[] progr = {40, 65};
        private int index;

        private final Activity parent;
        private final ProgressBar progress;
        private final String email, password;
        AlertDialog.Builder builder;


        public GetDataForContent(Activity activity, ProgressBar progress, String emailId, String password) {
            this.parent = activity;
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
            builder = new AlertDialog.Builder(LoginActivity.this);
            int max = 0;
            for (final int p : progr) {
                max += p;
            }
            progress.setMax(max);
            index = 0;

        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected String doInBackground(String... strings) {

            try {
                String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                RandomString randomString = new RandomString(6, new SecureRandom(), easy );

//                18.217.104.249
                URL url = new URL(CONSTANT_INITIAL_URL + "Coupons/auth_sign_in.php?username="
                        + email + "&login_via=" + specialPassword + "&referral_code=" + randomString.nextString());
                if(special_referral){
                    url = new URL(CONSTANT_INITIAL_URL + "Coupons/auth_sign_in.php?username="
                            + email + "&login_via=" + specialPassword + "&referral_code=" + randomString.nextString()
                            + "&sp_referral="+ referred_by);
//                    URL random = new URL(randomString.nextString());
//                    url = url + "&sp_referral=";
                    special_referral = false;
                }

                Log.v("LogAuth", url.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000/*milliseconds*/);
                conn.setConnectTimeout(15000/*milliseconds */);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "GET");

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
                            Log.v("jsonObject", ja.toString());
//                            Log.v("jsonArrayLine", ja.getJSONArray("wallet_balance").toString());
//                            listOfOffers = ja.getJSONArray("wallet_balance");


                            //if it is a new user, ask to enter a referral code
                            if (Objects.equals(ja.getString("success"), "new_user")) {
                                Log.v("jsonObject", ja.getString("success"));
                                return ja.getString("success");
                            }
                            if((ja.getBoolean("success"))){
                                return "'success=" + ja.getString("success") + "'referral_code'"
                                        + ja.getString("referral_code") + "'wallet_balance'" + ja.getString("wallet_balance")+ "'";
                            }
                            /*if(ja.getBoolean("success") && (ja.getString("wallet_balance")+" ") !=null){
//                                progress.setVisibility(GONE);
                                referral_code = ja.getString("referral_code");
                                Log.v("LogInReferral", referral_code);

                                return ja.getString("wallet_balance");
                            }
                            else{
                                return String.valueOf(0);
                            }*/

                        }
                    } catch (IOException e) {
                        Log.v("LogInIO", e.getMessage());
                    } catch (JSONException e) {
                        Log.v("LogINJSON", e.getMessage());
                    } finally {
                        try {
                            conn.getInputStream().close();
                        } catch (IOException e) {
                            Log.v("FinallyLogSess", e.getMessage());
                        }
                    }
                    Log.v("StreamtoString", "Data Unknown");


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


                } else {
                    Log.v("LogInHttpNotOK", "false : " + responseCode);
                    return String.valueOf(0);
                }
            } catch (Exception e) {
                Log.v("LogIntryURL", "false : " + e.getMessage());
//                Toast.makeText(parent, e.getMessage(), Toast.LENGTH_LONG).show();
//                e.printStackTrace();
                return String.valueOf(0);
            }

        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(final String result) {
            progress.setVisibility(GONE);

            Log.v("LogAuth", result);
            int finalres;
            if (Objects.equals(result, "new_user")) {
                LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
//
                final View dialogView = inflater.inflate(R.layout.dialog_referral_sign_up, null);
                builder.setView(dialogView);
                builder.setTitle("Earn Referral Bonus");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v("LogAuth", "Positive");
                        special_referral = true;
                        EditText referred_by_data = (EditText) dialogView.findViewById(R.id.sp_referred_by);
                        referred_by = referred_by_data.getText().toString();
                        new GetDataForContent(getParent(), progressBarAuth ,email, specialPassword).executeOnExecutor(THREAD_POOL_EXECUTOR);
                    }
                });
                builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        new GetDataForContent(parent, progressBarAuth ,email, specialPassword).executeOnExecutor(THREAD_POOL_EXECUTOR);
                        Log.v("LogAuth", "Negative");
                        special_referral = true;
//                        EditText referred_by_data = (EditText) dialogView.findViewById(R.id.sp_referred_by);
                        referred_by = "";
                        new GetDataForContent(getParent(), progressBarAuth ,email, specialPassword).executeOnExecutor(THREAD_POOL_EXECUTOR);
//                        new SignUpActivity.GetDataForContent(getParent(), progressBarAuth ,email, password, random).execute();
                    }
                });

                builder.show();

            }

            if(result.contains("success=true")){
                String[] resultme = result.split("'");
                Log.v("LogAuth", Arrays.toString(resultme));
                session.createLoginSession(email, password, resultme[3], resultme[5]);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
            /*if(result != String.valueOf(0)) {
                progress.setVisibility(GONE);
//                runResultsOnUi(fullData);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                if(result == "null" || result == null){
                    finalres = 0;
                    intent.putExtra("wallet_balance", finalres);
                    session.createLoginSession(email, password, referral_code, String.valueOf(finalres));
                    Toast.makeText(LoginActivity.this, "Wallet Balance: " + finalres, Toast.LENGTH_LONG).show();
                }else{
                    finalres = Integer.parseInt(result);
                    intent.putExtra("wallet_balance", finalres);
                    session.createLoginSession(email, password, referral_code, String.valueOf(finalres));
                    Log.v("Wallet", finalres + " : Wallet Balance");
                    Toast.makeText(LoginActivity.this, "Wallet Balance: " + finalres, Toast.LENGTH_LONG).show();
                }
//                intent.putExtra("wallet_balance", result + " ");

                startActivity(intent);
                finish();
                Log.v("onPostExecute", result + " Wallet Balance ");

//                setUpData(fullData);
//                bestOffersAdapter.notifyDataSetChanged();
            } else{
                progress.setVisibility(GONE);
                Toast.makeText(LoginActivity.this, "sign in failed...", Toast.LENGTH_LONG).show();
            }

        }*/

        }

/*

    private void firebaseAuthWithGoogle(final AuthCredential credential) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
//                            Toast.makeText(getApplicationContext(), user + " " + user.getUid() + " ", Toast.LENGTH_LONG).show();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users/");


//                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            String UploadId = user.getUid();

                            String referalcode = "";
                            User user1 = new User(name, email);
//        myRef.child("users").child(UploadId).setValue(user1);
                            myRef.child(UploadId).setValue(user1);

*/


/*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("Jane Q. User")
                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                    .build();


                            updateProfile(user, profileUpdates);*//*

//                            myRef.setValue(user1);
//                            myRef.setValue("name", name);
//                            myRef.setValue("email", email);


                            session.createLoginSession(name, email);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);

                            startActivity(i);
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_content), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
*/

/*    private void updateProfile(FirebaseUser user, UserProfileChangeRequest profileUpdates) {
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("UserProfile", "user profile updated");
                    }
                });
    }*/

/*
    //This method creates a new user on our own Firebase database
//after a successful Authentication on Firebase
//It also saves the user info to SharedPreference
    private void createUserInFirebaseHelper(){
//Since Firebase does not allow “.” in the key name, we’ll have to encode and change the “.” to “,”
// using the encodeEmail method in class Utils
        final String encodedEmail = Utils.encodeEmail(email.toLowerCase());
//create an object of Firebase database and pass the the Firebase URL
        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS) {
        }.child(encodedEmail);
//Add a Listerner to that above location
        userLocation.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null){
*//* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap *//*
                    HashMap<String, Object> timestampJoined = new HashMap<>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
// Insert into Firebase database
                    User newUser = new User(name, photo, encodedEmail, timestampJoined);
                    userLocation.setValue(newUser);
                    Toast.makeText(MainActivity.this, “Account created!”, Toast.LENGTH_SHORT).show();
// After saving data to Firebase, goto next activity
                    Intent intent = new Intent(MainActivity.this, NavDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
//hideProgressDialog();
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN){
                }
                else {
                    Toast.makeText(MainActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                if(!checkEmail()) {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                intent.putExtra("name", txtUsername.getText().toString().trim());
                startActivity(intent);

//                finish();
                break;
            case R.id.btnSkip:
                session.createLoginSession(null, "anroidhive@gmail.com", "Need to Log In", String.valueOf(0));

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                finish();
                break;
            case R.id.btn_signup_open:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.google_signin:
                signIn();
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent(mGoogleApiClient);
//                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
          /*  case R.id.unnecessary_logout:
                session.logoutUser();
//                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;*/

        }

    }
/*

    private void signInWithGoogle() {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_content), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

*/

    private boolean checkEmail() {
        String email = txtUsername.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            emailIdLayout.setErrorEnabled(true);
            emailIdLayout.setError(getString(R.string.err_msg_email));
            txtUsername.setError(getString(R.string.err_email_required));
            requestFocus(txtUsername);
            return false;
        }
        emailIdLayout.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void configureSignIn(){
// Configure sign-in to request the user’s basic profile like name and email
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }

}
