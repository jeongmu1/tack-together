package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationDTO implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;

    public LocationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
