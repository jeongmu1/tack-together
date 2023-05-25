package com.dnlab.tack_together.api.dto.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegistrationRequestDTO implements Serializable {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("nickname")
    private String nickname;

    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(String username, String password, String name, String nickname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }
}
