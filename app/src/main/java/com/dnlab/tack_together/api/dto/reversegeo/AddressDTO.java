package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    @SerializedName("address_name")
    private String addressName;
    @SerializedName("region_1depth_name")
    private String region1DepthName;
    @SerializedName("region_2depth_name")
    private String region2DepthName;
    @SerializedName("region_3depth_name")
    private String region3DepthName;
    @SerializedName("mountain_yn")
    private String mountainYn;
    @SerializedName("main_address_no")
    private String mainAddressNo;
    @SerializedName("sub_address_no")
    private String subAddressNo;

    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressName='" + addressName + '\'' +
                ", region1DepthName='" + region1DepthName + '\'' +
                ", region2DepthName='" + region2DepthName + '\'' +
                ", region3DepthName='" + region3DepthName + '\'' +
                ", mountainYn='" + mountainYn + '\'' +
                ", mainAddressNo='" + mainAddressNo + '\'' +
                ", subAddressNo='" + subAddressNo + '\'' +
                '}';
    }

    public AddressDTO() {
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

    public String getMountainYn() {
        return mountainYn;
    }

    public void setMountainYn(String mountainYn) {
        this.mountainYn = mountainYn;
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
}
