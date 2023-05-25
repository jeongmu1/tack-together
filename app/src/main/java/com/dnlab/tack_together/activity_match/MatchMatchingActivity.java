package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;

public class MatchMatchingActivity extends AppCompatActivity {

    Button matchingCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_matching);


        //임시로 취소버튼 누르면 매칭됨 액티비티 호출하도록 해놓음
        matchingCancelButton = (Button) findViewById(R.id.matchingCancelButton);
        matchingCancelButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), MatchMatchedActivity.class);
            startActivity(intent);
        });
    }
}