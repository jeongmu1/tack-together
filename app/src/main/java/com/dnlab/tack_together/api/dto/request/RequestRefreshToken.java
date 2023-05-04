package com.dnlab.tack_together.api.dto.request;

import com.google.gson.annotations.SerializedName;

public class RequestRefreshToken {
    @SerializedName("refreshToken")
    private final String refreshToken;

    public RequestRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
