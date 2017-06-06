package cool.lucasleabres.ruby.util;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;

import java.util.List;

import cool.lucasleabres.ruby.R;
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

        for (int i = 0; i < photoPost.getPhotos().size(); i++) {
            List<PhotoSize> sizes = photoPost.getPhotos().get(i).getSizes();
            Picasso.with(photoSetHolder.getTitle().getContext())
                    .load(sizes.get(0).getUrl())
                    .placeholder(R.drawable.loadingshadow)
                    .error(R.drawable.loadingshadow)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
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
                setHolder.getTitle().setText(photoPost.getSourceTitle());
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