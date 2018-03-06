package com.example.sai.couponduni; /**
 * Created by sai on 28/2/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

    // Email, password edittext
    EditText txtUsername, txtPassword;

    // login button
    Button btnLogin, btnSkip, passLogin, btx;

    SessionManager session;

    RelativeLayout userNameLayout, passwordLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView collapseImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                intent.putExtra("name", txtUsername.getText().toString().trim());
                startActivity(intent);

                finish();
                break;
            case R.id.btnSkip:
                session.createLoginSession(null, "anroidhive@gmail.com");

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                finish();
                break;

        }

    }
}
