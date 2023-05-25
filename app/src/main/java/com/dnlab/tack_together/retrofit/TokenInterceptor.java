package com.dnlab.tack_together.retrofit;

import android.content.Context;
import android.util.Log;

import com.dnlab.tack_together.common.jwt.TokenManager;
import com.dnlab.tack_together.common.jwt.TokenManagerImpl;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private final TokenManager tokenManager;
    private static final String TAG = "TokenInterceptor";

    public TokenInterceptor(Context context) {
        this.tokenManager = new TokenManagerImpl(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String accessToken = tokenManager.getAccessToken();
        if (accessToken == null) {
            Log.i(TAG, "accessToken이 존재하지 않습니다.");
            return chain.proceed(originalRequest);
        }

        Log.i(TAG, "accessToken이 존재합니다. accessToken: " + accessToken);
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .method(originalRequest.method(), originalRequest.body());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
