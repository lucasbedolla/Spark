package cool.lucasbedolla.swish.view;

import android.view.View;

import com.tumblr.jumblr.types.Post;

import java.io.IOException;

import cool.lucasbedolla.swish.util.PostType;
import cool.lucasbedolla.swish.util.ViewHolderPlacer;
import cool.lucasbedolla.swish.view.viewholders.BasicViewHolder;


/**
 * Created by LUCASVENTURES on 5/9/2016.
 */

public class ViewHolderSetup {

    public static final String TAG = "VIEWHOLDERSETUP";

    private ViewHolderSetup() {
    }

    public static void setBasicFunctions(
            BasicViewHolder inferredViewHolder,
            PostType type, Post post,
            View.OnClickListener listener) throws IOException {

        switch (type) {
            case PHOTO:
                ViewHolderPlacer.placePhotos(inferredViewHolder, post, listener);
                break;
            case TEXT:
                ViewHolderPlacer.placeText(inferredViewHolder, post, listener);
                break;
            case CHAT:
                ViewHolderPlacer.placeChat(inferredViewHolder, post, listener);
                break;
            case AUDIO:
                ViewHolderPlacer.placeAudio(inferredViewHolder, post, listener);
                break;
            case QUOTE:
                ViewHolderPlacer.placeQuote(inferredViewHolder, post, listener);
                break;
            case VIDEO:
                ViewHolderPlacer.placeVideo(inferredViewHolder, post, listener);
                break;
            case ANSWER:
                ViewHolderPlacer.placeAnswer(inferredViewHolder, post, listener);
                break;
            case UNKNOWN:
                ViewHolderPlacer.placeUnknown(inferredViewHolder, post, listener);
                break;
            case LOADING:
                ViewHolderPlacer.placeLoading(inferredViewHolder, listener);

                break;

        }
    }
}


