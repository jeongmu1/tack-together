package com.dnlab.tack_together.api.dto.reversegeo;

import java.io.Serializable;

public class CodeDTO implements Serializable {
    private String id;
    private String type;
    private String mappingId;

    @Override
    public String toString() {
        return "CodeDTO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", mappingId='" + mappingId + '\'' +
                '}';
    }

    public CodeDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }
}
