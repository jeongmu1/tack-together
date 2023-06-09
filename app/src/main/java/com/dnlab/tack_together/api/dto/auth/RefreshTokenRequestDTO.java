package com.dnlab.tack_together.api.dto.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefreshTokenRequestDTO implements Serializable {
    @SerializedName("refreshToken")
    private String refreshToken;

    public RefreshTokenRequestDTO() {
    }

    public RefreshTokenRequestDTO(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
