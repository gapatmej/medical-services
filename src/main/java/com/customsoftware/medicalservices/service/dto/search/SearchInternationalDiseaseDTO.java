package com.customsoftware.medicalservices.service.dto.search;

import java.io.Serializable;

public class SearchInternationalDiseaseDTO implements Serializable {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
