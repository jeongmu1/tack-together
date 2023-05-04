//package com.dnlab.tack_together.activity;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.dnlab.tack_together.R;
//import com.dnlab.tack_together.retrofit.RetrofitAPI;
//import com.dnlab.tack_together.api.dto.request.RequestRegistration;
//import com.dnlab.tack_together.api.dto.response.ResponseRegistration;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import java.io.IOException;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SignupActivity extends AppCompatActivity {
//    private EditText nameEditText;
//    private EditText usernameEditText;
//    private EditText passwordEditText;
//    private EditText checkPasswordEditText;
//    private AlertDialog dialog;
//    private final String TAG = "[" + SignupActivity.class.getSimpleName() + "] ";
//
//    private RetrofitAPI api;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        api = MainActivity.getInstance().getRetrofit().create(RetrofitAPI.class);
//
//        nameEditText = findViewById(R.id.name);
//        usernameEditText = findViewById(R.id.username);
//        passwordEditText = findViewById(R.id.password);
//        checkPasswordEditText = findViewById(R.id.password_check);
//
//        // 아이디 중복 확인
//        Button usernameCheck = findViewById(R.id.check_button);
//        usernameCheck.setOnClickListener(v -> {
//            String username = usernameEditText.getText().toString().trim();
//
//            if (username.isBlank()) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
//                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
//                dialog.show();
//                return;
//            }
//
//            Log.e(TAG, "username : " + username);
//            Call<ResponseBody> call = api.checkUsernameDuplicated(username);
//
//            call.enqueue(new Callback<>() {
//                @Override
//                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        try {
//                            String json = response.body().string();
//                            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//                            boolean duplicated = jsonObject.get("duplicated").getAsBoolean();
//                            Log.e(TAG, "JSON : " + json);
//                            Log.e(TAG, "Response : " + duplicated);
//
//                            AlertDialog alertDialog;
//                            if (duplicated) {
//                                alertDialog = getPositiveAlertDialog("사용할 수 없는 아이디입니다.");
//                            } else {
//                                alertDialog = getPositiveAlertDialog("사용할 수 있는 아이디입니다.");
//                            }
//                            alertDialog.show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e(TAG, "Failed to get data", t);
//                }
//            });
//
//        });
//
//        Button signupButton = findViewById(R.id.join_button);
//        signupButton.setOnClickListener(v -> {
//
//            String username = usernameEditText.getText().toString().trim();
//            String password = passwordEditText.getText().toString().trim();
//            String name = nameEditText.getText().toString().trim();
//            String checkPassword = checkPasswordEditText.getText().toString().trim();
//
//            if (!checkIsEqualTwoPasswords(password, checkPassword)) {
//                return;
//            }
//
//            Call<ResponseRegistration> call = api.signUp(new RequestRegistration(username, password, name));
//
//            call.enqueue(new Callback<>() {
//                @Override
//                public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
//                    int responseCode = response.code();
//                    if (responseCode == 200) {
//                        ResponseRegistration registration = response.body();
//                        assert registration != null;
//                        getPositiveAlertDialog("환영합니다 " + registration.getName() + "님!", (dialog, which) -> finish()).show();
//                    } else if (responseCode == 409) {
//                        AlertDialog alertDialog = getPositiveAlertDialog("사용할 수 없는 아이디입니다.");
//                        alertDialog.show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseRegistration> call, Throwable t) {
//                    Log.e(TAG, "Failed to get data", t);
//                }
//            });
//
//        });
//
//        Button deleteButton = findViewById(R.id.delete_join);
//        deleteButton.setOnClickListener(v -> {
//            finish();
//        });
//    }
//
//    private boolean checkIsEqualTwoPasswords(String password, String checkPassword) {
//        if (!(password.equals(checkPassword))) {
//            dialog = getPositiveAlertDialog("비밀번호가 일치하지 않습니다.");
//            dialog.show();
//            return false;
//        }
//
//        return true;
//    }
//
//    private AlertDialog getPositiveAlertDialog(String message) {
//        return new AlertDialog.Builder(SignupActivity.this).setMessage(message).setPositiveButton("확인", null).create();
//    }
//
//    private AlertDialog getPositiveAlertDialog(String message, DialogInterface.OnClickListener listener) {
//        return new AlertDialog.Builder(SignupActivity.this).setMessage(message).setPositiveButton("확인", listener).create();
//    }
//}
