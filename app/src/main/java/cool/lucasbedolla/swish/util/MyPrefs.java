package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.List;

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
    private static final String BLOG_NAMES = "BLOG_NAMES";
    private static final String CURRENT_BLOG = "CURRENT_BLOG";
    private static final String CURRENT_USER = "CURRENT_USER";
    private static final String FONT_STYLE = "FONT_STYLE";


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

    public static List<String> getBlogNames(Context ctx) {
        String jsonString = PreferenceManager.getDefaultSharedPreferences(ctx).getString(BLOG_NAMES, null);
        if (jsonString.equals(null)) {
            return null;
        } else {
            return new Gson().fromJson(jsonString, List.class);
        }
    }

    //this refers to which post creation or interaction will apply to
    public static void setBlogNames(Context ctx, String blogNames) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(BLOG_NAMES, blogNames).apply();
    }

    //this refers to which post creation or interaction will apply to
    public static String getCurrentBlog(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(CURRENT_BLOG, null);
    }

    public static void setCurrentBlog(Context ctx, String currentBlog) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(CURRENT_BLOG, currentBlog).apply();
    }

    //this refers to actual tumblr account which provides the dashboard content
    public static String getCurrentUser(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(CURRENT_USER, null);
    }

    //this refers to actual tumblr account which provides the dashboard content
    public static void setCurrentUser(Context ctx, String currentUser) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(CURRENT_USER, currentUser).apply();
    }

    public static void setIsFunFont(Context ctx, boolean isFun) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(FONT_STYLE, isFun).apply();
    }

    public static boolean getIsFunFont(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(FONT_STYLE, true);
    }
}
