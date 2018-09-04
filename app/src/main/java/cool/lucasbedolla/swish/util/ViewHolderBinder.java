package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.HashMap;
import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.activities.MainActivity;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.fragments.ProfileFragment;
import cool.lucasbedolla.swish.view.SmartImageView;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;

import static cool.lucasbedolla.swish.util.ImageHelper.downloadBlogAvatarIntoImageView;
import static cool.lucasbedolla.swish.util.ImageHelper.downloadImageIntoImageView;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class ViewHolderBinder {

    private ViewHolderBinder() {
    }

    public static void placePhotos(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener, View.OnLongClickListener longClickListener) {
        PhotoPost photoPost = (PhotoPost) post;
        setPhotos(ctx, inferredViewHolder, photoPost, listener, longClickListener);
        if (photoPost.getCaption() != null && photoPost.getCaption().length() > 0) {
            String captionHtml = ((PhotoPost) post).getCaption();
            String caption = Html.fromHtml(captionHtml).toString().trim();

            if (post.getSourceTitle() == null) {
                inferredViewHolder.getDescription().setText(caption);
            } else {
                inferredViewHolder.getDescription().setText(removeAuthorText(post.getSourceTitle(), caption));
            }
        } else {
            inferredViewHolder.getDescription().setVisibility(View.GONE);
        }
        basicHolderSetUp(ctx, photoPost, inferredViewHolder);
    }

    private static String removeAuthorText(String authorTitle, String caption) {

        if (caption.contains(":") && caption.contains(authorTitle)) {

            int firstColon = caption.indexOf(":");

            return caption.subSequence(firstColon + 1, caption.length() - 1).toString().trim();
        } else {
            return caption;
        }
    }


    private static void basicHolderSetUp(Context context, Post post, BasicViewHolder holder) {
        configureTopLayout(context, holder, post);
        configureBottomLayout(context, holder, post);
    }

    private static void configureBottomLayout(final Context context, final BasicViewHolder holder, final Post post) {
        //done to reset reused viewholder

        final Drawable filledHeart = context.getResources().getDrawable(R.drawable.ic_filled_heart);
        final Drawable emptyHeart = context.getResources().getDrawable(R.drawable.ic_empty_heart);
        final Drawable filledReblog = context.getResources().getDrawable(R.drawable.ic_filled_reblog);
        final Drawable emptyReblog = context.getResources().getDrawable(R.drawable.ic_empty_reblog);

        if (post.isLiked()) {
            //make post imageview to selected
            holder.getLikeButton().setBackground(filledHeart);
        } else {
            holder.getLikeButton().setBackground(emptyHeart);
        }

        holder.getReblogButton().setBackground(emptyReblog);

        if (post.getNoteCount() == 1) {
            holder.getNotes().setText(post.getNoteCount() + " note");
        } else {
            holder.getNotes().setText(post.getNoteCount() + " notes");
        }

        holder.getLikeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getBackground() == emptyHeart) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.like();
                        }
                    }).start();

                    holder.getLikeButton().setBackground(filledHeart);

                } else if (v.getBackground() == filledHeart) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.unlike();
                        }
                    }).start();
                    holder.getLikeButton().setBackground(emptyHeart);
                }
            }
        });

        holder.getReblogButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getBackground() == emptyReblog) {
                    //do  reblog
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (MyPrefs.getCurrentBlog(context) != null) {
                                    post.reblog(MyPrefs.getCurrentBlog(context), new HashMap<String, Object>());
                                } else {
                                    post.reblog(MyPrefs.getCurrentUser(context), new HashMap<String, Object>());
                                }
                            } catch (Exception e) {
                                //there's an issue with the jumblr library, but call is still successful
                            }

                        }
                    }).start();
                    holder.getReblogButton().setBackground(filledReblog);

                } else if (holder.getReblogButton().getBackground() == filledReblog) {
                    //delete reblog
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post.delete();
                        }
                    }).start();

                    holder.getReblogButton().setBackground(emptyReblog);
                }
            }
        });
    }

    private static void configureTopLayout(final Context context, BasicViewHolder holder, final Post post) {

        if (MyPrefs.getIsClassicMode(context)) {
            holder.getProfilePicture().setVisibility(View.VISIBLE);
        }

        //set up blog text
        if (post.getSourceTitle() == null) {
            holder.getAuthorText().setText(Html.fromHtml("<b>" + post.getBlogName() + "</b>"));
            holder.getAuthorText().setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.getFollowSource().setVisibility(View.GONE);
        } else {
            holder.getAuthorText().setText(Html.fromHtml("<b>" +
                    post.getBlogName() +
                    " <br> <font color='#5387ff'>reblogged</font> </br> " + "<br>"
                    + post.getSourceTitle() + "</br> </b>"));

            if (isFollowingSourceOrReblogger()) {
                holder.getFollowSource().setVisibility(View.GONE);
            } else {
                String sourceText = "view \n" + post.getSourceTitle();
                holder.getFollowSource().setText(sourceText);
            }
            holder.getFollowSource().setVisibility(View.VISIBLE);
        }

        holder.getProfilePicture().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: open up profile fragment
                ProfileFragment interactionFragment = new ProfileFragment();
                Bundle arguments = new Bundle();
                arguments.putString(ProfileFragment.BLOG_NAME, post.getBlogName());
                interactionFragment.setArguments(arguments);

                ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, interactionFragment, "IMAGE")
                        .commitNow();
            }
        });


