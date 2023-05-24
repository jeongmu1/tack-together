package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static RetrofitBuilder example = null;

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RetrofitBuilder getInstance() {
        if (example == null) {
            example = new RetrofitBuilder();
        }

        return example;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


}