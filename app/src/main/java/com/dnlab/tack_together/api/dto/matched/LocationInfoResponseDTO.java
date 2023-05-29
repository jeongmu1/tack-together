package com.dnlab.tack_together.api.dto.matched;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationInfoResponseDTO implements Serializable {
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("username")
    private String username;
    @SerializedName("location")
    private String location;
    @SerializedName("departureAgreed")
    private boolean departureAgreed;
    @SerializedName("ridingStarted")
    private boolean ridingStarted;

    public LocationInfoResponseDTO() {
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

    public boolean isRidingStarted() {
        return ridingStarted;
    }

    public void setRidingStarted(boolean ridingStarted) {
        this.ridingStarted = ridingStarted;
    }
}
