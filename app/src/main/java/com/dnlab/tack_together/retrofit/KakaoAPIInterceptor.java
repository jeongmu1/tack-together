package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.internal.EverythingIsNonNull;

public class KakaoAPIInterceptor implements Interceptor {

    @Override
    @EverythingIsNonNull
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "KakaoAK " + BuildConfig.KAKAO_KEY)
                .method(originalRequest.method(), originalRequest.body());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
