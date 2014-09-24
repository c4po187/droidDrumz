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
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Class that helps set the dimensions of elements upon launch.
 * Such a class is needed as we cannot rely on using absolute values
 * due to the fact that screen resolutions differ from device to device,
 * thus impacting the positioning and dimensions of elements respectively.
 */
public class DimensionHelper {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private static String _TAG_ = "DimensionHelper: ";

    private int _screen_width;
    private int _screen_height;
    private Activity _activity;

    /*********************************************************************
     * Getters and Setters ***********************************************
     *********************************************************************/

    public int getScreenWidth() {
        return _screen_width;
    }

    public int getScreenHeight() {
        return _screen_height;
    }

    /*********************************************************************
     * Constructor *******************************************************
     *********************************************************************/

    public DimensionHelper(Activity activity) {
        _screen_height = 0;
        _screen_width = 0;
        _activity = activity;
    }

    /*********************************************************************
     * Finalizer *********************************************************
     *********************************************************************/

    @Override
    protected void finalize() throws Throwable {
        _screen_height = _screen_width = 0;
        if (_activity != null)
            _activity = null;

        super.finalize();
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Method that calculates the screen dimensions on the users device.
     */
    public void calculateScreenDimensions() {
        WindowManager winManager = _activity.getWindowManager();
        Display display = winManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        /* Depending on which SDK range we are using, we calculate
           the devices dimensions using different methods.
         */
        if (Build.VERSION.SDK_INT >= 1 && Build.VERSION.SDK_INT < 14) {
            _screen_width = metrics.widthPixels;
            _screen_height = metrics.heightPixels;
        } else if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                _screen_width = (Integer)Display.class.getMethod("getRawWidth").invoke(display);
                _screen_height = (Integer)Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                Log.e(_TAG_, "Could not retrieve raw dimensions because, " + e.getMessage());
            }
        } else /* SDK 17 and upwards */ {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                _screen_width = realSize.x;
                _screen_height = realSize.y;
            } catch (Exception e) {
                Log.e(_TAG_, "Could not retrieve rel dimensions because, " + e.getMessage());
            }
        }
    }
}
