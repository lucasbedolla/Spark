package cool.lucasleabres.ruby.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Lucas Bedolla on 5/30/2017.
 */

public class PrefsManager {

    private static final String PANEL_SETTINGS = "panelSettings";
    private static final String ACCESSS_TOKEN = "access_token";
    private static final String ACCESSS_TOKEN_SECRET = " access_token_secret";



    private PrefsManager(){
        //so as to never be instantiated
    }

    public static boolean getPanelSettingsIsDual(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(PANEL_SETTINGS, false);
    }

    public static void setPanelSettingsIsDual(Context ctx, boolean isDual){
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(PANEL_SETTINGS, isDual).apply();
    }

    public static String getOAuthToken(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(ACCESSS_TOKEN, "");
    }

    public static void setOAuthToken(Context ctx, String token){
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(ACCESSS_TOKEN, token).apply();
    }

    public static String getOAuthTokenSecret(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(ACCESSS_TOKEN_SECRET, "");
    }

    public static void setOAuthTokenSecret(Context ctx, String token){
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(ACCESSS_TOKEN_SECRET, token).apply();
    }

}
