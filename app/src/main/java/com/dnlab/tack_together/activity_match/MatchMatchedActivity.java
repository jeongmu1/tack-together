package com.dnlab.tack_together.activity_match;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.dnlab.tack_together.api.dto.reversegeo.DocumentDTO;
import com.dnlab.tack_together.api.dto.reversegeo.KakaoReverseGeocodingResponseDTO;
import com.dnlab.tack_together.api.dto.reversegeo.RoadAddressDTO;
import com.dnlab.tack_together.api.dto.route.LocationDTO;
import com.dnlab.tack_together.api.dto.wrapper.MatchResponseWrapperDTO;
import com.dnlab.tack_together.common.status.MatchingStatus;
import com.dnlab.tack_together.retrofit.KakaoReverseGeocodingAPI;
import com.dnlab.tack_together.retrofit.ReverseGeocodingRetrofitBuilder;
import com.dnlab.tack_together.service.MatchingService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.StompClient;

public class MatchMatchedActivity extends AppCompatActivity {

    private MatchResultInfoDTO matchResultInfoDTO;
    private static final String TAG = "MatchMatchedActivity";
    private StompClient stompClient;
    private MatchResponseDTO matchResponseDTO;
    private BroadcastReceiver messageReceiver;
    private TextView opponentStatus;
    private boolean accepted = false;
    private boolean opponentAccepted = false;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matched);

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

            checkAllAccepted();
        });

        opponentStatus = findViewById(R.id.matchedOpponentStatus);

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                matchResponseDTO = new Gson().fromJson(message, MatchResponseWrapperDTO.class).getMatchResponseDTO();

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

    private void setTextViews() {
        setDestinationText();
        setMemberDestinationText();
        setTaxiFareText();
    }

    private void startLocationSharingActivity() {
        Intent sharingIntent = new Intent(MatchMatchedActivity.this, MatchLocationSharingActivity.class);
        sharingIntent.putExtra("sessionId", sessionId);
        sharingIntent.putExtra("opponentNickname", matchResultInfoDTO.getOpponentNickname());
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

        Call<KakaoReverseGeocodingResponseDTO> call = ReverseGeocodingRetrofitBuilder.getInstance()
                .getRetrofit()
                .create(KakaoReverseGeocodingAPI.class)
                .requestReverseGeocoding(destination[0], destination[1]);
        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<KakaoReverseGeocodingResponseDTO> call, Response<KakaoReverseGeocodingResponseDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    DocumentDTO documentDTO = response.body()
                            .getDocuments()
                            .stream().findFirst().get();
                    RoadAddressDTO roadAddressDTO = documentDTO.getRoadAddress();

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
            public void onFailure(Call<KakaoReverseGeocodingResponseDTO> call, Throwable t) {
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
        Call<KakaoReverseGeocodingResponseDTO> call = ReverseGeocodingRetrofitBuilder.getInstance()
                .getRetrofit()
                .create(KakaoReverseGeocodingAPI.class)
                .requestReverseGeocoding(String.valueOf(destination.getX()), String.valueOf(destination.getY()));

        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<KakaoReverseGeocodingResponseDTO> call, Response<KakaoReverseGeocodingResponseDTO> response) {
                Log.d(TAG, "reverse geocoding response: " + response.body());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    DocumentDTO documentDTO = response.body()
                            .getDocuments()
                            .stream().findFirst().get();
                    RoadAddressDTO roadAddressDTO = documentDTO.getRoadAddress();

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
            public void onFailure(Call<KakaoReverseGeocodingResponseDTO> call, Throwable t) {
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