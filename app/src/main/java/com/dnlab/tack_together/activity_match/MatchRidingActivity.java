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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.api.dto.matched.DropOffNotificationDTO;
import com.dnlab.tack_together.api.dto.matched.DropOffRequestDTO;
import com.dnlab.tack_together.api.dto.matched.SettlementReceivedRequestDTO;
import com.dnlab.tack_together.api.dto.wrapper.DropOffNotificationWrapperDTO;
import com.dnlab.tack_together.api.dto.wrapper.SettlementReceivedRequestWrapperDTO;
import com.dnlab.tack_together.service.MatchedService;
import com.google.gson.Gson;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import ua.naiksoftware.stomp.StompClient;

public class MatchRidingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MatchResultInfoDTO matchResultInfo;
    private TextView opponentNickname;
    private TextView opponentRidingStatus;
    private TextView estimatedTotalFare;
    private TextView estimatedFare;
    private StompClient stompClient;
    private BroadcastReceiver messageReceiver;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private String sessionId;
    private boolean destination;
    private LinearLayout ridingLayout;
    private LinearLayout waitingLayout;
    private SettlementReceivedRequestDTO settlementReceivedRequestDTO;
    private boolean opponentDropOffed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_riding);

        matchResultInfo = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");
        sessionId = getIntent().getStringExtra("sessionId");

        destination = ((double) matchResultInfo.getEstimatedTotalTaxiFare() / (double) matchResultInfo.getEstimatedTaxiFare()) < 2;

        // 소켓 설정
        stompClient = MatchedService.getStompClient();
        messageReceiver = getMessageReceiver();

        // View 설정
        opponentNickname = findViewById(R.id.ridingOpponentNickname);
        opponentRidingStatus = findViewById(R.id.ridingOpponentStatus);

        estimatedTotalFare = findViewById(R.id.ridingEstimatedTotalFare);
        estimatedFare = findViewById(R.id.ridingEstimatedFare);

        Button ridingEndButton = findViewById(R.id.ridingEndButton);
        ridingEndButton.setOnClickListener(this::handleDropOff);

        ridingLayout = findViewById(R.id.ridingButtonLayout);
        waitingLayout = findViewById(R.id.ridingWaitingSettlement);

        setTextViews();

        MapView mapView = findViewById(R.id.ridingMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(BuildConfig.BROADCAST_CONTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        naverMap.setLocationTrackingMode(LocationTrackingMode.None);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    private void handleDropOff(View view) {
        Location location = locationSource.getLastLocation();
        assert location != null;

        DropOffRequestDTO dropOffRequestDTO = new DropOffRequestDTO();
        dropOffRequestDTO.setSessionId(sessionId);
        dropOffRequestDTO.setEndLocation(location.getLongitude() + "," + location.getLatitude());

        stompClient.send("/app/matched/drop-off", new Gson().toJson(dropOffRequestDTO)).subscribe();

        if (destination) {
            startNextActivity();
        }

        ridingLayout.setVisibility(View.INVISIBLE);
        waitingLayout.setVisibility(View.VISIBLE);

        view.setEnabled(false);
    }

    private void startNextActivity() {
        Intent nextIntent = new Intent(this, MatchEndActivity.class);
        nextIntent.putExtra("destination", destination);
        nextIntent.putExtra("settlementReceivedRequest", settlementReceivedRequestDTO);
        nextIntent.putExtra("matchResultInfo", matchResultInfo);
        startActivity(nextIntent);
        finish();
    }

    private BroadcastReceiver getMessageReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");

                if (!destination) {
                    if (opponentDropOffed) {
                        settlementReceivedRequestDTO = new Gson().fromJson(message, SettlementReceivedRequestWrapperDTO.class).getPayload();
                        startNextActivity();
                    } else {
                        DropOffNotificationDTO dropOffNotificationDTO = new Gson().fromJson(message, DropOffNotificationWrapperDTO.class)
                                .getPayload();

                        if (sessionId.equals(dropOffNotificationDTO.getSessionId())) {
                            opponentRidingStatus.setText("하차");
                            opponentDropOffed = true;
                        }
                    }

                } else {
                    opponentRidingStatus.setText("하차");
                }
            }
        };
    }

    private void setTextViews() {
        opponentNickname.setText(matchResultInfo.getOpponentNickname());
        estimatedTotalFare.setText(String.valueOf(matchResultInfo.getEstimatedTotalTaxiFare()));
        estimatedFare.setText(String.valueOf(matchResultInfo.getEstimatedTaxiFare()));
    }
}