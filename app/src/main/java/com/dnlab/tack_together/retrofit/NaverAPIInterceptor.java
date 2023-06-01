package com.dnlab.tack_together.retrofit;

import android.util.Log;

import com.dnlab.tack_together.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.internal.EverythingIsNonNull;

public class NaverAPIInterceptor implements Interceptor {
    private static final String TAG = "NaverAPIInterceptor";

    @Override
    @EverythingIsNonNull
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!originalRequest.url().toString().contains("naveropenapi.apigw.ntruss.com")) {
            Log.d(TAG, "본 서버 요청이 아님");
            return chain.proceed(originalRequest);
        }

        String clientSecret = BuildConfig.NAVER_CLIENT_SECRET;
        String clientId = BuildConfig.NAVER_MAP_CLIENTID_STRING;
        Request.Builder builder = originalRequest.newBuilder()
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .method(originalRequest.method(), originalRequest.body());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
