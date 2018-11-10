package cool.lucasbedolla.swish.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.fragments.DashboardFragment;
import cool.lucasbedolla.swish.fragments.ProfileFragment;
import cool.lucasbedolla.swish.fragments.SearchFragment;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.view.NoSwipeViewPager;

public class MainActivity extends UnderTheHoodActivity implements FragmentEventController {

    private NoSwipeViewPager mViewPager;
    private int pressCount = 0;
    private final int PRESS_COUNT_TWICE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFirstPrep();
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void loginFirstPrep() {
        if (MyPrefs.getIsLoggedIn(this)) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        MyPrefs.setOAuthToken(this, bundle.getString("OAUTH_TOKEN"));
        MyPrefs.setOAuthTokenSecret(this, bundle.getString("OAUTH_TOKEN_SECRET"));
        MyPrefs.setIsLoggedIn(this, true);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DashboardFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle args = new Bundle();
                    args.putString(ProfileFragment.BLOG_NAME, MyPrefs.getCurrentUser(MainActivity.this));
                    profileFragment.setArguments(args);
                    return profileFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    @Override
    public void onBackPressed() {


        Fragment fragment = getSupportFragmentManager().findFragmentByTag("IMAGE");
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .remove(fragment)
                    .commit();

        } else {
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
    }


    //this handles events submitted by fragments
    @Override
    public void submitEvent(int fragmentID, View v, int action) {
        handleDashEvents(v, fragmentID);
    }

    private void handleDashEvents(View v, int fragmentID) {
        switch (v.getId()) {
            case R.id.menu_dash:
                if (fragmentID != 0) {
                    mViewPager.setCurrentItem(0, false);
                }
                break;
            case R.id.menu_search:
                if (fragmentID != 1) {
                    mViewPager.setCurrentItem(1, false);
                }
                break;
            case R.id.menu_profile:
                if (fragmentID != 2) {
                    mViewPager.setCurrentItem(2, false);
                }
                break;
        }
    }


}
