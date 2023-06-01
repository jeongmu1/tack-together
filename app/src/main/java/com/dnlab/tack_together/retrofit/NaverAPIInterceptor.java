package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.internal.EverythingIsNonNull;

public class NaverAPIInterceptor implements Interceptor {

    @Override
    @EverythingIsNonNull
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

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
