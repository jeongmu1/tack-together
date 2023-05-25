package com.dnlab.tack_together.api.dto.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RouteDTO implements Serializable {
    @SerializedName("result_code")
    private int resultCode;
    @SerializedName("result_msg")
    private String resultMsg;
    @SerializedName("summary")
    private SummaryDTO summary;
    @SerializedName("sections")
    private List<SectionDTO> sections;

    public RouteDTO() {
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public SummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(SummaryDTO summary) {
        this.summary = summary;
    }

    public List<SectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
    }
}
