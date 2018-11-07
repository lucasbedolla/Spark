package cool.lucasbedolla.swish.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.VideoPost;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.adapter.WrapperLinearLayoutManager;
import cool.lucasbedolla.swish.adapter.WrapperStaggeredLayout;
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.view.EndlessScrollListener;
import cool.lucasbedolla.swish.view.SpacerDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment
        extends Fragment implements FetchPostListener, View.OnClickListener {

    public static final int ID = 0;

    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Post> loadedPosts;
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshLayout.setRefreshing(true);
            loadingLottie.setVisibility(View.VISIBLE);
            recyclerViewMain.setVisibility(View.INVISIBLE);
            loadedPosts.clear();
            adapter.notifyDataSetChanged();
            fetchPosts(getActivity(), loadedPosts.size(), FetchTumblrPostsTask.DASHBOARD);
        }
    };

    private ImageView loadingLottie;
    private boolean alreadyInitialized = false;

    private void fetchPosts(Context ctx, int postSize, int actionID) {
        new FetchTumblrPostsTask().execute(ctx, postSize, DashboardFragment.this, actionID);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dashboard, container, false);
        loadedPosts = new ArrayList<>();
        loadingLottie = layout.findViewById(R.id.loading_lottie);

        // pull to refresh layout config
        refreshLayout = layout.findViewById(R.id.refresher);
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(R.color.blue, R.color.orange, R.color.red, R.color.charcoal);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setProgressViewOffset(false, 0, 225);
        refreshLayout.setOnRefreshListener(refreshListener);

        //init menu buttons
        layout.findViewById(R.id.menu_dash).setOnClickListener(this);
        layout.findViewById(R.id.menu_search).setOnClickListener(this);
        layout.findViewById(R.id.menu_spark).setOnClickListener(this);
        layout.findViewById(R.id.menu_profile).setOnClickListener(this);

        //recyclerview config
        recyclerViewMain = layout.findViewById(R.id.recycler);

        if (MyPrefs.getIsDualMode(getActivity())) {
            WrapperStaggeredLayout manager = new WrapperStaggeredLayout(2, StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerViewMain.setLayoutManager(manager);
            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    Log.d("scrolling", "STAGGERED: onLoadMore:  CALLED");
                    fetchPosts(getActivity(), loadedPosts.size(), FetchTumblrPostsTask.DASHBOARD);
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
                    fetchPosts(getActivity(), loadedPosts.size(), FetchTumblrPostsTask.DASHBOARD);
                }
            };
            recyclerViewMain.addOnScrollListener(endlessScrollListener);
            Log.d("scrolling", "MONO: endlessScrolling Initialized");
        }

        fetchPosts(getActivity(), loadedPosts.size(), FetchTumblrPostsTask.DASHBOARD);
        return layout;
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

        if (loadingLottie.getVisibility() == View.VISIBLE) {
            loadingLottie.setVisibility(View.GONE);
            loadingLottie.clearAnimation();
        }

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            recyclerViewMain.setVisibility(View.VISIBLE);
        }
        int indexEnd = loadedPosts.size() - 1;

        Log.d("POSTS", "fetchedPosts: notify item inserted between index:" + indexStart + ", and " + indexEnd);
        adapter.notifyItemRangeInserted(indexStart, indexEnd);
    }

    @Override
    public void fetchFailed(Exception e) {
    }


    @Override
    public void onClick(View v) {
        ((FragmentEventController) getActivity()).submitEvent(ID, v, 0);
    }
}
