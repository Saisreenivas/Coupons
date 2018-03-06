package com.example.sai.couponduni;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        txtPassword = (EditText) findViewById(R.id.pass_text);
        passwordShowHide = (ImageButton) findViewById(R.id.pass_show_hide);
        signInBtn = (Button) findViewById(R.id.pass_act_signin);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        passwordShowHide.setImageResource(R.drawable.password_view);
        passwordShowHide.setTag(R.drawable.password_view);
        passwordShowHide.setOnClickListener(this);
        signInBtn.setOnClickListener(this);



        txtUsername = getIntent().getStringExtra("name");
        if(txtUsername != null){
            setCollapsedToolbarTitle(txtUsername);
        }

        // Session Manager
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

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
                String username = txtUsername;
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                    Log.v("LoginSession", username +" " + password);
                    if(username.equals("test") && password.equals("test")){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(PasswordActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(PasswordActivity.this, "Login failed..", "Please enter username and password", false);
                }
                break;

        }
    }
}
