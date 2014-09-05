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
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

/**
 * Class that manages all Menu interactions
 */
public class MenuManager {

    /*********************************************************************
     * Enumerators *******************************************************
     *********************************************************************/

    public enum THEME_CHOICE {
        DEFAULT, GIRLY, PUNK, BLOBBY
    }

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private Activity _currentActivity;
    private THEME_CHOICE _theme_choice;
    private int _theme_current_index;

    /**
     * Constructor
     *
     * @param currentActivity :
     *                        Parameter that represents the Activity class.
     */
    public MenuManager(Activity currentActivity) {
        _currentActivity = currentActivity;
        _theme_choice = THEME_CHOICE.DEFAULT;
        _theme_current_index = 0;
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

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
     * Method that switches the theme to the user's choice, after
     * we have dealt with the menu interaction.
     */
    private void themeSwitcher() {
        // DEBUG
        Log.d("Current Theme Index: ", Integer.toString(_theme_current_index));

        // Initialize a Typeface
        Typeface btn_font = CustomFontHelper.get(_currentActivity, "");

        // Now let's switch the theme, first off we'll set the background and font
        RelativeLayout rl = (RelativeLayout)_currentActivity.findViewById(R.id.lay);
        switch(_theme_choice) {
            case DEFAULT:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.hexagrid));
                btn_font = CustomFontHelper.get(_currentActivity, "Ghang.ttf");
                break;
            case GIRLY:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.balloons));
                btn_font = CustomFontHelper.get(_currentActivity, "SoupLeaf.ttf");
                break;
            case PUNK:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.cropped_punk_collage));
                btn_font = CustomFontHelper.get(_currentActivity, "BROKEN15.TTF");
                break;
            case BLOBBY:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.blobby));
                btn_font = CustomFontHelper.get(_currentActivity, "Stab.ttf");
                break;
        }

        Button btn;
        GridLayout gl = (GridLayout)_currentActivity.findViewById(R.id.grid4x3);

        // Within this loop we'll apply the font and colour scheme to each button
        for (int i = 0; i < gl.getChildCount(); ++i) {
            btn = (Button)gl.getChildAt(i);
            btn.setTypeface(btn_font);
            if (_theme_choice == THEME_CHOICE.DEFAULT) {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_orange));
            } else if (_theme_choice == THEME_CHOICE.GIRLY) {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_pink));
            } else if (_theme_choice == THEME_CHOICE.PUNK) {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_snotty));
            } else {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_blobz));
            }
        }
    }

    /**
     * Method that displays the theme menu and sets the enumerator on 
     * users choice.
     */
    public void themesClicked() {
        final int previous_theme_index = _theme_current_index;
        String[] theme_list = { "Default", "Girly", "Punk", "Blobby" };
        final AlertDialog.Builder themes_builder = new AlertDialog.Builder(_currentActivity);
        themes_builder.setTitle(R.string._action_themes);
        themes_builder.setSingleChoiceItems(theme_list, _theme_current_index,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                _theme_current_index = which;
                switch (which) {
                    case 0:
                        _theme_choice = THEME_CHOICE.DEFAULT;
                        break;
                    case 1:
                        _theme_choice = THEME_CHOICE.GIRLY;
                        break;
                    case 2:
                        _theme_choice = THEME_CHOICE.PUNK;
                        break;
                    case 3:
                        _theme_choice = THEME_CHOICE.BLOBBY;
                        break;
                }
            }
        }).setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                themeSwitcher();
            }
        }).setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                _theme_current_index = previous_theme_index;
            }
        });
        AlertDialog theme_dialog = themes_builder.create();
        theme_dialog.show();
    }

}
