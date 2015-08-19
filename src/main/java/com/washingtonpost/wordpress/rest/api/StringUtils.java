package com.washingtonpost.wordpress.rest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 * Hand-jammed utility to avoid bringing in unnecessary dependencies to our wrapping classes</p>
 */
final class StringUtils {
    private static final int BUFFER_SIZE = 8192;

    private StringUtils() {
        // shutup, checkstyle
    }

    static String inputStreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder(BUFFER_SIZE);
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return sb.toString();
    }

}
