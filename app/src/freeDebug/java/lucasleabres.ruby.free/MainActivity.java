package lucasleabres.ruby.free;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.request.RequestBuilder;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;

import org.scribe.exceptions.OAuthConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cool.lucasbedolla.ruby.R;
import cool.lucasbedolla.ruby.http.ServiceGenerator;
import cool.lucasbedolla.ruby.http.TumblrAPIConnect;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoadingListener {

    private static String token;
    private static String token_secret;
    private static String blogName;
    private final String TAG = "MAIN_ACTIVITY";
    private final int PERMISSION_REQ = 1;
    @Bind(R.id.reconnect)
    Button reconnect;
    @Bind(R.id.refresher)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolBar)
    RelativeLayout menu;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.progressBar)
    ProgressBar prog;
    @Bind(R.id.profile)
    ImageButton profileButton;
    @Bind(R.id.settings)
    Button settingsButton;
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.profile_text)
    TextView profileText;
    @Bind(R.id.dashboard)
    Button dashButton;
    private Context context;
    private boolean isGrid;
    private boolean isUp = true;
    private boolean isalreadyrunningarequest = false;
    private List<Object> centralList;
    private List<Post> newPosts;
    private List<Post> posts;
    private Handler handler = new Handler();
    private RecyclerAdapter mRecyclerAdapter;
    private JumblrClient jumblr;
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            createDataSet();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 4200);
        }
    };
    private View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int height = size.y;

            if (isUp) {
                animateDown(height);
            } else if (!isUp) {
                animateUp(height);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initialize
        init();

        //check permissions

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //request permission here
            String[] request = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };

            ActivityCompat.requestPermissions(this, request, PERMISSION_REQ);

        } else {
            //load content
            //createDataSet();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //if preferences have changed refresh layout
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (isGrid != pref.getBoolean("grid", false)) {
            refreshLayout.setRefreshing(true);
            createDataSet();
            Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 4000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    createDataSet();

                } else {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQ);

                    Toast.makeText(MainActivity.this,
                            "You must agree to permissions in order to continue.", Toast.LENGTH_LONG).show();

                }

                return;
            }
        }
    }

    private void init() {
        context = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        //get tokens tokens
        getTokens();

        //api specific initializations done here
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            menu.setElevation(12);
        }

        //floating action button setup
        fab.setBackgroundColor(Color.WHITE);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(fabListener);

        //menu setup
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        animateDown(size.y);
        profileButton.setOnClickListener(this);
        profileButton.setEnabled(false);
        settingsButton.setOnClickListener(this);
        dashButton.setOnClickListener(this);

        //set typefaces
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/aleo.ttf");
        profileText.setTypeface(typeface);
        settingsButton.setTypeface(typeface);
        dashButton.setTypeface(typeface);

        //check saved layout style
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isGrid = prefs.getBoolean("grid", false);

        //for the recycler adapter
        posts = new ArrayList<>();
    }

    private void createDataSet() {
        NetworkChecker checker = new NetworkChecker(this);
        //checker object will return null if connection detected
        AlertDialog dialog = checker.isConnected();
        if (dialog != null) {
            dialog.show();
            prog.setVisibility(View.INVISIBLE);
            reconnect.setVisibility(View.VISIBLE);
            reconnect.setOnClickListener(this);
        } else {

            reconnect.setVisibility(View.INVISIBLE);
            loadPosts();


        }


    }

    private void loadPosts() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                OAuthHmacSigner signer = new OAuthHmacSigner();
                signer.clientSharedSecret = Constants.CONSUMER_SECRET;
                signer.tokenSharedSecret = token_secret;
                OAuthParameters authorizer = new OAuthParameters();
                authorizer.consumerKey = Constants.CONSUMER_KEY;
                authorizer.signer = signer;
                authorizer.token = token;

                TumblrAPIConnect connect = ServiceGenerator.createService(
                        TumblrAPIConnect.class, Constants.BASE_URL, authorizer);

                String x = connect.listRepos();
                Log.d(TAG, "loadPosts: JSONN:" + x);

            }
        }).start();
    }

    private void jumblrDash() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                Log.d(TAG, "setting up jumblr client");
                jumblr = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, token, token_secret);
                Log.d(TAG, "setting jumblr tokens");
                posts = jumblr.userDashboard();

                // Write the user's name
                User user = jumblr.user();
                blogName = user.getName();
                Log.i(TAG, "\t" + blogName);

                //save blog name to prefs
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("blog", blogName);
                editor.apply();

                String picUrl = "https://api.tumblr.com/v2/blog/" + blogName + ".tumblr.com/avatar";

                Picasso.with(context)
                        .load(picUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(profileButton);

                Log.d(TAG, "post list size: " + posts.size());
                //populating recycler view

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        profileText.setText(blogName);
                        profileText.setTextSize(22f);
                        profileText.setEnabled(true);
                        menu.setVisibility(View.VISIBLE);
                        setUpRecyclerView(posts);
                    }
                });
                //must be set up on main thread


            }
        });
        thread.start();
    }

    /*
    listener and menu animation items
     */

    private void setUpRecyclerView(List<Post> rawPosts) {

        //remove progress bar
        prog.setVisibility(View.GONE);
        // pull to refresh layout config
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(R.color.notification_green, R.color.liked_red, R.color.orange, R.color.search_blue);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setProgressViewOffset(false, 0, 225);
        refreshLayout.setOnRefreshListener(refreshListener);
        PostSorter.returnCasted(rawPosts);
        centralList = PostSorter.returnCasted(rawPosts);


        //prevents recreation of adapter
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new RecyclerAdapter(centralList, mRecyclerView);
            mRecyclerAdapter.setAppCompat(this);
        }

        //sets layout manager depending on shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("grid", false)) {
            StaggeredGridLayoutManager gridLayoutManager =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

            mRecyclerView.setLayoutManager(gridLayoutManager);

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        }


        mRecyclerAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }

    private void getTokens() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("access_token", null);
        Log.d(TAG, "1, token: " + token);
        token_secret = preferences.getString("access_token_secret", null);
        Log.d(TAG, "1, token secret: " + token_secret);
    }

    public void animateUp(int h) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "translationY", 0, -h);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        objectAnimator.start();
        //Toast.makeText(lucasleabres.ruby.free.MainActivity.this, "up", Toast.LENGTH_SHORT).show();

        ObjectAnimator toolbarAnimator = ObjectAnimator.ofFloat(menu, "translationY", h, 0);
        toolbarAnimator.setDuration(500);

        toolbarAnimator.setInterpolator(new OvershootInterpolator());
        menu.setVisibility(View.VISIBLE);
        toolbarAnimator.start();

        isUp = true;
    }

    public void animateDown(int h) {

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int height = size.y;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "translationY", height, 0);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        ObjectAnimator toolbarAnimator = ObjectAnimator.ofFloat(menu, "translationY", 0, height);

        toolbarAnimator.setDuration(500);
        toolbarAnimator.setInterpolator(new OvershootInterpolator());

        toolbarAnimator.start();
        isUp = false;
    }

    //click listener for all menu
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dashboard:
                Point size = new Point();
                getWindowManager().getDefaultDisplay().getSize(size);
                animateDown(size.y);
                break;
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("blog", blogName);
                startActivity(intent);

                break;
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.reconnect:
                NetworkChecker checker = new NetworkChecker(context);
                AlertDialog dialog = checker.isConnected();
                if (dialog != null) {
                    dialog.show();
                } else {
                    createDataSet();
                }
                break;
        }
    }

    @Override
    public void onLoadMore() {

        Log.d(TAG, "Loading More posts");
        //adding null equates to adding "loading view" into recyclerview
        Log.d(TAG, "onLoadMore: adding null to central list.");
        centralList.add(null);
        //call for more
        Log.d(TAG, "onLoadMore: starting new thread for getting new posts");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Make the request
                if (!isalreadyrunningarequest) {
                    Log.d(TAG, "run: on load listener has been called for first time.");
                    isalreadyrunningarequest = true;
                    try {
                        Log.d(TAG, "run: creating new jumblr object in ONLOADMORE listener");
                                /*
                                JumblrClient jumbly = new JumblrClient(Constants.CONSUMER_KEY,
                                        Constants.CONSUMER_SECRET,
                                        token, token_secret);
                                 */

                        Log.d(TAG, "run: recycler adapter setloaded method called");

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("limit", 20);
                        params.put("offset", centralList.size());

                        jumblr.setRequestBuilder(new RequestBuilder(jumblr));

                        newPosts = jumblr.userDashboard(params);
                    } catch (OAuthConnectionException o) {
                        Log.d(TAG, "run: error creating new posts in onload();");
                    } catch (JumblrException j) {
                        Log.d(TAG, "run: jumblr exception thrown! wtf");
                    }
                    Log.d(TAG, "central list size:" + centralList.size());

                    Log.d(TAG, "run:  handler is now posting update to recycleradapter");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //removing loading sign, populating new items
                            //Remove loading items

                            centralList.remove(centralList.size() - 1);
                            mRecyclerAdapter.notifyItemRemoved(centralList.size());

                            //Load data here
                            List<Object> newList = PostSorter.returnCasted(newPosts);
                            for (Object object : newList) {
                                centralList.add(object);
                            }
                            Log.d(TAG, "run: recycler adapter notify item range changed called");
                            mRecyclerAdapter.notifyItemRangeChanged(0, centralList.size());
                        }
                    });
                    mRecyclerAdapter.setLoaded();
                    isalreadyrunningarequest = false;
                } else {
                    Log.d(TAG, "run: loading request has yet to complete, do not call so quickly!");
                }

            }
        }).start();
    }
}





