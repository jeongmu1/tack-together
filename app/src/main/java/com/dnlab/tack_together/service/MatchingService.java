package com.dnlab.tack_together.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.api.dto.auth.MemberInfoResponseDTO;
import com.dnlab.tack_together.common.jwt.TokenManager;
import com.dnlab.tack_together.common.jwt.TokenManagerImpl;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;


import java.io.IOException;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MatchingService extends Service {

    private static final String CONNECTION_URL = BuildConfig.WEBSOCKET_URL + "/match";
    private StompClient stompClient;
    private TokenManager tokenManager;
    private MemberInfoResponseDTO memberInfoResponseDTO;
    private static final String TAG = "MatchingService";
    private Disposable disposable;
    private static final String CONTENT = BuildConfig.BROADCAST_CONTENT;

    @Override
    public void onCreate() {
        super.onCreate();
        connect();

        Call<MemberInfoResponseDTO> response = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(AuthorizationAPI.class)
                .getMemberInfo();
        try {
            memberInfoResponseDTO = response.execute().body();
        } catch (IOException e) {
            Log.d(TAG, "통신 실패");
            throw new RuntimeException(e);
        }

        Call<MemberInfoResponseDTO> call = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(AuthorizationAPI.class)
                .getMemberInfo();

        call.enqueue(new Callback<MemberInfoResponseDTO>() {
            @Override
            public void onResponse(Call<MemberInfoResponseDTO> call, Response<MemberInfoResponseDTO> response) {
                if (response.isSuccessful()) {
                    subscribeTopic();
                } else {
                    Log.e(TAG, "통신 실패");
                }
            }

            @Override
            public void onFailure(Call<MemberInfoResponseDTO> call, Throwable t) {
                Log.e(TAG, "연결 실패");
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stompClient.disconnect();
    }

    private void connect() {
        tokenManager = new TokenManagerImpl(getApplicationContext());
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, CONNECTION_URL + "?token=" + tokenManager.getAccessToken());
        stompClient.connect();
    }

    private void subscribeTopic() {
        stompClient.topic("/user" + memberInfoResponseDTO.getUsername() + "/queue/match")
                .subscribe(message -> sendBroadcast(message.compile()));
    }

    private void sendBroadcast(String payload) {
        Intent intent = new Intent(CONTENT);
        intent.putExtra("message", payload);
        LocalBroadcastManager.getInstance(MatchingService.this).sendBroadcast(intent);
    }
}
