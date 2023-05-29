package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.auth.CheckUsernameResponseDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationRequestDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationResponseDTO;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private final AtomicBoolean available = new AtomicBoolean(false);
    private EditText username;
    private EditText password;
    private EditText name;
    private EditText nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.registerUsername);
        password = findViewById(R.id.registerPassword);
        name = findViewById(R.id.registerName);
        nickname = findViewById(R.id.registerNickname);

        Button cancelButton = findViewById(R.id.registerCancelButton);
        cancelButton.setOnClickListener(getCancelButtonOnClickListener());

        Button checkDuplicationButton = findViewById(R.id.checkDuplicationButton);
        checkDuplicationButton.setOnClickListener(getCheckDuplicationUsernameButtonOnClickListener());

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(getRegisterButtonOnClickListener());
    }

    private View.OnClickListener getRegisterButtonOnClickListener() {
        return v -> {
            if (!available.get()) {
                showPositiveAlertMessage("아이디를 확인해주세요.");
                return;
            }

            String username = this.username.getText().toString();
            String password = this.password.getText().toString();
            String name = this.name.getText().toString();
            String nickname = this.nickname.getText().toString();

            AuthorizationAPI authorizationAPI = RetrofitBuilder.getInstance(getApplicationContext())
                    .getRetrofit()
                    .create(AuthorizationAPI.class);

            RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO(
                    username,
                    password,
                    name,
                    nickname
            );
            Call<RegistrationResponseDTO> call = authorizationAPI.signUp(registrationRequestDTO);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<RegistrationResponseDTO> call, Response<RegistrationResponseDTO> response) {
                    if (response.isSuccessful()) {
                        RegistrationResponseDTO registrationResponseDTO = response.body();
                        assert registrationResponseDTO != null;
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setMessage("가입되었습니다!")
                                .setPositiveButton("확인", ((dialog, which) -> startNextActivity()))
                                .setOnDismissListener(dialog -> startNextActivity())
                                .create()
                                .show();
                    } else {
                        handleFailure();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponseDTO> call, Throwable t) {
                    handleConnectionFailure();
                }
            });
        };
    }

    private void startNextActivity() {
        Intent authIntent = new Intent(RegisterActivity.this, AuthenticationActivity.class);
        startActivity(authIntent);
        finish();
    }

    private void handleFailure() {
        showPositiveAlertMessage("가입에 실패하였습니다.");
    }

    private View.OnClickListener getCheckDuplicationUsernameButtonOnClickListener() {
        return v -> {
            AuthorizationAPI authorizationAPI = RetrofitBuilder.getInstance(getApplicationContext())
                    .getRetrofit()
                    .create(AuthorizationAPI.class);

            Call<CheckUsernameResponseDTO> call = authorizationAPI.checkUsernameDuplicated(username.getText().toString());
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<CheckUsernameResponseDTO> call, Response<CheckUsernameResponseDTO> response) {
                    if (response.isSuccessful()) {
                        CheckUsernameResponseDTO checkUsernameResponseDTO = response.body();
                        assert checkUsernameResponseDTO != null;

                        available.set(checkUsernameResponseDTO.isAvailable());
                        if (!checkUsernameResponseDTO.isAvailable()) {
                            handleUnavailableUsername();
                        } else {
                            username.setEnabled(false);
                            v.setEnabled(false);
                            handleAvailableUsername();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CheckUsernameResponseDTO> call, Throwable t) {
                    handleConnectionFailure();
                }
            });
        };
    }

    private View.OnClickListener getCancelButtonOnClickListener() {
        return v -> {
            Intent authIntent = new Intent(RegisterActivity.this, AuthenticationActivity.class);
            startActivity(authIntent);
            finish();
        };
    }

    private void handleAvailableUsername() {
        showPositiveAlertMessage("사용할 수 있는 아이디입니다.");
    }

    private void handleUnavailableUsername() {
        showPositiveAlertMessage("사용할 수 없는 아이디입니다.");
    }

    private void handleConnectionFailure() {
        showPositiveAlertMessage("통신에 실패하였습니다.");
    }

    private void showPositiveAlertMessage(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> {
                })
                .create()
                .show();
    }
}