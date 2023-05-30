package com.dnlab.tack_together.activity_match;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchRequestDTO;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.dnlab.tack_together.service.MatchingService;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

public class MatchMatchingActivity extends AppCompatActivity {

    private BroadcastReceiver messageReceiver;
    private MatchResultInfoDTO matchResultInfoDTO;
    private Intent matchingServiceIntent;
    private StompClient stompClient;
    private static final String TAG = "MatchMatchingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matching);

        matchingServiceIntent = new Intent(getApplicationContext(), MatchingService.class);
        startService(matchingServiceIntent);

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String serializedMessage = intent.getStringExtra("message");
                StompMessage message = StompMessage.from(serializedMessage);
                stompClient = MatchingService.getStompClient();

                matchResultInfoDTO = new Gson().fromJson(message.getPayload(), MatchResultInfoDTO.class);
                Intent matchedIntent = new Intent(MatchMatchingActivity.this, MatchMatchedActivity.class);
                matchedIntent.putExtra("matchResultInfo", matchResultInfoDTO);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MatchRequestDTO matchRequestDTO = (MatchRequestDTO) getIntent().getSerializableExtra("requestInfo");
                String payload = new Gson().toJson(matchRequestDTO);
                Log.d(TAG, "payload: " + payload);
                stompClient = MatchingService.getStompClient();
                stompClient.send("/app/match/request", payload).subscribe();
                Log.d(TAG, "message sent");
            }
        }, new IntentFilter(BuildConfig.BROADCAST_CONNECTED_CONTENT));


        Button matchingCancelButton = findViewById(R.id.matchingCancelButton);
        matchingCancelButton.setOnClickListener(v -> {
            stopService(matchingServiceIntent);
            finish();
        });
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
}