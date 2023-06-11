package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HistorySummaryListDTO implements Serializable {

    @SerializedName("historySummaries")
    private List<HistorySummaryDTO> historySummaryDTOList;

    public HistorySummaryListDTO() {
    }

    @Override
    public String toString() {
        return "HistorySummaryListDTO{" +
                "historySummaryDTOList=" + historySummaryDTOList +
                '}';
    }

    public List<HistorySummaryDTO> getHistorySummaryDTOList() {
        return historySummaryDTOList;
    }

    public void setHistorySummaryDTOList(List<HistorySummaryDTO> historySummaryDTOList) {
        this.historySummaryDTOList = historySummaryDTOList;
    }
}
