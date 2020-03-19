package cool.lucasbedolla.swish.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;
import cool.lucasbedolla.swish.util.ImageHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, FetchPostListener {

    public static final int ID = 3;
    public static final String BLOG_NAME = "BLOG_NAME";

    private List<Post> loadedPosts = new ArrayList<>();
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);

        //lets cut to the chase, shall we?
        if (getArguments() == null) {
            return getErrorLayout();
        }

        //init menu buttons
        layout.findViewById(R.id.menu_dash).setOnClickListener(this);
        layout.findViewById(R.id.menu_search).setOnClickListener(this);
        layout.findViewById(R.id.menu_spark).setOnClickListener(this);
        layout.findViewById(R.id.menu_profile).setOnClickListener(this);
        //recyclerview config
        recyclerViewMain = layout.findViewById(R.id.recycler);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setItemViewCacheSize(26);
        recyclerViewMain.setDrawingCacheEnabled(true);
        recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bundle bundle = getArguments();
        /*
        blog name is not self contained for flexibility of class
         */
        String blogName = bundle.getString(BLOG_NAME);

        if (blogName != null) {
            ImageView backdrop = layout.findViewById(R.id.backdrop);
            ImageHelper.downloadBlogAvatarIntoImageView(backdrop, blogName);
            fetchPosts(getActivity(), 0, this, blogName);
        } else {
            //todo: provide error message and return error layout  :O
            return getErrorLayout();
        }

        //success!
        return layout;
    }

    public View getErrorLayout() {
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
    public void fetchedPosts(List<Post> fetchedPosts) {
        int initialPostSize = loadedPosts.size();

        for (Post post : fetchedPosts) {
            if (post instanceof PhotoPost) {
                loadedPosts.add(post);
            }
        }

        if (initialPostSize == 0) {
            //recycleradapter config
            adapter = new RecyclerAdapter((MainActivity) getActivity(), loadedPosts);
            recyclerViewMain.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_in);
            recyclerViewMain.startAnimation(anim);
        } else {
            adapter.notifyItemRangeChanged(0, loadedPosts.size());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void fetchFailed(Exception e) {
        Toast.makeText(getActivity(), "Search has unexpectedly failed. Please try again later.", Toast.LENGTH_SHORT).show();
    }

}
