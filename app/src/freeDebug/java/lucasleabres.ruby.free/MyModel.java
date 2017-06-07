package lucasleabres.ruby.free;

import com.tumblr.jumblr.types.Post;

/**
 * Created by LUCASURE on 2/4/2016.
 */
public class MyModel {

    private Post mPost;

    public MyModel(Post post) {
        mPost = post;
    }

    public Post getPost() {
        return mPost;

    }
}