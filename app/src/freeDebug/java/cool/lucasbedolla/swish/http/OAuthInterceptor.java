package cool.lucasbedolla.ruby.http;


import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Lucas Leabres on 9/12/2016.
 */

public class OAuthInterceptor implements okhttp3.Interceptor {


    private final OAuthParameters oAuthParams;

    public OAuthInterceptor(OAuthParameters oAuthParams) {
        this.oAuthParams = oAuthParams;
    }


    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Request originalRequest = chain.request();
        GenericUrl requestUrl = new GenericUrl(originalRequest.url().url());
        oAuthParams.computeTimestamp();
        try {
            oAuthParams.computeSignature("GET", requestUrl);
            okhttp3.Request compressedRequest = originalRequest.newBuilder()
                    .header("Authorization", oAuthParams.getAuthorizationHeader())
                    .header("Accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
                    .build();
            return chain.proceed(compressedRequest);
        } catch (GeneralSecurityException e) {
        }


        return chain.proceed(originalRequest);
    }
}


