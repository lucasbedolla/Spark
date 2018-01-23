package cool.lucasbedolla.swish.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotosetPost;
import com.tumblr.jumblr.types.Post;

import java.io.IOException;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.util.MyPrefs;
import cool.lucasbedolla.swish.util.PostType;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;
import cool.lucasbedolla.swish.view.viewholders.PhotoSetViewHolder;

/**
 * Created by LUCASURE on 2/4/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BasicViewHolder> implements View.OnClickListener {

    public static final String TAG = "RECYCLER ADAPTER";
    private final List<Post> itemList;

    public RecyclerAdapter(List<Post> inputList) {
        itemList = inputList;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int postType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //staggered
        if (MyPrefs.getPanelSettingsIsDual(parent.getContext())) {
/*

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
            }*/
        } else {

            switch (postType) {
                case 0:
                case 1:
                    return new PhotoSetViewHolder(inflater.inflate(R.layout.single_photoset, parent, false));
                case 666:
                    return new EmptyViewHolder(inflater.inflate(R.layout.is_loading, parent, false));
            }
        }
                    /*
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


  */
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        Post post = itemList.get(position);
        if (post instanceof PhotosetPost ||
                post instanceof PhotoPost) {
            return 0;
        } else {
            return 666;
        }
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {

        Post post = itemList.get(position);
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

            case 666:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.extras_parent:
                animateExtras(view);
                break;
            case R.id.like_button:
                Toast.makeText(view.getContext(), "Liked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reblog_button:
                Toast.makeText(view.getContext(), "Reblogged!", Toast.LENGTH_SHORT).show();

                break;
        }

    }


    private void animateExtras(final View view) {
        final ImageView buttonSpin = view.findViewById(R.id.extras_button);
        final LinearLayout extraContentLayout = view.findViewById(R.id.extras_content);

        if (extraContentLayout.getVisibility() == View.GONE) {

            extraContentLayout.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.extras_content_animate_down));
            buttonSpin.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.arrow_clockwise_rotate));
            extraContentLayout.setVisibility(View.VISIBLE);
        } else {

            Animation upAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.extras_content_animate_up);
            upAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    extraContentLayout.setVisibility(View.GONE);
                    //animate arrow
                    buttonSpin.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.arrow_counterclockwise_rotate));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            extraContentLayout.startAnimation(upAnimation);

        }
    }
}

