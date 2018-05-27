package cool.lucasbedolla.swish.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Post;

import org.scribe.exceptions.OAuthConnectionException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.util.Constants;
import cool.lucasbedolla.swish.util.MyPrefs;

/**
 * Created by Lucas Bedolla on 7/4/2017.
 */

public class FetchTumblrPostsTask extends AsyncTask {

    private static final String TAG = "FetchTumblrPostsTask";

    private WeakReference<FetchPostListener> listener;

    @Override
    protected Object doInBackground(Object[] objects) {

        WeakReference<Context> ctx = new WeakReference<>((Context) objects[0]);

        int currentSizeOfPostsList = (int) objects[1];

        listener = new WeakReference<>((FetchPostListener) objects[2]);

        List<Post> dashList = new ArrayList<>(40);
        try {
            String token = MyPrefs.getOAuthToken(ctx.get());
            String token_secret = MyPrefs.getOAuthTokenSecret(ctx.get());
            JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, token, token_secret);
            Map<String, Object> params = new HashMap<>();
            params.put("limit", 40);
            params.put("offset", currentSizeOfPostsList);

            dashList = client.userDashboard(params);
        } catch (OAuthConnectionException o) {
            Log.d(TAG, "run: error creating new posts in onload();");
        } catch (JumblrException j) {
            Log.d(TAG, "run: jumblr exception thrown!");
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "OUT OF MEMORY ERROR: " + e.getMessage());
        }
        return dashList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        listener.get().fetchedPosts((List<Post>) o);
        listener.clear();
        listener = null;
    }
}
