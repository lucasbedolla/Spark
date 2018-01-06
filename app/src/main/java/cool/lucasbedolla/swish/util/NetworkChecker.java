package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by LUCASVENTURES on 5/4/2016.
 */
public class NetworkChecker {
    public static final String TAG = "NETWORKCHECKER";

    private Context context;

    public NetworkChecker(Context c) {
        context = c;
    }


    public boolean isConnected() {
        return checkConnection();
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
