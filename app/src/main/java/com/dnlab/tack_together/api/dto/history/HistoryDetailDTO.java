package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryDetailDTO implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("origin")
    private String origin;
    @SerializedName("waypoints")
    private String waypoints;
    @SerializedName("destination")
    private String destination;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("distance")
    private int distance;
    @SerializedName("dropOffTime")
    private long dropOffTime;
    @SerializedName("matchEndTime")
    private long matchEndTime;
    @SerializedName("opponentMember")
    private String opponentMember;
    @SerializedName("paymentAmount")
    private int paymentAmount;

    @Override
    public String toString() {
        return "HistoryDetailDTO{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", waypoints='" + waypoints + '\'' +
                ", destination='" + destination + '\'' +
                ", createTime=" + createTime +
                ", distance=" + distance +
                ", dropOffTime=" + dropOffTime +
                ", matchEndTime=" + matchEndTime +
                ", opponentMember='" + opponentMember + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }

    public HistoryDetailDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(String waypoints) {
        this.waypoints = waypoints;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public long getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(long dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public long getMatchEndTime() {
        return matchEndTime;
    }

    public void setMatchEndTime(long matchEndTime) {
        this.matchEndTime = matchEndTime;
    }

    public String getOpponentMember() {
        return opponentMember;
    }

    public void setOpponentMember(String opponentMember) {
        this.opponentMember = opponentMember;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
