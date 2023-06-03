package com.dnlab.tack_together.api.dto.kakaogeo.geo;

import com.google.gson.annotations.SerializedName;

public class GeoRoadAddressDTO {
    @SerializedName("address_name")
    private String addressName;

    @SerializedName("region_1depth_name")
    private String regionDepth1Name;

    @SerializedName("region_2depth_name")
    private String regionDepth2Name;

    @SerializedName("region_3depth_name")
    private String regionDepth3Name;

    @SerializedName("road_name")
    private String roadName;

    @SerializedName("underground_yn")
    private String undergroundYN;

    @SerializedName("main_building_no")
    private String mainBuildingNo;

    @SerializedName("sub_building_no")
    private String subBuildingNo;

    @SerializedName("building_name")
    private String buildingName;

    @SerializedName("zone_no")
    private String zoneNo;

    @SerializedName("y")
    private String latitude;

    @SerializedName("x")
    private String longitude;

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

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getUndergroundYN() {
        return undergroundYN;
    }

    public void setUndergroundYN(String undergroundYN) {
        this.undergroundYN = undergroundYN;
    }

    public String getMainBuildingNo() {
        return mainBuildingNo;
    }

    public void setMainBuildingNo(String mainBuildingNo) {
        this.mainBuildingNo = mainBuildingNo;
    }

    public String getSubBuildingNo() {
        return subBuildingNo;
    }

    public void setSubBuildingNo(String subBuildingNo) {
        this.subBuildingNo = subBuildingNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
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

    @Override
    public String toString() {
        return "RoadAddress{" +
                "addressName='" + addressName + '\'' +
                ", regionDepth1Name='" + regionDepth1Name + '\'' +
                ", regionDepth2Name='" + regionDepth2Name + '\'' +
                ", regionDepth3Name='" + regionDepth3Name + '\'' +
                ", roadName='" + roadName + '\'' +
                ", undergroundYN='" + undergroundYN + '\'' +
                ", mainBuildingNo='" + mainBuildingNo + '\'' +
                ", subBuildingNo='" + subBuildingNo + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", zoneNo='" + zoneNo + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}

