package com.dnlab.tack_together.activity_main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.auth.MemberInfoResponseDTO;
import com.dnlab.tack_together.api.dto.auth.MemberUpdateDTO;
import com.dnlab.tack_together.common.jwt.TokenManager;
import com.dnlab.tack_together.common.jwt.TokenManagerImpl;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MyInfoActivity extends AppCompatActivity {

    private AuthorizationAPI authorizationAPI;
    private EditText username, password, name, nickname;
    private Button logoutButton, editButton;
    private TextView usernameText, passwordText, nameText, nicknameText;
    private Boolean editableState;
    private ImageView logo;

    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        username = findViewById(R.id.myInfoUsername);
        password = findViewById(R.id.myInfoPassword);
        name = findViewById(R.id.myInfoName);
        nickname = findViewById(R.id.myInfoNickname);

        logoutButton = findViewById(R.id.myInfoLogoutButton);
        editButton = findViewById(R.id.myInfoEditButton);
        logo = findViewById(R.id.myInfoLogo);
        logo.setOnClickListener(this::egg);

        usernameText = findViewById(R.id.myInfoUsernameText);
        passwordText =findViewById(R.id.passwordText);
        nameText = findViewById(R.id.myInfoNameText);
        nicknameText = findViewById(R.id.myInfoNicknameText);

        editableState = false;
        count = 0;

        editButton.setOnClickListener(this::onEditButtonClick);
        logoutButton.setOnClickListener(this::onLogoutButtonClick);

        authorizationAPI = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(AuthorizationAPI.class);
        getMyInfo();
    }

    private void getMyInfo(){
        Call<MemberInfoResponseDTO> call = authorizationAPI.getMemberInfo();
        call.enqueue(new Callback<MemberInfoResponseDTO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<MemberInfoResponseDTO> call, Response<MemberInfoResponseDTO> response) {
                if(response.isSuccessful()){
                    MemberInfoResponseDTO memberInfoResponseDTO = response.body();
                    assert memberInfoResponseDTO != null;
                    username.setText(memberInfoResponseDTO.getUsername());
                    usernameText.setText(memberInfoResponseDTO.getUsername());
                    name.setText(memberInfoResponseDTO.getName());
                    nameText.setText(memberInfoResponseDTO.getName());
                    nickname.setText(memberInfoResponseDTO.getNickname());
                    nicknameText.setText(memberInfoResponseDTO.getNickname());
                }else{
                    showPositiveAlertMessage("정보를 불러오지 못했습니다.");
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<MemberInfoResponseDTO> call, Throwable t) {
                showPositiveAlertMessage("통신에 실패하였습니다.");
            }
        });
    }

    private void onEditButtonClick(View view){
        if(editableState){
            submit();
        }else {
            onEdit();
        }
    }

    private void onLogoutButtonClick(View view){
        if(editableState){
            cancelEdit();
        }else {
            logout();
        }
    }

    private void onEdit(){
        usernameText.setVisibility(View.GONE);
        nameText.setVisibility(View.GONE);
        nicknameText.setVisibility(View.GONE);

        username.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        nickname.setVisibility(View.VISIBLE);
        passwordText.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);

        editButton.setText("수정 완료");
        logoutButton.setText("취소");

        editableState=true;
    }

    private void cancelEdit(){
        usernameText.setVisibility(View.VISIBLE);
        nameText.setVisibility(View.VISIBLE);
        nicknameText.setVisibility(View.VISIBLE);

        username.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        nickname.setVisibility(View.GONE);
        passwordText.setVisibility(View.GONE);
        password.setVisibility(View.GONE);

        editButton.setText("수정하기");
        logoutButton.setText("로그아웃");

        editableState=false;
    }

    private void submit(){
        String editName = name.getText().toString();
        String editNickname = nickname.getText().toString();
        String editPassword = password.getText().toString();
        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(editNickname, editName, editPassword);
        Call<MemberUpdateDTO> call = authorizationAPI.updateMemberInfo(memberUpdateDTO);
        call.enqueue(new Callback<MemberUpdateDTO>() {
            @Override
            public void onResponse(Call<MemberUpdateDTO> call, Response<MemberUpdateDTO> response) {
                if(response.isSuccessful()){
                    MemberUpdateDTO memberUpdateDTO = response.body();
                    assert memberUpdateDTO != null;
                    new AlertDialog.Builder(MyInfoActivity.this)
                            .setMessage("가입되었습니다!")
                            .setPositiveButton("확인", ((dialog, which) -> restartThisActivity()))
                            .setOnDismissListener(dialog -> restartThisActivity())
                            .create()
                            .show();

                }else {
                    showPositiveAlertMessage("수정을 실패하였습니다.");
                }
            }

            @Override
            public void onFailure(Call<MemberUpdateDTO> call, Throwable t) {
                showPositiveAlertMessage("통신에 실패하였습니다.");
            }
        });
    }

    private void logout(){
        TokenManager tokenManager = new TokenManagerImpl(getApplicationContext());
        tokenManager.storeAccessToken(null);
        tokenManager.storeRefreshToken(null);
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void restartThisActivity(){
        Intent intent = new Intent(this, MyInfoActivity.class);
        startActivity(intent);
        finish();
    }


    private void showPositiveAlertMessage(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> {
                })
                .create()
                .show();
    }

    private void egg(View view){
        if(count>=10){
            Intent intent = new Intent(MyInfoActivity.this, EggActivity.class);
            startActivity(intent);
            count = 0;
        }else {
            count++;
        }
    }
}
