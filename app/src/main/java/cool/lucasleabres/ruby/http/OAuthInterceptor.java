package cool.lucasleabres.ruby.http;


import android.util.Log;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Lucas Leabres on 9/12/2016.
 */

public class OAuthInterceptor implements okhttp3.Interceptor {


    private final OAuthParameters oAuthParams;

    public OAuthInterceptor(OAuthParameters oAuthParams) {
        this.oAuthParams = oAuthParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        GenericUrl requestUrl = new GenericUrl(chain.request().url().url());
        Log.d("OAUTH_INTERCEPTOR", "intercept: request url: "+ chain.request().url().url());
        oAuthParams.computeTimestamp();
        try {

            oAuthParams.computeSignature("GET", requestUrl);
            okhttp3.Request compressedRequest = originalRequest.newBuilder()
                    .header("Authorization", oAuthParams.getAuthorizationHeader())
                    .header("Accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
                    .build();

            Log.d("OAUTH_INTERCEPTOR", "authorization header: "+oAuthParams.getAuthorizationHeader());

            return chain.proceed(compressedRequest);
        } catch (GeneralSecurityException e) {
            Log.d("OAUTH_IINTERCEPTOR", "intercept: interceptor has failed!");
        }
        return chain.proceed(originalRequest);
    }
}


