package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryDetailDTO implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private long date;
    @SerializedName("origin")
    private String origin;
    @SerializedName("waypoints")
    private String waypoints;
    @SerializedName("destination")
    private String destination;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("dropOffTime")
    private long dropOffTime;
    @SerializedName("opponentMember")
    private String opponentMember;
    @SerializedName("paymentAmount")
    private int paymentAmount;

    @Override
    public String toString() {
        return "HistoryDetailDTO{" +
                "id=" + id +
                ", date=" + date +
                ", origin='" + origin + '\'' +
                ", waypoints='" + waypoints + '\'' +
                ", destination='" + destination + '\'' +
                ", createTime=" + createTime +
                ", dropOffTime=" + dropOffTime +
                ", opponentMember='" + opponentMember + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }

    public HistoryDetailDTO(long id, long date, String origin, String waypoints, String destination, long createTime, long dropOffTime, String opponentMember, int paymentAmount) {
        this.id = id;
        this.date = date;
        this.origin = origin;
        this.waypoints = waypoints;
        this.destination = destination;
        this.createTime = createTime;
        this.dropOffTime = dropOffTime;
        this.opponentMember = opponentMember;
        this.paymentAmount = paymentAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public long getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(long dropOffTime) {
        this.dropOffTime = dropOffTime;
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
