package com.dnlab.tack_together.activity_match;

import android.content.Intent;
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
import com.dnlab.tack_together.api.dto.settlement.SettlementInfoDTO;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;
import com.dnlab.tack_together.retrofit.SettlementAPI;
import com.dnlab.tack_together.service.MatchedService;
import com.google.gson.Gson;

import java.util.Locale;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import ua.naiksoftware.stomp.StompClient;

public class MatchEndActivity extends AppCompatActivity {
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

    private static final String TAG = "MatchEndActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_end);

        destination = getIntent().getBooleanExtra("destination", false);
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
            settlementRequestDTO.setAccountInfo(accountInput.getText().toString().trim());
            settlementRequestDTO.setTotalFare(Integer.parseInt(totalFareInput.getText().toString().trim()));

            Disposable disposable = stompClient.send("/app/matched/settlement", new Gson().toJson(settlementRequestDTO)).subscribe(() -> {
                stopService(new Intent(this, MatchedService.class));
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
    }
}