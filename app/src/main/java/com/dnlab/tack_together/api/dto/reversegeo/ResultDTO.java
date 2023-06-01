package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultDTO implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private CodeDTO code;
    @SerializedName("region")
    private RegionDTO region;

    @Override
    public String toString() {
        return "ResultDTO{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", region=" + region +
                '}';
    }

    public ResultDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CodeDTO getCode() {
        return code;
    }

    public void setCode(CodeDTO code) {
        this.code = code;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }
}
