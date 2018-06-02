package cool.lucasbedolla.swish.http;

import android.content.Context;
import android.os.AsyncTask;

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

    public static final int DASHBOARD = 0;
    public static final int SEARCH = 1;
    public static final int PROFILE = 2;

    private WeakReference<FetchPostListener> listener;

    @Override
    protected Object doInBackground(Object[] objects) {

        WeakReference<Context> ctx = new WeakReference<>((Context) objects[0]);

        int currentSizeOfPostsList = (int) objects[1];

        listener = new WeakReference<>((FetchPostListener) objects[2]);

        int actionID = (int) objects[3];
        String blogName = null;
        if (objects.length > 4) {
            blogName = (String) objects[4];
        }

        List<Post> fetchedList = new ArrayList<>(40);
        try {
            String token = MyPrefs.getOAuthToken(ctx.get());
            String token_secret = MyPrefs.getOAuthTokenSecret(ctx.get());
            JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, token, token_secret);
            MyPrefs.setCurrentUser(ctx.get(), client.user().getName());

            Map<String, Object> params = new HashMap<>();
            params.put("limit", 40);
            params.put("offset", currentSizeOfPostsList);

            switch (actionID) {
                case SEARCH:
                    if (blogName == null) {
                        listener.get().fetchFailed(new Exception("No blog name set during fetch task."));
                    }
                    fetchedList = client.tagged(blogName, params);
                    break;
                case DASHBOARD:
                    fetchedList = client.userDashboard(params);
                    break;
                case PROFILE:
                    if (blogName == null) {
                        listener.get().fetchFailed(new Exception("No blog name set during fetch task."));
                    } else {
                        fetchedList = client.blogPosts(blogName, params);
                    }
                    break;
            }

        } catch (OAuthConnectionException | JumblrException o) {
            listener.get().fetchFailed(o);
        } catch (OutOfMemoryError e) {
            listener.get().fetchFailed((Exception) e.getCause());
        }
        return fetchedList;
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
