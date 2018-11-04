package cool.lucasbedolla.swish;

import android.app.Application;


/**
 * Created by LUCASVENTURES on 5/18/2016.
 */
public class EmbrApplication extends Application {

    private static EmbrApplication context;

    public static Application getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
}

