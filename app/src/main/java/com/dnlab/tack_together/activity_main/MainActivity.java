package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.activity_match.MatchMainActivity;

public class MainActivity extends AppCompatActivity {

    CardView matchMenuCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchMenuCardButton = (CardView) findViewById(R.id.match_menu_button);

        matchMenuCardButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), MatchMainActivity.class);
            startActivity(intent);
        });
    }
}