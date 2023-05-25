package com.dnlab.tack_together.api.dto.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Set;

public class RegistrationResponseDTO implements Serializable {
    @SerializedName("username")
    private final String username;
    @SerializedName("password")
    private final String password;
    @SerializedName("name")
    private final String name;
    @SerializedName("authorities")
    private final Set<String> authorities;

    public RegistrationResponseDTO(String username, String password, String name, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
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

    public Set<String> getAuthorities() {
        return authorities;
    }
}