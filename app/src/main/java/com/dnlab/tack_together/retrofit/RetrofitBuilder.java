package com.dnlab.tack_together.retrofit;

import android.content.Context;
import android.util.Log;

import com.dnlab.tack_together.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static RetrofitBuilder instance = null;
    private final Retrofit retrofit;
    private static final String TAG = "RetrofitBuilder";

    private RetrofitBuilder(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(context))
                .addInterceptor(interceptor)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public synchronized static RetrofitBuilder getInstance(Context context) {
        Log.i(TAG, "getInstance: 호출되었습니다.");
        if (instance == null) {
            instance = new RetrofitBuilder(context);
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}