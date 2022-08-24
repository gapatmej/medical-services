package com.customsoftware.medicalservices.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String DOCTOR = "ROLE_DOCTOR";

    public static final String PATIENT = "ROLE_PATIENT";

    private AuthoritiesConstants() {}
}
