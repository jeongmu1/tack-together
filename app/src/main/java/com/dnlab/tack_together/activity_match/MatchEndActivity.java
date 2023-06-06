package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.api.dto.matched.SettlementReceivedRequestDTO;
import com.dnlab.tack_together.api.dto.matched.SettlementRequestDTO;
import com.dnlab.tack_together.api.dto.settlement.RouteInfoDTO;
import com.dnlab.tack_together.api.dto.settlement.SettlementInfoDTO;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;
import com.dnlab.tack_together.retrofit.SettlementAPI;
import com.dnlab.tack_together.service.MatchedService;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolylineOverlay;

import java.util.Arrays;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.StompClient;

public class MatchEndActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SettlementReceivedRequestDTO settlementReceivedRequestDTO;
    private boolean destination;
    private SettlementInfoDTO settlementInfo;
    private MatchResultInfoDTO matchResultInfo;
    private StompClient stompClient;

    private TextView opponentNickname;
    private TextView totalFareOutput;
    private EditText totalFareInput;
    private TextView accountOutput;
    private EditText accountInput;
    private TextView opponentFareRate;
    private TextView opponentFare;
    private TextView myFareRate;
    private TextView myFare;
    private Button endButton;
    private NaverMap naverMap;
    private String sessionId;

    private static final String TAG = "MatchEndActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_end);

        MapView mapView = findViewById(R.id.endMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        destination = getIntent().getBooleanExtra("destination", false);
        sessionId = getIntent().getStringExtra("sessionId");
        matchResultInfo = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");
        settlementReceivedRequestDTO = (SettlementReceivedRequestDTO) getIntent().getSerializableExtra("settlementReceivedRequest");
        stompClient = MatchedService.getStompClient();

        opponentNickname = findViewById(R.id.settlementOpponentNickname);

        accountOutput = findViewById(R.id.accountNumberOutput);
        accountInput = findViewById(R.id.accountNumberInput);

        totalFareOutput = findViewById(R.id.totalFareOutput);
        totalFareInput = findViewById(R.id.totalFareInput);

        opponentFare = findViewById(R.id.settlementOpponentFare);
        opponentFareRate = findViewById(R.id.settlementOpponentRate);
        myFare = findViewById(R.id.settlementMyFare);
        myFareRate = findViewById(R.id.settlementMyRate);

        endButton = findViewById(R.id.endEndButton);

        setViews();
        if (destination) {
            setViewsForDestination();
        } else {
            stopService(new Intent(this, MatchedService.class));
            setViewForWaypoint();
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
    }

    private void setViews() {
        opponentNickname.setText(matchResultInfo.getOpponentNickname());
    }

    private void setViewsForDestination() {
        Call<SettlementInfoDTO> call = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(SettlementAPI.class)
                .getSettlementInfo();

        call.enqueue(getCallBackOfSettlementInfo());

        endButton.setOnClickListener(this::handleRequestingSettlement);
    }

    private void handleRequestingSettlement(View view) {
        if (destination) {
            SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
            settlementRequestDTO.setSessionId(sessionId);
            settlementRequestDTO.setAccountInfo(accountInput.getText().toString().trim());
            settlementRequestDTO.setTotalFare(Integer.parseInt(totalFareInput.getText().toString().trim()));

            Disposable disposable = stompClient.send("/app/matched/settlement", new Gson().toJson(settlementRequestDTO)).subscribe(() -> {
                stopService(new Intent(this, MatchedService.class));
                MatchMainActivity.destroyActivity();
                finish();
            });
        } else {
            finish();
        }
    }

    private Callback<SettlementInfoDTO> getCallBackOfSettlementInfo() {
        return new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<SettlementInfoDTO> call, Response<SettlementInfoDTO> response) {
                if (response.isSuccessful()) {
                    settlementInfo = response.body();

                    assert settlementInfo != null;
                    opponentFareRate.setText(getStringOfDouble(settlementInfo.getOpponentPaymentRate()));
                    myFareRate.setText(getStringOfDouble(settlementInfo.getPaymentRate()));

                    RouteInfoDTO routeInfoDTO = new RouteInfoDTO(settlementInfo.getOrigin(),
                            settlementInfo.getWaypoint(),
                            settlementInfo.getDestination());
                    setMapView(routeInfoDTO);

                    totalFareInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String trimmedString = s.toString().trim();
                            if (trimmedString.equals("")) {
                                myFare.setText(String.valueOf(0));
                                opponentFare.setText(String.valueOf(0));
                                return;
                            }
                            if (trimmedString.length() < 1) {
                                myFare.setText(String.valueOf(0));
                                opponentFare.setText(String.valueOf(0));
                                return;
                            }
                            int totalFare = Integer.parseInt(trimmedString);
                            int fare = (int) ((double) totalFare * (settlementInfo.getPaymentRate() / 100));
                            myFare.setText(String.valueOf(fare));
                            opponentFare.setText(String.valueOf(totalFare - fare));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<SettlementInfoDTO> call, Throwable t) {
                Log.d(TAG, "통신 실패");
                t.printStackTrace();
            }
        };
    }

    private String getStringOfDouble(double value) {
        return String.format(Locale.KOREA, "%.2f", value);
    }

    private void setMapView(RouteInfoDTO routeInfoDTO) {
        LatLng origin = parseStringLocation(routeInfoDTO.getOrigin());
        LatLng waypoint = parseStringLocation(routeInfoDTO.getWaypoint());
        LatLng destination = parseStringLocation(routeInfoDTO.getDestination());

        printMarkerOfLocation(origin);
        printMarkerOfLocation(waypoint);
        printMarkerOfLocation(destination);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLngBounds.Builder()
                .include(origin)
                .include(waypoint)
                .include(destination)
                .build().getCenter());
        naverMap.moveCamera(cameraUpdate);

        PolylineOverlay polyline = new PolylineOverlay();
        polyline.setCoords(Arrays.asList(
                origin,
                waypoint,
                destination
        ));
        polyline.setPattern(10, 10);
        polyline.setMap(naverMap);
    }

    private void printMarkerOfLocation(LatLng location) {
        Marker marker = new Marker();
        marker.setPosition(location);
        marker.setMap(naverMap);
    }

    private LatLng parseStringLocation(String location) {
        String[] stringLocations = location.split(",");
        return new LatLng(Double.parseDouble(stringLocations[1]), Double.parseDouble(stringLocations[0]));
    }

    private void setViewForWaypoint() {
        accountOutput.setVisibility(View.VISIBLE);
        accountInput.setVisibility(View.GONE);

        totalFareOutput.setVisibility(View.VISIBLE);
        totalFareInput.setVisibility(View.GONE);

        accountOutput.setText(settlementReceivedRequestDTO.getAccountInfo());
        totalFareOutput.setText(String.valueOf(settlementReceivedRequestDTO.getTotalFare()));

        opponentFare.setText(String.valueOf(settlementReceivedRequestDTO.getTotalFare() - settlementReceivedRequestDTO.getRequestedFare()));
        opponentFareRate.setText(getStringOfDouble(settlementReceivedRequestDTO.getDestinationRate()));

        myFare.setText(String.valueOf(settlementReceivedRequestDTO.getRequestedFare()));
        myFareRate.setText(getStringOfDouble(settlementReceivedRequestDTO.getWaypointRate()));

        endButton.setOnClickListener(this::handleFinishSettlement);

        setMapView(settlementReceivedRequestDTO.getRouteInfo());
    }

    private void handleFinishSettlement(View view) {
        MatchMainActivity.destroyActivity();
        finish();
    }
}