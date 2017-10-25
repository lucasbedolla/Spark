package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lucas Bedolla on 10/23/2017.
 */

public class EasyPager extends FragmentPagerAdapter {

    String[] urls = {
            "https://78.media.tumblr.com/23a03432e9129aae6754995535ca695f/tumblr_ope3fv3ZJs1v1fqrro1_500.gif",
            "https://78.media.tumblr.com/f5c05f21654fcfebb2d0a63c2fc6ed13/tumblr_oqhp6zgFGQ1v8hxmso1_r1_1280.gif",
            "http://78.media.tumblr.com/46310e07580fd30881ea04f6378e205a/tumblr_mq8v7nEkwN1sne1e1o1_250.gif",
            "https://78.media.tumblr.com/7f1e80b9067a22238353705c70ecf1bf/tumblr_oue5i7LRd11v8hxmso1_1280.gif"};

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
        bundle.putString("url", urls[position]);
        frag.setArguments(bundle);

        return frag;
    }

}
