package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.matched.DropOffNotificationDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DropOffNotificationWrapperDTO implements Serializable {
    @SerializedName("payload")
    private DropOffNotificationDTO payload;
    @SerializedName("headers")
    private StompHeaderDTO headers;

    @Override
    public String toString() {
        return "DropOffNotificationWrapperDTO{" +
                "payload=" + payload +
                ", headers=" + headers +
                '}';
    }

    public DropOffNotificationWrapperDTO() {
    }

    public DropOffNotificationDTO getPayload() {
        return payload;
    }

    public void setPayload(DropOffNotificationDTO payload) {
        this.payload = payload;
    }

    public StompHeaderDTO getHeaders() {
        return headers;
    }

    public void setHeaders(StompHeaderDTO headers) {
        this.headers = headers;
    }
}
