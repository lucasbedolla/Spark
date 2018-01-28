package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.swish.R;
import cool.lucasbedolla.swish.adapter.RecyclerAdapter;
import cool.lucasbedolla.swish.view.SmartImageView;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class ViewHolderBinder {

    private ViewHolderBinder() {
    }

    public static void placePhotos(Context ctx, BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {
        PhotoPost photoPost = (PhotoPost) post;
        setPhotos(ctx, inferredViewHolder, photoPost);
        basicHolderSetUp(ctx, photoPost, inferredViewHolder, listener);
    }

    private static void setPhotos(Context ctx, BasicViewHolder holder, PhotoPost photoPost) {
        FrameLayout targetLayout = holder.getContentTargetLayout();
        if (targetLayout.getChildCount() > 0) {
            targetLayout.removeAllViews();
        }

        LinearLayout contentHolder = holder.getTargetLayoutAsLinearLayout();

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {

            SmartImageView imageView = new SmartImageView(ctx);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            contentHolder.addView(imageView);

            downloadImageIntoImageView(imageView, photos.get(i).getSizes().get(0).getUrl());
        }
    }

    private static void basicHolderSetUp(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {

        configureTopLayout(context, holder, post);
        setClickListeners(holder, listener);


    }

    private static void configureTopLayout(Context context, BasicViewHolder holder, Post post) {

        if (MyPrefs.getIsClassicMode(context)) {
            holder.getProfilePicture().setVisibility(View.VISIBLE);
            downloadImageIntoImageView(holder.getProfilePicture(), "http://api.tumblr.com/v2/blog/" + post.getSourceTitle() + "/avatar/128");
        }

        if (post.getSourceTitle() == null) {
            holder.getAuthorText().setText(post.getBlogName());
            holder.getAuthorText().setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.getAuthorText().setText(Html.fromHtml(post.getBlogName() + " <font color='#5387ff'>reblogged</font> " + post.getSourceTitle()));
        }

    }


    private static void downloadImageIntoImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.1f)
                .into(imageView);
    }


    public static void placeText(Context context, BasicViewHolder holder, Post post) {

    }

    private static void setClickListeners(BasicViewHolder holder, View.OnClickListener listener) {
        holder.getExtrasButton().setOnClickListener(listener);
        holder.getLikeButton().setOnClickListener(listener);
        holder.getReblogButton().setOnClickListener(listener);
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