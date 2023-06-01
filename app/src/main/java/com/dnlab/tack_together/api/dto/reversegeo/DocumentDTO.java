package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentDTO implements Serializable {
    @SerializedName("road_address")
    private RoadAddressDTO roadAddress;
    @SerializedName("address")
    private AddressDTO address;

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "roadAddress=" + roadAddress +
                ", address=" + address +
                '}';
    }

    public DocumentDTO() {
    }

    public RoadAddressDTO getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(RoadAddressDTO roadAddress) {
        this.roadAddress = roadAddress;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
