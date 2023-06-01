package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaDTO implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("coords")
    private CoordsDTO coords;

    @Override
    public String toString() {
        return "AreaDTO{" +
                "name='" + name + '\'' +
                ", coords=" + coords +
                '}';
    }

    public AreaDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordsDTO getCoords() {
        return coords;
    }

    public void setCoords(CoordsDTO coords) {
        this.coords = coords;
    }
}
