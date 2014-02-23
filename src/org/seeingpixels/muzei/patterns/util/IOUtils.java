/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Helpers for working with I/O streams
 * 
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public final class IOUtils {

    /* This class is never initialized */
    private IOUtils() {
    }

    /**
     * Reads the contents of an {@link InputStream} to a {@link String}
     * 
     * @param in The {@link InputStream} to read from
     * @return The contents of the {@link InputStream} as a {@link String}
     * @throws IOException
     */
    public static String toString(InputStream in) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        final StringBuilder builder = new StringBuilder(in.available());
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

}
