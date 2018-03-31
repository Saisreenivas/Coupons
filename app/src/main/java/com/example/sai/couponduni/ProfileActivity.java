package com.example.sai.couponduni;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView btnAccountDetails,profileWallet, profileWithdraw;
    SessionManager session;
    CollapsingToolbarLayout profileUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        String email = session.getUserDetails().get(SessionManager.KEY_NAME);
        profileUserName = (CollapsingToolbarLayout) findViewById(R.id.profile_username);
        profileUserName.setTitle(email);
//
//        String wallet;
        profileWallet = (TextView) findViewById(R.id.profile_wallet);
//
        if(getIntent().getExtras() != null) {
            profileWallet.setText("Rs." + getIntent().getExtras().getString("wallet_balance"));
        }

        profileWithdraw = (TextView) findViewById(R.id.profile_withdraw);

        profileWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.valueOf(session.getUserDetails().get(SessionManager.KEY_WALLET_BAL)) >=0) {
                    startActivity(new Intent(ProfileActivity.this, AccountDetailsActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Minimum Wallet Balance Required to withdraw...", Toast.LENGTH_LONG).show();
                }
            }
        });

//        btnAccountDetails = (TextView) findViewById(R.id.prof_add_account_btn);
//        btnAccountDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileActivity.this, AccountDetailsActivity.class));

//            }
//        });
    }
}
