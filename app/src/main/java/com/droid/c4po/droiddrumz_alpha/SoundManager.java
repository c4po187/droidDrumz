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
import android.media.SoundPool;
import android.media.AudioManager;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that deals with all Audio related data.
 * Class inherits from SoundPool.
 */
public class SoundManager extends SoundPool {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private Activity                 _currentActivity;

    private int                      _streamID,
                                     _loop;

    private float                    _volume,
                                     _pitch;

    private static int               _SOUND_BANK_START_INDEX_ = 0x7f040000,
                                     _SOUND_BANK_END_INDEX_ = 0x7f0402a2;

    private ArrayList<Sample>        _samples;

    public static ArrayList<String>  _PRESET_NAMES_;

    private List<List<Sample>>       _preset_container;

    private List<Sample>             _selected_samples;

    private List<Sample>             _electro_samples,
                                     _chiptune_samples,
                                     _tech_grit_samples,
                                     _jungle_samples;

    private List<String>             _current_btn_assigned;
    private String                   _current_kit_assigned;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public ArrayList<Sample> get_samples() {
        return _samples;
    }

    public void set_pitch(float pitch) { _pitch = pitch; }

    public String get_current_kit_assigned() { return _current_kit_assigned; }

    public void set_current_kit_assigned(String current_kit_assigned) {
        _current_kit_assigned = current_kit_assigned;
    }

    public List<String> get_current_btn_assigned() { return _current_btn_assigned; }

