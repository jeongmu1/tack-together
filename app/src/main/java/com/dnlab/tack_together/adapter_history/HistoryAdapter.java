package com.dnlab.tack_together.adapter_history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.api.dto.history.HistorySummaryDTO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<HistorySummaryDTO> historySummaryDTOS;
    private Context context;

    public HistoryAdapter(List<HistorySummaryDTO> historySummaryDTOS, Context context) {
        this.historySummaryDTOS = historySummaryDTOS;
        this.context = context;
    }

    public void setHistorySummaryDTOS(List<HistorySummaryDTO> historySummaryDTOS) {
        this.historySummaryDTOS = historySummaryDTOS;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_history_item, viewGroup, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        HistorySummaryDTO summaryDTO = historySummaryDTOS.get(i);

        historyViewHolder.createTimeView.setText(toTimeStamp(summaryDTO.getCreateTime()));
        historyViewHolder.originView.setText(summaryDTO.getOrigin());
        historyViewHolder.waypointsView.setText(summaryDTO.getWaypoints());
        historyViewHolder.destinationView.setText(summaryDTO.getDestination());
        historyViewHolder.paymentAmountView.setText(String.valueOf(summaryDTO.getPaymentAmount()));
    }

    @Override
    public int getItemCount() {
        return historySummaryDTOS.size();
    }

    private String toTimeStamp(long num){
        Timestamp toTimeStamp = new Timestamp(num);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
        return dateFormat.format(toTimeStamp) ;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView createTimeView;
        public TextView originView;
        public TextView destinationView;
        public TextView waypointsView;
        public TextView paymentAmountView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            createTimeView = itemView.findViewById(R.id.history_summary_date_item);
            originView = itemView.findViewById(R.id.history_summary_origin_item);
            destinationView = itemView.findViewById(R.id.history_summary_destination_item);
            waypointsView = itemView.findViewById(R.id.history_summary_waypoints_item);
            paymentAmountView = itemView.findViewById(R.id.history_summary_payment_item);
        }
    }
}
