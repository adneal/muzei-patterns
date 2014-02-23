/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.app.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.seeingpixels.muzei.patterns.app.PatternsRemoteArtSource;
import org.seeingpixels.muzei.patterns.app.fragment.PrefsFragment;

/**
 * An {@link Activity} used to manage the {@link PrefsFragment} for the app
 * 
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public class PreferenceActivity extends Activity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Load the preferences
        if (savedInstanceState == null) {
            final FragmentTransaction fOps = getFragmentManager().beginTransaction();
            fOps.replace(android.R.id.content, new PrefsFragment()).commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        // Schedule a new update
        final Intent muzei = new Intent(this, PatternsRemoteArtSource.class);
        muzei.setAction(PatternsRemoteArtSource.ACTION_UPDATE);
        startService(muzei);
        super.onDestroy();
    }

}
