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
import com.dnlab.tack_together.api.dto.wrapper.MatchResultInfoWrapperDTO;
import com.dnlab.tack_together.service.MatchingService;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.StompClient;

public class MatchMatchingActivity extends AppCompatActivity {

    private BroadcastReceiver messageReceiver;
    private MatchResultInfoDTO matchResultInfoDTO;
    private Intent matchingServiceIntent;
    private StompClient stompClient;
    private static final String TAG = "MatchMatchingActivity";
    private MatchRequestDTO matchRequestDTO;
    private static MatchMatchingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matching);
        instance = this;

        matchingServiceIntent = new Intent(getApplicationContext(), MatchingService.class);
        startService(matchingServiceIntent);

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String serializedMessage = intent.getStringExtra("message");

                Log.d(TAG, "serializedMessage: " + serializedMessage);
                matchResultInfoDTO = new Gson().fromJson(serializedMessage, MatchResultInfoWrapperDTO.class).getPayload();
                Log.d(TAG, "matchResultInfoDTO: " + matchResultInfoDTO.toString());
                Intent matchedIntent = new Intent(MatchMatchingActivity.this, MatchMatchedActivity.class);
                matchedIntent.putExtra("matchResultInfo", matchResultInfoDTO);
                matchedIntent.putExtra("matchRequest", matchRequestDTO);
                startActivity(matchedIntent);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                matchRequestDTO = (MatchRequestDTO) getIntent().getSerializableExtra("requestInfo");
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

    public static void finishActivity() {
        if (instance != null) {
            instance.finish();
        }
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