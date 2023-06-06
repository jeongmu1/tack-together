package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HistorySummaryListDTO implements Serializable {

    @SerializedName("historySummaries")
    private List<HistorySummaryDTO> historySummaryListDTO;

    public HistorySummaryListDTO() {
    }

    @Override
    public String toString() {
        return "HistorySummaryListDTO{" +
                "historySummaryDTOS=" + historySummaryListDTO +
                '}';
    }

    public List<HistorySummaryDTO> getHistorySummaryListDTO() {
        return historySummaryListDTO;
    }

    public void setHistorySummaryListDTO(List<HistorySummaryDTO> historySummaryListDTO) {
        this.historySummaryListDTO = historySummaryListDTO;
    }
}
