package com.dnlab.tack_together.adapter_history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public List<HistorySummaryListDTO> historyList;

    //뷰 홀더 클래스
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public HistoryViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.history_summary_item);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    //생성자
    public HistoryAdapter(List<HistorySummaryListDTO> historyListParam) {
        historyList = historyListParam;
    }

    //ViewHolder 객체 생성해서 리턴
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);
        HistoryAdapter.HistoryViewHolder viewHolder = new HistoryAdapter.HistoryViewHolder(v);
        return viewHolder;
    }

//    @Override
//    public void onBindViewHolder(HistoryViewHolder holder, int id) {
//        holder.textView.setText(historyList.toString());
//    }

    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int id) {
        String text = String.valueOf(historyList.get(id));
        holder.textView.setText(text);
    }

    public int getItemCount() {
        return historyList.size();
    }
}
