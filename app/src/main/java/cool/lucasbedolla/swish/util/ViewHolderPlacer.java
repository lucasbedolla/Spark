package cool.lucasbedolla.swish.util;

import android.view.View;
import android.widget.ImageView;

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

public class ViewHolderPlacer {
    private ViewHolderPlacer() {
    }

    public static void placePhotos(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

        PhotoSetViewHolder photoSetHolder = (PhotoSetViewHolder) inferredViewHolder;
        PhotoPost photoPost = (PhotoPost) post;
        int photoSetSize = photoPost.getPhotos().size();
        setSpecificFunctions(photoSetHolder, photoPost, PostType.PHOTO, listener);
        setPhotoVisibility(photoSetHolder, photoSetSize);
        setPhotos(photoSetHolder, photoPost);
        basicHolderSetUp(photoPost, photoSetHolder, listener);
    }

    private static void setPhotoVisibility(PhotoSetViewHolder viewHolder, int numberOfPhotos) {
        ImageView[] images = viewHolder.getImages();
        int remainder = 10;
        for (int i = 0; i < numberOfPhotos; i++) {
            images[i].setVisibility(View.VISIBLE);
            remainder--;
        }
        while (remainder != 0) {
            images[remainder].setVisibility(View.GONE);
            remainder--;
        }
    }

    private static void setPhotos(final PhotoSetViewHolder photoSetHolder, final PhotoPost photoPost) {
        final SmartImageView[] image = photoSetHolder.getImages();
        SmartImageView imageView = image[0];

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {
            float width = photos.get(i).getSizes().get(0).getWidth();
            float height = photos.get(i).getSizes().get(0).getHeight();

            //this is the ratio for the image.
            float ratio = height / width;
            imageView.setAspectRatio(ratio);

            Glide.with(image[i].getContext())
                    .load(photos.get(i).getSizes().get(0).getUrl())
                    .thumbnail(0.1f)
                    .into(image[i]);
        }
    }

    private static void basicHolderSetUp(Post post, BasicViewHolder holder, View.OnClickListener listener) {
        if (MyPrefs.isProfilePicInvisible(holder.getExtrasButton().getContext())) {
            holder.getProfilePicture().setVisibility(View.GONE);
        } else {
            /*holder.getProfilePicture().setVisibility(View.VISIBLE);
            //set profile picture
            Glide.with(holder.itemView.getContext())
                    .load("https://api.tumblr.com/v2/blog/"
                            + post.getBlogName() +
                            ".tumblr.com/avatar/512")
                    .into(holder.getProfilePicture());
        }*/
        }

        //set basic item click listeners
        holder.getExtrasParentLayout().setOnClickListener(listener);
        holder.getExtrasButton().setOnClickListener(listener);
        holder.getLikeButton().setOnClickListener(listener);
        holder.getReblogButton().setOnClickListener(listener);

    }

    //this sets viewholder specific functionality
    private static void setSpecificFunctions(BasicViewHolder holder, Post post, PostType type, View.OnClickListener listener) {

        switch (type) {
            case PHOTO:
                PhotoSetViewHolder setHolder = (PhotoSetViewHolder) holder;
                PhotoPost photoPost = (PhotoPost) post;
                setPhotos(setHolder, photoPost);
                placeText(holder, post, listener);
                break;
        }
    }

    public static void placeText(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {
        inferredViewHolder.getTitleTextView().setText(post.getBlogName());
    }

    public static void placeVideo(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeAudio(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeUnknown(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeChat(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeAnswer(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {

    }

    public static void placeQuote(BasicViewHolder inferredViewHolder, Post post, View.OnClickListener listener) {
    }

    public static void placeLoading(BasicViewHolder inferredViewHolder, View.OnClickListener listener) {

    }


}