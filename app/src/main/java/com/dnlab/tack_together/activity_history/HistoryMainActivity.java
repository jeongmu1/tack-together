package com.dnlab.tack_together.activity_history;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.history.HistorySummaryDTO;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;
import com.dnlab.tack_together.retrofit.HistoryAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ConstraintLayout historyMainLayout;



    private HistoryAPI historyAPI;
    private static final String TAG = "HistoryMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        historyMainLayout = findViewById(R.id.historyMainLayout);
        historyScrollView = findViewById(R.id.historyScrollView);
        historyItemsLayout = findViewById(R.id.historyItemsLayout);

        historyAPI = RetrofitBuilder.getInstance(getApplicationContext()).getRetrofit().create(HistoryAPI.class);

        fetchHistorySummaries();



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
        long id = item.getId();
        String date = toTimeStamp(item.getDate());
        String origin = item.getOrigin();
        String destination = item.getDestination();
        int payment = item.getPaymentAmount();


        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(params);

        LinearLayout subLayout = new LinearLayout(this);
        subLayout.setOrientation(LinearLayout.HORIZONTAL);
        subLayout.setLayoutParams(params);

        TextView dateText = new TextView(this);
        dateText.setLayoutParams(params);
        dateText.setText(date);

        TextView paymentText = new TextView(this);
        paymentText.setLayoutParams(params);
        paymentText.setText("" + payment);

        subLayout.addView(dateText);
        subLayout.addView(paymentText);

        TextView originText = new TextView(this);
        originText.setLayoutParams(params);
        originText.setText(""+origin);

        TextView destinationText = new TextView(this);
        destinationText.setLayoutParams(params);
        destinationText.setText(""+destination);

        mainLayout.addView(subLayout);
        mainLayout.addView(originText);
        mainLayout.addView(destinationText);

        return mainLayout;
    }

    public void addToItemsLayout(HistorySummaryListDTO items){
        items.getHistorySummaryDTOList()
                .stream()
                .map(this::createItemLayout)
                .forEach(historyItemsLayout::addView);
    }

    public String toTimeStamp(long num){
        Date toTimeStamp = new Date(num);
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
        return datef.format(toTimeStamp) ;
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
                        addToItemsLayout(historySummaryListDTO);
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