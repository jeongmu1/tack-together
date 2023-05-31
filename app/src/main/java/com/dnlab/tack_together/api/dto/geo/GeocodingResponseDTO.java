package com.dnlab.tack_together.api.dto.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GeocodingResponseDTO implements Serializable {
    @SerializedName("status")
    private String status;
    @SerializedName("meta")
    private MetaDTO meta;
    @SerializedName("address")
    private List<AddressDTO> addresses;
    @SerializedName("errorMessage")
    private String errorMessage;

    public GeocodingResponseDTO() {
    }

    @Override
    public String toString() {
        return "GeocodingResponseDTO{" +
                "status='" + status + '\'' +
                ", meta=" + meta +
                ", addresses=" + addresses +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
