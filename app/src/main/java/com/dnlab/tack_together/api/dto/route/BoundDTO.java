package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BoundDTO implements Serializable {
    @SerializedName("min_x")
    private double minX;
    @SerializedName("min_y")
    private double minY;
    @SerializedName("max_x")
    private double maxX;
    @SerializedName("max_y")
    private double maxY;

    public BoundDTO() {
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
}
