package com.customsoftware.medicalservices.service.dto.search;

import java.io.Serializable;
import java.util.List;

public class SearchUserDTO implements Serializable {

    private String query;
    private List<String> roles;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
