package lucasleabres.ruby.free;

import android.util.Log;

import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotosetPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.VideoPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUCASVENTURES on 5/7/2016.
 */

/*
returns list of Post objects that have been cast to their
corresponding post types.
*/
public class PostSorter {
    private List<Post> mPosts;

    public PostSorter() {
        //util class, so empty constructor!
    }

    static List<Object> returnCasted(List<Post> unCastedPosts) {
        List<Object> castedList = new ArrayList<>(20);
        for (Post post : unCastedPosts) {
            Log.d("SORT", "returnCasted: TYPE:" + post.getType().toLowerCase());

            switch (post.getType().toLowerCase()) {
                case "photo":
                    PhotoPost photo = (PhotoPost) post;
                    castedList.add(photo);

                    break;
                case "text":
                    TextPost text = (TextPost) post;
                    castedList.add(text);
                    break;
                case "photoset":
                    PhotosetPost set = (PhotosetPost) post;
                    castedList.add(set);
                    break;
                case "video":
                    VideoPost videoPost = (VideoPost) post;
                    castedList.add(videoPost);
                    break;
                case "audio":
                    //not yet added

                    // AudioPost audio = (AudioPost) post;
                    // castedList.add(audio);
                    break;
                case "answer":
                    AnswerPost answer = (AnswerPost) post;
                    castedList.add(answer);
                    break;
                case "quote":
                    QuotePost quote = (QuotePost) post;
                    castedList.add(quote);
                    break;
                case "unkown":
                    //UnknownTypePost unknown = (UnknownTypePost) post;
                    //castedList.add(unknown);
                default:
            }
        }
        return castedList;
    }

}
