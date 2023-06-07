package com.dnlab.tack_together.activity_match;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.common.MatchDecisionStatus;
import com.dnlab.tack_together.api.dto.match.MatchRequestDTO;
import com.dnlab.tack_together.api.dto.match.MatchResponseDTO;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.reversegeo.ReverseGeoDocumentDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.reversegeo.KakaoReverseGeoResponseDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.reversegeo.ReverseGeoRoadAddressDTO;
import com.dnlab.tack_together.api.dto.route.LocationDTO;
import com.dnlab.tack_together.api.dto.route.RouteDTO;
import com.dnlab.tack_together.api.dto.wrapper.MatchResponseWrapperDTO;
import com.dnlab.tack_together.common.status.MatchingStatus;
import com.dnlab.tack_together.retrofit.kakaogeo.KakaoGeoAPI;
import com.dnlab.tack_together.retrofit.kakaogeo.KakaoGeoRetrofitBuilder;
import com.dnlab.tack_together.service.MatchingService;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.StompClient;

public class MatchMatchedActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MatchResultInfoDTO matchResultInfoDTO;
    private static final String TAG = "MatchMatchedActivity";
    private StompClient stompClient;
    private MatchResponseDTO matchResponseDTO;
    private BroadcastReceiver messageReceiver;
    private TextView opponentStatus;
    private boolean accepted = false;
    private boolean opponentAccepted = false;
    private String sessionId;
    private NaverMap naverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matched);

        MapView mapView = findViewById(R.id.matchedMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        stompClient = MatchingService.getStompClient();
        matchResultInfoDTO = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");
        Log.d(TAG, "matchResultInfoDTO" + matchResultInfoDTO.toString());

        setTextViews();

        Button rejectButton = findViewById(R.id.matchedRejectButton);
        rejectButton.setOnClickListener(view -> stompClient.send("/app/match/reject").subscribe());

        Button acceptButton = findViewById(R.id.matchedAcceptButton);
        acceptButton.setOnClickListener(view -> {
            LinearLayout acceptedLayout = findViewById(R.id.matchedWaitingLayout);
            LinearLayout waitingLayout = findViewById(R.id.matchedLocationSharingButtonLayout);
            waitingLayout.setVisibility(View.INVISIBLE);
            acceptedLayout.setVisibility(View.VISIBLE);
            stompClient.send("/app/match/accept").subscribe();
            accepted = true;
        });

        opponentStatus = findViewById(R.id.matchedOpponentStatus);

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                matchResponseDTO = new Gson().fromJson(message, MatchResponseWrapperDTO.class).getMatchResponseDTO();
                Log.d(TAG, "매칭 반응 DTO : " + matchResponseDTO);

                if (matchResponseDTO.getMatchDecisionStatus().equals(MatchDecisionStatus.REJECTED)) {
                    opponentStatus.setTextColor(ContextCompat.getColor(MatchMatchedActivity.this, R.color.red));
                    opponentStatus.setText(MatchingStatus.REJECTED);

                    new AlertDialog.Builder(MatchMatchedActivity.this)
                            .setMessage("매칭이 거절되었습니다.")
                            .setPositiveButton("확인", (dialog, which) -> resumeMatchingActivity())
                            .setOnDismissListener(dialog -> resumeMatchingActivity())
                            .create()
                            .show();
                } else if (matchResponseDTO.getMatchDecisionStatus().equals(MatchDecisionStatus.ACCEPTED)) {
                    opponentStatus.setTextColor(ContextCompat.getColor(MatchMatchedActivity.this, R.color.green));
                    opponentStatus.setText(MatchingStatus.ACCEPTED);
                    opponentAccepted = true;

                    sessionId = matchResponseDTO.getMatchSessionId();
                    Log.d(TAG, "매칭 수락대기 sessionId: " + sessionId);

                    checkAllAccepted();
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(BuildConfig.BROADCAST_CONTENT));
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        setMapView();
    }

    private void setMapView() {
        Optional<RouteDTO> optionalRoute = matchResultInfoDTO.getRoutes().stream().findFirst();

        optionalRoute.ifPresent(routeDTO -> {
            LocationDTO origin = routeDTO.getSummary().getOrigin();
            LocationDTO waypoint = routeDTO.getSummary().getWaypoints().stream().findFirst().orElse(null);
            LocationDTO destination = routeDTO.getSummary().getDestination();

            createInfoWindow("출발", new LatLng(origin.getY(), origin.getX()));
            assert waypoint != null;
            createInfoWindow("경유", new LatLng(waypoint.getY(), waypoint.getX()));
            createInfoWindow("도착", new LatLng(destination.getY(), destination.getX()));

            setCamera(origin, waypoint, destination);
            setDirectionLines(routeDTO);
        });
    }

    private void createInfoWindow(String s, LatLng latLng) {
        InfoWindow infoWindow = new InfoWindow(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return s;
            }
        });

        infoWindow.setPosition(latLng);
        infoWindow.open(naverMap);
    }

    private void setDirectionLines(RouteDTO routeDTO) {
        PathOverlay path = new PathOverlay();
        List<LatLng> latLngs = routeDTO.getSections().stream()
                .flatMap(section -> section.getRoads().stream())
                .flatMap(road -> IntStream.range(0, road.getVertexes().size())
                        .filter(i -> i % 2 == 0)
                        .mapToObj(i -> new LatLng(road.getVertexes().get(i + 1), road.getVertexes().get(i)))
                )
                .collect(Collectors.toList());

        path.setCoords(latLngs);
        path.setColor(ContextCompat.getColor(this, R.color.lightBlue));
        path.setWidth(25);
        path.setOutlineWidth(0);

        path.setMap(naverMap);
    }

    private void setCamera(LocationDTO location1, LocationDTO location2, LocationDTO location3) {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(location1.getY(), location1.getX()))
                .include(new LatLng(location2.getY(), location2.getX()))
                .include(new LatLng(location3.getY(), location3.getX()))
                .build();

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(bounds.getCenter());
        naverMap.moveCamera(cameraUpdate);
    }

    private void setTextViews() {
        setDestinationText();
        setMemberDestinationText();
        setTaxiFareText();
    }

    private void startLocationSharingActivity() {
        Intent sharingIntent = new Intent(MatchMatchedActivity.this, MatchLocationSharingActivity.class);
        sharingIntent.putExtra("sessionId", sessionId);
        sharingIntent.putExtra("matchResultInfo", matchResultInfoDTO);
        startActivity(sharingIntent);

        // 매칭 서비스 정지
        Intent matchingServiceIntent = new Intent(getApplicationContext(), MatchingService.class);
        stopService(matchingServiceIntent);

        // 매칭 액티비티 정지
        MatchMatchingActivity.finishActivity();

        finish();
    }

    private void checkAllAccepted() {
        if (opponentAccepted && accepted) {
            startLocationSharingActivity();
        }
    }

    private void resumeMatchingActivity() {
        finish();
    }

    private void setMemberDestinationText() {
        MatchRequestDTO matchRequestDTO = (MatchRequestDTO) getIntent().getSerializableExtra("matchRequest");
        Log.d(TAG, "myDestination:" + matchRequestDTO.getDestination());
        String[] destination = matchRequestDTO.getDestination().split(",");

        Call<KakaoReverseGeoResponseDTO> call = KakaoGeoRetrofitBuilder.getInstance()
                .getRetrofit()
                .create(KakaoGeoAPI.class)
                .requestReverseGeocoding(destination[0], destination[1]);
        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<KakaoReverseGeoResponseDTO> call, Response<KakaoReverseGeoResponseDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ReverseGeoDocumentDTO documentDTO = response.body()
                            .getDocuments()
                            .stream().findFirst().get();
                    ReverseGeoRoadAddressDTO roadAddressDTO = documentDTO.getRoadAddress();

                    String address = "";
                    if (roadAddressDTO != null) {
                        address = roadAddressDTO.getAddressName();
                    } else {
                        address = documentDTO.getAddress().getAddressName();
                    }

                    TextView destinationText = findViewById(R.id.matchedMyDestination);
                    destinationText.setText(address);
                }
            }

            @Override
            public void onFailure(Call<KakaoReverseGeoResponseDTO> call, Throwable t) {
                Log.d(TAG, "통신 실패");
                t.printStackTrace();
            }
        });
    }

    private void setTaxiFareText() {
        TextView estimatedTotalTaxiFare = findViewById(R.id.matchedEstimatedTotalFare);
        estimatedTotalTaxiFare.setText(matchResultInfoDTO.getEstimatedTotalTaxiFare() + "원");

        TextView estimatedTaxiFare = findViewById(R.id.matchedEstimatedFare);
        estimatedTaxiFare.setText(matchResultInfoDTO.getEstimatedTaxiFare() + "원");
    }

    private void setDestinationText() {
        LocationDTO destination = matchResultInfoDTO.getRoutes().stream().findAny().get().getSummary().getDestination();
        Call<KakaoReverseGeoResponseDTO> call = KakaoGeoRetrofitBuilder.getInstance()
                .getRetrofit()
                .create(KakaoGeoAPI.class)
                .requestReverseGeocoding(String.valueOf(destination.getX()), String.valueOf(destination.getY()));

        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<KakaoReverseGeoResponseDTO> call, Response<KakaoReverseGeoResponseDTO> response) {
                Log.d(TAG, "reverse geocoding response: " + response.body());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ReverseGeoDocumentDTO documentDTO = response.body()
                            .getDocuments()
                            .stream().findFirst().get();
                    ReverseGeoRoadAddressDTO roadAddressDTO = documentDTO.getRoadAddress();

                    String address = "";
                    if (roadAddressDTO != null) {
                        address = roadAddressDTO.getAddressName();
                    } else {
                        address = documentDTO.getAddress().getAddressName();
                    }

                    TextView destinationText = findViewById(R.id.matchedOpponentDestination);
                    destinationText.setText(address);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<KakaoReverseGeoResponseDTO> call, Throwable t) {
                Log.d(TAG, "통신 실패");
                t.printStackTrace();

            }
        });

        TextView opponentNickname = findViewById(R.id.matchedOpponentNickname);
        opponentNickname.setText(matchResultInfoDTO.getOpponentNickname());

        TextView opponentDestination = findViewById(R.id.matchedOpponentDestination);
        opponentDestination.setText(null);


    }
}