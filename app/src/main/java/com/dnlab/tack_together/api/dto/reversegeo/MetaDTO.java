package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaDTO implements Serializable {
    @SerializedName("total_count")
    private int totalCount;

    @Override
    public String toString() {
        return "MetaDTO{" +
                "totalCount=" + totalCount +
                '}';
    }

    public MetaDTO() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
