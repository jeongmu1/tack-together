package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.match.MatchResultInfoDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResultInfoWrapperDTO implements Serializable {
    @SerializedName("payload")
    private MatchResultInfoDTO payload;

    public MatchResultInfoWrapperDTO() {
    }

    @Override
    public String toString() {
        return "MatchResultInfoWrapperDTO{" +
                "payload=" + payload +
                '}';
    }

    public MatchResultInfoDTO getPayload() {
        return payload;
    }

    public void setPayload(MatchResultInfoDTO payload) {
        this.payload = payload;
    }
}
