package com.example.sai.couponduni;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                if(Integer.valueOf(session.getUserDetails().get(SessionManager.KEY_WALLET_BAL)) >0) {
                    startActivity(new Intent(ProfileActivity.this, AccountDetailsActivity.class));
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setTitle("Withdrawal Balance is Low");
                    builder.setMessage("Balance in your account must be greater than 0 to withdraw");
                    builder.show();
//                    Toast.makeText(getApplicationContext(),
//                            "Minimum Wallet Balance Required to withdraw...", Toast.LENGTH_LONG).show();
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
