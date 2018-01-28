package cool.lucasbedolla.swish.util;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasbedolla.swish.view.SmartImageView;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;
import cool.lucasbedolla.swish.view.viewholders.PhotoSetViewHolder;

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
        LinearLayout imageHolder = holder.getTargetLayoutAsLinearLayout();
        if (imageHolder.getChildCount() > 0) {
            imageHolder.removeAllViews();
        }

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {

            SmartImageView imageView = new SmartImageView(ctx);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            imageHolder.addView(imageView);

            Glide.with(imageView.getContext())
                    .load(photos.get(i).getSizes().get(0).getUrl())
                    .thumbnail(0.1f)
                    .into(imageView);
        }
    }

    private static void basicHolderSetUp(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {

        if (MyPrefs.getIsClassicMode(context)) {
            configureToClassicMode(context, post, holder, listener);
        } else if (MyPrefs.getIsMinimalistMode(context)) {
            configureToMinimalistMode(context, post, holder, listener);
        } else if (MyPrefs.getIsExtremeMinimalist(context)) {
            configureToExtremeMinimalistMode(context, post, holder, listener);
        }


        //set basic item click listeners
//        holder.getExtrasParentLayout().setOnClickListener(listener);
//        holder.getExtrasButton().setOnClickListener(listener);
//        holder.getLikeButton().setOnClickListener(listener);
//        holder.getReblogButton().setOnClickListener(listener);

    }


    public static void placeText(BasicViewHolder holder, Post post){
        holder.getTitleTextView().setText(post.getBlogName());
    }

    private static void configureToClassicMode(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {

    }


    private static void configureToMinimalistMode(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {

    }


    private static void configureToExtremeMinimalistMode(Context context, Post post, BasicViewHolder holder, View.OnClickListener listener) {

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


}