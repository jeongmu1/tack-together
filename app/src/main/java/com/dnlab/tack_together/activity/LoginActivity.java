package com.dnlab.tack_together.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.RetrofitAPI;
import com.dnlab.tack_together.api.dto.request.RequestLogin;
import com.dnlab.tack_together.api.dto.response.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private RetrofitAPI api;

    private final String TAG = "[" + LoginActivity.class.getSimpleName() + "] ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        api = MainActivity.getInstance().getRetrofit().create(RetrofitAPI.class);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            RequestLogin requestLogin = new RequestLogin(username, password);

            login(requestLogin);
        });
    }

    private void login(RequestLogin requestLogin) {
        if (requestLogin.getUsername().isBlank() || requestLogin.getPassword().isBlank()) {
            getPositiveAlertDialog("아이디 또는 비밀번호를 입력하여 주십시오.").show();
            return;
        }

        Call<ResponseLogin> call = api.login(requestLogin);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();
                    Intent intent = new Intent();

                    assert responseLogin != null;
                    intent.putExtra("ACCESS_TOKEN", responseLogin.getAccessToken());
                    intent.putExtra("REFRESH_TOKEN", responseLogin.getRefreshToken());

                    getPositiveAlertDialog("로그인에 성공하였습니다!", ((dialog, which) -> finish())).show();
                    Log.e(TAG, "accessToken : " + responseLogin.getAccessToken());
                    Log.e(TAG, "refreshToken : " + responseLogin.getRefreshToken());

                } else if (response.code() == 401) {
                    getPositiveAlertDialog("로그인에 실패하였습니다.").show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                getPositiveAlertDialog("통신에 실패하였습니다.");
                Log.e(TAG, "로그인 통신에 실패하였습니다.");
            }
        });
    }

    private AlertDialog getPositiveAlertDialog(String message) {
        return getPositiveAlertDialog(message, null);
    }

    private AlertDialog getPositiveAlertDialog(String message, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("확인", listener)
                .create();
    }

}
