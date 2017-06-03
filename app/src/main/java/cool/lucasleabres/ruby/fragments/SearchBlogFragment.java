package cool.lucasleabres.ruby.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import cool.lucasleabres.ruby.view.LoadingListener;

//on search, get viewpager position, inflate pagerfragment's recyclerview

public class SearchBlogFragment extends Fragment implements LoadingListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "SEARCH BLOG FRAGMENT";

    public RecyclerView recycler;
    public TextView notFound;
    private int mPage;
    private Handler handler;
    private List<Post> posts;
    private List<Post> newPosts;
    private RecyclerAdapter mRecyclerAdapter;

    public SearchBlogFragment() {
        // Requirejava.lang.Stringd empty public constructor
    }

    public static SearchBlogFragment getFragmentInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SearchBlogFragment fragment = new SearchBlogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_blog, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.search_recycler);
        notFound = (TextView) view.findViewById(R.id.not_found);
        return view;
    }

    public void inflateResults(List<Post> tagged, AppCompatActivity app) {

        if (mPage == 0) {
            //inflate recycler for tagged posts
            setUpRecyclerView(tagged, app);
        } else if (mPage == 1) {
            //inflate recycler for blog post search
            setUpRecyclerView(tagged, app);
        }
    }

    private void setUpRecyclerView(List<Post> model, AppCompatActivity compat) {

        recycler.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
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

                        posts.addAll(newPosts);

                        Log.d(TAG, "run: recycler adapter notify item range changed called");
                        mRecyclerAdapter.notifyItemRangeChanged(size, posts.size());
                        mRecyclerAdapter.setLoaded();
                    }
                });


            }
        }).start();
    }

    private String[] getTokens() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String token = preferences.getString("access_token", null);
        Log.d(TAG, "1, token: " + token);
        String token_secret = preferences.getString("access_token_secret", null);
        Log.d(TAG, "1, token secret: " + token_secret);
        return new String[]{token, token_secret};
    }

}
