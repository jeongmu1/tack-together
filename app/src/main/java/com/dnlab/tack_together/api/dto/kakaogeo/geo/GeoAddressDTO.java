package com.dnlab.tack_together.api.dto.kakaogeo.geo;

import com.google.gson.annotations.SerializedName;

public class GeoAddressDTO {
    @SerializedName("address_name")
    private String addressName;

    @SerializedName("region_1depth_name")
    private String regionDepth1Name;

    @SerializedName("region_2depth_name")
    private String regionDepth2Name;

    @SerializedName("region_3depth_name")
    private String regionDepth3Name;

    @SerializedName("region_3depth_h_name")
    private String regionDepth3HName;

    @SerializedName("h_code")
    private String hCode;

    @SerializedName("b_code")
    private String bCode;

    @SerializedName("mountain_yn")
    private String mountainYN;

    @SerializedName("main_address_no")
    private String mainAddressNo;

    @SerializedName("sub_address_no")
    private String subAddressNo;

    @SerializedName("x")
    private String longitude;

    @SerializedName("y")
    private String latitude;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getRegionDepth1Name() {
        return regionDepth1Name;
    }

    public void setRegionDepth1Name(String regionDepth1Name) {
        this.regionDepth1Name = regionDepth1Name;
    }

    public String getRegionDepth2Name() {
        return regionDepth2Name;
    }

    public void setRegionDepth2Name(String regionDepth2Name) {
        this.regionDepth2Name = regionDepth2Name;
    }

    public String getRegionDepth3Name() {
        return regionDepth3Name;
    }

    public void setRegionDepth3Name(String regionDepth3Name) {
        this.regionDepth3Name = regionDepth3Name;
    }

    public String getRegionDepth3HName() {
        return regionDepth3HName;
    }

    public void setRegionDepth3HName(String regionDepth3HName) {
        this.regionDepth3HName = regionDepth3HName;
    }

    public String gethCode() {
        return hCode;
    }

    public void sethCode(String hCode) {
        this.hCode = hCode;
    }

    public String getbCode() {
        return bCode;
    }

    public void setbCode(String bCode) {
        this.bCode = bCode;
    }

    public String getMountainYN() {
        return mountainYN;
    }

    public void setMountainYN(String mountainYN) {
        this.mountainYN = mountainYN;
    }

    public String getMainAddressNo() {
        return mainAddressNo;
    }

    public void setMainAddressNo(String mainAddressNo) {
        this.mainAddressNo = mainAddressNo;
    }

    public String getSubAddressNo() {
        return subAddressNo;
    }

    public void setSubAddressNo(String subAddressNo) {
        this.subAddressNo = subAddressNo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressName='" + addressName + '\'' +
                ", regionDepth1Name='" + regionDepth1Name + '\'' +
                ", regionDepth2Name='" + regionDepth2Name + '\'' +
                ", regionDepth3Name='" + regionDepth3Name + '\'' +
                ", regionDepth3HName='" + regionDepth3HName + '\'' +
                ", hCode='" + hCode + '\'' +
                ", bCode='" + bCode + '\'' +
                ", mountainYN='" + mountainYN + '\'' +
                ", mainAddressNo='" + mainAddressNo + '\'' +
                ", subAddressNo='" + subAddressNo + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
