package com.dnlab.tack_together.adapter_history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.activity_history.HistoryItemActivity;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<HistoryItemActivity> historyItems = new ArrayList<>();

    void addItem(HistoryItemActivity historyItem) {
        historyItems.add(historyItem);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    //뷰 홀더 클래스
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView dateView;
        public TextView originView;
        public TextView destinationView;
        public TextView paymentView;


        public HistoryViewHolder(View v) {
            super(v);

            dateView = v.findViewById(R.id.history_summary_date_item);
            originView = v.findViewById(R.id.history_summary_origin_item);
            destinationView = v.findViewById(R.id.history_summary_destination_item);
            paymentView = v.findViewById(R.id.history_summary_payment_item);
        }

        void setItem(HistoryItemActivity item) {
            dateView.setText((int) item.getDate());
            originView.setText(item.getOrigin());
            destinationView.setText(item.getDestination());
            paymentView.setText(item.getPaymentAmount());

        }
    }

    //생성자
    public HistoryAdapter(List<HistoryItemActivity> historyListParam) {
        historyItems = (ArrayList<HistoryItemActivity>) historyListParam;
    }

    public HistoryAdapter() {
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//ViewHolder 객체 생성 해서 리턴 <- 뷰 홀더 만들어 지는 시점에 호출 되는 메서드. 재사용 시 호출 X
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_history_item, parent, false);
        return new HistoryViewHolder(v); //itemView 가지고 있는 뷰 홀더 만들어서 반환
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int id) {
        holder.setItem(historyItems.get(id));
    }
}
