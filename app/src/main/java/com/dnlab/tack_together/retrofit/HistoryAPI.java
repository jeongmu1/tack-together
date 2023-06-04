package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.history.HistoryDetailDTO;
import com.dnlab.tack_together.api.dto.history.HistorySummaryListDTO;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryAPI {
    @GET("/api/history/simple")
    Call<HistorySummaryListDTO> handleHistorySummaryList();

    @GET("/api/history/detail")
    Call<HistoryDetailDTO> handleHistoryDetail(@Query("id") long historyId);
}
