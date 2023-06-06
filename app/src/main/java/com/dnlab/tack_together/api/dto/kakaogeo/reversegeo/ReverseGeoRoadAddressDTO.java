package com.dnlab.tack_together.api.dto.kakaogeo.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReverseGeoRoadAddressDTO implements Serializable {
    @SerializedName("address_name")
    private String addressName;
    @SerializedName("region_1depth_name")
    private String region1DepthName;
    @SerializedName("region_2depth_name")
    private String region2DepthName;
    @SerializedName("region_3depth_name")
    private String region3DepthName;
    @SerializedName("underground_yn")
    private String undergroundYn;
    @SerializedName("main_building_no")
    private String mainBuildingNo;
    @SerializedName("sub_building_no")
    private String subBuildingNo;
    @SerializedName("building_name")
    private String buildingName;
    @SerializedName("zone_no")
    private String zoneNo;

    @Override
    public String toString() {
        return "RoadAddressDTO{" +
                "addressName='" + addressName + '\'' +
                ", region1DepthName='" + region1DepthName + '\'' +
                ", region2DepthName='" + region2DepthName + '\'' +
                ", region3DepthName='" + region3DepthName + '\'' +
                ", undergroundYn='" + undergroundYn + '\'' +
                ", mainBuildingNo='" + mainBuildingNo + '\'' +
                ", subBuildingNo='" + subBuildingNo + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", zoneNo='" + zoneNo + '\'' +
                '}';
    }

    public ReverseGeoRoadAddressDTO() {
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getRegion1DepthName() {
        return region1DepthName;
    }

    public void setRegion1DepthName(String region1DepthName) {
        this.region1DepthName = region1DepthName;
    }

    public String getRegion2DepthName() {
        return region2DepthName;
    }

    public void setRegion2DepthName(String region2DepthName) {
        this.region2DepthName = region2DepthName;
    }

    public String getRegion3DepthName() {
        return region3DepthName;
    }

    public void setRegion3DepthName(String region3DepthName) {
        this.region3DepthName = region3DepthName;
    }

    public String getUndergroundYn() {
        return undergroundYn;
    }

    public void setUndergroundYn(String undergroundYn) {
        this.undergroundYn = undergroundYn;
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
}
