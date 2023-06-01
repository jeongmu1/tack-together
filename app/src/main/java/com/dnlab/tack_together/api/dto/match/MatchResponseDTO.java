package com.dnlab.tack_together.api.dto.match;

import com.dnlab.tack_together.api.dto.common.MatchDecisionStatus;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResponseDTO implements Serializable {
    @SerializedName("matchDecisionStatus")
    private MatchDecisionStatus matchDecisionStatus;
    @SerializedName("matchSessionId")
    private String matchSessionId;

    @Override
    public String toString() {
        return "MatchResponseDTO{" +
                "matchDecisionStatus=" + matchDecisionStatus +
                ", matchSessionId='" + matchSessionId + '\'' +
                '}';
    }

    public MatchResponseDTO() {
    }

    public MatchDecisionStatus getMatchDecisionStatus() {
        return matchDecisionStatus;
    }

    public void setMatchDecisionStatus(MatchDecisionStatus matchDecisionStatus) {
        this.matchDecisionStatus = matchDecisionStatus;
    }

    public String getMatchSessionId() {
        return matchSessionId;
    }

    public void setMatchSessionId(String matchSessionId) {
        this.matchSessionId = matchSessionId;
    }
}
