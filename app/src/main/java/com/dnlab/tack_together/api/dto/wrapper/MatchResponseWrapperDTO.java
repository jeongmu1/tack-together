package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.match.MatchResponseDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResponseWrapperDTO implements Serializable {
    @SerializedName("payload")
    private MatchResponseDTO matchResponseDTO;

    @SerializedName("headers")
    private StompHeaderDTO headers;

    @Override
    public String toString() {
        return "MatchResponseWrapperDTO{" +
                "matchResponseDTO=" + matchResponseDTO +
                ", headers=" + headers +
                '}';
    }

    public MatchResponseWrapperDTO() {
    }

    public StompHeaderDTO getHeaders() {
        return headers;
    }

    public void setHeaders(StompHeaderDTO headers) {
        this.headers = headers;
    }

    public MatchResponseDTO getMatchResponseDTO() {
        return matchResponseDTO;
    }

    public void setMatchResponseDTO(MatchResponseDTO matchResponseDTO) {
        this.matchResponseDTO = matchResponseDTO;
    }
}
