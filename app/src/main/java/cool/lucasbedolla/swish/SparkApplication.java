package cool.lucasbedolla.swish;

import android.app.Application;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;

import cool.lucasbedolla.swish.activities.main.DashboardActivity;
import cool.lucasbedolla.swish.activities.onboarding.OnboardingActivity;
import cool.lucasbedolla.swish.util.MyPrefs;

/**
 * Created by LUCASVENTURES on 5/18/2016.
 */
public class SparkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
