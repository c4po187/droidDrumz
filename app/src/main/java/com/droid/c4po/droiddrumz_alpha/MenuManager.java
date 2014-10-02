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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.FileOutputStream;
import org.jetbrains.annotations.*;

/**
 * Class that manages all Menu interactions
 *
 * TODO: Fix minor bug in Theme Switcher
 */
public class MenuManager {

    /*********************************************************************
     * Enumerators *******************************************************
     *********************************************************************/

    public enum THEME_CHOICE {
        DEFAULT, GIRLY, PUNK, BLOBBY, HORROR, TECHNOLOGY
    }

    public enum PRESET_MENU_CHOICE {
        LOAD_PRESET, SAVE_PRESET
    }

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private Activity            _currentActivity;
    private THEME_CHOICE        _theme_choice;
    private PRESET_MENU_CHOICE  _preset_menu_choice;
    public float                _pitch;
    private int                 _theme_current_index,
                                _previous_theme_index,
                                _pitch_current_index,
                                _previous_pitch_index;

    /**
     * Constructor
     *
     * @param currentActivity :
     *                        Parameter that represents the Activity class.
     */
    public MenuManager(Activity currentActivity) {
        _currentActivity = currentActivity;
        _theme_choice = THEME_CHOICE.DEFAULT;
        _preset_menu_choice = PRESET_MENU_CHOICE.LOAD_PRESET;
        _theme_current_index = _previous_theme_index = 0;
        _pitch_current_index = _previous_pitch_index = 1;
        _pitch = 1.0f;
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Method that handles the About button being clicked.
     */
    public void actionBar_AboutSelected() {
        new AlertDialog.Builder(_currentActivity)
                .setTitle(R.string._action_about)
                .setMessage(R.string.about_content)
                .setPositiveButton(R.string.action_about_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * Method that creates the Pitch Dialog, it also captures the users choice.
     */
    public void pitchClicked(@NotNull final SoundManager soundManager) {
        _previous_pitch_index = _pitch_current_index;
        String[] pitchValues = { "Half Speed", "Normal Speed", "Double Speed" };

        new AlertDialog.Builder(_currentActivity)
                .setTitle("Change Pitch")
                .setSingleChoiceItems(pitchValues, _pitch_current_index,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            _pitch_current_index = which;
                            switch (which) {
                                case 0:
                                    _pitch = 0.5f;
                                    break;
                                case 1:
                                    _pitch = 1.0f;
                                    break;
                                case 2:
                                    _pitch = 2.0f;
                                    break;
                            }
                        }
                })
                .setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        soundManager.set_pitch(_pitch);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _pitch_current_index = _previous_pitch_index;
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    /**
     * Method to handle the clicking of the Presets option
     */
    public void presetsClicked(@NotNull final SoundManager soundManager) {
        String[] presets_options = { "Load Preset", "Save Preset" };

        new AlertDialog.Builder(_currentActivity)
                .setTitle("Preset Menu")
                .setSingleChoiceItems(presets_options, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0:
                                _preset_menu_choice = PRESET_MENU_CHOICE.LOAD_PRESET;
                                break;
                            case 1:
                                _preset_menu_choice = PRESET_MENU_CHOICE.SAVE_PRESET;
                                break;
                        }
                    }
                })
                .setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (_preset_menu_choice == PRESET_MENU_CHOICE.LOAD_PRESET) {
                            Intent load_preset_intent = new Intent(_currentActivity
                                    .getApplicationContext(), AssignmentMenu.class);
                            load_preset_intent.putExtra("currentkit",
                                    soundManager.get_current_kit_assigned());
                            load_preset_intent.putStringArrayListExtra("presetnames",
                                    SoundManager._PRESET_NAMES_);
                            _currentActivity.startActivityForResult(load_preset_intent,
                                    DroidDrumzAlphaMain._PRESET_SOUND_CODE_);
                        } else {
                            savePreset(soundManager);
                        }
                    }
                })
                .setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    /**
     * Method that helps with the saving of a custom preset
     * to internal storage.
     *
     * @param soundManager :
     *                     Parameter represents the SoundManager class.
     */
    private void savePreset(final SoundManager soundManager) {
        final EditText input = new EditText(_currentActivity);

        new AlertDialog.Builder(_currentActivity)
                .setTitle("Save Preset")
                .setMessage("Enter a name...")
                .setView(input)
                .setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Editable save_name = input.getText();
                        String ext = ".pst";
                        String filename = save_name.toString() + ext;
                        FileOutputStream ostream;

                        try {
                            ostream = _currentActivity.openFileOutput(filename,
                                    Context.MODE_PRIVATE);
                            int index = 0;
                            String toWrite = "";
                            for (; index < soundManager.get_current_btn_assigned().size(); ++index) {
                                toWrite += soundManager.get_current_btn_assigned().get(index);
                                toWrite += '\n';
                            }
                            ostream.write(toWrite.getBytes());
                            ostream.close();
                            Log.d("Compiled String: ", toWrite);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialogInterface.dismiss();
                        Toast.makeText(_currentActivity.getApplicationContext(),
                                filename + " Saved Successfully...", Toast.LENGTH_LONG).show();

                        // Get our saved preset added immediately
                        soundManager.getSavedPresets();
                        soundManager.set_current_kit_assigned(save_name.toString());
                    }
                })
                .setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
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
            case HORROR:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.lothorror));
                btn_font = CustomFontHelper.get(_currentActivity, "urban decay.ttf");
                break;
            case TECHNOLOGY:
                rl.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.tech));
                btn_font = CustomFontHelper.get(_currentActivity, "Wipeout HD Fury.ttf");
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
            } else if (_theme_choice == THEME_CHOICE.HORROR) {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_horror));
            } else if (_theme_choice == THEME_CHOICE.BLOBBY) {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_blobz));
            } else {
                btn.setBackground(_currentActivity.getResources()
                        .getDrawable(R.drawable.btn_tech));
            }
        }
    }

    /**
     * Method that displays the theme menu and sets the enumerator on 
     * users choice.
     */
    public void themesClicked() {
        _previous_theme_index = _theme_current_index;
        String[] theme_list = { "Default", "Girly", "Punk", "Blobby", "Horror", "Technology" };

        new AlertDialog.Builder(_currentActivity)
                .setTitle(R.string._action_themes)
                .setSingleChoiceItems(theme_list, _theme_current_index,
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
                                case 4:
                                    _theme_choice = THEME_CHOICE.HORROR;
                                    break;
                                case 5:
                                    _theme_choice = THEME_CHOICE.TECHNOLOGY;
                                    break;
                            }
                        }
                })
                .setPositiveButton(R.string.tok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        themeSwitcher();
                    }
                })
                .setNegativeButton(R.string.tc, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        _theme_current_index = _previous_theme_index;
                    }
                })
                .show();
    }

}
