package com.dnlab.tack_together.api.dto.match;

import com.dnlab.tack_together.api.dto.common.MatchDecisionStatus;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResponseDTO implements Serializable {
    @SerializedName("matchDecisionStatus")
    private MatchDecisionStatus matchDecisionStatus;
    @SerializedName("matchSessionId")
    private String matchSessionId;

}
