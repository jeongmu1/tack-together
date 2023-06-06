package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.matched.LocationInfoResponseDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationInfoWrapperDTO implements Serializable {
    @SerializedName("payload")
    private LocationInfoResponseDTO locationInfoResponseDTO;

    @SerializedName("headers")
    private StompHeaderDTO headers;

    @Override
    public String toString() {
        return "LocationInfoWrapperDTO{" +
                "locationInfoResponseDTO=" + locationInfoResponseDTO +
                ", headers=" + headers +
                '}';
    }

    public LocationInfoWrapperDTO() {
    }

    public LocationInfoResponseDTO getLocationInfoResponseDTO() {
        return locationInfoResponseDTO;
    }

    public StompHeaderDTO getHeaders() {
        return headers;
    }

    public void setHeaders(StompHeaderDTO headers) {
        this.headers = headers;
    }

    public void setLocationInfoResponseDTO(LocationInfoResponseDTO locationInfoResponseDTO) {
        this.locationInfoResponseDTO = locationInfoResponseDTO;
    }
}
