package cool.lucasleabres.ruby.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by LUCASVENTURES on 5/4/2016.
 */
public class NetworkChecker {
    public static final String TAG = "NETWORKCHECKER";

    private Context context;
    private AlertDialog alert = null;

    public NetworkChecker(Context c) {
        context = c;
    }


    public AlertDialog isConnected() {

        if (checkConnection()) {
            /*
            connection is valid!
             */
        } else {
            alert = new AlertDialog.Builder(context)
                    .setTitle("No internet connection detected.")
                    .setMessage("Please connect to the internet.")
                    .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert.dismiss();
                        }
                    }).create();
        }
        context = null;
        return alert;
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
