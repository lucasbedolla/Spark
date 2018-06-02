package cool.lucasbedolla.swish.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.fragments.DashboardFragment;
import cool.lucasbedolla.swish.fragments.ProfileFragment;
import cool.lucasbedolla.swish.fragments.SearchFragment;
import cool.lucasbedolla.swish.fragments.SparkFragment;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.view.NoSwipeViewPager;

public class MainActivity extends UnderTheHoodActivity implements FetchPostListener, View.OnTouchListener, FragmentEventController {

    private NoSwipeViewPager mViewPager;

    private int pressCount = 0;
    private final int PRESS_COUNT_TWICE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        //TODO: this will handle menu controls

    }

    @Override
    public void fetchedPosts(List<Post> posts) {

    }

    @Override
    public void fetchFailed(Exception e) {
        Toast.makeText(this, "Search has unexpectedly failed. Please try again later.", Toast.LENGTH_SHORT).show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DashboardFragment();
                case 1:
                    return new SparkFragment();
                case 2:
                    return new SearchFragment();
                case 3:
                    return new ProfileFragment();
                default:
                    return new DashboardFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_SCROLL:
                Toast.makeText(this, "scrolling", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(this, "action up", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this, "action down", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (pressCount == PRESS_COUNT_TWICE) {
            super.onBackPressed();
        } else {
            pressCount++;
            Toast.makeText(this, "Press back once more to exit.", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressCount = 0;
                }
            }, 3000);
        }
    }


    //this handles events submitted by fragments
    @Override
    public void submitEvent(int fragmentID, View v, int action) {
        switch (fragmentID) {
            case 0:
                handleDashEvents(v);
                break;
            case 1:
                handleSparkEvents();
                break;
            case 2:
                handleSearchEvents(v);
                break;
            case 3:
                handleProfEvents();
                break;
        }
    }

    private void handleProfEvents() {
    }

    private void handleSearchEvents(View v) {
        switch (v.getId()) {
            case R.id.menu_dash:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.menu_spark:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.menu_search:

                break;
            case R.id.menu_profile:
                mViewPager.setCurrentItem(3, true);
                break;
        }
    }

    private void handleSparkEvents() {
    }

    private void handleDashEvents(View v) {
        switch (v.getId()) {
            case R.id.menu_dash:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.menu_spark:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.menu_search:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.menu_profile:
                mViewPager.setCurrentItem(3, true);
                break;
        }
    }
}
