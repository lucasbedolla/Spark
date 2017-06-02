package cool.lucasleabres.ruby.view;

import com.tumblr.jumblr.types.Post;

import java.io.IOException;

import cool.lucasleabres.ruby.listeners.BasicViewHolderActionListener;
import cool.lucasleabres.ruby.util.PostType;
import cool.lucasleabres.ruby.util.ViewHolderPlacer;
import cool.lucasleabres.ruby.view.viewholders.BasicViewHolder;

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
            BasicViewHolderActionListener listener) throws IOException {

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


