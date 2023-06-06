package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResultInfoWrapperDTO implements Serializable {
    @SerializedName("payload")
    private MatchResultInfoDTO payload;

    @SerializedName("headers")
    private StompHeaderDTO headers;

    @Override
    public String toString() {
        return "MatchResultInfoWrapperDTO{" +
                "payload=" + payload +
                ", headers=" + headers +
                '}';
    }

    public MatchResultInfoWrapperDTO() {
    }

    public StompHeaderDTO getHeaders() {
        return headers;
    }

    public void setHeaders(StompHeaderDTO headers) {
        this.headers = headers;
    }

    public MatchResultInfoDTO getPayload() {
        return payload;
    }

    public void setPayload(MatchResultInfoDTO payload) {
        this.payload = payload;
    }
}
