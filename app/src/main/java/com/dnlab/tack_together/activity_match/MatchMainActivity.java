package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;

public class MatchMainActivity extends AppCompatActivity {

    Button matchFindButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);


        //임시로 액티비티 연결해놓음, 추후에 매칭하기 정보 컨텍스트 넘겨줘야함
        matchFindButton = (Button) findViewById(R.id.matchFindButton);
        matchFindButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), MatchMatchingActivity.class);
            startActivity(intent);
        });
    }
}