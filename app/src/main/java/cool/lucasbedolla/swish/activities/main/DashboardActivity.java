package cool.lucasbedolla.swish.activities.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.settings.SettingsActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.core.UnderTheHoodActivity;
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.util.MyPrefs;


public class DashboardActivity extends UnderTheHoodActivity implements FetchPostListener, View.OnTouchListener {

    int firstVisibleItem, visibleItemCount, totalItemCount;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager manager;
    private List<Post> posts = new ArrayList<>();
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshLayout.setRefreshing(true);
            posts.clear();
            fetchPosts(DashboardActivity.this, posts.size(), DashboardActivity.this);
        }
    };

    private View menuButton;

    private View lay;
    private TextView menuBack;
    private View menuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

//        ImageView toBlog = findViewById(R.id.to_blog);
//        toBlog.setOnClickListener(this);

        lay = findViewById(R.id.lay);
        lay.setOnTouchListener(this);

        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);

        menuBack = findViewById(R.id.menu_back);
        menuBack.setOnClickListener(this);

        menuLayout = findViewById(R.id.menu_layout);

        TextView settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // pull to refresh layout config
        refreshLayout = findViewById(R.id.refresher);
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(R.color.pool_blue, R.color.pool_deep_end, R.color.pool_shallow, R.color.pool_time);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setProgressViewOffset(false, 0, 225);
        refreshLayout.setOnRefreshListener(refreshListener);

        //recyclerview config
        recyclerViewMain = findViewById(R.id.recycler);

        recyclerViewMain.setItemViewCacheSize(26);
        recyclerViewMain.setDrawingCacheEnabled(true);
        recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (MyPrefs.getIsDualMode(this)) {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            ((StaggeredGridLayoutManager) manager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerViewMain.setLayoutManager(manager);
        } else {
            manager = new LinearLayoutManager(this);
            ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewMain.setLayoutManager(manager);
            setOnScroll();
        }

        fetchPosts(this, posts.size(), this);
    }

    private void fetchPosts(Context ctx, int postSize, FetchPostListener listener) {

        new FetchTumblrPostsTask().execute(ctx, postSize, listener);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_button) {
            menuButton.setVisibility(View.GONE);
            menuLayout.setVisibility(View.VISIBLE);
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        } else if (v.getId() == R.id.menu_back) {
            menuLayout.setVisibility(View.GONE);
            menuButton.setVisibility(View.VISIBLE);
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (v.getId() == R.id.to_blog) {
            //TODO make the layout come in from the right
        }
    }


    private void setOnScroll() {
        recyclerViewMain.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (MyPrefs.getIsDualMode(DashboardActivity.this)) {
                    visibleItemCount = recyclerViewMain.getChildCount();
                    totalItemCount = manager.getItemCount();
                    firstVisibleItem = ((StaggeredGridLayoutManager) manager).findFirstCompletelyVisibleItemPositions(null)[0];
                } else {
                    visibleItemCount = recyclerViewMain.getChildCount();
                    totalItemCount = manager.getItemCount();
                    firstVisibleItem = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                }

                if (loading) {
                    if (totalItemCount >= previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    fetchPosts(DashboardActivity.this, posts.size(), DashboardActivity.this);

                    loading = true;
                }
            }
        });
    }

    private void animateInMenuLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuBack.setVisibility(View.GONE);
                findViewById(R.id.menu_layout).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.menu_layout).startAnimation(animation);
    }

    private void animateOutMenuLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.menu_layout).setVisibility(View.GONE);
                menuBack.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.menu_layout).startAnimation(animation);
    }

    @Override
    public void fetchedPosts(List<Post> fetchedPosts) {

        int initialPostSize = posts.size();

        for (Post post : fetchedPosts) {
            if (post instanceof PhotoPost) {
                posts.add(post);
            }
        }

        if (initialPostSize == 0) {
            //recycleradapter config
            adapter = new RecyclerAdapter(this, posts);
            recyclerViewMain.setAdapter(adapter);
            refreshLayout.setRefreshing(false);
        } else {
            adapter.notifyItemRangeChanged(0, posts.size());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_SCROLL:
                Toast.makeText(this, "scrolling", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(this, "action up", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this, "action down", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentByTag("IMAGE") != null) {
            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
            return;
        }

        if (menuLayout.getVisibility() == View.VISIBLE) {
            menuLayout.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

}