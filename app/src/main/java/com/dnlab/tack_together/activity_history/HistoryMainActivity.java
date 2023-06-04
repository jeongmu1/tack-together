package com.dnlab.tack_together.activity_history;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dnlab.tack_together.databinding.ActivityHistoryMainBinding;

public class HistoryMainActivity extends AppCompatActivity {

    private ActivityHistoryMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
        binding = ActivityHistoryMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}