package cool.lucasleabres.ruby.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Post;

import org.scribe.exceptions.OAuthConnectionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.adapter.RecyclerAdapter;
import cool.lucasleabres.ruby.util.Constants;
import cool.lucasleabres.ruby.util.NetworkChecker;
import cool.lucasleabres.ruby.util.PrefsManager;
import cool.lucasleabres.ruby.view.LoadingListener;

public class SearchyActivity extends AppCompatActivity implements LoadingListener, View.OnClickListener {

    private static final String TAG = "SEARCHY";
    private RecyclerAdapter mRecyclerAdapter;
    private List<Post> posts;
    List<Post> newPosts;

    EditText query;
    Button search;
    Button go;
    RecyclerView recycler;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchy);
        handler = new Handler();

        search.setOnClickListener(this);
        go.setOnClickListener(this);

        query.setImeActionLabel("Search", EditorInfo.IME_ACTION_SEARCH);

        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH & query.getText().toString() != "") {

                    NetworkChecker checker = new NetworkChecker(SearchyActivity.this);
                    AlertDialog dialog = checker.isConnected();
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        go.setVisibility(View.INVISIBLE);
                        searchTagged();
                    }

                    //close ime
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getCurrentFocus() != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    handled = true;
                }
                return handled;
            }
        });

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                go.setVisibility(View.VISIBLE);

            }
        });
    }


    private void searchTagged() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String[] tokens = getTokens();
                JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, tokens[0], tokens[1]);

                try {
                    final List<Post> posts = client.tagged(query.getText().toString());
                    Log.d("SearchActivity", "run: tagged size" + posts.size());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (posts.size() != 0) {
                                setUpRecyclerView(posts);
                            } else {
                                Toast.makeText(SearchyActivity.this, "Blog not found.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } catch (JumblrException e) {
                    Log.d(TAG, "jumblr e thrown.");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SearchyActivity.this, "Blog not found.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void setUpRecyclerView(List<Post> model) {

        recycler.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        posts = model;
        mRecyclerAdapter = new RecyclerAdapter(recycler, model);
        mRecyclerAdapter.setOnLoadMoreListener(this);
        recycler.setAdapter(mRecyclerAdapter);

    }
    @Override
    public void onLoadMore() {

        Log.d(TAG, "Loading More posts");
        //adding null equates to adding "loading view" into recyclerview
        Log.d(TAG, "onLoadMore: adding null to central list.");
        posts.add(null);
        //call for more
        Log.d(TAG, "onLoadMore: starting new thread for getting new posts");
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Make the request
                Log.d(TAG, "run: on load listener has been called for first time.");
                try {
                    Log.d(TAG, "run: creating new jumblr object in ONLOADMORE listener");

                    Log.d(TAG, "run: recycler adapter setloaded method called");
                    String[] tokens = getTokens();
                    JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, tokens[0], tokens[1]);

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("limit", 20);
                    params.put("offset", posts.size());

                    newPosts = client.userDashboard(params);
                } catch (OAuthConnectionException o) {
                    Log.d(TAG, "run: error creating new posts in onload();");
                } catch (JumblrException j) {
                    Log.d(TAG, "run: jumblr exception thrown! wtf");
                } catch (OutOfMemoryError e) {
                    Log.e(TAG, "OUT OF MEMORY ERROR");
                }
                Log.d(TAG, "central list size:" + posts.size());

                Log.d(TAG, "run:  handler is now posting update to recycleradapter");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //removing loading sign, populating new items
                        //Remove loading items

                        int size = posts.size() - 1;
                        posts.remove(posts.size() - 1);
                        mRecyclerAdapter.notifyItemRemoved(posts.size());

                        if (newPosts != null) {
                            List<Post> newList = newPosts;
                            for (Object object : newList) {
                                posts.add((Post) object);
                            }
                        }

                        Log.d(TAG, "run: recycler adapter notify item range changed called");
                        mRecyclerAdapter.notifyItemRangeChanged(size, posts.size());
                        mRecyclerAdapter.setLoaded();
                    }
                });


            }
        }).start();
    }

    private String[] getTokens() {
        String token = PrefsManager.getOAuthToken(this);
        String tokenSecret = PrefsManager.getOAuthToken(this);
        return new String[]{token, tokenSecret};
    }

    @Override
    public void onClick(View v) {
        go.setVisibility(View.INVISIBLE);
        searchTagged();
    }

}
