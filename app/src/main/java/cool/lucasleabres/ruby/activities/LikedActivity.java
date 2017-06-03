package cool.lucasleabres.ruby.activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;

import org.scribe.exceptions.OAuthException;

import java.util.ArrayList;
import java.util.List;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.adapter.RecyclerAdapter;
import cool.lucasleabres.ruby.model.MyModel;
import cool.lucasleabres.ruby.util.Constants;
import cool.lucasleabres.ruby.util.NetworkChecker;

public class LikedActivity extends AppCompatActivity {

    public static final String TAG = "ACTIVITY LIKED";

    private String token;
    private String token_secret;

    ImageView mback;
    RecyclerView recycler;
    ProgressBar progress;
    TextView likedTitle;
    private List<Post> userPosts;
    private List<MyModel> globalList;
    private Handler handler = new Handler(Looper.getMainLooper());
    private View.OnClickListener likeBacker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //go back
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);
        mback.setOnClickListener(likeBacker);
        likedTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aleo.ttf"));

        try {
            createDataSet();
        } catch (OAuthException e) {
            Log.d(TAG, "onCreate: oauth exception thrown on dataset creation. : " + e.toString());
        }
    }

    private void createDataSet() throws OAuthException {

        getTokens();

        NetworkChecker checker = new NetworkChecker(this);
        //checker object will return null if connection detected
        AlertDialog dialog = checker.isConnected();
        if (dialog != null) {
            dialog.show();

        } else {



            new Thread(new Runnable() {
                @Override
                public void run() {

                    //jumblr stuff
                    Log.d(TAG, "setting up jumblr client");
                    JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
                    Log.d(TAG, "setting jumblr tokens");
                    client.setToken(token, token_secret);
                    //get liked posts
                    userPosts = client.userLikes();

                    Log.d(TAG, "post list size: " + userPosts.size());
                    //populating recycler view
                    globalList = new ArrayList<>();
                    //note: handler must be created in related thread (and is)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setUpRecyclerView(userPosts);
                            Log.d(TAG, "setuprecyclerview has been called with globallist with size of: " + globalList.size());
                        }
                    });
                    //end of thread
                }
            }).start();
        }
    }

    private void setUpRecyclerView(List<Post> model) {
        //remove progress bar
        progress.setVisibility(View.GONE);

        recycler = (RecyclerView) findViewById(R.id.liked_recycler);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        RecyclerAdapter mRecyclerAdapter = new RecyclerAdapter(recycler, model);
        recycler.setAdapter(mRecyclerAdapter);
    }

    private void getTokens() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("access_token", null);
        Log.d(TAG, "1, token: " + token);
        token_secret = preferences.getString("access_token_secret", null);
        Log.d(TAG, "1, token secret: " + token_secret);
    }

}
