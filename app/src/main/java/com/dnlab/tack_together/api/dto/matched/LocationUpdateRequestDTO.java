package com.dnlab.tack_together.api.dto.matched;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationUpdateRequestDTO implements Serializable {
    @SerializedName("location")
    private String location;
    @SerializedName("departureAgreed")
    private boolean departureAgreed;
    @SerializedName("sessionId")
    private String sessionId;

    public LocationUpdateRequestDTO() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDepartureAgreed() {
        return departureAgreed;
    }

    public void setDepartureAgreed(boolean departureAgreed) {
        this.departureAgreed = departureAgreed;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
