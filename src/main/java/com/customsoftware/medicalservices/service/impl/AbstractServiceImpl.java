package com.customsoftware.medicalservices.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractServiceImpl {

    protected final Logger log;

    public AbstractServiceImpl(Class c) {
        this.log = LoggerFactory.getLogger(c);
    }
}
