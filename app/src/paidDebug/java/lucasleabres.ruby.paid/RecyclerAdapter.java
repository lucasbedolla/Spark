package lucasleabres.ruby.paid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.LinkPost;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotosetPost;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.UnknownTypePost;
import com.tumblr.jumblr.types.VideoPost;

import java.util.List;

import cool.lucasbedolla.ruby.R;

/**
 * Created by LUCASURE on 2/4/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "RECYCLER ADAPTER";
    private final int
            PHOTO = 0,
            PHOTOSET = 1,
            TEXT = 2,
            ANSWER = 3,
            QUOTE = 4,
            VIDEO = 5,
            CHAT = 6,
            LINK = 7,
            UNKNOWN = 8,
            LOADING = 9;

    AppCompatActivity appCompat;

    public Handler handler = new Handler();
    public boolean isLoading;
    private List<Object> itemList;
    private LoadingListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;


    public RecyclerAdapter(List<Object> inputList, RecyclerView recyclerView) {
        itemList = inputList;

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

    public void setAppCompat(AppCompatActivity compat){
        this.appCompat = compat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        //staggered
        if (prefs.getBoolean("grid", false)) {
            switch (viewType) {

                case PHOTO:
                    View v1 = inflater.inflate(R.layout.grid_photoset, parent, false);
                    viewHolder = new PhotosetViewHolder(v1);
                    break;
                case PHOTOSET:
                    View v2 = inflater.inflate(R.layout.grid_photoset, parent, false);
                    viewHolder = new PhotosetViewHolder(v2);
                    break;
                case TEXT:
                    View v3 = inflater.inflate(R.layout.grid_text, parent, false);
                    viewHolder = new TextViewHolder(v3);
                    break;
                case ANSWER:
                    View v4 = inflater.inflate(R.layout.grid_answer, parent, false);
                    viewHolder = new AnswerViewHolder(v4);
                    break;
                case VIDEO:
                    View v5 = inflater.inflate(R.layout.grid_video, parent, false);
                    viewHolder = new VideoViewHolder(v5);
                    break;
                case QUOTE:
                    View q = inflater.inflate(R.layout.grid_quote, parent, false);
                    viewHolder = new QuoteViewHolder(q);
                    break;
                case CHAT:
                    View v6 = inflater.inflate(R.layout.grid_chat, parent, false);
                    viewHolder = new ChatViewHolder(v6);
                    break;
                case LINK:
                    View v7 = inflater.inflate(R.layout.grid_link, parent, false);
                    viewHolder = new LinkViewHolder(v7);
                    break;
                case UNKNOWN:
                    View v8 = inflater.inflate(R.layout.grid_photo, parent, false);
                    viewHolder = new UnknownPostViewHolder(v8);
                    break;
                case LOADING:
                    View v = inflater.inflate(R.layout.is_loading, parent, false);
                    viewHolder = new LoadingViewHolder(v);
                    break;
            }
        } else {

            switch (viewType) {
                case PHOTO:
                    View v1 = inflater.inflate(R.layout.single_photoset, parent, false);
                    viewHolder = new PhotosetViewHolder(v1);
                    break;
                case PHOTOSET:
                    View v2 = inflater.inflate(R.layout.single_photoset, parent, false);
                    viewHolder = new PhotosetViewHolder(v2);
                    break;
                case TEXT:
                    View v3 = inflater.inflate(R.layout.single_text, parent, false);
                    viewHolder = new TextViewHolder(v3);
                    break;
                case ANSWER:
                    View v4 = inflater.inflate(R.layout.single_answer, parent, false);
                    viewHolder = new AnswerViewHolder(v4);
                    break;
                case VIDEO:
                    View v5 = inflater.inflate(R.layout.single_video, parent, false);
                    viewHolder = new VideoViewHolder(v5);
                    break;
                case QUOTE:
                    View q = inflater.inflate(R.layout.single_quote, parent, false);
                    viewHolder = new QuoteViewHolder(q);
                    break;
                case CHAT:
                    View v6 = inflater.inflate(R.layout.single_chat, parent, false);
                    viewHolder = new ChatViewHolder(v6);
                    break;
                case LINK:
                    View v7 = inflater.inflate(R.layout.single_link, parent, false);
                    viewHolder = new LinkViewHolder(v7);
                    break;
                case UNKNOWN:
                    View v8 = inflater.inflate(R.layout.single_photo, parent, false);
                    viewHolder = new UnknownPostViewHolder(v8);
                    break;
                case LOADING:
                    View v = inflater.inflate(R.layout.is_loading, parent, false);
                    viewHolder = new LoadingViewHolder(v);
                    break;
            }

        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof PhotoPost) {
            return PHOTO;
        } else if (itemList.get(position) instanceof PhotosetPost) {
            return PHOTOSET;
        } else if (itemList.get(position) instanceof TextPost) {
            return TEXT;
        } else if (itemList.get(position) instanceof AnswerPost) {
            return ANSWER;
        } else if (itemList.get(position) instanceof VideoPost) {
            return VIDEO;
        } else if (itemList.get(position) instanceof ChatPost) {
            return CHAT;
        } else if (itemList.get(position) instanceof QuotePost) {
            return QUOTE;
        } else if (itemList.get(position) instanceof LinkPost) {
            return LINK;
        } else if (itemList.get(position) instanceof UnknownTypePost) {
            return UNKNOWN;
        } else if (itemList.get(position) == null) {
            return LOADING;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case PHOTO:
                ViewHolderSetup setup = new ViewHolderSetup(PhotosetViewHolder.context, holder, 0, position, itemList, handler, appCompat);
                setup.setBasicFunctions();
                break;
            case PHOTOSET:
                ViewHolderSetup set = new ViewHolderSetup(RecyclerAdapter.PhotosetViewHolder.context, holder, 1, position, itemList, handler, appCompat);
                set.setBasicFunctions();
                break;
            case TEXT:
                ViewHolderSetup textset = new ViewHolderSetup(TextViewHolder.context, holder, 2, position, itemList, handler);
                textset.setBasicFunctions();
                break;
            case ANSWER:
                ViewHolderSetup ans = new ViewHolderSetup(AnswerViewHolder.context, holder, 3, position, itemList, handler);
                ans.setBasicFunctions();
                break;
            case QUOTE:
                ViewHolderSetup vid = new ViewHolderSetup(QuoteViewHolder.context, holder, 4, position, itemList, handler);
                vid.setBasicFunctions();
                break;
            case VIDEO:
                ViewHolderSetup quo = new ViewHolderSetup(VideoViewHolder.context, holder, 5, position, itemList, handler, appCompat);
                quo.setBasicFunctions();
                break;
            case CHAT:
                ViewHolderSetup chat = new ViewHolderSetup(ChatViewHolder.context, holder, 6, position, itemList, handler);
                chat.setBasicFunctions();
                break;
            case LINK:
                ViewHolderSetup link = new ViewHolderSetup(LinkViewHolder.context, holder, 7, position, itemList, handler);
                link.setBasicFunctions();
                break;
            case UNKNOWN:
                ViewHolderSetup unk = new ViewHolderSetup(UnknownPostViewHolder.context, holder, 8, position, itemList, handler);
                unk.setBasicFunctions();
                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                loadingViewHolder.progressBar.getIndeterminateDrawable().setColorFilter(0xFFcc0000,
                        android.graphics.PorterDuff.Mode.MULTIPLY);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //progress bar
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    //TODO: add description text to bottom of views

    //class for photos
    public static class PhotosetViewHolder extends BasicViewHolder {

        static Context context;
        //tumblr allows max of 10 images per photoset,
        // will set View.GONE where appropriate
        protected ImageView vImage;
        protected ImageView vImage1;
        protected ImageView vImage2;
        protected ImageView vImage3;
        protected ImageView vImage4;
        protected ImageView vImage5;
        protected ImageView vImage6;
        protected ImageView vImage7;
        protected ImageView vImage8;
        protected ImageView vImage9;
        // protected TextView vTitle;
        protected TextView vTitle;


        public PhotosetViewHolder(View v) {
            super(v);
            context = v.getContext();

            vImage = (ImageView) v.findViewById(R.id.image_post);
            vImage1 = (ImageView) v.findViewById(R.id.image_post1);
            vImage2 = (ImageView) v.findViewById(R.id.image_post2);
            vImage3 = (ImageView) v.findViewById(R.id.image_post3);
            vImage4 = (ImageView) v.findViewById(R.id.image_post4);
            vImage5 = (ImageView) v.findViewById(R.id.image_post5);
            vImage6 = (ImageView) v.findViewById(R.id.image_post6);
            vImage7 = (ImageView) v.findViewById(R.id.image_post7);
            vImage8 = (ImageView) v.findViewById(R.id.image_post8);
            vImage9 = (ImageView) v.findViewById(R.id.image_post9);
            vTitle = (TextView) v.findViewById(R.id.vTitle);
        }
    }

    //class for text posts
    public static class TextViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vContent;

        public TextViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.text_title);
            vContent = (TextView) v.findViewById(R.id.text_content);
        }
    }

    //class for answers
    public static class AnswerViewHolder extends BasicViewHolder {
        static Context context;
        protected TextView vQuestion;
        protected TextView vAnswer;

        public AnswerViewHolder(View v) {
            super(v);
            context = v.getContext();
            vQuestion = (TextView) v.findViewById(R.id.question);
            vAnswer = (TextView) v.findViewById(R.id.answer);
        }
    }


    //class for videos
    //add mediaviewer into layout file
    public static class VideoViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vDescription;
        protected EMVideoView vid;

        public VideoViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.vTitle);
            vDescription = (TextView) v.findViewById(R.id.vDescription);
            vid = (EMVideoView) v.findViewById(R.id.video_view);


        }
    }

    //class for quote posts
    public static class QuoteViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vDescription;

        public QuoteViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.quote_title);
            vDescription = (TextView) v.findViewById(R.id.quote_desc);
            //add more textviews as needed
        }
    }

    //class for chat posts
    public static class ChatViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vDescription;

        public ChatViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.chat_title);
            vDescription = (TextView) v.findViewById(R.id.chat_desc);
        }
    }

    //class for chat posts
    public static class AudioViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vDescription;

        public AudioViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.chat_title);
            vDescription = (TextView) v.findViewById(R.id.chat_desc);
        }
    }

    //class for link
    public static class LinkViewHolder extends BasicViewHolder {

        static Context context;
        protected TextView vTitle;
        protected TextView vDescription;
        protected WebChromeClient vWebView;

        public LinkViewHolder(View v) {
            super(v);
            context = v.getContext();
            vTitle = (TextView) v.findViewById(R.id.link_title);
            vDescription = (TextView) v.findViewById(R.id.link_desc);
            //vWebView = (WebChromeClient) v.findViewById(R.id.link_webview);
        }
    }

    //class for unknown posts
    public static class UnknownPostViewHolder extends BasicViewHolder {

        static Context context;

        public UnknownPostViewHolder(View v) {
            super(v);
            context = v.getContext();
        }
    }
}

