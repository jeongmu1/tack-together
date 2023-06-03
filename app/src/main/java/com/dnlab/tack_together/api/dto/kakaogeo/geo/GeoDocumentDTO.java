package com.dnlab.tack_together.api.dto.kakaogeo.geo;

import com.google.gson.annotations.SerializedName;

public class GeoDocumentDTO {
    @SerializedName("address_name")
    private String addressName;

    @SerializedName("y")
    private String latitude;

    @SerializedName("x")
    private String longitude;

    @SerializedName("address_type")
    private String addressType;

    @SerializedName("address")
    private GeoAddressDTO address;

    @SerializedName("road_address")
    private GeoRoadAddressDTO roadAddress;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public GeoAddressDTO getAddress() {
        return address;
    }

    public void setAddress(GeoAddressDTO address) {
        this.address = address;
    }

    public GeoRoadAddressDTO getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(GeoRoadAddressDTO roadAddress) {
        this.roadAddress = roadAddress;
    }
}
