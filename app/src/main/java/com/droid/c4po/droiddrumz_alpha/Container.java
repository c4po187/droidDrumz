package com.droid.c4po.droiddrumz_alpha;

/**
 * Created by c4po on 25/08/14.
 */

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

import java.lang.reflect.InvocationTargetException;

public class Container {

    private Activity _currentActivity;

    public Container(final Activity currentActivity) {
        _currentActivity = currentActivity;
        GridLayout gl = (GridLayout)currentActivity.findViewById(R.id.grid4x3);
        ViewTreeObserver vto_a = gl.getViewTreeObserver();

        vto_a.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                GridLayout gl = (GridLayout)currentActivity.findViewById(R.id.grid4x3);
                reorganizeGrid(gl);
                ViewTreeObserver vto_b = gl.getViewTreeObserver();
                vto_b.removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void reorganizeGrid(GridLayout gl) {
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
        heightPixels /= 1;

        // Draw button sizes relative to the modified grid calculations.
        Button tmp_btn;
        int btn_width = ((widthPixels - 20) / gl.getColumnCount());
        // Subtract 10%
        btn_width -= (btn_width / 10) * 2;

        /* Create custom font using CustomFontHelper class
         * to avoid a crash. The typeface class does not
         * handle exceptions on provided fonts, whereas my
         * class does.
         */
        Typeface btn_font = CustomFontHelper.get(_currentActivity, "Ghang.ttf");

        for (int i = 0; i < gl.getChildCount(); ++i) {
            tmp_btn = (Button)gl.getChildAt(i);
            tmp_btn.setWidth(btn_width);
            tmp_btn.setHeight(btn_width);
            tmp_btn.setTypeface(btn_font);
            ViewGroup.MarginLayoutParams btn_margin =
                    (ViewGroup.MarginLayoutParams)tmp_btn.getLayoutParams();
            btn_margin.leftMargin = 10;
            btn_margin.bottomMargin = 10;
            btn_margin.rightMargin = 10;
            btn_margin.topMargin = 10;
        }
    }

    public void buttonRoutine(final SoundManager soundManager) {
        Button _a = (Button)_currentActivity.findViewById(R.id.btn1);
        _a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(0);
                    //return true;
                }
                return false;
            }
        });
        Button _b = (Button)_currentActivity.findViewById(R.id.btn2);
        _b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(1);
                    //return true;
                }
                return false;
            }
        });
        Button _c = (Button)_currentActivity.findViewById(R.id.btn3);
        _c.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(2);
                    //return true;
                }
                return false;
            }
        });
        Button _d = (Button)_currentActivity.findViewById(R.id.btn4);
        _d.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(3);
                    //return true;
                }
                return false;
            }
        });
        Button _e = (Button)_currentActivity.findViewById(R.id.btn5);
        _e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(4);
                    //return true;

                }
                return false;
            }
        });
        Button _f = (Button)_currentActivity.findViewById(R.id.btn6);
        _f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(5);
                    //return true;
                }
                return false;
            }
        });
        Button _g = (Button)_currentActivity.findViewById(R.id.btn7);
        _g.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(6);
                    //return true;
                }
                return false;
            }
        });
        Button _h = (Button)_currentActivity.findViewById(R.id.btn8);
        _h.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(7);
                    //return true;
                }
                return false;
            }
        });
        Button _i = (Button)_currentActivity.findViewById(R.id.btn9);
        _i.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(8);
                    //return true;
                }
                return false;
            }
        });
        Button _j = (Button)_currentActivity.findViewById(R.id.btn10);
        _j.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(9);
                    //return true;
                }
                return false;
            }
        });
        Button _k = (Button)_currentActivity.findViewById(R.id.btn11);
        _k.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(10);
                    //return true;
                }
                return false;
            }
        });
        Button _l = (Button)_currentActivity.findViewById(R.id.btn12);
        _l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent .getAction() == MotionEvent.ACTION_DOWN) {
                    soundManager.playSound(11);
                    //return true;
                }
                return false;
            }
        });
    }
}
