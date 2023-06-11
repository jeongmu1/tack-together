package com.dnlab.tack_together.activity_main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private EditText username, password, passwordTest, name, nickname;
    private Button logoutButton, editButton, passwordEditButton;
    private TextView usernameText, passwordText, passwordTestText, nameText, nicknameText;
    private Boolean editableState, passwordEditableState;
    private ImageView logo;

    private LinearLayout myInfoLayout, myInfoPasswordLayout;

    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        myInfoLayout = findViewById(R.id.myInfoLayout);
        myInfoPasswordLayout = findViewById(R.id.myInfoPasswordLayout);

        username = findViewById(R.id.myInfoUsername);
        password = findViewById(R.id.myInfoPassword);
        passwordTest = findViewById(R.id.myInfoPasswordTest);
        name = findViewById(R.id.myInfoName);
        nickname = findViewById(R.id.myInfoNickname);

        logoutButton = findViewById(R.id.myInfoLogoutButton);
        editButton = findViewById(R.id.myInfoEditButton);
        passwordEditButton = findViewById(R.id.myInfoPasswordEditButton);
        logo = findViewById(R.id.myInfoLogo);
        logo.setOnClickListener(this::egg);

        usernameText = findViewById(R.id.myInfoUsernameText);
        passwordText =findViewById(R.id.passwordText);
        passwordTestText = findViewById(R.id.passwordTestText);
        nameText = findViewById(R.id.myInfoNameText);
        nicknameText = findViewById(R.id.myInfoNicknameText);

        editableState = false;
        passwordEditableState = false;
        count = 0;

        editButton.setOnClickListener(this::onEditButtonClick);
        logoutButton.setOnClickListener(this::onLogoutButtonClick);
        passwordEditButton.setOnClickListener(this::onPasswordEditButtonClick);

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
            if(passwordEditableState){
                passwordEditSubmit();
            }else{
                editSubmit();
            }

        }else {
            onEdit();
        }
    }

    private void onLogoutButtonClick(View view){
        if(editableState){
            if(passwordEditableState){
                onEdit();
            }else {
                cancelEdit();
            }
        }else {
            logout();
        }
    }

    private void onPasswordEditButtonClick(View view){
        onPasswordEdit();
    }

    //내정보 수정 모드
    private void onEdit(){
        usernameText.setVisibility(View.GONE);
        nameText.setVisibility(View.GONE);
        nicknameText.setVisibility(View.GONE);

        username.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        nickname.setVisibility(View.VISIBLE);
        passwordEditButton.setVisibility(View.VISIBLE);

        myInfoLayout.setVisibility(View.VISIBLE);
        myInfoPasswordLayout.setVisibility(View.GONE);

        editButton.setText("수정 완료");
        logoutButton.setText("취소");

        editableState=true;
        passwordEditableState=false;
    }

    //비밀번호 수정 모드
    private void onPasswordEdit(){
        myInfoLayout.setVisibility(View.GONE);
        myInfoPasswordLayout.setVisibility(View.VISIBLE);
        passwordEditButton.setVisibility(View.GONE);
        editButton.setText("변경 완료");

        passwordEditableState=true;
    }

    //내정보 수정 취소
    private void cancelEdit(){
        usernameText.setVisibility(View.VISIBLE);
        nameText.setVisibility(View.VISIBLE);
        nicknameText.setVisibility(View.VISIBLE);

        username.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        nickname.setVisibility(View.GONE);
        passwordEditButton.setVisibility(View.GONE);

        editButton.setText("수정하기");
        logoutButton.setText("로그아웃");

        editableState=false;
    }

    //내정보 수정 완료
    private void editSubmit(){
        String editName = name.getText().toString();
        String editNickname = nickname.getText().toString();
        //String editPassword = password.getText().toString();
        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(editNickname, editName, null);
        Call<MemberUpdateDTO> call = authorizationAPI.updateMemberInfo(memberUpdateDTO);
        call.enqueue(new Callback<MemberUpdateDTO>() {
            @Override
            public void onResponse(Call<MemberUpdateDTO> call, Response<MemberUpdateDTO> response) {
                if(response.isSuccessful()){
                    MemberUpdateDTO memberUpdateDTO = response.body();
                    assert memberUpdateDTO != null;
                    new AlertDialog.Builder(MyInfoActivity.this)
                            .setMessage("수정되었습니다!")
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


    //비밀번호 수정 완료
    private void passwordEditSubmit(){
        String editPassword = password.getText().toString();
        String editPasswordTest = passwordTest.getText().toString();

        if(editPassword.length()<1){
            showPositiveAlertMessage("비밀번호를 입력하세요");
            return;
        }
        if(!editPassword.equals(editPasswordTest)){
            showPositiveAlertMessage("비밀번호가 일치하지 않습니다.");
            return;
        }

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(null, null, editPassword);
        Call<MemberUpdateDTO> call = authorizationAPI.updateMemberInfo(memberUpdateDTO);
        call.enqueue(new Callback<MemberUpdateDTO>() {
            @Override
            public void onResponse(Call<MemberUpdateDTO> call, Response<MemberUpdateDTO> response) {
                if(response.isSuccessful()){
                    MemberUpdateDTO memberUpdateDTO = response.body();
                    assert memberUpdateDTO != null;
                    new AlertDialog.Builder(MyInfoActivity.this)
                            .setMessage("비밀번호가 변경되었습니다!")
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
        recreate();
//        Intent intent = new Intent(this, MyInfoActivity.class);
//        startActivity(intent);
//        finish();
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
            Intent intent = new Intent(MyInfoActivity.this, EasterEggActivity.class);
            startActivity(intent);
            setCountZero();
        }else {
            count++;
            new Handler(Looper.getMainLooper()).postDelayed(this::setCountZero, 5000);
        }
    }

    private void setCountZero(){
        count = 0;
    }
}
