package com.dnlab.tack_together.api.dto.matched;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropOffRequestDTO implements Serializable {
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("endLocation")
    private String endLocation;

    public DropOffRequestDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
}
