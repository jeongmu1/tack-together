package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegionDTO implements Serializable {
    @SerializedName("area0")
    private AreaDTO area0;
    @SerializedName("area1")
    private AreaDTO area1;
    @SerializedName("area2")
    private AreaDTO area2;
    @SerializedName("area3")
    private AreaDTO area3;
    @SerializedName("area4")
    private AreaDTO area4;

    @Override
    public String toString() {
        return "RegionDTO{" +
                "area0=" + area0 +
                ", area1=" + area1 +
                ", area2=" + area2 +
                ", area3=" + area3 +
                ", area4=" + area4 +
                '}';
    }

    public RegionDTO() {
    }

    public AreaDTO getArea0() {
        return area0;
    }

    public void setArea0(AreaDTO area0) {
        this.area0 = area0;
    }

    public AreaDTO getArea1() {
        return area1;
    }

    public void setArea1(AreaDTO area1) {
        this.area1 = area1;
    }

    public AreaDTO getArea2() {
        return area2;
    }

    public void setArea2(AreaDTO area2) {
        this.area2 = area2;
    }

    public AreaDTO getArea3() {
        return area3;
    }

    public void setArea3(AreaDTO area3) {
        this.area3 = area3;
    }

    public AreaDTO getArea4() {
        return area4;
    }

    public void setArea4(AreaDTO area4) {
        this.area4 = area4;
    }
}
