package com.dnlab.tack_together.api.dto.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaDTO implements Serializable {
    @SerializedName("totalCount")
    private int totalCount;
    @SerializedName("page")
    private int page;
    @SerializedName("count")
    private int count;

    @Override
    public String toString() {
        return "MetaDTO{" +
                "totalCount=" + totalCount +
                ", page=" + page +
                ", count=" + count +
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
