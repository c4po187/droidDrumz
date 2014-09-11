/**
 * DroidDrumz, a simple yet effective, open source drum machine / sampler
 * Copyright (C) 2014  Rici Underwood
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * c4po187@gmail.com
 *
 **/

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
