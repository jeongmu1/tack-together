package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SectionDTO implements Serializable {
    @SerializedName("distance")
    private int distance;
    @SerializedName("duration")
    private int duration;
    @SerializedName("bound")
    private BoundDTO bound;
    @SerializedName("roads")
    private List<RoadDTO> roads;
    @SerializedName("guides")
    private List<GuideDTO> guides;

    public SectionDTO() {
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

    public BoundDTO getBound() {
        return bound;
    }

    public void setBound(BoundDTO bound) {
        this.bound = bound;
    }

    public List<RoadDTO> getRoads() {
        return roads;
    }

    public void setRoads(List<RoadDTO> roads) {
        this.roads = roads;
    }

    public List<GuideDTO> getGuides() {
        return guides;
    }

    public void setGuides(List<GuideDTO> guides) {
        this.guides = guides;
    }
}
