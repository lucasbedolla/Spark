package cool.lucasleabres.ruby.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;


public class MyLauncher extends AppCompatActivity {

    private Handler handler;

    ImageView animView;
    TextView rubyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        handler = new Handler();

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/arial.ttf");
        rubyText.setTypeface(face);
        rubyText.setTextSize(30f);


        startAnimation(animView,1400);
        startAnimation(rubyText,1600);
        killAnimation();

    }


        private void startAnimation(View v,int milis) {

            v.setVisibility(View.VISIBLE);
            Animation scaleAnim = new ScaleAnimation(
                                            0f,1f,
                                            0f,1f,
                                            Animation.RELATIVE_TO_SELF,0.5f,
                                            Animation.RELATIVE_TO_SELF,0.5f);
            scaleAnim.setInterpolator(new OvershootInterpolator(8f));
            Animation alphaAnim = new AlphaAnimation(0f,1f);
            alphaAnim.setInterpolator(new LinearInterpolator());
            AnimationSet set = new AnimationSet(false);
            set.addAnimation(scaleAnim);
            //set.addAnimation(alphaAnim);
            set.setFillAfter(true);
            set.setDuration(milis);
            set.start();
            v.setAnimation(set);

        }

        private void killAnimation() {
            handler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MyLauncher.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                                    }
                    },2000);
        }




}