//
//        holder.getProfileImageView().setOnClickListener(listener);
//        holder.getAuthorText().setOnClickListener(listener);

        downloadBlogAvatarIntoImageView(holder.getProfilePicture(), post.getBlogName());
    }

    private static boolean isFollowingSourceOrReblogger() {
        return false;
    }


    private static void setPhotos(Context ctx, BasicViewHolder holder, PhotoPost photoPost, View.OnClickListener listener, View.OnLongClickListener longCLickListener) {
        FrameLayout targetLayout = holder.getContentTargetLayout();
        if (targetLayout.getChildCount() > 0) {
            targetLayout.removeAllViews();
        }

        LinearLayout contentHolder = holder.getTargetLayoutAsLinearLayout();

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {

            SmartImageView imageView = new SmartImageView(ctx);
            int h = photos.get(i).getOriginalSize().getHeight();
            int w = photos.get(i).getOriginalSize().getWidth();

            imageView.setAspectRatio(h, w);
            imageView.setImageUrl(photos.get(i).getSizes().get(0).getUrl());
            contentHolder.addView(imageView);
            imageView.setOnClickListener(listener);
            imageView.setOnLongClickListener(longCLickListener);

            downloadImageIntoImageView(imageView, imageView.getImageUrl());
        }
    }

    public static void placeText(Context ctx, BasicViewHolder holder, TextPost post) {
        FrameLayout targetLayout = holder.getContentTargetLayout();
        if (targetLayout.getChildCount() > 0) {
            targetLayout.removeAllViews();
        }

        String title = post.getTitle();
        String body = post.getBody();


        ConstraintLayout textPostLayout = (ConstraintLayout) LayoutInflater.from(ctx).inflate(R.layout.mono_text_post,null);

        TextView titleTextView = textPostLayout.findViewById(R.id.text_title);
        TextView bodyTextView = textPostLayout.findViewById(R.id.text_body);

        if(title == null){
            titleTextView.setVisibility(View.GONE);
        }else{
            titleTextView.setText(title);
        }

        if(body == null){
            bodyTextView.setVisibility(View.GONE);
        }else{
            bodyTextView.setText(title);
        }

        targetLayout.addView(textPostLayout);
        basicHolderSetUp(ctx, post, holder);
    }

    public static void placeVideo(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeAudio(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeUnknown(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeChat(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeAnswer(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeQuote(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {
    }

    public static void placeLoading(Context ctx, BasicViewHolder inferredViewHolder, View.OnClickListener listener) {

    }

    public static void placeQuestion(Context ctx, BasicViewHolder holder, Post post, RecyclerAdapter recyclerAdapter) {

    }
}