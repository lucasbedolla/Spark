package lucasleabres.ruby.paid;
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

    /*
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.0'
    compile 'com.squareup.retrofit:adapter-rxjava:2.0.0-beta2'
     */
}
