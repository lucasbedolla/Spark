package cool.lucasleabres.ruby.http;


import com.google.api.client.auth.oauth.OAuthParameters;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas Leabres on 9/12/2016.
 */

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder();

    public static <S> S createService(Class<S> serviceClass, String baseUrl, final OAuthParameters oAuthParams) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new OAuthInterceptor(oAuthParams))
                .build();

        builder.baseUrl(baseUrl);
        builder.client(client);
        builder.addConverterFactory(GsonConverterFactory.create());

        Retrofit adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
