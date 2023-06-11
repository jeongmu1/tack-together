package com.dnlab.tack_together.activity_main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dnlab.tack_together.R;

import java.net.URI;
import java.util.ArrayList;

public class EasterEggActivity extends AppCompatActivity {

    private ImageView easterEggImageView;
    private int count = 0;

    Button githubButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);

        easterEggImageView = findViewById(R.id.easterEggImageView);
        easterEggImageView.setOnClickListener(this::changeImage);

        githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(this::onGithubButtonClick);

    }

    private void changeImage(View view){
        count = 1 - count;
        if(count == 1){
            easterEggImageView.setImageResource(R.drawable.egg2);
        }else{
            easterEggImageView.setImageResource(R.drawable.egg1);
        }

    }

    private void onGithubButtonClick(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jeongmu1/tack-together"));
        startActivity(intent);
    }
}
