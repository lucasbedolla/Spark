package cool.lucasbedolla.swish.fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.http.FetchTumblrPostsTask;
import cool.lucasbedolla.swish.listeners.FetchPostListener;
import cool.lucasbedolla.swish.listeners.FragmentEventController;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements FetchPostListener, View.OnClickListener {

    public static final int ID = 2;

    private String searchValue;
    private String lastSearch;

    private List<Post> loadedPosts = new ArrayList<>();
    private RecyclerView recyclerViewMain;
    private RecyclerAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_search, container, false);

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

        //SEARCHBOX CONFIG
        EditText editText = layout.findViewById(R.id.search_bar);
        editText.setImeActionLabel("Go", EditorInfo.IME_ACTION_GO);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) {
                    return;
                }
                searchValue = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchValue == null || searchValue.equals(null)) {
                        Toast.makeText(getContext(), "Enter a search to proceed.", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    if (searchValue.equals(lastSearch)) {
                        Toast.makeText(getContext(), "Searching...", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    //TODO: search using jumblr through main activity
                    Log.d("Search Value", "onEditorAction: " + searchValue);

                    fetchPosts(getContext(), 40, SearchFragment.this, FetchTumblrPostsTask.SEARCH);

                    //todo: ONSEARCH COMPLETED SET BELOW CODE, MUST SET AFTER SEARCH FAILURE
                    //TODO: ON SEARCH COMPLETED, ANIMATE THE WHALE OUT
                    lastSearch = searchValue;
                    return true;
                }
                return false;
            }
        });
        return layout;
    }

    private void fetchPosts(Context ctx, int postSize, FetchPostListener listener, int actionID) {
        new FetchTumblrPostsTask().execute(ctx, postSize, listener, actionID, searchValue);
    }

    @Override
    public void fetchedPosts(List<Post> fetchedPosts) {
        if (adapter != null && adapter.getItemCount() > 0) {
            //clear items upon a new search
            loadedPosts.clear();
        }
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
        Toast.makeText(getContext(), "Search has unexpectedly failed. Please try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        ((FragmentEventController) getActivity()).submitEvent(ID, v, 0);
    }
}
