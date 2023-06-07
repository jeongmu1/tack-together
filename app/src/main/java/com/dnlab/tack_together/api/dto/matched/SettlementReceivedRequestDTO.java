package com.dnlab.tack_together.api.dto.matched;

import com.dnlab.tack_together.api.dto.settlement.RouteInfoDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettlementReceivedRequestDTO implements Serializable {
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("username")
    private String username;
    @SerializedName("requestedFare")
    private int requestedFare;
    @SerializedName("totalFare")
    private int totalFare;
    @SerializedName("accountInfo")
    private String accountInfo;
    @SerializedName("waypointRate")
    private double waypointRate;
    @SerializedName("destinationRate")
    private double destinationRate;
    @SerializedName("routeInfo")
    private RouteInfoDTO routeInfo;

    @Override
    public String toString() {
        return "SettlementReceivedRequestDTO{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", requestedFare=" + requestedFare +
                ", totalFare=" + totalFare +
                ", accountInfo='" + accountInfo + '\'' +
                ", waypointRate=" + waypointRate +
                ", destinationRate=" + destinationRate +
                ", routeInfo=" + routeInfo +
                '}';
    }

    public SettlementReceivedRequestDTO() {
    }

    public RouteInfoDTO getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfoDTO routeInfo) {
        this.routeInfo = routeInfo;
    }

    public double getDestinationRate() {
        return destinationRate;
    }

    public void setDestinationRate(double destinationRate) {
        this.destinationRate = destinationRate;
    }

    public double getWaypointRate() {
        return waypointRate;
    }

    public void setWaypointRate(double waypointRate) {
        this.waypointRate = waypointRate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRequestedFare() {
        return requestedFare;
    }

    public void setRequestedFare(int requestedFare) {
        this.requestedFare = requestedFare;
    }

    public int getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
}
