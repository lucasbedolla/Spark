package cool.lucasbedolla.swish.activities.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.tumblr.loglr.Interfaces.ExceptionHandler;
import com.tumblr.loglr.Interfaces.LoginListener;
import com.tumblr.loglr.LoginResult;
import com.tumblr.loglr.Loglr;

import org.jetbrains.annotations.NotNull;

import androidx.viewpager.widget.ViewPager;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.util.Constants;
import cool.lucasbedolla.swish.util.MyPrefs;


public class OnboardingActivity extends UnderTheHoodActivity implements ViewPager.OnPageChangeListener, OnClickListener, ExceptionHandler, LoginListener {

    public static final String CALLBACK_URL = "gem://www.gem.com/ok";

    private ViewPager pager;
    private PageIndicatorView pageIndicatorView;
    private EasyPager adapter;
    private View goButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyPrefs.getIsLoggedIn(OnboardingActivity.this)) {
            Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_onboarding);

        pager = findViewById(R.id.content_pager);
        pager.setOffscreenPageLimit(5);
        adapter = new EasyPager(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setViewPager(pager);
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                view.setTranslationX(position < 0 ? 0f : -view.getWidth() * position);
            }
        });
        pager.addOnPageChangeListener(this);

        goButton = findViewById(R.id.go);
        goButton.setOnClickListener(this);

        findViewById(R.id.go).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        login();
    }

    private void login() {
        try {
            Loglr.INSTANCE
                    .setConsumerKey(Constants.CONSUMER_KEY)
                    .setConsumerSecretKey(Constants.CONSUMER_SECRET)
                    .setLoginListener(this)
                    .setExceptionHandler(new ExceptionHandler() {
                        @Override
                        public void onLoginFailed(RuntimeException exception) {
                            Toast.makeText(OnboardingActivity.this, "Login failed. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setUrlCallBack(CALLBACK_URL)
                    .initiate(this);
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginSuccessful(@NotNull LoginResult loginResult) {
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //addBundle
        Bundle bundle = new Bundle();
        bundle.putString("OAUTH_TOKEN", loginResult.getOAuthToken());
        bundle.putString("OAUTH_TOKEN_SECRET", loginResult.getOAuthTokenSecret());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            pageIndicatorView.setAnimation(getAlphaAnimation(1f, 0f));
            pageIndicatorView.setVisibility(View.GONE);
            goButton.setAnimation(getAlphaAnimation(0f, 1f));
            goButton.setVisibility(View.VISIBLE);
        } else {
            if (pageIndicatorView.getVisibility() == View.GONE) {
                pageIndicatorView.setAnimation(getAlphaAnimation(0f, 1f));
                pageIndicatorView.setVisibility(View.VISIBLE);
                goButton.setAnimation(getAlphaAnimation(1f, 0f));
                goButton.setVisibility(View.GONE);
            }
        }

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

    @Override
    public void onLoginFailed(RuntimeException exception) {
        Toast.makeText(this, "Login failed. Please try again!", Toast.LENGTH_LONG).show();
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
