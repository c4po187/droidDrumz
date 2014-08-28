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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

enum THEME_CHOICE {
    DEFAULT, GIRLY
}

/**
 * Class that manages all Menu interactions
 */
public class MenuManager {

    /**
     * Members
     */
    private Activity _currentActivity;
    private THEME_CHOICE _theme_choice;

    /**
     * Constructor
     *
     * @param currentActivity :
     *                        Parameter that represents the Activity class.
     */
    public MenuManager(Activity currentActivity) {
        _currentActivity = currentActivity;
        _theme_choice = THEME_CHOICE.DEFAULT;
    }

    /**
     * Method that handles the About button being clicked.
     */
    public void actionBar_AboutSelected() {
        final AlertDialog.Builder about_builder = new AlertDialog.Builder(_currentActivity);
        about_builder.setTitle(R.string._action_about).setMessage(R.string.about_content);
        about_builder.setPositiveButton(R.string.action_about_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog about_dialog = about_builder.create();
        about_dialog.show();
    }

    /**
     * Method that changes the theme.
     *
     * TODO: Fix this method, as button colours and background are not changing.
     */
    public void themesClicked() {
        String[] theme_list = { "Default", "Girly" };
        final AlertDialog.Builder themes_builder = new AlertDialog.Builder(_currentActivity);
        themes_builder.setTitle(R.string._action_themes);
        themes_builder.setSingleChoiceItems(theme_list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        _theme_choice = THEME_CHOICE.DEFAULT;
                        break;
                    case 1:
                        _theme_choice = THEME_CHOICE.GIRLY;
                        break;
                }
            }
        }).setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final GridLayout gl = (GridLayout) _currentActivity.findViewById(R.id.grid4x3);
                final ViewTreeObserver vto = gl.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Button btn;
                        // Change button colours
                        for (int i = 0; i < gl.getChildCount(); ++i) {
                            btn = (Button) gl.getChildAt(i);
                            if (_theme_choice == THEME_CHOICE.DEFAULT) {
                                btn.setBackground(_currentActivity.getResources().getDrawable(
                                        R.drawable.btn_orange));
                            }
                            if (_theme_choice == THEME_CHOICE.GIRLY) {
                                btn.setBackground(_currentActivity.getResources().getDrawable(
                                        R.drawable.btn_pink));
                            }
                        }
                        vto.removeOnGlobalLayoutListener(this);
                    }
                });
                final RelativeLayout rl = (RelativeLayout)_currentActivity.findViewById(R.id.lay);
                final ViewTreeObserver vtoo = rl.getViewTreeObserver();
                vtoo.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Change Backgrounds
                        if (_theme_choice == THEME_CHOICE.DEFAULT) {
                            rl.setBackground(_currentActivity.getResources().getDrawable(
                                    R.drawable.hexagrid));
                        }
                        if (_theme_choice == THEME_CHOICE.GIRLY) {
                            rl.setBackground(_currentActivity.getResources().getDrawable(
                                    R.drawable.balloons));
                        }
                        vtoo.removeOnGlobalLayoutListener(this);
                    }
                });
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog theme_dialog = themes_builder.create();
        theme_dialog.show();
    }

}
