package com.customsoftware.medicalservices.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractResource {

    protected final Logger log;
    protected final String entityName;

    public AbstractResource(Class clas, String entityName) {
        this.log = LoggerFactory.getLogger(clas);
        this.entityName = entityName;
    }
}
