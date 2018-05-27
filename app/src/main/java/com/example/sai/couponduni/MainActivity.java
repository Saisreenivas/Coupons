package com.example.sai.couponduni;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appinvite.AppInvite;
//import com.google.android.gms.appinvite.AppInviteInvitationResult;
//import com.google.android.gms.appinvite.AppInviteReferral;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Adapter.ImagesPagerAdapter;
import Adapter.TabsPagerAdapter;
import Model.OfferData;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {


//    public static final String CONSTANT_INITIAL_URL = "http://192.168.1.104/";
    public static final String CONSTANT_INITIAL_URL = "http://couponkhajana.com/android/";

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    Menu menu;

    // Button Logout
    Button btnLogout, contentLoginBtn;
    LinearLayout contentData, contentLogin;
    View header_View;
    MenuItem menuItem;

    //viewPager
    ViewPager viewPager, viewImagePager;
//    Toolbar actionBar;
    TabLayout tabLayout, tabImageLayout;
    TabsPagerAdapter mAdapter;
    ImagesPagerAdapter mImageAdapter;

    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions options;

    private String[] tabs = { "Best Offers", "Categories", "Top Stories" };
    private static int REQUEST_CODE = 25;
    String wallet;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Bundle extras = getIntent().getExtras();
        if(extras != null){
            String email = extras.getString("email");
            String password = extras.getString("password");
            String referral_code = extras.getString("referral_code");
            String wallet_balance = extras.getString("wallet_balance");
            Log.v("walletBalance", wallet_balance);
            session.createLoginSession(email, password, referral_code, wallet_balance);
        }*/


//        startActivityForResult(new Intent(MainActivity.this, EmptyLoadingActivity.class), REQUEST_CODE);
        toolbarAndFloatingBtn();
        navigationStartUp();
        sessionLogin();



//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Menu menu = navigationView.getMenu();
//        MenuItem button= (MenuItem) navigationView.findViewById(R.id.action_balance);
//        button.setTitle("₹Rupee");




        configureSignIn();
        imagesOfTopBar();
        contentOfContentMain();
    }

    private void configureSignIn() {
        // Configure sign-in to request the user’s basic profile like name and email
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
//                .addApi(AppInvite.API)
                .build();

    }

    private void imagesOfTopBar(){
        viewImagePager = (ViewPager) findViewById(R.id.img_scroll_view_pager);
        tabImageLayout = (TabLayout) findViewById(R.id.img_scroll_tab_layout);
//        actionBar = (Toolbar) findViewById(R.id.toolbar);
        mImageAdapter = new ImagesPagerAdapter(getSupportFragmentManager());

        viewImagePager.setAdapter(mImageAdapter);
        tabImageLayout.setupWithViewPager(viewImagePager);

        viewImagePager.setOffscreenPageLimit(3);
    }


    private void contentOfContentMain() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        actionBar = (Toolbar) findViewById(R.id.toolbar);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);
        // Adding Tabs
        for (String tab_name : tabs) {
//            actionBar.addTab(actionBar.newTab().setText(tab_name)
//                    .setTabListener(this));
//            tabLayout.addTab(tabLayout.newTab().setText(tab_name));
//            tabLayout.setOnTabSelectedListener(this);
        }

    }

/*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
//                actionBar.setSelectedNavigationItem(position);
                tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
                if (position == 0) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });*/
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 25){
//            if(resultCode == RESULT_OK){
//                Bundle extras = data.getExtras();
//                ArrayList<OfferData> full= extras.getIntegerArrayList("data");
//                ArrayList<OfferData> fullList = (ArrayList<OfferData>) data.getParcelableArrayListExtra("data");
//            }
//        }
//    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void navigationStartUp() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(R.id.btn_logout).setEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menuItem = menu.findItem(R.id.btn_logout);
        Log.v("NavView", navigationView.getMenu() + " " );

        header_View = navigationView.getHeaderView(0);

    }

    private void toolbarAndFloatingBtn() {


        /*Bundle intent = getIntent().getExtras();
        if(intent!= null){
            String data = intent.getString("wallet_balance").trim();
            MenuItem button= (MenuItem) findViewById(R.id.action_balance);
            button.setTitle("₹" + data);
        }*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, " ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void sessionLogin() {

        // Session class instance
        session = new SessionManager(getApplicationContext());

        TextView lblName = (TextView) header_View.findViewById(R.id.lblName);
        TextView lblEmail = (TextView) header_View.findViewById(R.id.lblEmail);
        contentData = (LinearLayout) header_View.findViewById(R.id.contentData);
        contentLogin = (LinearLayout) header_View.findViewById(R.id.contentLogin);
        contentLoginBtn = (Button) header_View.findViewById(R.id.contentLoginBtn);

//        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        /**
         * Call this function whenever you want to check user login
         * This will redirect user to com.example.sai.couponduni.LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        wallet = user.get(SessionManager.KEY_WALLET_BAL);
        Log.v("Wallet", wallet+ " Hello");

        if(name == null){
            contentData.setVisibility(View.GONE);
            contentLogin.setVisibility(View.VISIBLE);
            menuItem.setEnabled(false);
        }
        else {
            // displaying user data
//            lblName.setText(Html.fromHtml("<b>" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "</b>"));
            lblEmail.setText(Html.fromHtml("<b>" + name + "</b>"));
            contentData.setVisibility(View.VISIBLE);

            contentLogin.setVisibility(GONE);
        }
        /**
         * Logout button click event
         * */
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // Clear the session data
//                // This will clear all session data and
//                // redirect user to com.example.sai.couponduni.LoginActivity
//                session.logoutUser();
//            }
//        });

        contentLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(contentData.getVisibility() != GONE){

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            this.menu = menu;
            MenuItem menuItem = menu.findItem(R.id.action_balance);

            /*if((session.getUserDetails().get(SessionManager.KEY_WALLET) == null) || (session.getUserDetails().get(SessionManager.KEY_WALLET) == "null")){
                wallet = String.valueOf(0);
            }else{
                wallet = session.getUserDetails().get(SessionManager.KEY_WALLET);
            }*/
    //        sessionLogin();
            menuItem.setTitle("₹" + wallet);
    }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_notifications) {
//            return true;
        /*}else */
        if (id == R.id.action_balance) {
            return true;
        }/*else if (id == R.id.action_search) {
            return true;
        }*/else

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            if(session.getUserDetails().get(SessionManager.KEY_NAME) != null) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)
                        .putExtra("wallet_balance", wallet));
            }else{
                session.logoutUser();
                LoginManager.getInstance().logOut();
//                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        } else if (id == R.id.nav_refer_earn) {
            startActivity(new Intent(getApplication(), ReferAndEarnActivity.class));

        }
//        else if (id == R.id.nav_help) {
//
//        } else if (id == R.id.nav_feedback) {
//
//        } else if (id == R.id.nav_favourites){
//
//        }
        else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
        } else if(id == R.id.btn_logout) {
            session.logoutUser();
            LoginManager.getInstance().logOut();
//            FirebaseAuth.getInstance().signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {

                }
            });
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public Bitmap getCroppedBitmap(Bitmap bitmap) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
//                bitmap.getWidth() / 2, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
//        //return _bmp;
//        return output;
//    }
}
