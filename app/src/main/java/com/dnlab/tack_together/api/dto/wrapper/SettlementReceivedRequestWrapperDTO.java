package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.matched.SettlementReceivedRequestDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettlementReceivedRequestWrapperDTO implements Serializable {
    @SerializedName("payload")
    private SettlementReceivedRequestDTO payload;
    @SerializedName("headers")
    private StompHeaderDTO headers;

    @Override
    public String toString() {
        return "SettlementReceivedRequestWrapperDTO{" +
                "payload=" + payload +
                ", headers=" + headers +
                '}';
    }

    public SettlementReceivedRequestWrapperDTO() {
    }

    public SettlementReceivedRequestDTO getPayload() {
        return payload;
    }

    public void setPayload(SettlementReceivedRequestDTO payload) {
        this.payload = payload;
    }

    public StompHeaderDTO getHeaders() {
        return headers;
    }

    public void setHeaders(StompHeaderDTO headers) {
        this.headers = headers;
    }
}
