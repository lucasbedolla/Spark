package cool.lucasleabres.ruby;

import android.app.Application;

/**
 * Created by LUCASVENTURES on 5/18/2016.
 */
public class CustomApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

       /*
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (preferences.getString("token", null) != null) {
            Intent intent = new Intent(this, lucasleabres.ruby.free.MyLauncher.class);
            startActivity(intent);
        }
        */

    }
}
