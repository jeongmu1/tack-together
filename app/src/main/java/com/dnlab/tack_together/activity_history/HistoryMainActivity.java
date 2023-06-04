package com.dnlab.tack_together.activity_history;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.adapter_history.HistoryAdapter;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;
import com.dnlab.tack_together.databinding.ActivityHistoryMainBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryMainActivity extends AppCompatActivity {

    private ActivityHistoryMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
        binding = ActivityHistoryMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.historySummaryItems;

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        List<HistorySummaryListDTO> historySummaryListDTOList = fetchHistory();
//        mAdapter = new HistoryAdapter(historySummaryListDTOList);
        recyclerView.setAdapter(mAdapter);
    }
//    private List<HistorySummaryListDTO> fetchHistory() {
//        List<HistorySummaryListDTO> historyData = new ArrayList() {
//            //이용기록 데이터 가져오는 로직 구현해야함
//            // historyData에 가져온 데이터 추가하고 리턴
//        }
//        return historyData;
//    }
}