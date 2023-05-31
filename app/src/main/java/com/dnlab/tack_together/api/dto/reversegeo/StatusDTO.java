package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusDTO implements Serializable {
    @SerializedName("code")
    private int code;
    @SerializedName("name")
    private String name;
    @SerializedName("message")
    private String message;

    @Override
    public String toString() {
        return "StatusDTO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public StatusDTO() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
