package cool.lucasbedolla.swish;

import android.app.Application;

import io.fabric.sdk.android.Fabric;


/**
 * Created by LUCASVENTURES on 5/18/2016.
 */
public class SparkApplication extends Application {

    private static SparkApplication context;

    public static Application getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this);
        context = this;
    }
}

