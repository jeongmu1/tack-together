package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;

public class LoginActivity extends AppCompatActivity {

    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancelButton = findViewById(R.id.login_cancel_button);
        cancelButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}