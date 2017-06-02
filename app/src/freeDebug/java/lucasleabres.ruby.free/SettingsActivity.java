package lucasleabres.ruby.free;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cool.lucasbedolla.ruby.BuildConfig;
import cool.lucasbedolla.ruby.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.cachingCheckBox)
    CheckBox cachingChecked;
    @Bind(R.id.gridCheckBox)
    CheckBox multiPostChecked;
    @Bind(R.id.info)
    Button info;
    @Bind(R.id.logout)
    Button logout;
    @Bind(R.id.infoText)
    TextView infoText;
    @Bind(R.id.gem)
    ImageView gem;
    @Bind(R.id.rubyText)
    TextView rubyText;
    private boolean isUp = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        context = this;

        multiPostChecked.setOnCheckedChangeListener(this);
        cachingChecked.setOnCheckedChangeListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        loadSharedPrefs(context.getPackageName() + "_preferences");
        if (preferences.getBoolean("grid", false)) {
            multiPostChecked.setChecked(true);
        } else {
            multiPostChecked.setChecked(false);
        }
        if (preferences.getBoolean("cache", false)) {
            cachingChecked.setChecked(true);
        } else {
            cachingChecked.setChecked(false);
        }




        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/aleo.ttf");
        rubyText.setTypeface(face);
        infoText.setText("Ruby, Version: "+ BuildConfig.VERSION_NAME+", by Lucas Leabres");
        info.setOnClickListener(this);
        logout.setOnClickListener(this);
        logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
                Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return false;
            }
        });


        startAnimation(gem,1400);
        startAnimation(rubyText,1800);
    }

    public void loadSharedPrefs(String... prefs) {

        // Define default return values. These should not display, but are needed
        final String STRING_ERROR = "error!";
        final Integer INT_ERROR = -1;
        // ...
        final Set<String> SET_ERROR = new HashSet<>(1);

        // Add an item to the set
        SET_ERROR.add("Set Error!");

        // Loop through the Shared Prefs
        Log.i("Loading Shared Prefs", "-----------------------------------");
        Log.i("------------------", "-------------------------------------");

        for (String pref_name : prefs) {

            SharedPreferences preference = getSharedPreferences(pref_name, MODE_PRIVATE);
            Map<String, ?> prefMap = preference.getAll();

            Object prefObj;
            Object prefValue = null;

            for (String key : prefMap.keySet()) {

                prefObj = prefMap.get(key);

                if (prefObj instanceof String) prefValue = preference.getString(key, STRING_ERROR);
                if (prefObj instanceof Integer) prefValue = preference.getInt(key, INT_ERROR);
                // ...
                if (prefObj instanceof Set) prefValue = preference.getStringSet(key, SET_ERROR);

                Log.i(String.format("Shared Preference : %s - %s", pref_name, key),
                        String.valueOf(prefValue));

            }

            Log.i("------------------", "-------------------------------------");

        }

        Log.i("Loaded Shared Prefs", "------------------------------------");

    }

    @Override
    public void onResume() {
        super.onResume();


    }



    private void startAnimation(View v, int milis) {

        v.setVisibility(View.VISIBLE);

        Animation scaleAnim = new ScaleAnimation(
                0f,1f,
                0f,1f,
                Animation.RELATIVE_TO_SELF,.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnim.setInterpolator(new OvershootInterpolator(4f));


        //Animation alphaAnim = new AlphaAnimation(0f,1f);
        //alphaAnim.setInterpolator(new LinearInterpolator());


        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scaleAnim);
        //set.addAnimation(alphaAnim);
        set.setFillAfter(true);
        set.setDuration(milis);
        set.start();
        v.setAnimation(set);



    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.info:
                //moves views up, fades in text
                if(!isUp){

                    isUp=true;
                    //fade textview away

                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(info, "translationY", 0, -100);
                    objectAnimator.setDuration(400);
                    objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                    ObjectAnimator logoutAnimator = ObjectAnimator.ofFloat(logout, "translationY", 0, -100);
                    objectAnimator.setDuration(400);
                    objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(600);
                    infoText.setVisibility(View.VISIBLE);

                    infoText.startAnimation(animation);
                    logoutAnimator.start();
                    objectAnimator.start();
                }else{
                    //moves views down, fades text out
                    isUp=false;

                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(info, "translationY", -100, 0);
                    objectAnimator.setDuration(400);
                    objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                    ObjectAnimator logoutAnimator = ObjectAnimator.ofFloat(logout, "translationY", -100, 0);
                    objectAnimator.setDuration(400);
                    objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                    Animation animation = new AlphaAnimation(1,0);
                    animation.setDuration(300);
                    animation.setFillAfter(true);

                    infoText.startAnimation(animation);
                    logoutAnimator.start();
                    objectAnimator.start();
                }

                break;
            case R.id.logout:
                Toast.makeText(SettingsActivity.this, "Hold down to logout.", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.gridCheckBox:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                if (isChecked) {
                    multiPostChecked.setChecked(true);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("grid", true).commit();
                } else {
                    multiPostChecked.setChecked(false);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("grid", false).commit();
                }
                break;
            case R.id.cachingCheckBox:
                SharedPreferences refs = PreferenceManager.getDefaultSharedPreferences(this);
                if (isChecked) {
                    cachingChecked.setChecked(true);
                    SharedPreferences.Editor edit = refs.edit();
                    edit.putBoolean("cache", true).commit();

                } else {
                    cachingChecked.setChecked(false);
                    SharedPreferences.Editor edit = refs.edit();
                    edit.putBoolean("cache", false).commit();
                }
                break;
        }
    }
}
