package com.dnlab.tack_together.api.dto.matched;

import java.io.Serializable;

public class SettlementRequestDTO implements Serializable {
    private String sessionId;
    private int totalFare;
    private String accountInfo;

    public SettlementRequestDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
