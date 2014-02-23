/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Helpers used to close any {@link Closeable} interfaces
 * 
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public final class CloseableUtils {

    /* This class is never initialized */
    private CloseableUtils() {
    }

    /**
     * Close the {@link Closeable} ignoring any {@link IOException}
     * 
     * @param closeable The {@link Closeable} to close
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                closeable = null;
            } catch (final IOException ignored) {
                // Nothing to do
            }
        }
    }

}
