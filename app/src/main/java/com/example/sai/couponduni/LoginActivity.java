package com.example.sai.couponduni; /**
 * Created by sai on 28/2/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Model.User;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // Email, password edittext
    EditText txtUsername, txtPassword;

    GoogleApiClient mGoogleApiClient;

    // login button
    Button btnLogin, btnSkip, passLogin, btx, btnSignUp;

    SessionManager session;

    RelativeLayout userNameLayout, passwordLayout;
    TextInputLayout emailIdLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView collapseImage;
    SignInButton googleSignIn;
    private static final int RC_SIGN_IN = 20;
    FirebaseAuth auth;
    GoogleSignInOptions options;
    private GoogleSignInApi mGoogleSignInClient;
    String idToken, name, email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignUp = (Button) findViewById(R.id.btn_signup_open);
        btnSignUp.setOnClickListener(this);

        googleSignIn = (SignInButton) findViewById(R.id.google_signin);
        googleSignIn.setOnClickListener(this);
        auth  = FirebaseAuth.getInstance();
        configureSignIn();



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

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        btnLogin.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        session = new SessionManager(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//                firebaseAuthWithGoogle(credential);

//                firebaseAuthWithGoogle(credential);
            } else {
// Google Sign In failed, update UI appropriately
                Log.e(TAG, "Login Unsuccessful. ");
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show();
            }
        }


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

    private void updateProfile(FirebaseUser user, UserProfileChangeRequest profileUpdates) {
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("UserProfile", "user profile updated");
                    }
                });
    }

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

                finish();
                break;
            case R.id.btnSkip:
                session.createLoginSession(null, "anroidhive@gmail.com", "Need to Log In");

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
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
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
            txtUsername.setError(getString(R.string.err_msg_required));
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

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
