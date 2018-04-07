package com.example.sai.couponduni;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Objects;

import Model.User;
import Utils.RandomString;

public class ReferAndEarnActivity extends AppCompatActivity {

    private static final String TAG = "ReferralCheck";
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions options;

    TextView btnShare;
    TextView referalCodeMine;
    Button btnactivateReferal;
    EditText activateReferal;
    Button btnShareReferral;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        referalCodeMine = (TextView) findViewById(R.id.invite_referral);
        btnShareReferral = (Button) findViewById(R.id.btn_share_referral);

        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            session.checkLogin();
            HashMap<String, String > user =session.getUserDetails();
            referalCodeMine.setText(user.get(SessionManager.KEY_REFERRAL));
        }

        btnShareReferral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        getResources().getString(R.string.share_referral) + referalCodeMine.getText().toString());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Coupons App");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        final FirebaseUser user = auth.getCurrentUser();
//                            Toast.makeText(getApplicationContext(), user + " " + user.getUid() + " ", Toast.LENGTH_LONG).show();
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("users/" + user.getUid());


//        btnactivateReferal = (Button) findViewById(R.id.btn_activate_referral);
//        activateReferal = (EditText) findViewById(R.id.activate_my_referral);

//        btnactivateReferal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference myReferen = database.getReference("referalcodes/"+ activateReferal.getText());
/*                myReferen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            String value = String.valueOf(dataSnapshot.getValue());
                            Log.d(TAG, "Activated coupon has " + value);
                        }else
                            Toast.makeText(getApplicationContext(),"Entered Code is invalid", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Entered Code does not exists", Toast.LENGTH_LONG).show();
                    }
                });*/
//            }
//        });

//        String UploadId = user.getUid();

        referalCodeMine = (TextView) findViewById(R.id.invite_referral);
//        User user1 = new User(name, email);
//        myRef.child("users").child(UploadId).setValue(user1);
//        myRef.child(UploadId).setValue(user1);

/*
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("referalcode").exists()) {
                        String referalcode = dataSnapshot.getValue(User.class).referalcode.toString();
                        if (!referalcode.isEmpty()) {
                            referalCodeMine.setText(referalcode);
                        } else {
                            String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                            RandomString tickets = new RandomString(23, new SecureRandom(), easy);
                            String random = tickets.nextString();
                            Log.v("referAndEarn", tickets + " " + random);
                            DatabaseReference myRef = database.getReference("users/" + user.getUid());
                            myRef.child("referalcode").setValue(random);
                            DatabaseReference myReferen = database.getReference("referalcodes/");
//                    String UploadId = String.valueOf(tickets);
//                    User user1 = new User(user.getDisplayName(), user.getEmail());
                            myReferen.child(random).child("Main").setValue(user.getEmail());
                            referalCodeMine.setText(random);

                        }
                    }
                *//*if(dataSnapshot.getValue(User.class).referalcode != null) {
                    Log.v("DataSnapshot", dataSnapshot.getKey().toString() + " " + dataSnapshot.getValue(User.class).email
                            + " " + dataSnapshot.getValue(User.class).referalcode);
                    String referalcode = dataSnapshot.getValue(User.class).referalcode.toString();
                    if (!referalcode.isEmpty()) {
                        referalCodeMine.setText(referalcode);

                    }
                }
*//**//*
                if(dataSnapshot.getValue("referalcode")) {
//                    String value = String.valueOf(dataSnapshot.getValue());
//                    Log.d(TAG, "Value is: " + value);
                }*//**//*
                    else *//**//*if(!Objects.equals(dataSnapshot.getKey(), "UwHd0rkMu7x8xJY6hjP0dbi"))*//**//* {
                    String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                    RandomString tickets = new RandomString(23, new SecureRandom(), easy);
                    String random = tickets.nextString();
                        Log.v("referAndEarn", tickets + " " + random);
                        DatabaseReference myRef = database.getReference("users/" + user.getUid());
                        myRef.child("referalcode").setValue(random);
                        DatabaseReference myReferen = database.getReference("referalcodes/");
//                    String UploadId = String.valueOf(tickets);
//                    User user1 = new User(user.getDisplayName(), user.getEmail());
                        myReferen.child(random).child("Main").setValue(user.getEmail());
                        referalCodeMine.setText(random);
//
                    }*//*
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                *//*String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                RandomString tickets = new RandomString(23, new SecureRandom(), easy);
                String random = tickets.nextString();
                Log.v("referAndEarn", tickets + " " + tickets.nextString());
                DatabaseReference myRef = database.getReference("referalcodes/");
                String UploadId = String.valueOf(tickets);


                User user1 = new User(user.getDisplayName(), user.getEmail());
                myRef.child(random).child("Main").setValue(user1);
                referalCodeMine.setText(random);
*//*

                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    }
/*        btnShare = (TextView) findViewById(R.id.share_the_link);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInviteClicked();
            }
        });

        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this *//* FragmentActivity *//*, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }*//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addApi(AppInvite.API)
                .build();

        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                    @Override
                    public void onResult(@NonNull AppInviteInvitationResult appInviteInvitationResult) {
                        if(appInviteInvitationResult.getStatus().isSuccess()){

                            Intent intent = appInviteInvitationResult.getInvitationIntent();
                            String Deeplink = AppInviteReferral.getDeepLink(intent);
                            String invitationId = AppInviteReferral.getInvitationId(intent);
                        }
                    }
                });
    }


    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }*/

//}
