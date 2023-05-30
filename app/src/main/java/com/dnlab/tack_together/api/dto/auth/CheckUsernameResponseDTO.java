package com.dnlab.tack_together.api.dto.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckUsernameResponseDTO implements Serializable {
    @SerializedName("available")
    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
