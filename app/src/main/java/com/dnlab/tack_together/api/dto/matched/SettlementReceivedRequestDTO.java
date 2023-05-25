package com.dnlab.tack_together.api.dto.matched;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettlementReceivedRequestDTO implements Serializable {
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("username")
    private String username;
    @SerializedName("requestedFare")
    private int requestedFare;
    @SerializedName("totalFare")
    private int totalFare;
    @SerializedName("accountInfo")
    private String accountInfo;

    public SettlementReceivedRequestDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRequestedFare() {
        return requestedFare;
    }

    public void setRequestedFare(int requestedFare) {
        this.requestedFare = requestedFare;
    }

    public int getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
}
