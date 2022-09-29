package com.customsoftware.medicalservices.service.dto;

import java.io.Serializable;

public class InternationalDiseaseDTO implements Serializable {

    private Long id;

    private String cod;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
