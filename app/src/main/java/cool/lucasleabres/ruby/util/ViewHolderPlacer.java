package cool.lucasleabres.ruby.util;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasleabres.ruby.listeners.BasicViewHolderActionListener;
import cool.lucasleabres.ruby.view.viewholders.BasicViewHolder;
import cool.lucasleabres.ruby.view.viewholders.PhotoSetViewHolder;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class ViewHolderPlacer {
    private ViewHolderPlacer() {
    }

    public static void placePhotos(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

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
        final ImageView[] image = photoSetHolder.getImages();
        ImageView imageView = image[0];

        double w = ((Activity) imageView.getContext()).getWindow().getWindowManager().getDefaultDisplay().getWidth();

        List<Photo> photos = photoPost.getPhotos();
        for (int i = 0; i < photos.size(); i++) {
            int width = photos.get(i).getSizes().get(0).getWidth();
            int height = photos.get(i).getSizes().get(0).getHeight();

            //this is the ratio for the image.
            double ratio = width / height;
            //this is the height for the image view before it is completely loaded
            double h = w * ratio;

            image[i].setMinimumHeight((int) h);
            image[i].invalidate();

            Glide.with(image[i].getContext())
                    .load(photos.get(i).getSizes().get(0).getUrl())
                    .thumbnail(0.1f)
                    .into(image[i]);
        }
    }

    private static void basicHolderSetUp(Post post, BasicViewHolder holder, BasicViewHolderActionListener listener) {

       /*
        Picasso.with(holder.getTitle().getContext())
                .load("https://api.tumblr.com/v2/blog/" + post.getBlogName() + ".tumblr.com/avatar/512")
                .placeholder(R.drawable.loadingshadow)
                .error(R.drawable.loadingshadow)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(holder.getSourcePic());
        */
    }

    //this sets viewholder specific functionality
    private static void setSpecificFunctions(BasicViewHolder holder, Post post, PostType type, BasicViewHolderActionListener listener) {

        switch (type) {
            case PHOTO:
                PhotoSetViewHolder setHolder = (PhotoSetViewHolder) holder;
                PhotoPost photoPost = (PhotoPost) post;
                setPhotos(setHolder, photoPost);
                break;

        }
    }

    public static void placeText(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeVideo(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeAudio(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeUnknown(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeChat(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeAnswer(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {

    }

    public static void placeQuote(BasicViewHolder inferredViewHolder, Post post, BasicViewHolderActionListener listener) {
    }

    public static void placeLoading(BasicViewHolder inferredViewHolder, BasicViewHolderActionListener listener) {

    }


}