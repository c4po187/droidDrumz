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
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;

/**
 * Small Class that simply handles the splash screen, and starts the main
 * program upon clicking the start button funnily enough.
 */
public class splash_screen extends Activity implements OnClickListener {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private MediaPlayer _splashPlayer_looper, _needle_scratch;

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Method is called as soon as this class is launched. We create all
     * the objects here.
     *
     * @param savedInstanceState :
     *                           Parameter represents a saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Typeface btn_font = CustomFontHelper.get(this, "Maya Culpa.ttf");
        Button startBtn = (Button)findViewById(R.id.splash_start_btn);
        startBtn.setTypeface(btn_font);
        startBtn.setOnClickListener(this);
        Button exitBtn = (Button)findViewById(R.id.splash_exit_btn);
        exitBtn.setTypeface(btn_font);
        exitBtn.setOnClickListener(this);
        setButtonVerticalPositions();

        // Get needle scratch ready
        _needle_scratch = MediaPlayer.create(this, R.raw.needlescratch);
        _needle_scratch.setLooping(false);
        _needle_scratch.setVolume(0.5f, 0.5f);

        // Play our intro tune
        _splashPlayer_looper = MediaPlayer.create(this, R.raw.splashloop);
        _splashPlayer_looper.setLooping(true);
        _splashPlayer_looper.setVolume(0.35f, 0.35f);
        _splashPlayer_looper.start();

    }

    /**
     * This method sets up the button positions and dimensions
     * correctly based on the users device.
     */
    private void setButtonVerticalPositions() {
        // Retrieve the screen dimensions (we just need the height for now).
        int height = 0;
        try {
            height = DimensionHelper.calculateDeviceScreenResolution(this.getApplicationContext()).y;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Position Button row vertically
        int row_vert_pos = ((height / 100) * 65);   // 65%
        TableRow btnRow = (TableRow)findViewById(R.id.splash_btn_row);
        ViewGroup.MarginLayoutParams btnRowMargin =
                (ViewGroup.MarginLayoutParams)btnRow.getLayoutParams();
        btnRowMargin.topMargin = row_vert_pos;
    }

    /**
     * Method handles the clicking of the start button
     * and subsequently stats the main program.
     *
     * @param view :
     *             Parameter represents a view.
     */
    @Override
    public void onClick(View view) {
        // Start button clicked
        if (view.getId() == R.id.splash_start_btn) {
            // Stop and release the resources for our Splash Player instance
            _splashPlayer_looper.stop();
            _splashPlayer_looper.release();

            /* Setup an on Completion listener for the needle scratch so we
             * can release the resource, as we have finished with it.
             */
            _needle_scratch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer needle_scratch) {
                    needle_scratch.stop();
                    needle_scratch.release();
                }
            });
            _needle_scratch.start();

            // Initialise an intent in order to start the main activity
            Intent runMain = new Intent(this, DroidDrumzAlphaMain.class);
            this.startActivity(runMain);
        }
        // Exit button clicked
        else if (view.getId() == R.id.splash_exit_btn) {
            if (_splashPlayer_looper != null)
                _splashPlayer_looper.stop();
            finish();
        }
    }
}
