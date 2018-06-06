package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
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
        basicHolderSetUp(ctx, photoPost, inferredViewHolder, listener);
    }

    private static String removeAuthorText(String authorTitle, String caption) {

        if (caption.contains(":") && caption.contains(authorTitle)) {

            int firstColon = caption.indexOf(":");

            return caption.subSequence(firstColon + 1, caption.length() - 1).toString().trim();
        } else {
            return caption;
        }
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

    private static void basicHolderSetUp(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {
        configureTopLayout(context, holder, post);
        configureBottomLayout(context, holder, post);
        setClickListeners(holder, listener);
    }

    private static void configureBottomLayout(Context context, BasicViewHolder holder, Post post) {
        if (post.getNoteCount() == 1) {
            holder.getNotes().setText(post.getNoteCount() + " note");
        } else {
            holder.getNotes().setText(post.getNoteCount() + " notes");
        }
    }

    private static void configureTopLayout(Context context, BasicViewHolder holder, Post post) {

        if (MyPrefs.getIsClassicMode(context)) {
            holder.getProfilePicture().setVisibility(View.VISIBLE);
        }

        //setting up follow source text


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
                String sourceText = "follow \n" + post.getSourceTitle();
                holder.getFollowSource().setText(sourceText);
            }
            holder.getFollowSource().setVisibility(View.VISIBLE);
        }

        downloadBlogAvatarIntoImageView(holder.getProfilePicture(), post.getBlogName());
    }

    private static boolean isFollowingSourceOrReblogger() {
        return false;
    }

    public static void placeText(Context context, BasicViewHolder holder, Post post) {

    }

    private static void setClickListeners(BasicViewHolder holder, View.OnClickListener listener) {

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