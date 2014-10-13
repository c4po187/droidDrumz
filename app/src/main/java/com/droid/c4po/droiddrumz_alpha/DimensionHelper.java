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

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Class that helps with dimensions based on the users device.
 */
public class DimensionHelper {

    /**
     * Method that returns a Point representing the screen resolution of the user's device.
     * X = Width, Y = Height.
     *
     * @param context       :
     *                      Parameter represents a reference to a context.
     * @return              :
     *                      Returns a Point.
     * @throws Exception
     */
    public static Point calculateDeviceScreenResolution(Context context) throws Exception {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        if (Build.VERSION.SDK_INT >= 1 && Build.VERSION.SDK_INT < 14)
            return new Point(metrics.widthPixels, metrics.heightPixels);
        else if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            return new Point((Integer)Display.class.getMethod("getRawWidth").invoke(display),
                    (Integer)Display.class.getMethod("getRawHeight").invoke(display));
        else {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            return realSize;
        }
    }

    /**
     * Method that gets the screen area in pixels squared.
     *
     * @param context   :
     *                  Parameter represents a reference to a context.
     * @return          :
     *                  Returns width multiplied by height as int.
     *                  If the calculation of the screen resolution fails,
     *                  (the first step in the algorithm) zero will be returned
     *                  due to the point being initialized to zero on X and Y.
     */
    public static int getScreenArea(Context context) {
        Point resolution = new Point(0, 0);
        try {
            resolution = calculateDeviceScreenResolution(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (resolution.x * resolution.y);
    }
}
