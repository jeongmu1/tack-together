package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CenterDTO implements Serializable {
    @SerializedName("crs")
    private String crs;
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;

    @Override
    public String toString() {
        return "CenterDTO{" +
                "crs='" + crs + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public CenterDTO() {
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
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
