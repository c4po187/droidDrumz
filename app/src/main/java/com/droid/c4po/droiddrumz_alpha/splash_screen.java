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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Small Class that simply handles the splash screen, and starts the main
 * program upon clicking the start button funnily enough.
 */
public class splash_screen extends Activity implements OnClickListener{

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
        setButtonPositions(startBtn, exitBtn);
    }

    /**
     * This method sets up the button positions and dimensions
     * correctly based on the users device.
     *
     * @param start :
     *              Parameter represents the start button.
     * @param exit  :
     *              Parameter represents the exit button.
     *
     * TODO: Calculations need tweaking, layout slightly out on my device!
     */
    private void setButtonPositions(Button start, Button exit) {
        // Retrieve the screen dimensions
        DimensionHelper dimHelper = new DimensionHelper(this);
        dimHelper.calculateScreenDimensions();
        int width = dimHelper.getScreenWidth();
        int height = dimHelper.getScreenHeight();

        // Percentage Padders
        int x1pc = (width / 100);               // 1%
        int x3pc = ((width / 100) * 3);         // 3%
        int xbtn_pad = ((width / 100) * 10);    // 10%
        int ybtn_pad = ((height / 100) * 65);   // 65%

        // Set Dimensions of buttons
        int btn_width = ((width / 100) * 35);   // 35%
        start.setWidth(btn_width);
        start.setHeight(xbtn_pad);
        exit.setWidth(btn_width);
        exit.setHeight(xbtn_pad);

        // Set Margins of buttons: ultimately, positioning them
        ViewGroup.MarginLayoutParams start_margins =
                (ViewGroup.MarginLayoutParams)start.getLayoutParams();
        start_margins.leftMargin = xbtn_pad + x3pc;
        start_margins.rightMargin = xbtn_pad + x1pc;
        start_margins.topMargin = ybtn_pad;
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
        if (view.getId() == R.id.splash_start_btn) {
            Intent runMain = new Intent(this, DroidDrumzAlphaMain.class);
            this.startActivity(runMain);
        } else if (view.getId() == R.id.splash_exit_btn) {
            finish();
        }
    }
}
