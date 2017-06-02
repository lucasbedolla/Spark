package lucasleabres.ruby.free;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.ruby.R;

//on search, get viewpager position, inflate pagerfragment's recyclerview

public class SearchBlogFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public ProgressBar progress;
    public RecyclerView recycler;
    public TextView notFound;
    private int mPage;

    public SearchBlogFragment() {
        // Required empty public constructor
    }

    public static SearchBlogFragment getFragmentInstance(int page){

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_blog, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.search_recycler);
        progress = (ProgressBar) view.findViewById(R.id.search_progress);
        notFound = (TextView) view.findViewById(R.id.not_found);
        return view;
    }

    public void inflateResults(int page, List<Post> tagged){

        if(page ==0){
            //inflate recycler for tagged posts
            setUpRecyclerView(tagged);
        }else if (page == 1){
            //inflate recycler for blog post search
            setUpRecyclerView(tagged);
        }
    }

    private void setUpRecyclerView(List<Post> model) {

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

        recycler.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        RecyclerAdapter mRecyclerAdapter = new RecyclerAdapter(PostSorter.returnCasted(model), recycler);
        recycler.setAdapter(mRecyclerAdapter);
    }

}
