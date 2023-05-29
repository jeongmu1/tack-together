package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RoadDTO implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("distance")
    private int distance;
    @SerializedName("duration")
    private int duration;
    @SerializedName("traffic_speed")
    private int trafficSpeed;
    @SerializedName("traffic_state")
    private int trafficState;
    @SerializedName("vertexes")
    private List<Double> vertexes;

    public RoadDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTrafficSpeed() {
        return trafficSpeed;
    }

    public void setTrafficSpeed(int trafficSpeed) {
        this.trafficSpeed = trafficSpeed;
    }

    public int getTrafficState() {
        return trafficState;
    }

    public void setTrafficState(int trafficState) {
        this.trafficState = trafficState;
    }

    public List<Double> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Double> vertexes) {
        this.vertexes = vertexes;
    }
}
