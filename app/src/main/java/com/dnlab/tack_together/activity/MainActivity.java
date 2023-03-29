package com.dnlab.tack_together.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.BuildConfig;
import com.dnlab.tack_together.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "[" + MainActivity.class.getSimpleName() + "] ";
    private Retrofit retrofit;
    private static MainActivity instance;

    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, 101);
        });
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static MainActivity getInstance() {
        return instance;
    }
}