package cool.lucasbedolla.swish.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.VideoPost;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.adapter.WrapperLinearLayoutManager;
import cool.lucasbedolla.swish.adapter.WrapperStaggeredLayout;
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.Constants;
import cool.lucasbedolla.swish.util.ImageHelper;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.view.EndlessScrollListener;
import cool.lucasbedolla.swish.view.SpacerDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, FetchPostListener {

    public static final int ID = 2;
    public static final String BLOG_NAME = "BLOG_NAME";

    private ArrayList<Post> loadedPosts;
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;
    private boolean alreadyInitialized;
    private SettingsFragment settingsFragment;
    private String blogName;
    private ImageView backdrop;
    private TextView blog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        loadedPosts = new ArrayList<>();

        //lets cut to the chase, shall we?
        if (getArguments() == null) {
            return getErrorLayout();
        }

        settingsFragment = new SettingsFragment();

        //init menu buttons
        layout.findViewById(R.id.menu_dash).setOnClickListener(this);
        layout.findViewById(R.id.menu_search).setOnClickListener(this);
        layout.findViewById(R.id.menu_profile).setOnClickListener(this);
        backdrop = layout.findViewById(R.id.backdrop);
        blog = layout.findViewById(R.id.blog_name);

        //recyclerview config
        recyclerViewMain = layout.findViewById(R.id.recycler);

        Bundle bundle = getArguments();
        blogName = bundle.getString(BLOG_NAME, null);


        Toolbar toolbar = layout.findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.menu_settings);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.settings) {
                    //inflate rounded edge / white backgrounf fragment above the ando
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.settings_container, settingsFragment, "IMAGE")
                            .commitNow();

                    return true;
                }
                return false;
            }
        });

        final Handler handler = new Handler();

        if (blogName == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JumblrClient client = new JumblrClient(Constants.CONSUMER_KEY,
                            Constants.CONSUMER_SECRET,
                            MyPrefs.getOAuthToken(getContext()),
                            MyPrefs.getOAuthTokenSecret(getContext()));
                    blogName = client.user().getName();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            blogBasedCall(blogName);
                        }
                    });
                }
            }).start();
        } else {
            blogBasedCall(blogName);
        }

        return layout;
    }

    private void blogBasedCall(final String name) {


        fetchPosts(getActivity(), 0, this, name);
        blog.setText(name);
        ImageHelper.downloadBlogAvatarIntoImageView(backdrop, name);


        if (MyPrefs.getIsDualMode(getActivity())) {
            WrapperStaggeredLayout manager = new WrapperStaggeredLayout(2, StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerViewMain.setLayoutManager(manager);
            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    Log.d("scrolling", "STAGGERED: onLoadMore:  CALLED");
                    fetchPosts(getActivity(), loadedPosts.size(), ProfileFragment.this, name);
                }
            };
            recyclerViewMain.addOnScrollListener(endlessScrollListener);
            Log.d("scrolling", "STAGGERED: endlessScrolling Initialized");

        } else {
            WrapperLinearLayoutManager manager = new WrapperLinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerViewMain.setLayoutManager(manager);

            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    Log.d("scrolling", "MONO: onLoadMore:  CALLED");
                    fetchPosts(getActivity(), loadedPosts.size(), ProfileFragment.this, name);
                }
            };
            recyclerViewMain.addOnScrollListener(endlessScrollListener);
            Log.d("scrolling", "MONO: endlessScrolling Initialized");
        }
    }


    private View getErrorLayout() {
        return new ImageView(getActivity());
    }

    @Override
    public void onClick(View v) {
        ((FragmentEventController) getActivity()).submitEvent(ID, v, 0);
    }


    private void fetchPosts(Context ctx, int postSize, FetchPostListener listener, String blogName) {
        new FetchTumblrPostsTask().execute(ctx, postSize, listener, FetchTumblrPostsTask.PROFILE, blogName);
    }

    @Override
    public void fetchedPosts(final List<Post> fetchedPosts) {

        //index of last item loaded
        int indexStart = 0;
        if (loadedPosts.size() != 0) {
            indexStart = loadedPosts.size() - 1;
        }

        Iterator<Post> postIterator = fetchedPosts.iterator();

        while (postIterator.hasNext()) {
            Post post = postIterator.next();
            if (post instanceof VideoPost
                    || post instanceof AudioPost || post instanceof ChatPost) {
                postIterator.remove();
            }
        }

        loadedPosts.addAll(fetchedPosts);

        if (!alreadyInitialized) {
            //recycleradapter config
            adapter = new RecyclerAdapter((MainActivity) getActivity(), loadedPosts);
            if (MyPrefs.getIsDualMode(getContext())) {
                SpacerDecoration decoration = new SpacerDecoration(20);
                recyclerViewMain.addItemDecoration(decoration);
            }
            recyclerViewMain.setAdapter(adapter);
            alreadyInitialized = true;
        }

        int indexEnd = loadedPosts.size() - 1;

        Log.d("POSTS", "fetchedPosts: notify item inserted between index:" + indexStart + ", and " + indexEnd);
        adapter.notifyItemRangeInserted(indexStart, indexEnd);
    }

    @Override
    public void fetchFailed(Exception e) {
    }

}
