package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;

public class MatchRidingActivity extends AppCompatActivity {
    private MatchResultInfoDTO matchResultInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_riding);

        matchResultInfo = (MatchResultInfoDTO) getIntent().getSerializableExtra("matchResultInfo");
    }
}