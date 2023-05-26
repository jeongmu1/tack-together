package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;

public class AuthenticationActivity extends AppCompatActivity {

    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        loginButton = findViewById(R.id.auth_login_button);
        registerButton = findViewById(R.id.auth_register_button);

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(AuthenticationActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

    }
}