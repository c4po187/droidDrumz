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
import android.graphics.Typeface;
import android.util.Log;
import java.util.Hashtable;

/**
 * Class that aids in the creation of a Typeface from true-type fonts.
 * The Typeface class, itself achieves this task. However, it does not
 * handle exceptions, resulting in an applications crash. This class
 * does catch exceptions, and reports it. If a Typeface cannot be
 * created, we just default back to a generic font and use that rather
 * than exiting the program.
 */
public class CustomFontHelper {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private static final String TAG = "CustomFontHelper";
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Method that tries to create a Typeface from a given true-type font.
     *
     * @param context   :
     *                  Parameter represents a context.
     * @param assetPath :
     *                  Parameter represents a string that is a path to
     *                  the true-type font.
     * @return          :
     *                  Returns a Typeface created from the true-type font
     *                  if successful. Returns a null Typeface otherwise.
     */
    public static Typeface get(Context context, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface, " + assetPath
                            + " because " + e.getMessage());
                    return null;
                }
            }
        }
        return cache.get(assetPath);
    }
}
