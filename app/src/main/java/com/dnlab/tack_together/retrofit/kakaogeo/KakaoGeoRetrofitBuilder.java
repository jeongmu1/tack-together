package com.dnlab.tack_together.retrofit.kakaogeo;

import android.util.Log;

import com.dnlab.tack_together.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoGeoRetrofitBuilder {
    private static KakaoGeoRetrofitBuilder instance = null;
    private final Retrofit retrofit;
    private static final String TAG = "ReverseGeocodingRetrofitBuilder";

    private KakaoGeoRetrofitBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new KakaoAPIInterceptor())
                .addInterceptor(interceptor)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.KAKAO_GEOCODING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public synchronized static KakaoGeoRetrofitBuilder getInstance() {
        Log.i(TAG, "getInstance: 호출되었습니다.");
        if (instance == null) {
            instance = new KakaoGeoRetrofitBuilder();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
