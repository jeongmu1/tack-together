package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.settlement.SettlementInfoDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SettlementAPI {
    @GET("/api/settlement/info")
    Call<SettlementInfoDTO> getSettlementInfo();
}
