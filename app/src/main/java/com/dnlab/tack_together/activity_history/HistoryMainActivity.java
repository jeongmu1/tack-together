package com.dnlab.tack_together.activity_history;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.adapter_history.HistoryAdapter;
import com.dnlab.tack_together.api.dto.history.HistorySummaryDTO;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;
import com.dnlab.tack_together.retrofit.HistoryAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class HistoryMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistorySummaryDTO> summaries = new ArrayList<>();


    private static final String TAG = "HistoryMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryMainActivity.this));

        adapter = new HistoryAdapter(summaries, this);
        recyclerView.setAdapter(adapter);

        setRecyclerView();
    }

    private void setRecyclerView() {
        Call<HistorySummaryListDTO> call = RetrofitBuilder.getInstance(getApplicationContext())
                .getRetrofit()
                .create(HistoryAPI.class)
                .handleHistorySummaryList();

        call.enqueue(new Callback<>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<HistorySummaryListDTO> call, Response<HistorySummaryListDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        summaries = response.body().getHistorySummaryDTOList();
                        adapter.setHistorySummaryDTOS(summaries);
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<HistorySummaryListDTO> call, Throwable t) {

            }
        });
    }
}