package cool.lucasbedolla.swish.activities.onboarding;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Lucas Bedolla on 10/23/2017.
 */

public class EasyPager extends FragmentPagerAdapter {

    String[] urls = {

            "https://78.media.tumblr.com/78c1f796b9af285c85106728221f638f/tumblr_otrybhSCsG1ujqvcvo1_500.gif",
            "https://78.media.tumblr.com/f5c05f21654fcfebb2d0a63c2fc6ed13/tumblr_oqhp6zgFGQ1v8hxmso1_r1_1280.gif",
            "https://78.media.tumblr.com/d3f1f85ed8203d249425eeaf70f5f0e4/tumblr_oynyi5sHUX1rk9xsjo2_400.gif",
            "https://78.media.tumblr.com/0c4556acc0873af9e78d879ee7b02f90/tumblr_oystneD33f1ubmu0ko1_400.gif",
            "https://78.media.tumblr.com/d16dcfc43f0816b34e9fd69cc0613b48/tumblr_oypfa3IfCH1qdwujbo3_250.gif"};

    String[] titles = {
            "A new way to tumblr!",
            "Manage your blogs.",
            "Check out multiple thingies at once.",
            "Experience  things in new fangled ways.",
            "OK. Now click the button."
    };


    public EasyPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {

        OnboardingFragment frag = new OnboardingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", titles[position]);
        bundle.putString("url", urls[position]);
        frag.setArguments(bundle);

        return frag;
    }

}
