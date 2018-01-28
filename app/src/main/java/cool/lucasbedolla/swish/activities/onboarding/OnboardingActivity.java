package cool.lucasbedolla.swish.activities.onboarding;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.main.DashboardActivity;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.util.Constants;
import cool.lucasbedolla.swish.util.MyPrefs;

public class OnboardingActivity extends UnderTheHoodActivity implements ViewPager.OnPageChangeListener, OnClickListener {

    public static final String CALLBACK_URL = "gem://www.gem.com/ok";

    private ViewPager pager;
    private PageIndicatorView pageIndicatorView;

    private EasyPager adapter;
    private View goButton;
    private ImageView splashImageView;
    private RelativeLayout whiteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyPrefs.getIsLoggedIn(this)) {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        setContentView(R.layout.activity_onboarding);

        Window window = getWindow();

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        pager = findViewById(R.id.content_pager);
        pager.setOffscreenPageLimit(5);
        adapter = new EasyPager(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setAnimationType(AnimationType.SLIDE);
        pageIndicatorView.setViewPager(pager);
        pager.addOnPageChangeListener(this);

        goButton = findViewById(R.id.go);
        goButton.setOnClickListener(this);

        whiteLayout = findViewById(R.id.splash);
        splashImageView = findViewById(R.id.splash_image);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginSparkAnimation();
            }
        }, 300);
    }

    private void beginSparkAnimation() {

        final AlphaAnimation alphaIn = new AlphaAnimation(1f, 0f);
        alphaIn.setDuration(900);
        alphaIn.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaIn.setRepeatMode(Animation.REVERSE);
        alphaIn.setRepeatCount(2);
        alphaIn.setStartOffset(400);


        alphaIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation alphaOut = new AlphaAnimation(1f, 0);
                alphaOut.setFillAfter(true);
                alphaOut.setInterpolator(new LinearInterpolator());
                alphaOut.setDuration(1000);
                alphaOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        whiteLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                whiteLayout.startAnimation(alphaOut);

                TransitionDrawable drawable = (TransitionDrawable) splashImageView.getDrawable();
                drawable.startTransition(1000);

                Window window = getWindow();
                WindowManager.LayoutParams winParams = window.getAttributes();
                winParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(winParams);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashImageView.startAnimation(alphaIn);
    }

    @Override
    public void onClick(View view) {
        login();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    private AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha) {
        AlphaAnimation fade = new AlphaAnimation(fromAlpha, toAlpha);
        fade.setInterpolator(new LinearInterpolator());
        fade.setDuration(500);
        fade.setFillAfter(true);
        return fade;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void login() {
        try {
                Loglr.getInstance()
                        .setConsumerKey(Constants.CONSUMER_KEY)
                        .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                        .setLoginListener(new LoginListener() {
                            @Override
                            public void onLoginSuccessful(LoginResult loginResult) {
                                MyPrefs.setOAuthToken(OnboardingActivity.this, loginResult.getOAuthToken());
                                MyPrefs.setOAuthTokenSecret(OnboardingActivity.this, loginResult.getOAuthTokenSecret());
                                MyPrefs.setIsLoggedIn(OnboardingActivity.this, true);

                                Intent intent = new Intent(OnboardingActivity.this, DashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setExceptionHandler(new ExceptionHandler() {
                            @Override
                            public void onLoginFailed(RuntimeException exception) {
                                Toast.makeText(OnboardingActivity.this, "Login failed. Please try again!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setUrlCallBack(CALLBACK_URL)
                        .initiateInActivity(this);
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() > 0) {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }
}
