package com.dnlab.tack_together.adapter_history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public List<HistorySummaryListDTO> historyList;

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView historySummaryItem;

        public HistoryViewHolder(View v) {
            super(v);
            historySummaryItem = v.findViewById(R.id.history_summary_item);
        }
    }

    public HistoryAdapter(List<HistorySummaryListDTO> historyList) {
        this.historyList = historyList;
    }

    public HistoryAdapter.HistoryViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int id ) {
        holder.historySummaryItem.setText(historyList.toString());
            }

    public int getItemCount() {
        return historyList.size();
    }
}
