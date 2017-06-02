package cool.lucasbedolla.ruby.http;


import com.google.api.client.auth.oauth.OAuthParameters;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Lucas Leabres on 9/12/2016.
 */

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder();


    private ServiceGenerator() {
    }


    public static <S> S createService(Class<S> serviceClass, String baseUrl, final OAuthParameters oAuthParams) {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new OAuthInterceptor(oAuthParams));
        builder.client(client);
        builder.baseUrl(baseUrl);
        Retrofit adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
