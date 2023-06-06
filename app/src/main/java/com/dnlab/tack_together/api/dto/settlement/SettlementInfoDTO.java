package com.dnlab.tack_together.api.dto.settlement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettlementInfoDTO implements Serializable {
    @SerializedName("origin")
    private String origin;
    @SerializedName("waypoint")
    private String waypoint;
    @SerializedName("destination")
    private String destination;
    @SerializedName("totalDistance")
    private int totalDistance;
    @SerializedName("paymentRate")
    private double paymentRate;
    @SerializedName("opponentPaymentRate")
    private double opponentPaymentRate;

    @Override
    public String toString() {
        return "SettlementInfo{" +
                "origin='" + origin + '\'' +
                ", waypoint='" + waypoint + '\'' +
                ", destination='" + destination + '\'' +
                ", totalDistance=" + totalDistance +
                ", paymentRate=" + paymentRate +
                ", opponentPaymentRate=" + opponentPaymentRate +
                '}';
    }

    public SettlementInfoDTO() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(String waypoint) {
        this.waypoint = waypoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    public double getOpponentPaymentRate() {
        return opponentPaymentRate;
    }

    public void setOpponentPaymentRate(double opponentPaymentRate) {
        this.opponentPaymentRate = opponentPaymentRate;
    }
}
