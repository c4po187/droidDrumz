package com.droid.c4po.droiddrumz_alpha;

/**
 * Created by c4po on 26/08/14.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import java.util.Hashtable;

public class CustomFontHelper {

    private static final String TAG = "CustomFontHelper";
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), assetPath);
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
