package lucasleabres.ruby.free;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cool.lucasbedolla.ruby.R;
import oauth.signpost.exception.OAuthCommunicationException;

public class LikedActivity extends AppCompatActivity {

    public static final String TAG = "ACTIVITY LIKED";

    private String token;
    private String token_secret;

    @Bind(R.id.liked_back)
    ImageView mback;
    @Bind(R.id.liked_recycler)
    RecyclerView recycler;
    @Bind(R.id.liked_progress)
    ProgressBar progress;
    @Bind(R.id.liked_title)
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
        ButterKnife.bind(this);
        mback.setOnClickListener(likeBacker);
        likedTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/aleo.ttf"));

        try {
            createDataSet();
        } catch (OAuthCommunicationException e) {
            Log.d(TAG, "onCreate: oauth exception thrown on dataset creation. : " + e.toString());
        }
    }

    private void createDataSet() throws OAuthCommunicationException {

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
                            setUpRecyclerView(PostSorter.returnCasted(userPosts));
                            Log.d(TAG, "setuprecyclerview has been called with globallist with size of: " + globalList.size());
                        }
                    });
                    //end of thread
                }
            }).start();
        }
    }

    private void setUpRecyclerView(List<Object> model) {
        //remove progress bar
        progress.setVisibility(View.GONE);

        // pull to refresh layout config
       /*
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(R.color.lightRuby, R.color.red, R.color.crimson);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setProgressViewOffset(false,0,225);
        refreshLayout.setOnRefreshListener(refreshListener);
        */
        //recyclerview setup

        recycler = (RecyclerView) findViewById(R.id.liked_recycler);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        RecyclerAdapter mRecyclerAdapter = new RecyclerAdapter(model, recycler);
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
