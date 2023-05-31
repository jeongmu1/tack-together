package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoordsDTO implements Serializable {
    @SerializedName("center")
    private CenterDTO center;

    @Override
    public String toString() {
        return "CoordsDTO{" +
                "center=" + center +
                '}';
    }

    public CoordsDTO() {
    }

    public CenterDTO getCenter() {
        return center;
    }

    public void setCenter(CenterDTO center) {
        this.center = center;
    }
}
