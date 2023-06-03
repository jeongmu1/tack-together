package com.dnlab.tack_together.activity_match;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.api.dto.matched.LocationInfoResponseDTO;
import com.dnlab.tack_together.api.dto.matched.LocationUpdateRequestDTO;
import com.dnlab.tack_together.api.dto.wrapper.LocationInfoWrapperDTO;
import com.dnlab.tack_together.service.MatchedService;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticMeasurement;
import org.gavaghan.geodesy.GlobalPosition;

import java.util.Arrays;
import java.util.Optional;

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
    private boolean departureAgreed = false;
    private LocationInfoResponseDTO opponentLocationInfo;
    private Marker opponentMarker;
    private TextView distance;
    private TextView opponentDepartureAgreed;
    private MatchResultInfoDTO matchResultInfoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_location_sharing);

        matchResultInfoDTO = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");

        MapView mapView = findViewById(R.id.locationSharingMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // 웹소켓 연결 관련
        startMatchedService();
        connectedBroadcastReceiver = getConnectedBroadcastReceiver();
        messageReceiver = getMessageReceiver();

        // 위치 공유 관련
        sessionId = getIntent().getStringExtra("sessionId");
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        distance = findViewById(R.id.sharingDistance);

        opponentDepartureAgreed = findViewById(R.id.locationSharingOpponentDepartureAgreed);

        setOpponentNicknameText();

        // 버튼 매핑
        Button acceptButton = findViewById(R.id.locationSharingAcceptButton);
        acceptButton.setOnClickListener(this::updateAgreed);
    }

    private void setOpponentNicknameText() {
        String opponentNickname = matchResultInfoDTO.getOpponentNickname();

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
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
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
        Log.d(TAG, "updateLocation()이 호출됨");
        if (location != null && stompClient != null) {
            LocationUpdateRequestDTO locationUpdateRequestDTO = new LocationUpdateRequestDTO();
            locationUpdateRequestDTO.setLocation(location.getLongitude() + "," + location.getLatitude());
            locationUpdateRequestDTO.setSessionId(sessionId);
            locationUpdateRequestDTO.setDepartureAgreed(departureAgreed);

            String payload = new Gson().toJson(locationUpdateRequestDTO);
            stompClient.send("/app/matched/share-location", payload).subscribe();
        }

        Optional<LocationInfoResponseDTO> lastInfo = Optional.ofNullable(opponentLocationInfo);
        if (location != null) {
            lastInfo.ifPresent(action -> distance.setText(String.valueOf(calculateDistance(new LatLng(location.getLatitude(), location.getLongitude()),
                    getOpponentLocation()))));
        }
    }

    private void updateAgreed(View view) {
        view.setEnabled(false);
        departureAgreed = true;
        updateLocation(locationSource.getLastLocation());
    }

    private void startMatchedService() {
        Log.d(TAG, "startMatchedService() 호출됨");
        Intent matchedServiceIntent = new Intent(this, MatchedService.class);
        startService(matchedServiceIntent);
    }

    private BroadcastReceiver getConnectedBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Stomp Connected");
                stompClient = MatchedService.getStompClient();
            }
        };
    }

    private void startNextActivity() {
        Log.d(TAG, "startNextActivity()가 호출되었습니다.");

        naverMap.setLocationTrackingMode(LocationTrackingMode.None);

        Intent nextIntent = new Intent(this, MatchRidingActivity.class);
        nextIntent.putExtra("matchResultInfo", matchResultInfoDTO);
        startActivity(nextIntent);
        finish();
    }

    private BroadcastReceiver getMessageReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                opponentLocationInfo = new Gson().fromJson(message, LocationInfoWrapperDTO.class)
                        .getLocationInfoResponseDTO();
                setOpponentLocation();

                if (opponentLocationInfo.isDepartureAgreed()) {
                    opponentDepartureAgreed.setText("수락되었습니다");
                    opponentDepartureAgreed.setTextColor(ContextCompat.getColor(MatchLocationSharingActivity.this, R.color.green));
                }
                if (opponentLocationInfo.isRidingStarted()) {
                    startNextActivity();
                }
            }
        };
    }

    private void setOpponentLocation() {
        // 지도 마커 설정
        if (opponentMarker == null) {
            opponentMarker = new Marker();
            opponentMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_icon_round));
            opponentMarker.setPosition(getOpponentLocation());
            opponentMarker.setMap(naverMap);
            return;
        }
        opponentMarker.setPosition(getOpponentLocation());
    }

    private int calculateDistance(LatLng latLng1, LatLng latLng2) {
        final double elevation = 0;
        GlobalPosition position1 = new GlobalPosition(latLng1.latitude, latLng1.longitude, elevation);
        GlobalPosition position2 = new GlobalPosition(latLng2.latitude, latLng2.longitude, elevation);

        GeodeticCalculator calculator = new GeodeticCalculator();
        GeodeticMeasurement measurement = calculator.calculateGeodeticMeasurement(Ellipsoid.WGS84, position1, position2);
        return (int) measurement.getPointToPointDistance();
    }

    private LatLng getOpponentLocation() {
        String[] location = opponentLocationInfo.getLocation().split(",");
        Log.d(TAG, "opponentLocation: " + Arrays.toString(location));
        double longitude = Double.parseDouble(location[0]);
        double latitude = Double.parseDouble(location[1]);

        return new LatLng(latitude, longitude);
    }
}