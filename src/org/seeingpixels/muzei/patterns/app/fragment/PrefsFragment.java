/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.seeingpixels.muzei.patterns.R;

/**
 * The {@link PreferenceFragment} used to handle the app's settings
 * 
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public class PrefsFragment extends PreferenceFragment {

    /**
     * Empty constructor as per the {@link Fragment} docs
     */
    public PrefsFragment() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add the preferences
        addPreferencesFromResource(R.xml.preferences);
    }

}
