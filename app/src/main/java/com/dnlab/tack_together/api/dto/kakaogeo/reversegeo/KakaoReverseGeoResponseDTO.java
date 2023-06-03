package com.dnlab.tack_together.api.dto.kakaogeo.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KakaoReverseGeoResponseDTO implements Serializable {
    @SerializedName("meta")
    private ReverseGeoMetaDTO meta;
    @SerializedName("documents")
    private List<ReverseGeoDocumentDTO> documents;

    @Override
    public String toString() {
        return "KakaoReverseGeocodingResponseDTO{" +
                "meta=" + meta +
                ", documents=" + documents +
                '}';
    }

    public KakaoReverseGeoResponseDTO() {
    }

    public ReverseGeoMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(ReverseGeoMetaDTO meta) {
        this.meta = meta;
    }

    public List<ReverseGeoDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ReverseGeoDocumentDTO> documents) {
        this.documents = documents;
    }
}
