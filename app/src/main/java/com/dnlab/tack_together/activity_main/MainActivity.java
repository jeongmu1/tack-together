package com.dnlab.tack_together.activity_main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.activity_history.HistoryMainActivity;
import com.dnlab.tack_together.activity_match.MatchMainActivity;

public class MainActivity extends AppCompatActivity {

    CardView matchMenuCardButton;
    CardView historyMenuCardButton;
    CardView myInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchMenuCardButton = (CardView) findViewById(R.id.matchMenuButton);

        matchMenuCardButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), MatchMainActivity.class);
            startActivity(intent);
        });

        historyMenuCardButton = (CardView) findViewById(R.id.historyMenuButton);

        historyMenuCardButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryMainActivity.class);
            startActivity(intent);
        });

        myInfoButton = findViewById(R.id.mypageButton);
        myInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }
}