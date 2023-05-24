package com.dnlab.tack_together.api.dto.request;


import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("username")
    private final String username;
    @SerializedName("password")
    private final String password;

    public RequestLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
