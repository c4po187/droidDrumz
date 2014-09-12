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
import android.widget.TableRow;

/**
 * Small Class that simply handles the splash screen, and starts the main
 * program upon clicking the start button funnily enough.
 */
public class splash_screen extends Activity implements OnClickListener {

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
    }

    /**
     * This method sets up the button positions and dimensions
     * correctly based on the users device.
     */
    private void setButtonVerticalPositions() {
        // Retrieve the screen dimensions
        DimensionHelper dimHelper = new DimensionHelper(this);
        dimHelper.calculateScreenDimensions();
        int height = dimHelper.getScreenHeight();

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
        if (view.getId() == R.id.splash_start_btn) {
            Intent runMain = new Intent(this, DroidDrumzAlphaMain.class);
            this.startActivity(runMain);
        } else if (view.getId() == R.id.splash_exit_btn) {
            finish();
        }
    }
}
