package cool.lucasbedolla.swish.listeners;

import com.tumblr.jumblr.types.Post;

import java.util.List;

/**
 * Created by Lucas Bedolla on 7/4/2017.
 */

public interface FetchPostListener {

    void fetchedPosts(List<Post> posts);
}
