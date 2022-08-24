package com.customsoftware.medicalservices.web.rest.errors;

public class DniAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public DniAlreadyUsedException() {
        super(ErrorConstants.DNI_ALREADY_USED_TYPE, "Dni name already used!", "userManagement", "dniExists");
    }
}
