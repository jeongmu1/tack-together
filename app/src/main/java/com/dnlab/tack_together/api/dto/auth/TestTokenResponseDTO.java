package com.dnlab.tack_together.api.dto.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestTokenResponseDTO implements Serializable {
    @SerializedName("authorized")
    private boolean authorized;

    public TestTokenResponseDTO() {
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
}
