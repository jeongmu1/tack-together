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

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MatchingService extends Service {

    private static final String CONNECTION_URL = BuildConfig.WEBSOCKET_URL + "/match";
    private static StompClient stompClient;
    private MemberInfoResponseDTO memberInfoResponseDTO;
    private static final String TAG = "MatchingService";
    private static Disposable subscriptionDisposable;
    private static Disposable connectionDisposable;
    private static final String CONTENT = BuildConfig.BROADCAST_CONTENT;

    public static StompClient getStompClient() {
        return stompClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Call<MemberInfoResponseDTO> call = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(AuthorizationAPI.class)
                .getMemberInfo();

        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<MemberInfoResponseDTO> call, Response<MemberInfoResponseDTO> response) {
                if (response.isSuccessful()) {
                    memberInfoResponseDTO = response.body();
                    connect();
                } else {
                    Log.e(TAG, "통신 실패");
                }
            }

            @Override
            @EverythingIsNonNull
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

    public static Disposable getSubscriptionDisposable() {
        return subscriptionDisposable;
    }

    public static Disposable getConnectionDisposable() {
        return connectionDisposable;
    }

    private void connect() {
        TokenManager tokenManager = new TokenManagerImpl(getApplicationContext());
        String accessToken = tokenManager.getAccessToken();
        Log.i(TAG, "연결 uri" + CONNECTION_URL + "?token=" + accessToken);
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, CONNECTION_URL + "?token=" + accessToken);
        connectionDisposable  = stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    subscribeTopic();
                    Intent broadcastIntent = new Intent(BuildConfig.BROADCAST_CONNECTED_CONTENT);
                    broadcastIntent.putExtra("connectionOpened", "OPENED");
                    LocalBroadcastManager.getInstance(MatchingService.this).sendBroadcast(broadcastIntent);
                    Log.d(TAG, "구독 방송 보냄");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
            }
        });
        stompClient.connect();
        Log.d(TAG, "웹소켓 연결됨");
    }

    private void subscribeTopic() {
        subscriptionDisposable = stompClient.topic("/user/" + memberInfoResponseDTO.getUsername() + "/queue/match")
                .subscribe(message -> sendBroadcast(message.compile()), throwable -> Log.e(TAG, "구독 실패", throwable));
    }

    private void sendBroadcast(String payload) {
        Intent intent = new Intent(CONTENT);
        intent.putExtra("message", payload);
        LocalBroadcastManager.getInstance(MatchingService.this).sendBroadcast(intent);
    }
}
