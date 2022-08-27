package com.customsoftware.medicalservices.security;

public class AccessConstants {

    public static final String ADMIN = "hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")";
    public static final String DOCTOR = "hasAuthority(\"" + AuthoritiesConstants.DOCTOR + "\")";
    public static final String PATIENT = "hasAuthority(\"" + AuthoritiesConstants.PATIENT + "\")";

    public static final String ADMIN_DOCTOR =
        "hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\", " + "\"" + AuthoritiesConstants.DOCTOR + "\")";
    public static final String ADMIN_PATIENT =
        "hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\", " + "\"" + AuthoritiesConstants.PATIENT + "\")";
    public static final String DOCTOR_PATIENT =
        "hasAnyAuthority(\"" + AuthoritiesConstants.DOCTOR + "\", " + "\"" + AuthoritiesConstants.PATIENT + "\")";
    public static final String ADMIN_DOCTOR_PATIENT =
        "hasAnyAuthority(\"" +
        AuthoritiesConstants.ADMIN +
        "\", " +
        "\"" +
        AuthoritiesConstants.DOCTOR +
        "\", " +
        "\"" +
        AuthoritiesConstants.PATIENT +
        "\" )";
}
