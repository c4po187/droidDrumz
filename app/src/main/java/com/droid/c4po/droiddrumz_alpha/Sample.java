package com.droid.c4po.droiddrumz_alpha;

import android.app.Activity;
import android.util.Log;

/**
 * Class that represents an audio sample
 */
public class Sample {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private int _resource_id;
    private String _resource_name;
    private Activity _currentActivity;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public int get_resource_id() {
        return _resource_id;
    }

    public String get_resource_name() {
        return _resource_name;
    }

    /*********************************************************************
     * Constructor *******************************************************
     *********************************************************************/

    public Sample(Activity activity, int resId) {
        _currentActivity = activity;
        _resource_id = resId;
        init();
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    private void init() {
        try {
            _resource_name = _currentActivity.getApplicationContext()
                    .getResources().getResourceEntryName(_resource_id);
        } catch (Exception e) {
            Log.e("Sample: ", "Could not get Resource entry name because, " + e.getMessage());
        }
    }
}
