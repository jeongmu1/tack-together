package com.dnlab.tack_together.api.dto.matched;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropOffNotificationDTO implements Serializable {
    @SerializedName("username")
    private String username;
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("dropOffLocation")
    private String dropOffLocation;

    public DropOffNotificationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }
}
