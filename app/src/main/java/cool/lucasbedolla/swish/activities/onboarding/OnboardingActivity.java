package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.UnderTheHoodActivity;

public class OnboardingActivity extends UnderTheHoodActivity implements ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private PageIndicatorView pageIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        pager = findViewById(R.id.content_pager);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(new EasyPager(getSupportFragmentManager()));

        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setViewPager(pager);

        pager.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            pageIndicatorView.setAnimation(getAlphaAnimation(1f, 0f));
            pageIndicatorView.setVisibility(View.GONE);
        } else {
            if (pageIndicatorView.getVisibility() == View.GONE) {
                pageIndicatorView.setAnimation(getAlphaAnimation(0f, 1f));
                pageIndicatorView.setVisibility(View.VISIBLE);
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
}
