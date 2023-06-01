package com.dnlab.tack_together.api.dto.reversegeo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KakaoReverseGeocodingResponseDTO implements Serializable {
    @SerializedName("meta")
    private MetaDTO meta;
    @SerializedName("documents")
    private List<DocumentDTO> documents;

    @Override
    public String toString() {
        return "KakaoReverseGeocodingResponseDTO{" +
                "meta=" + meta +
                ", documents=" + documents +
                '}';
    }

    public KakaoReverseGeocodingResponseDTO() {
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }
}
