package com.dnlab.tack_together.api.dto.request;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestRegistration {
    @SerializedName("username")
    private final String username;
    @SerializedName("password")
    private final String password;
    @SerializedName("name")
    private final String name;

    @Builder
    public RequestRegistration(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
