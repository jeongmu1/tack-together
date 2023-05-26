package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.auth.LoginRequestDTO;
import com.dnlab.tack_together.api.dto.auth.LoginResponseDTO;
import com.dnlab.tack_together.common.jwt.TokenManager;
import com.dnlab.tack_together.common.jwt.TokenManagerImpl;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button cancelButton = findViewById(R.id.login_cancel_button);
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        });

        Button registerButton = findViewById(R.id.login_register_button);
        registerButton.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            EditText username = findViewById(R.id.login_username);
            EditText password = findViewById(R.id.login_password);

            // 아이디, 비밀번호 공백 체크
            if (username.length() < 1) {
                alertNoText(username.getText().toString(), "아이디");
                return;
            }
            if (password.length() < 1) {
                alertNoText(password.getText().toString(), "비밀번호");
                return;
            }

            AuthorizationAPI authorizationAPI = RetrofitBuilder.getInstance(getApplicationContext())
                    .getRetrofit()
                    .create(AuthorizationAPI.class);


            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                    username.getText().toString(),
                    password.getText().toString()
            );

            Call<LoginResponseDTO> call = authorizationAPI.login(loginRequestDTO);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                    if (response.isSuccessful()) {
                        TokenManager tokenManager = new TokenManagerImpl(getApplicationContext());

                        LoginResponseDTO loginResponseDTO = response.body();
                        assert loginResponseDTO != null;
                        tokenManager.storeAccessToken(loginResponseDTO.getAccessToken());
                        tokenManager.storeRefreshToken(loginResponseDTO.getRefreshToken());

                        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                        finish();
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("로그인에 실패하였습니다.")
                                .setPositiveButton("확인", ((dialog, which) -> {}))
                                .create()
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("통신에 실패하였습니다.")
                            .setPositiveButton("확인", ((dialog, which) -> {}))
                            .create()
                            .show();
                }
            });

        });
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void alertNoText(String text, String messagePrefix) {
        new AlertDialog.Builder(this)
                .setMessage(messagePrefix + "를 입력해주세요.")
                .setPositiveButton("확인", (dialog, which) -> {})
                .create()
                .show();
    }
}