package com.dnlab.tack_together.api.dto.settlement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RouteInfoDTO implements Serializable {
    @SerializedName("origin")
    private String origin;
    @SerializedName("waypoint")
    private String waypoint;
    @SerializedName("destination")
    private String destination;

    @Override
    public String toString() {
        return "RouteInfoDTO{" +
                "origin='" + origin + '\'' +
                ", waypoint='" + waypoint + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public RouteInfoDTO(String origin, String waypoint, String destination) {
        this.origin = origin;
        this.waypoint = waypoint;
        this.destination = destination;
    }

    public RouteInfoDTO() {
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
}
