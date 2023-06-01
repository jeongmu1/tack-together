package com.dnlab.tack_together.activity_match;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.matched.LocationInfoResponseDTO;
import com.dnlab.tack_together.api.dto.matched.LocationUpdateRequestDTO;
import com.dnlab.tack_together.api.dto.wrapper.LocationInfoWrapperDTO;
import com.dnlab.tack_together.service.MatchedService;
import com.google.gson.Gson;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import ua.naiksoftware.stomp.StompClient;

public class MatchLocationSharingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MatchLocationSharingActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private StompClient stompClient;
    private BroadcastReceiver connectedBroadcastReceiver;
    private BroadcastReceiver messageReceiver;
    private String sessionId;
    private LocationOverlay locationOverlay;
    private boolean departureAgreed = false;
    private LocationInfoResponseDTO opponentLocationInfo;
    private Intent matchedServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_location_sharing);

        // 웹소켓 연결 관련
        startMatchedService();
        connectedBroadcastReceiver = getConnectedBroadcastReceiver();
        messageReceiver = getMessageReceiver();

        // 위치 공유 관련
        sessionId = getIntent().getStringExtra("sessionId");
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        setOpponentNicknameText();

        // 버튼 매핑
        Button acceptButton = findViewById(R.id.locationSharingAcceptButton);
        acceptButton.setOnClickListener(this::updateAgreed);
    }

    private void setOpponentNicknameText() {
        String opponentNickname = getIntent().getStringExtra("opponentNickname");

        TextView opponentNicknameTextView = findViewById(R.id.sharingOpponentNickname);
        opponentNicknameTextView.setText(opponentNickname);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        naverMap.addOnLocationChangeListener(this::updateLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(connectedBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(connectedBroadcastReceiver, new IntentFilter(BuildConfig.BROADCAST_CONNECTED_CONTENT));
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(BuildConfig.BROADCAST_CONTENT));
    }

    private void updateLocation(Location location) {
        if (location != null) {
            LocationUpdateRequestDTO locationUpdateRequestDTO = new LocationUpdateRequestDTO();
            locationUpdateRequestDTO.setLocation(location.getLongitude() + "," + location.getLatitude());
            locationUpdateRequestDTO.setSessionId(sessionId);
            locationUpdateRequestDTO.setDepartureAgreed(departureAgreed);

            String payload = new Gson().toJson(locationUpdateRequestDTO);
            stompClient.send("/app/matched/share-location", payload).subscribe();
        }
    }

    private void updateAgreed(View view) {
        view.setEnabled(false);
        departureAgreed = true;
        updateLocation(locationSource.getLastLocation());
    }

    private void startMatchedService() {
        Log.d(TAG, "startMatchedService() 호출됨");
        matchedServiceIntent = new Intent(this, MatchedService.class);
        startService(matchedServiceIntent);
    }

    private BroadcastReceiver getConnectedBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                stompClient = MatchedService.getStompClient();
            }
        };
    }

    private void startNextActivity() {
        Log.d(TAG, "startNextActivity()가 호출되었습니다.");
    }

    private BroadcastReceiver getMessageReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                opponentLocationInfo = new Gson().fromJson(message, LocationInfoWrapperDTO.class)
                        .getLocationInfoResponseDTO();

                if (opponentLocationInfo.isRidingStarted()) {
                    startNextActivity();
                }
            }
        };
    }
}