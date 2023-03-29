package com.dnlab.tack_together.api.dto.request;


import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestLogin {
    @SerializedName("username")
    private final String username;
    @SerializedName("name")
    private final String name;

    @Builder
    public RequestLogin(String username, String name) {
        this.username = username;
        this.name = name;
    }
}
