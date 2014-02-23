/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.app;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

import org.json.JSONException;
import org.seeingpixels.muzei.patterns.Config;
import org.seeingpixels.muzei.patterns.R;
import org.seeingpixels.muzei.patterns.model.Pattern;
import org.seeingpixels.muzei.patterns.util.CloseableUtils;
import org.seeingpixels.muzei.patterns.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public class PatternsRemoteArtSource extends RemoteMuzeiArtSource {

    /** The name of the worker {@link Thread} */
    private static final String SIMPLE_DESKTOPS = "PatternsRemoteArtSource";

    /** Indicates to schedule an immediate update */
    public static final String ACTION_UPDATE = "patterns.muzei.intent.action.UPDATE";

    /**
     * Constructor for <code>PatternsRemoteArtSource</code>
     */
    public PatternsRemoteArtSource() {
        super(SIMPLE_DESKTOPS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();
        setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        if (intent != null && intent.getAction().equals(ACTION_UPDATE)) {
            scheduleNext();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onTryUpdate(int reason) throws RetryException {
        // The Pattern to download
        Pattern pattern = null;

        // Parse the SimpleDesktops API
        InputStream in = null;
        try {
            final URL url = new URL(Config.API);
            in = new BufferedInputStream(url.openStream());
            pattern = new Pattern(IOUtils.toString(in));
        } catch (final IOException ignored) {
            // Nothing to do
        } catch (final JSONException ignored) {
            // Nothing to do
        } finally {
            CloseableUtils.closeQuietly(in);
        }

        if (pattern == null) {
            throw new RetryException();
        }

        // Check for a current token
        final Artwork artwork = getCurrentArtwork();
        final String token = artwork != null ? artwork.getToken() : pattern.token;

        // Display the wallpaper in Muzei
        publishArtwork(new Artwork.Builder()
                .imageUri(Uri.parse(pattern.img))
                .title(pattern.title)
                .byline(pattern.user)
                .token(token)
                .viewIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(pattern.permalink)))
                .build());

        // Schedule another update
        scheduleNext();
    }

    /** Schedules a new update based on user preference */
    private void scheduleNext() {
        final int updateInterval = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
                this).getString(getString(R.string.pref_update_interval_key),
                getString(R.string.pref_update_interval_default)));
        if (updateInterval > 0) {
            scheduleUpdate(System.currentTimeMillis() + 60 * updateInterval * 60 * 1000);
        }
    }

}
