package cool.lucasbedolla.swish.util;

/**
 * Created by Lucas Bedolla on 6/17/2017.
 */

public class FirebaseManager {
    public enum Type {
        LOGIN_SUCCESSFUL,
        APP_STARTED,
        TIMELINE_REFRESH,
        BLOG_CHANGE,

    }

   /*
    public static void logEvent(UnderTheHoodActivity activity, String eventType){

        //user has started viewing through this activity
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "test_id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "test_name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "tumblr_timeline");
        activity.getFirebaseInstance().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


    }
    */
}
