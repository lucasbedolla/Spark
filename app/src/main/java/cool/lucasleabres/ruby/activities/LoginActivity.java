package cool.lucasleabres.ruby.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.loginpager.LoginPagerContentFragment;
import cool.lucasleabres.ruby.util.Constants;

public class LoginActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener, LoginListener, ExceptionHandler {

    public static final String TAG = "LOGIN ACTIVITY";
    //interval time for page switch
    public static final int PAGE = 2000;
    public static final String CALLBACK_URL = "gem://www.gem.com/ok";
    public ImageView spin;

    ViewPager imagePager;
    LinearLayout pager_indicator;
    Button go;
    private int check = 0;
    private Handler handler;
    private boolean isUserActive = false;
    private int dotsCount;
    private ImageView[] dots;
    private int[] pageLayouts = {
            R.layout.fragment_login_pager_spinner,
            R.layout.fragment_login_pager_content,
            R.layout.fragment_login_pager_content,
            R.layout.fragment_login_pager_content,
            R.layout.fragment_login_pager_bounce,
    };


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        go = (Button) findViewById(R.id.go);
        imagePager = (ViewPager) findViewById(R.id.pager_introduction);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        if (settings.getString("access_token",null) != null) {
            Intent intent = new Intent(LoginActivity.this, MyLauncher.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            getWindow().setStatusBarColor(getResources().getColor(R.color.post_luscent));
        }
        //makes system notification bar rest on top of >lollipop layouts

        go.setOnClickListener(this);
        //viewpager setup
        imagePager.addOnPageChangeListener(this);
        imagePager.setOffscreenPageLimit(4);
        imagePager.setAdapter(new MyViewPager(getSupportFragmentManager()));
        setUiPageViewController();

        autoPager();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onresume called.");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onpause called.");
    }

    private void autoPager() {
        handler = new Handler();
        handler.postDelayed(myRunnable, 3000);
    }

    private void setUiPageViewController() {

        dotsCount = imagePager.getAdapter().getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            pager_indicator.setBackgroundColor(Color.alpha(0));
            pager_indicator.setGravity(Gravity.CENTER);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //initiates OAUTH
            case R.id.go:
                Loglr.getInstance()
                        //Set your application consumer key from Tumblr
                        .setConsumerKey(Constants.CONSUMER_KEY)
                        //Set your application Secret consumer key from Tumblr
                        .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                        //Implement interface to receive Token and Secret Token
                        .setLoginListener(this)
                        //Interface to receive call backs when things go wrong
                        .setExceptionHandler(this)
                        .setUrlCallBack(CALLBACK_URL)
                        .initiateInDialog(getSupportFragmentManager());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //This line passes callback to the DialogFragment
        Loglr.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onLoginFailed(RuntimeException exception) {
        Toast.makeText(LoginActivity.this, "An error occurred. Please make sure you're connected to the internet.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLoginSuccessful(LoginResult loginResult) {
        Log.d(TAG, "onLoginSuccessful: Login successful!");
        //add to shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", loginResult.getOAuthToken());
        editor.putString("access_token_secret", loginResult.getOAuthTokenSecret());
        editor.apply();
        isUserActive = true;
        Intent intent = new Intent(LoginActivity.this, MyLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public class MyViewPager extends FragmentPagerAdapter {

        public MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Bundle fragBundl1e = new Bundle();
                    fragBundl1e.putInt("frag", 1);
                    LoginPagerContentFragment one1 = new LoginPagerContentFragment();
                    one1.setArguments(fragBundl1e);
                    return one1;
                case 1:
                    Bundle fragBundle = new Bundle();
                    fragBundle.putInt("frag", 1);
                    LoginPagerContentFragment one = new LoginPagerContentFragment();
                    one.setArguments(fragBundle);
                    return one;
                case 2:
                    Bundle fragBundle1 = new Bundle();

                    fragBundle1.putInt("frag", 2);
                    LoginPagerContentFragment two = new LoginPagerContentFragment();
                    two.setArguments(fragBundle1);
                    return two;
                case 3:
                    Bundle fragBundle2 = new Bundle();

                    fragBundle2.putInt("frag", 3);
                    LoginPagerContentFragment three = new LoginPagerContentFragment();
                    three.setArguments(fragBundle2);
                    return three;
                case 4:
                    Bundle fragBundle3 = new Bundle();

                    fragBundle3.putInt("frag", 4);
                    LoginPagerContentFragment four = new LoginPagerContentFragment();
                    four.setArguments(fragBundle3);
                    return four;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    private Runnable myRunnable = new Runnable() {

        @Override
        public void run() {
            //Log.d(TAG, "run is starting execution now...");

            if (!isUserActive) {
                //Log.d(TAG, "user is not active. autoPager continues.");
                handler.postDelayed(this, PAGE);
                imagePager.setCurrentItem(check, true);
                check++;
                if (check == pageLayouts.length || check > pageLayouts.length) {
                    handler.removeCallbacksAndMessages(null);
                    //Log.d(TAG, "check is at the limit. exiting pager...");
                }

            } else {
                handler.removeCallbacksAndMessages(null);
                //Log.d(TAG, "user is active... exiting pager.");
            }
        }

    };

}




