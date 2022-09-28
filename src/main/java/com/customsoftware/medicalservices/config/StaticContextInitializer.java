package com.customsoftware.medicalservices.config;

import com.customsoftware.medicalservices.web.rest.util.HeaderUtil;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticContextInitializer {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public StaticContextInitializer() {}

    @PostConstruct
    public void init() {
        HeaderUtil.setApplicationName(this.applicationName);
    }
}
