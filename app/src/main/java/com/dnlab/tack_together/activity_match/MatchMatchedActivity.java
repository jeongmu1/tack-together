package com.dnlab.tack_together.activity_match;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchRequestDTO;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.api.dto.reversegeo.DocumentDTO;
import com.dnlab.tack_together.api.dto.reversegeo.KakaoReverseGeocodingResponseDTO;
import com.dnlab.tack_together.api.dto.reversegeo.RoadAddressDTO;
import com.dnlab.tack_together.api.dto.route.LocationDTO;
import com.dnlab.tack_together.retrofit.KakaoReverseGeocodingAPI;
import com.dnlab.tack_together.retrofit.ReverseGeocodingRetrofitBuilder;
import com.dnlab.tack_together.service.MatchingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.StompClient;

public class MatchMatchedActivity extends AppCompatActivity {

    private MatchResultInfoDTO matchResultInfoDTO;
    private static final String TAG = "MatchMatchedActivity";
    private StompClient stompClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matched);

        stompClient = MatchingService.getStompClient();
        matchResultInfoDTO = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");
        Log.d(TAG, "matchResultInfoDTO" + matchResultInfoDTO.toString());

        setDestinationText();
        setMemberDestinationText();
        setTaxiFareText();


        Button rejectButton = findViewById(R.id.matchedRejectButton);
        rejectButton.setOnClickListener(view -> stompClient.send("/app/match/reject").subscribe());

        Button acceptButton = findViewById(R.id.matchedAcceptButton);
        acceptButton.setOnClickListener(view -> stompClient.send("/app/match/accept").subscribe());
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