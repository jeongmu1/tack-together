package com.dnlab.tack_together.api.dto.wrapper;

import com.dnlab.tack_together.api.dto.matched.LocationInfoResponseDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationInfoWrapperDTO implements Serializable {
    @SerializedName("payload")
    private LocationInfoResponseDTO locationInfoResponseDTO;

    @Override
    public String toString() {
        return "LocationInfoWrapperDTO{" +
                "locationInfoResponseDTO=" + locationInfoResponseDTO +
                '}';
    }

    public LocationInfoWrapperDTO() {
    }

    public LocationInfoResponseDTO getLocationInfoResponseDTO() {
        return locationInfoResponseDTO;
    }

    public void setLocationInfoResponseDTO(LocationInfoResponseDTO locationInfoResponseDTO) {
        this.locationInfoResponseDTO = locationInfoResponseDTO;
    }
}
