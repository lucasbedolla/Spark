package cool.lucasleabres.ruby.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.LinkPost;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotosetPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.UnknownTypePost;
import com.tumblr.jumblr.types.VideoPost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cool.lucasleabres.ruby.R;
import cool.lucasleabres.ruby.listeners.BasicViewHolderActionListener;
import cool.lucasleabres.ruby.util.PostType;
import cool.lucasleabres.ruby.util.PrefsManager;
import cool.lucasleabres.ruby.view.LoadingListener;
import cool.lucasleabres.ruby.view.ViewHolderSetup;
import cool.lucasleabres.ruby.view.viewholders.AnswerViewHolder;
import cool.lucasleabres.ruby.view.viewholders.AudioViewHolder;
import cool.lucasleabres.ruby.view.viewholders.BasicViewHolder;
import cool.lucasleabres.ruby.view.viewholders.ChatViewHolder;
import cool.lucasleabres.ruby.view.viewholders.LinkViewHolder;
import cool.lucasleabres.ruby.view.viewholders.LoadingViewHolder;
import cool.lucasleabres.ruby.view.viewholders.PhotoSetViewHolder;
import cool.lucasleabres.ruby.view.viewholders.QuoteViewHolder;
import cool.lucasleabres.ruby.view.viewholders.TextViewHolder;
import cool.lucasleabres.ruby.view.viewholders.VideoViewHolder;

/**
 * Created by LUCASURE on 2/4/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BasicViewHolder> implements BasicViewHolderActionListener {

    public static final String TAG = "RECYCLER ADAPTER";
    private boolean isLoading;
    private final List<Post> itemList = new ArrayList<>();
    private LoadingListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public RecyclerAdapter(RecyclerView recyclerView, List<Post> inputList) {
        for (Post post : inputList) {
            if (post instanceof PhotoPost) {
                itemList.add(post);
            }
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    totalItemCount = manager.getItemCount();
                    lastVisibleItem = manager.findLastVisibleItemPosition();
                } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    totalItemCount = manager.getItemCount();
                    lastVisibleItem = itemList.size() - 1;
                }

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                        Log.d(TAG, "LOADING MORE POSTS NOW");
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(LoadingListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int postType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //staggered
        if (PrefsManager.getPanelSettingsIsDual(parent.getContext())) {

            switch (postType) {
                case 0:
                    view = inflater.inflate(R.layout.grid_photoset, parent, false);
                    return new PhotoSetViewHolder(view);

                case 1:
                    view = inflater.inflate(R.layout.grid_text, parent, false);
                    return new TextViewHolder(view);

                case 2:
                    view = inflater.inflate(R.layout.grid_answer, parent, false);
                    return new AnswerViewHolder(view);

                case 3:
                    view = inflater.inflate(R.layout.grid_video, parent, false);
                    return new VideoViewHolder(view);

                case 4:
                    view = inflater.inflate(R.layout.grid_quote, parent, false);
                    return new QuoteViewHolder(view);

                case 5:
                    view = inflater.inflate(R.layout.grid_chat, parent, false);
                    return new ChatViewHolder(view);

                case 6:
                    view = inflater.inflate(R.layout.grid_link, parent, false);
                    return new LinkViewHolder(view);

                case 7:
                    view = inflater.inflate(R.layout.grid_photo, parent, false);
                    return new BasicViewHolder(view);

                case 8:
                    view = inflater.inflate(R.layout.is_loading, parent, false);
                    return new LoadingViewHolder(view);

                case 9:
                    view = inflater.inflate(R.layout.grid_audio, parent, false);
                    return new AudioViewHolder(view);

                default:
                    view = inflater.inflate(R.layout.grid_photo, parent, false);
                    return new BasicViewHolder(view);
            }
        } else {
            switch (postType) {
                case 0:
                case 1:
                    view = inflater.inflate(R.layout.single_photoset, parent, false);
                    return new PhotoSetViewHolder(view);
                case 2:
                    view = inflater.inflate(R.layout.single_text, parent, false);
                    return new TextViewHolder(view);

                case 3:
                    view = inflater.inflate(R.layout.single_answer, parent, false);
                    return new AnswerViewHolder(view);

                case 4:
                    view = inflater.inflate(R.layout.single_video, parent, false);
                    return new VideoViewHolder(view);

                case 5:
                    view = inflater.inflate(R.layout.single_quote, parent, false);
                    return new QuoteViewHolder(view);

                case 6:
                    view = inflater.inflate(R.layout.single_chat, parent, false);
                    return new ChatViewHolder(view);

                case 7:
                    view = inflater.inflate(R.layout.single_link, parent, false);
                    return new LinkViewHolder(view);

                case 8:
                    view = inflater.inflate(R.layout.single_photo, parent, false);
                    return new BasicViewHolder(view);

                case 9:
                    view = inflater.inflate(R.layout.is_loading, parent, false);
                    return new LoadingViewHolder(view);

                case 10:
                    view = inflater.inflate(R.layout.single_audio, parent, false);
                    return new AudioViewHolder(view);

                default:
                    view = inflater.inflate(R.layout.single_text, parent, false);
                    return new BasicViewHolder(view);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof PhotosetPost) {
            return 0;
        } else if (itemList.get(position) instanceof PhotoPost) {
            return 1;
        } else if (itemList.get(position) instanceof TextPost) {
            return 2;
        } else if (itemList.get(position) instanceof AnswerPost) {
            return 3;
        } else if (itemList.get(position) instanceof VideoPost) {
            return 4;
        } else if (itemList.get(position) instanceof ChatPost) {
            return 5;
        } else if (itemList.get(position) instanceof QuotePost) {
            return 6;
        } else if (itemList.get(position) instanceof LinkPost) {
            return 7;
        } else if (itemList.get(position) instanceof UnknownTypePost) {
            return 8;
        } else if (itemList.get(position) == null) {
            return 9;
        } else if (itemList.get(position) instanceof AudioPost) {
            return 10;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {

        Post post = (Post) itemList.get(position);
        switch (holder.getItemViewType()) {
            case 0:
            case 1:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.PHOTO, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.TEXT, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.ANSWER, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.VIDEO, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.CHAT, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.QUOTE, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.LINK, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.UNKNOWN, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.LOADING, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 10:
                try {
                    ViewHolderSetup.setBasicFunctions(holder, PostType.AUDIO, post, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void setResult() {

    }

    @Override
    public void invoke() {

    }
}

