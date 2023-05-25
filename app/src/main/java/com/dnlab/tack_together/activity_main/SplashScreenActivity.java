package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.auth.TestTokenResponseDTO;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 3000; // 3 seconds
    private final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            RetrofitBuilder retrofitBuilder = RetrofitBuilder.getInstance(getApplicationContext());
            AuthorizationAPI authorizationAPI = retrofitBuilder.getRetrofit().create(AuthorizationAPI.class);
            Call<TestTokenResponseDTO> call = authorizationAPI.testAuthentication();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<TestTokenResponseDTO> call, Response<TestTokenResponseDTO> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().isAuthorized()) {
                            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // 로그인 액티비티 넘어가기 예정
                            startLoginActivity();
                        }
                    } else {
                        // 로그인 액티비티 넘어가기 예정
                        startLoginActivity();
                    }
                }

                @Override
                public void onFailure(Call<TestTokenResponseDTO> call, Throwable t) {
                    // 로그인 액티비티 넘어가기 예정
                    startLoginActivity();
                }
            });
        }, SPLASH_SCREEN_TIMEOUT);
    }

    private void startLoginActivity() {
        Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        // 로그인 액티비티 넘어가기 예정
    }
}