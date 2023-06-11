package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistorySummaryDTO implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("origin")
    private String origin;
    @SerializedName("destination")
    private String destination;
    @SerializedName("waypoints")
    private String waypoints;
    @SerializedName("paymentAmount")
    private int paymentAmount;

    public HistorySummaryDTO() {
    }

    @Override
    public String toString() {
        return "HistorySummaryDTO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", waypoints='" + waypoints + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(String waypoints) {
        this.waypoints = waypoints;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
