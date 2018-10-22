package cool.lucasbedolla.swish.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
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
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.view.EndlessScrollListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment
        extends Fragment implements FetchPostListener, View.OnTouchListener, View.OnClickListener {

    public static final int ID = 0;

    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Post> loadedPosts;
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshLayout.setRefreshing(true);
            loadedPosts.clear();
            fetchPosts(getActivity(), loadedPosts.size(), ((MainActivity) getActivity()), FetchTumblrPostsTask.DASHBOARD);
        }
    };

    private ImageView loadingLottie;
    private boolean alreadyInitialized = false;

    private void fetchPosts(Context ctx, int postSize, FetchPostListener listener, int actionID) {
        new FetchTumblrPostsTask().execute(ctx, postSize, listener, actionID);
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
        recyclerViewMain.setItemViewCacheSize(4);
        recyclerViewMain.setDrawingCacheEnabled(true);
        recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        if (MyPrefs.getIsDualMode(getActivity())) {
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerViewMain.setLayoutManager(manager);
            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    Log.d("scrolling", "STAGGERED: onLoadMore:  CALLED");
                    fetchPosts(getActivity(), loadedPosts.size(), DashboardFragment.this, FetchTumblrPostsTask.DASHBOARD);
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
                    fetchPosts(getActivity(), loadedPosts.size(), DashboardFragment.this, FetchTumblrPostsTask.DASHBOARD);
                }
            };
            recyclerViewMain.addOnScrollListener(endlessScrollListener);
            Log.d("scrolling", "MONO: endlessScrolling Initialized");
        }

        fetchPosts(getActivity(), loadedPosts.size(), DashboardFragment.this, FetchTumblrPostsTask.DASHBOARD);
        return layout;
    }


    @Override
    public void fetchedPosts(final List<Post> fetchedPosts) {

        if (loadingLottie.getVisibility() == View.VISIBLE) {
            loadingLottie.setVisibility(View.GONE);
            loadingLottie.clearAnimation();
        }
        //index of last item loaded
        int indexStart = 0;
        if (loadedPosts.size() != 0) {
            indexStart = loadedPosts.size() - 1;
        }

        loadedPosts.addAll(fetchedPosts);

        if (!alreadyInitialized) {
            //recycleradapter config
            adapter = new RecyclerAdapter((MainActivity) getActivity(), loadedPosts);
            recyclerViewMain.setAdapter(adapter);
            refreshLayout.setRefreshing(false);
            alreadyInitialized = true;
        }

        int indexEnd = loadedPosts.size() - 1;


        Log.d("POSTS", "fetchedPosts: notify item inserted between index:" + indexStart + ", and " + indexEnd);
        adapter.notifyItemRangeInserted(indexStart, indexEnd);
    }

    @Override
    public void fetchFailed(Exception e) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        ((FragmentEventController) getActivity()).submitEvent(ID, v, 0);
    }
}
