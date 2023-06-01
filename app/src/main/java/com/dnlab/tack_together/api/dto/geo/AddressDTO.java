package com.dnlab.tack_together.api.dto.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressDTO implements Serializable {
    @SerializedName("roadAddress")
    private String roadAddress;
    @SerializedName("jibunAddress")
    private String jibunAddress;
    @SerializedName("englishAddress")
    private String englishAddress;
    @SerializedName("addressElements")
    private List<AddressElementDTO> addressElements;
    @SerializedName("x")
    private String x;
    @SerializedName("y")
    private String y;
    @SerializedName("distance")
    private double distance;

    @Override
    public String toString() {
        return "AddressDTO{" +
                "roadAddress='" + roadAddress + '\'' +
                ", jibunAddress='" + jibunAddress + '\'' +
                ", englishAddress='" + englishAddress + '\'' +
                ", addressElements=" + addressElements +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", distance=" + distance +
                '}';
    }

    public AddressDTO() {
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public void setJibunAddress(String jibunAddress) {
        this.jibunAddress = jibunAddress;
    }

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }

    public List<AddressElementDTO> getAddressElements() {
        return addressElements;
    }

    public void setAddressElements(List<AddressElementDTO> addressElements) {
        this.addressElements = addressElements;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
