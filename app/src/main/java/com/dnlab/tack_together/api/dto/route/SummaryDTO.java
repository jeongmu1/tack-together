package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SummaryDTO implements Serializable {
    @SerializedName("origin")
    private LocationDTO origin;
    @SerializedName("destination")
    private LocationDTO destination;
    @SerializedName("waypoints")
    private List<LocationDTO> waypoints;
    @SerializedName("priority")
    private String priority;
    @SerializedName("bound")
    private BoundDTO bound;
    @SerializedName("fare")
    private FareDTO fare;
    @SerializedName("distance")
    private int distance;
    @SerializedName("duration")
    private int duration;

    public SummaryDTO() {
    }

    public LocationDTO getOrigin() {
        return origin;
    }

    public void setOrigin(LocationDTO origin) {
        this.origin = origin;
    }

    public LocationDTO getDestination() {
        return destination;
    }

    public void setDestination(LocationDTO destination) {
        this.destination = destination;
    }

    public List<LocationDTO> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<LocationDTO> waypoints) {
        this.waypoints = waypoints;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public BoundDTO getBound() {
        return bound;
    }

    public void setBound(BoundDTO bound) {
        this.bound = bound;
    }

    public FareDTO getFare() {
        return fare;
    }

    public void setFare(FareDTO fare) {
        this.fare = fare;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
