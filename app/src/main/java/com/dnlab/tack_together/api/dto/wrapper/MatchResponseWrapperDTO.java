package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.match.MatchResponseDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchResponseWrapperDTO implements Serializable {
    @SerializedName("payload")
    private MatchResponseDTO matchResponseDTO;

    public MatchResponseWrapperDTO() {
    }

    public MatchResponseDTO getMatchResponseDTO() {
        return matchResponseDTO;
    }

    public void setMatchResponseDTO(MatchResponseDTO matchResponseDTO) {
        this.matchResponseDTO = matchResponseDTO;
    }
}
