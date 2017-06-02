package cool.lucasleabres.ruby.loginpager;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPagerSpinnerFragment extends Fragment {

    ImageView gem;
    ImageView spinner;
    TextView rubyText;


    public LoginPagerSpinnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TUMBLR IS NOT SENDING INFO BACK TO APP
        //OR APP IS NOT PROPERLY READING TUMBLR RESPONSE OF GEM:// IN MANIFEST

        //ALSO WEBVIEW RESETS ITSELF AND DOES NOT ALLOW PERSON TO SIGN IN
        //PERHAPS TEST TO SEE IF

        // GET EXTRAS FROM LOG OUT, SHOW LOG OUT PAGE,
        // ALLOW FOR GARBAGE COLLECTION TO OCCUR THEN LOAD IMAGES
        // TO AVOID OOME ON LOGOUT

        //GO TO SINGLE PAGE ACTIVITY THAT SAYS WAIT
        //THEN ONCE GARBAGE COLLECTION IS COMPLETE GO TO LOGIN

        // MAKE RUBY LOGIN IMAGE NON TRANSPARENT

        //CLICKING NO WAY MAKES APP OPEN MAIN ACTIVITY AND CRASH -> IS SETTING NULL SHARED PREFS VALUES DESPITE


        View view = inflater.inflate(R.layout.fragment_login_pager_spinner, container, false);
        gem = (ImageView) view.findViewById(R.id.gem_center);
        spinner = (ImageView) view.findViewById(R.id.gem_spinner);
        rubyText = (TextView) view.findViewById(R.id.login_ruby);

        //Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/aleo.ttf");
        //rubyText.setTypeface(type);
        startAnimation(rubyText, 1000);
        startAnimation(gem, 1000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startSpinAnimation(spinner, 5000);
            }
        }, 1000);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //startSpinAnimation(spinner,10000);

    }

    private void startAnimation(View v, int milis) {

        Animation scaleAnim = new ScaleAnimation(
                0f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setInterpolator(new OvershootInterpolator(4f));


        Animation alphaAnim = new AlphaAnimation(0f, 1f);
        alphaAnim.setInterpolator(new LinearInterpolator());

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scaleAnim);
        //set.addAnimation(alphaAnim);
        set.setFillAfter(true);
        set.setDuration(milis);
        set.start();
        v.setAnimation(set);
    }


    private void startSpinAnimation(View v, int milis) {

        Animation rotateAnimation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(milis);
        v.setAnimation(rotateAnimation);
        rotateAnimation.start();

    }

}
