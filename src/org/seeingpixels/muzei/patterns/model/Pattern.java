/*
 * Copyright (C) 2014 Andrew Neal
 */

package org.seeingpixels.muzei.patterns.model;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A POD for the wallpaper being downloaded
 * 
 * @author Andrew Neal (andrew@seeingpixels.org)
 */
public class Pattern {

    /** Used to fetch the 1200x800 pattern */
    private static final String IMG_URL = "http://www.colourlovers.com/wallPaper/1200x800/n/ID/COLOURlovers.com-x.png";

    // //////////////////////////////////////////////////////////////////////////
    // JSON objects for the wallpaper
    // //////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String USERNAME = "userName";
    private static final String URL = "url";

    /** The wallpaper id */
    public final String token;
    /** The name of the wallpaper */
    public final String title;
    /** The user who submitted the wallpaper */
    public final String user;
    /** The URL to the wallpaper */
    public final String permalink;
    /** The wallpaper image URL */
    public final String img;

    /**
     * Constructor for <code>Pattern</code>
     * 
     * @param json The JSON used to retrieve each piece of data
     * @throws JSONException If the mapping doesn't exist or is not a JSONObject
     */
    public Pattern(String json) throws JSONException {
        final JSONObject pattern = new JSONArray(json).getJSONObject(0);
        token = pattern.getString(ID);
        title = pattern.getString(TITLE);
        user = pattern.getString(USERNAME);
        permalink = Uri.decode(pattern.getString(URL));
        img = IMG_URL.replace("ID", token);
    }

}
