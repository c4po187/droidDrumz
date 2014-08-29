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
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Button;
import android.view.ViewTreeObserver;

/**
 * Class that provides a platform to the grid, and its children (hit pads)
 * which happen to be buttons.
 */
public class Container {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private Activity _currentActivity;

    /**
     * Constructor
     *
     * @param currentActivity :
     *                        Parameter that represents the main Activity class.
     * @param soundManager    :
     *                        Parameter that represents the SoundManager class.
     */
    public Container(final Activity currentActivity, final SoundManager soundManager) {
        _currentActivity = currentActivity;
        GridLayout gl = (GridLayout)currentActivity.findViewById(R.id.grid4x3);
        ViewTreeObserver vto_a = gl.getViewTreeObserver();

        vto_a.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                GridLayout gl = (GridLayout)currentActivity.findViewById(R.id.grid4x3);
                reorganizeGrid(gl, soundManager);
                ViewTreeObserver vto_b = gl.getViewTreeObserver();
                vto_b.removeOnGlobalLayoutListener(this);
            }
        });
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * For some reason, which is beyond myself, the GridLayout does not seem to
     * be drawn as one would expect. Therefore after its initial draw call further
     * up in the hierarchy, we recalculate its dimensions, along with its child
     * elements.
     *
     * @param gl           :
     *                     Parameter that represents the GridLayout.
     * @param soundManager :
     *                     Parameter that represents the SoundManager class.
     */
    public void reorganizeGrid(GridLayout gl, SoundManager soundManager) {
        // Get Window Metrics and apply our layout based on what we retrieve.
        WindowManager winManager = _currentActivity.getWindowManager();
        Display display = winManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int widthPixels = 0, heightPixels = 0;

        if (Build.VERSION.SDK_INT >= 1 && Build.VERSION.SDK_INT < 14) {
            widthPixels = metrics.widthPixels;
            heightPixels = metrics.heightPixels;
        } else if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                Log.e("Container: ", "Could not retrieve raw dimensions because, " +
                    e.getMessage());
            }
        } else {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception e) {
                Log.e("Container: ", "Could not retrieve real dimensions because, " +
                    e.getMessage());
            }
        }

        // Resize the grid based on users device
        int five_percent_x = ((widthPixels / 100) * 5);
        int five_percent_y = ((heightPixels / 100) * 5);
        gl.setLeft(five_percent_x);
        gl.setTop(five_percent_y);
        gl.setMinimumWidth(widthPixels - five_percent_x);
        gl.setMinimumHeight(heightPixels - five_percent_y);

        // Draw button sizes relative to the modified grid calculations.
        Button tmp_btn;
        int btn_width = ((gl.getWidth() / gl.getColumnCount()) - (five_percent_x * 2));

        /* Create custom font using CustomFontHelper class
         * to avoid a crash. The typeface class does not
         * handle exceptions on provided fonts, whereas my
         * class does.
         */
        Typeface btn_font = CustomFontHelper.get(_currentActivity, "Ghang.ttf");

        for (int i = 0; i < gl.getChildCount(); ++i) {
            tmp_btn = (Button) gl.getChildAt(i);
            tmp_btn.setWidth(btn_width);
            tmp_btn.setHeight(btn_width);
            tmp_btn.setTypeface(btn_font);
            ViewGroup.MarginLayoutParams btn_margin =
                    (ViewGroup.MarginLayoutParams) tmp_btn.getLayoutParams();
            btn_margin.leftMargin = five_percent_x;
            btn_margin.bottomMargin = five_percent_x;
            btn_margin.rightMargin = five_percent_x;
            btn_margin.topMargin = five_percent_x;
            assignSoundToButton(i, tmp_btn, soundManager);
        }
    }

    /**
     * This method is called from within a loop in the above method.
     * It assigns a sound from the sound back and a way of detecting
     * a touch event to each hit pad (button). Its final goal is to
     * get each button to play a sound from the sound bank.
     *
     * @param i             :
     *                      Parameter represents the current iteration
     *                      of the loop from the previous method.
     * @param btn           :
     *                      Parameter represents a button.
     * @param soundManager  :
     *                      Parameter represents the SoundManager class.
     */
    private void assignSoundToButton(int i, Button btn, SoundManager soundManager) {
        final int f_i = i;
        final SoundManager f_soundManager = soundManager;
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    f_soundManager.playSound(f_i);
                return false;
            }
        });
    }

}
