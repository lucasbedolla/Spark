package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lucas Bedolla on 10/23/2017.
 */

public class EasyPager extends FragmentPagerAdapter {

    public EasyPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        OnboardingFragment frag = new OnboardingFragment();
        Bundle bundle = new Bundle();

        if (position == 3) {
            bundle.putInt("key", 1);
        } else {
            bundle.putInt("key", 0);
        }
        frag.setArguments(bundle);

        return frag;
    }

}
