package com.dnlab.tack_together.api.dto.response;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("username")
    private final String username;
    @SerializedName("accessToken")
    private final String accessToken;
    @SerializedName("refreshToken")
    private final String refreshToken;

    public ResponseLogin(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
