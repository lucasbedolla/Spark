package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Lucas Bedolla on 5/30/2017.
 */

public class MyPrefs {

    private static final String PANEL_SETTINGS = "PANEL_SETTINGS";
    private static final String ACCESSS_TOKEN = "ACCESSS_TOKEN";
    private static final String ACCESSS_TOKEN_SECRET = " ACCESSS_TOKEN_SECRET";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String PROFILE_PIC_VISIBLE = "PROFILE_PIC_VISIBLE";
    private static final String IS_DUAL_VIEW = "IS_DUAL_VIEW";
    private static final String IS_CLASSIC_MODE = "IS_CLASSIC_MODE";
    private static final String IS_MINIMALIST_MODE = "IS_MINIMALIST_MODE";
    private static final String IS_EXTREME_MINIMALIST_MODE = "IS_EXTREME_MINIMALIST_MODE";


    private MyPrefs() {
        //so as to never be instantiated
    }

    public static boolean getPanelSettingsIsDual(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(PANEL_SETTINGS, false);
    }

    public static void setPanelSettingsIsDual(Context ctx, boolean isDual) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(PANEL_SETTINGS, isDual).apply();
    }

    public static String getOAuthToken(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(ACCESSS_TOKEN, "");
    }

    public static void setOAuthToken(Context ctx, String token) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(ACCESSS_TOKEN, token).apply();
    }

    public static String getOAuthTokenSecret(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(ACCESSS_TOKEN_SECRET, "");
    }

    public static void setOAuthTokenSecret(Context ctx, String token) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(ACCESSS_TOKEN_SECRET, token).apply();
    }

    public static boolean getIsLoggedIn(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(IS_LOGGED_IN, false);
    }

    public static void setIsLoggedIn(Context ctx, boolean isLoggedIn) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply();
    }

    public static void setProfilePicInvisible(Context ctx, boolean isVisible) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(PROFILE_PIC_VISIBLE, isVisible).apply();
    }

    public static boolean isProfilePicInvisible(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(PROFILE_PIC_VISIBLE, false);
    }

    public static boolean getIsDualMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_DUAL_VIEW, false);
    }

    public static void setIsDualMode(Context context, boolean isDualView) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(IS_DUAL_VIEW, isDualView).apply();
    }

    public static void setIsClassicMode(Context ctx, boolean enabled) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(IS_CLASSIC_MODE, enabled).apply();
    }

    public static boolean getIsClassicMode(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(IS_CLASSIC_MODE, true);
    }

    public static void setIsMinimalistMode(Context ctx, boolean enabled) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(IS_MINIMALIST_MODE, enabled).apply();
    }

    public static boolean getIsMinimalistMode(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(IS_MINIMALIST_MODE, false);
    }

    public static void setIsExtremeMinimalistMode(Context ctx, boolean enabled) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(IS_EXTREME_MINIMALIST_MODE, enabled).apply();
    }

    public static boolean getIsExtremeMinimalist(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(IS_EXTREME_MINIMALIST_MODE, false);
    }
}