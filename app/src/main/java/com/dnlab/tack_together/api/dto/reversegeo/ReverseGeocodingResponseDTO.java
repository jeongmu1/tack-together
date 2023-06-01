package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReverseGeocodingResponseDTO implements Serializable {
    @SerializedName("status")
    private StatusDTO status;
    @SerializedName("results")
    private List<ResultDTO> results;

    @Override
    public String toString() {
        return "ReverseGeocodingResponseDTO{" +
                "status=" + status +
                ", results=" + results +
                '}';
    }

    public ReverseGeocodingResponseDTO() {
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public List<ResultDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultDTO> results) {
        this.results = results;
    }
}