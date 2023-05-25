package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FareDTO implements Serializable {
    @SerializedName("taxi")
    private int taxi;
    @SerializedName("toll")
    private int toll;

    public FareDTO() {
    }

    public int getTaxi() {
        return taxi;
    }

    public void setTaxi(int taxi) {
        this.taxi = taxi;
    }

    public int getToll() {
        return toll;
    }

    public void setToll(int toll) {
        this.toll = toll;
    }
}
