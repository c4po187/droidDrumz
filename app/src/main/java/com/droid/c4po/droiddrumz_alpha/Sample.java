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

//import android.app.Activity;
import android.content.Context;
import android.media.SoundPool;
import android.util.Log;
import org.jetbrains.annotations.*;

/**
 * Class that represents an audio sample
 */
public class Sample implements Comparable<Sample> {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private int         _soundID,
                        _resource_id;
    private String      _resource_name;
    private Context     _context;
    //private Activity    _currentActivity;
    private SoundPool   _soundpool;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public int get_soundID() {
        return _soundID;
    }

    public int get_resource_id() {
        return _resource_id;
    }

    public String get_resource_name() {
        return _resource_name;
    }

    /*********************************************************************
     * Chain Setters *****************************************************
     *********************************************************************/

    public Sample initLoadSoundID() {
        //_soundID = _soundpool.load(_currentActivity, _resource_id, 1);
        _soundID = _soundpool.load(_context, _resource_id, 1);
        return this;
    }

    /*********************************************************************
     * Constructor *******************************************************
     *********************************************************************/

    public Sample(SoundPool soundpool, Context context/*Activity activity*/, int resId) {
        _soundpool = soundpool;
        //_currentActivity = activity;
        _context = context;
        _resource_id = resId;
        init();
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Initialises the class, by setting up the resource name and soundID
     * linked to the resource id.
     */
    private void init() {
        try {
            /*
            _resource_name = _currentActivity.getApplicationContext()
                    .getResources().getResourceEntryName(_resource_id);
            */
            _resource_name = _context.getResources().getResourceEntryName(_resource_id);
        } catch (Exception e) {
            Log.e("Sample: ", "Something wicked happened, " + e.getMessage());
        }
    }

    /**
     * Compares this instance of Sample against another provided
     * as an argument, based on resource id.
     *
     * @param other     :
     *                  Parameter represents a sample to compare
     *                  against this instance.
     * @return          :
     *                  Returns -1 if this instances resource id is less,
     *                  Returns 1 if this instances resource id is more,
     *                  Returns 0 if both resource ids are equal.
     */
    @Override
    public int compareTo(@NotNull Sample other) {
        return (this.get_resource_id() < other.get_resource_id()) ? -1 :
                (this.get_resource_id() > other.get_resource_id()) ? 1 : 0;
    }
}