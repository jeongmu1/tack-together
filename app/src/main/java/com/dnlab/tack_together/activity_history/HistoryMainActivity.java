package com.dnlab.tack_together.activity_history;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dnlab.tack_together.adapter_history.HistoryAdapter;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;
import com.dnlab.tack_together.databinding.ActivityHistoryMainBinding;
import com.dnlab.tack_together.retrofit.HistoryAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryMainActivity extends AppCompatActivity {

    private ActivityHistoryMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryAPI historyAPI;
    private static final String TAG = "HistoryMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historyAPI = RetrofitBuilder.getInstance(getApplicationContext()).getRetrofit().create(HistoryAPI.class);

        binding = ActivityHistoryMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.historySummaryItems;

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<HistorySummaryListDTO> historySummaryListDTOList = fetchHistorySummaries();
        mAdapter = new HistoryAdapter(historySummaryListDTOList);
        recyclerView.setAdapter(mAdapter);
    }
    private List<HistorySummaryListDTO> fetchHistorySummaries() {
        List<HistorySummaryListDTO> historyData = new ArrayList<>();

        Call<HistorySummaryListDTO> call = historyAPI.handleHistorySummaryList();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<HistorySummaryListDTO> call, Response<HistorySummaryListDTO> response) {
                if (response.isSuccessful()) {
                    HistorySummaryListDTO historySummaryListDTO = response.body();
                    if (historySummaryListDTO != null) {
                        // 이용기록 출력
                    } else {
                        // 이용기록이 없습니다 출력
                    }
                }
            }

            @Override
            public void onFailure(Call<HistorySummaryListDTO> call, Throwable t) {
                Log.d(TAG, "통신 실패");
            }
        });

        return historyData;
    }

}