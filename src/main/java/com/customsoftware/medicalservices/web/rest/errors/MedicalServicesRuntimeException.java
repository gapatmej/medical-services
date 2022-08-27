package com.customsoftware.medicalservices.web.rest.errors;

public class MedicalServicesRuntimeException extends RuntimeException {

    private String keyMessage = "";

    public MedicalServicesRuntimeException(String message) {
        super(message);
    }

    public MedicalServicesRuntimeException(String message, String keyMessage) {
        super(message);
        this.keyMessage = keyMessage;
    }

    public MedicalServicesRuntimeException(String message, String keyMessage, Throwable cause) {
        super(message, cause);
        this.keyMessage = keyMessage;
    }

    public String getKeyMessage() {
        return keyMessage;
    }
}
