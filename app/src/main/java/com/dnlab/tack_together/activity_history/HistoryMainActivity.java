package com.dnlab.tack_together.activity_history;

import android.content.Context;
import android.os.Bundle;
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
import com.dnlab.tack_together.databinding.ActivityHistoryMainBinding;
import com.dnlab.tack_together.retrofit.HistoryAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryMainActivity extends AppCompatActivity {


//    private ActivityHistoryMainBinding binding;
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    private ScrollView historyScrollView;
    private LinearLayout historyItemsLayout;



    private HistoryAPI historyAPI;
    private static final String TAG = "HistoryMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        historyScrollView = findViewById(R.id.historyScrollView);
        historyItemsLayout = findViewById(R.id.historyItemsLayout);

        historyAPI = RetrofitBuilder.getInstance(getApplicationContext()).getRetrofit().create(HistoryAPI.class);



//        binding = ActivityHistoryMainBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
//
//
////        RecyclerView recyclerView = findViewById(R.id.history_summary_items);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
//        recyclerView.setLayoutManager(linearLayoutManager); //LayoutManager 설정
//
//        HistoryAdapter historyAdapter = new HistoryAdapter(fetchHistorySummaries());
//        recyclerView.setAdapter(historyAdapter); //Adapter 설정

    }

    private LinearLayout createItemLayout(HistorySummaryDTO item){
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(params);

        LinearLayout topLayout = new LinearLayout(this);
        topLayout.setOrientation(LinearLayout.HORIZONTAL);
        topLayout.setLayoutParams(params);

        LinearLayout middleLayout = new LinearLayout(this);
        middleLayout.setOrientation(LinearLayout.HORIZONTAL);
        middleLayout.setLayoutParams(params);

        LinearLayout bottomLayout = new LinearLayout(this);
        bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
        bottomLayout.setLayoutParams(params);

        mainLayout.addView(topLayout);
        mainLayout.addView(middleLayout);
        mainLayout.addView(bottomLayout);

        TextView dateText = new TextView(this);
        dateText.setLayoutParams(params);

        TextView paymentText = new TextView(this);
        paymentText.setLayoutParams(params);

        topLayout.addView(dateText);
        topLayout.addView(paymentText);

        return mainLayout;
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
                        Log.i(TAG, "이용기록 출력");
                        Log.i(TAG, "history Data : " + historyData.toString());
                        Log.i(TAG, "historySummaryList : " + historySummaryListDTO);
                    } else {
                        Log.i(TAG, "이용기록 없습니다 출력");
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