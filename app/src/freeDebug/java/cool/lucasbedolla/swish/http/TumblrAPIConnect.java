package cool.lucasbedolla.ruby.http;

import retrofit2.http.GET;

/**
 * Created by Lucas Leabres on 9/12/2016.
 * this class is a retrofit api interface
 */
public interface TumblrAPIConnect {
    @GET("user/dashboard")
    String listRepos();
}