    /**
     *
     * Constructor
     *
     * @param maxStreams      :
     *                        Maximum number of streams this class
     *                        can handle synchronously.
     * @param streamType      :
     *                        AudioManager stream type.
     * @param srcQuality      :
     *                        Needed but unused parameter: always
     *                        set to zero.
     * @param currentActivity :
     *                        Parameter that passes the Activity
     *                        class, merely needed to access many
     *                        of its useful methods.
     */
    public SoundManager(int maxStreams, int streamType, int srcQuality,
                        Activity currentActivity) {
        super(maxStreams, streamType, srcQuality);

        _currentActivity = currentActivity;
        _loop = 0;
        _pitch = 1.0f;

        _current_btn_assigned = new ArrayList<String>();
        _current_kit_assigned = "";

        _preset_container = new ArrayList<List<Sample>>();
        _selected_samples = new ArrayList<Sample>();

        _chiptune_samples = new ArrayList<Sample>();
        _electro_samples = new ArrayList<Sample>();
        _tech_grit_samples = new ArrayList<Sample>();
        _jungle_samples = new ArrayList<Sample>();
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Initialise all objects and data within this class.
     */
    public void init() {
        // If Load Presets Successful, continue
        if (loadPresets()) {
            // Set up AudioManager
            AudioManager audioManager = (AudioManager) _currentActivity.getSystemService(
                    Context.AUDIO_SERVICE);
            _volume = ((float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) /
                    (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            _currentActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

            // Set up Samples
            _samples = new ArrayList<Sample>();

            /* Grab all the audio samples from the raw folder and add them to the samples list
             * for use with the Assignment menu, as a list of strings will be compiled from
             * each and every resource name within this list of samples.
             */
            for (int index = _SOUND_BANK_START_INDEX_; index <= _SOUND_BANK_END_INDEX_; ++index) {
                // We don't want the splash loop in there
                if (index != 0x7f040221)
                    _samples.add(new Sample(this, _currentActivity, index));
            }

            // Sort the sample list alphabetically by resource name
            Collections.sort(_samples, new SortSamplesByResourceName());

            // Init the Selected samples and button strings to 12 non-nullable values
            for (int i = 0; i < 12; ++i) {
                _current_btn_assigned.add("empty");
                _selected_samples.add(new Sample(this, _currentActivity, i));
            }

            // Get saved presets
            getSavedPresets();

            // Copy the electro kit in the preset container (index 1) to the selected samples
            Collections.copy(_selected_samples, _preset_container.get(1));

            // Set Current Kit string to Electro string from the preset names list
            _current_kit_assigned = _PRESET_NAMES_.get(1);

            // Setup all the currently assigned resource names
            assignResourceNames();

        } else {
            Toast.makeText(_currentActivity.getApplicationContext(),
                    "Error Initializing Sound Bank!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method loads all the preset kits, into individual lists which themselves
     * are stored in yet another list, so yes: lists within a list ;)
     *
     * @return :
     *         Returns true if the storage list is filled.
     */
    private boolean loadPresets() {
        // Init Preset Names Array List
        _PRESET_NAMES_ = new ArrayList<String>();

        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipkick2).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipsn5).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipsn6).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipkick1).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chiphat6).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chiphat8).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx1).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx3).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx5).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx6).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx7).initLoadSoundID());
        _chiptune_samples.add(new Sample(this, _currentActivity, R.raw.chipfx10).initLoadSoundID());

        _preset_container.add(_chiptune_samples);
        _PRESET_NAMES_.add("Chiptune");

        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808kick01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808snare01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808clap01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808hatc01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808hato01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808shaker01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808clave).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808cow).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808ride04).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808conga01).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808conga02).initLoadSoundID());
        _electro_samples.add(new Sample(this, _currentActivity, R.raw.tr808conga03).initLoadSoundID());

        _preset_container.add(_electro_samples);
        _PRESET_NAMES_.add("Electro");

        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660kick37).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660snare10).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660snare47).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660kick42).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660snare21).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660snare22).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660crash03).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660ride02).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660hatc05).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc65).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc40).initLoadSoundID());
        _jungle_samples.add(new Sample(this, _currentActivity, R.raw.dr660hato05).initLoadSoundID());

        _preset_container.add(_jungle_samples);
        _PRESET_NAMES_.add("Jungle");

        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.biabkick10).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.biabhardsn2).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc32).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.biabkick4).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.dr660snare11).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.biabhat2).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc69).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc68).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.biabhat3).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.dr660perc72).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.quasia011).initLoadSoundID());
        _tech_grit_samples.add(new Sample(this, _currentActivity, R.raw.quasia013).initLoadSoundID());

        _preset_container.add(_tech_grit_samples);
        _PRESET_NAMES_.add("Technical Grit");

        return (_preset_container.size() == _PRESET_NAMES_.size());
    }

    /**
     * Method that grabs all the user saved presets and adds them
     * to the preset container for instant usage.
     */
    public void getSavedPresets() {
        boolean kill = false;
        String[] savedPresets = _currentActivity.fileList();
        if (savedPresets.length > 0) {
            for (String filename : savedPresets) {

                // First let's see if the preset has already been added
                for (String presetNames : _PRESET_NAMES_) {
                    if (presetNames.equals(filename.replace(".pst", "")))
                        kill = true;
                }

                if(!kill) {
                    _PRESET_NAMES_.add(filename.replace(".pst", ""));

                    // Get a list of sounds in the preset from each file
                    ArrayList<String> presetItemsStr = new ArrayList<String>();
                    try {
                        presetItemsStr = IO_Man.getStringArrayListFromFileName(filename, _currentActivity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Add all the sounds to a preset kit
                    List<Sample> custom_preset = new ArrayList<Sample>();
                    for (String item : presetItemsStr) {
                        for (Sample sample : _samples) {
                            if (item.equals(sample.get_resource_name())) {
                                custom_preset.add(sample.initLoadSoundID());
                            }
                        }
                    }

                    // Add the kit to the container
                    _preset_container.add(custom_preset);
                }
                else
                    kill = false;
            }
        }
    }

    /**
     * Assign all the Samples Resource Names to a string list
     * upon a user designated change.
     *
     * @return  :
     *          Returns true on success, false otherwise.
     */
    private boolean assignResourceNames() {
        boolean error_free = true;
        for (int i = 0; i < _current_btn_assigned.size(); ++i) {
            try {
                _current_btn_assigned.set(i, _selected_samples.get(i).get_resource_name());
            } catch (Exception e) {
                Log.e("SoundManager: ", "Could not copy resource name at index[" +
                                String.valueOf(i) + "] because, " + e.getMessage());
                error_free = false;
            }
        }

        return error_free;
    }

    /**
     * Method that changes a soundId in the preset sounds (used by the hit
     * pads), to a given soundId from the sound bank.
     *
     * @param soundId :
     *                Parameter represents the soundId from the sound bank.
     * @param index   :
     *                Parameter represents the position in the array to set.
     * @return        :
     *                Returns true upon loading a sound to the presets, and
     *                assigning the resource names. False if the index is
     *                out of bounds.
     */
    public boolean setPresetSoundIndex(int soundId, int index) {
        if (index >= _selected_samples.size() || index < 0)
            return false;
        else {
            _selected_samples.set(index, new Sample(this, _currentActivity,
                    _samples.get(soundId).get_resource_id()).initLoadSoundID());

            return assignResourceNames();
        }
    }

    /**
     * Method that sets up the entire kit with a chosen preset.
     *
     * @param userChoiceStr :
     *                      Parameter represents the users choice
     *                      from the menu.
     * @return              :
     *                      Returns true on success whilst assigning
     *                      the resource names, false otherwise.
     */
    public boolean setEntirePresetKit(String userChoiceStr) {
        if (userChoiceStr != null) {
            _current_kit_assigned = userChoiceStr;
            int position = _PRESET_NAMES_.indexOf(userChoiceStr);
            Collections.copy(_selected_samples, _preset_container.get(position));

            return assignResourceNames();
        } else {
            return false;
        }
    }

    /**
     * Plays a sample from the selected sample list when a pad is pressed.
     *
     * @param btn_num   :
     *                  Parameter represents one of the 12 hit pads.
     */
    public void playSample(int btn_num) {
        _streamID = this.play(_selected_samples.get(btn_num).get_soundID(),
                _volume, _volume, 1, _loop, _pitch);
    }
}
