package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HistorySummaryListDTO implements Serializable {

    @SerializedName("historySummaryDTOS")
    private List<HistorySummaryDTO> historySummaryDTOS;

    @Override
    public String toString() {
        return "HistorySummaryListDTO{" +
                "historySummaryDTOS=" + historySummaryDTOS +
                '}';
    }
}
