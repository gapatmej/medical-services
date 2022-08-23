/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.rubrica.validaciones;

import java.io.File;

/**
 *
 * @author mfernandez
 */
public class Extensiones {

    public static final String ODS = "ods";
    public static final String ODT = "odt";
    public static final String PDF = "pdf";
    public static final String TXT = "txt";
    public static final String XLS = "xls";
    public static final String XML = "xml";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
