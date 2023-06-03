package com.dnlab.tack_together.api.dto.kakaogeo.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReverseGeoDocumentDTO implements Serializable {
    @SerializedName("road_address")
    private ReverseGeoRoadAddressDTO roadAddress;
    @SerializedName("address")
    private ReverseGeoAddressDTO address;

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "roadAddress=" + roadAddress +
                ", address=" + address +
                '}';
    }

    public ReverseGeoDocumentDTO() {
    }

    public ReverseGeoRoadAddressDTO getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(ReverseGeoRoadAddressDTO roadAddress) {
        this.roadAddress = roadAddress;
    }

    public ReverseGeoAddressDTO getAddress() {
        return address;
    }

    public void setAddress(ReverseGeoAddressDTO address) {
        this.address = address;
    }
}
