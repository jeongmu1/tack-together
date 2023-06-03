package com.dnlab.tack_together.api.dto.kakaogeo.geo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KakaoGeoResponseDTO {
    @SerializedName("meta")
    private GeoMetaDTO meta;

    @SerializedName("documents")
    private List<GeoDocumentDTO> documents;

    public GeoMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(GeoMetaDTO meta) {
        this.meta = meta;
    }

    public List<GeoDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<GeoDocumentDTO> documents) {
        this.documents = documents;
    }
}
