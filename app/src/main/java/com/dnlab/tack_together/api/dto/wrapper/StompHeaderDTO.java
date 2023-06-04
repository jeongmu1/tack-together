package com.dnlab.tack_together.api.dto.wrapper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StompHeaderDTO implements Serializable {
    @SerializedName("event-type")
    private String eventType;
    @SerializedName("id")
    private String id;
    @SerializedName("timestamp")
    private Long timestamp;

    @Override
    public String toString() {
        return "StompHeaderDTO{" +
                "eventType='" + eventType + '\'' +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public StompHeaderDTO() {
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
