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
import java.io.File;
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
    private List<Integer>            _selected_preset;
    private List<List<Integer>>      _preset_lists;
    private ArrayList<Sample>        _samples;

    private List<String>             _current_btn_assigned;
    private String                   _current_kit_assigned;

    /**
     * Presets
     */

    public static ArrayList<String>  _PRESET_NAMES_;
    private List<Integer>            _electro,
                                     _tech_grit,
                                     _chiptune;
    
    private List<String>             _electro_str,
                                     _tech_grit_str,
                                     _chiptune_str;

    private ArrayList<ArrayList<String>>       _custom_presets;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public ArrayList<Sample> get_samples() {
        return _samples;
    }

    public void set_pitch(float pitch) { _pitch = pitch; }

    public String get_current_kit_assigned() { return _current_kit_assigned; }

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
        _selected_preset = new ArrayList<Integer>();
        _preset_lists = new ArrayList<List<Integer>>();
        _current_btn_assigned = new ArrayList<String>();
        _current_kit_assigned = "";
        // Init our presets
        _electro = new ArrayList<Integer>();
        _tech_grit = new ArrayList<Integer>();
        _chiptune = new ArrayList<Integer>();

        _custom_presets = new ArrayList<ArrayList<String>>();
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

            for (int index = _SOUND_BANK_START_INDEX_; index <= _SOUND_BANK_END_INDEX_; ++index) {
                // We don't want the splash loop in there
                if (index != 0x7f040221)
                    _samples.add(new Sample(_currentActivity, index));
            }

            // Init the Selected Presets to 12 non-nullable values
            for (int i = 0; i < 12; ++i) {
                _selected_preset.add(i);
                _current_btn_assigned.add("empty");
            }

            /* Setup the Current Assigned Button strings initially to
             * mirror the default electro kit
             */
            Collections.copy(_current_btn_assigned, _electro_str);

            // Set selected preset to electro initially using a deep copy
            Collections.copy(_selected_preset, _preset_lists.get(1));
            _current_kit_assigned = _PRESET_NAMES_.get(1);

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

        

        // Fill up Chiptune preset
        _chiptune.add(this.load(_currentActivity, R.raw.chipkick2, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipsn5, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipsn6, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipkick1, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chiphat6, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chiphat8, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx1, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx3, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx5, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx6, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx7, 1));
        _chiptune.add(this.load(_currentActivity, R.raw.chipfx10, 1));

        // Add Chiptune list to major preset list, and its name to a String list
        _preset_lists.add(_chiptune);
        _PRESET_NAMES_.add("Chiptune");

        // Fill up Electro Presets, Strings, etc...
        _electro.add(this.load(_currentActivity, R.raw.tr808kick01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808snare01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808clap01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808hatc01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808hato01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808shaker01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808clave, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808cow, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808ride04, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808conga01, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808conga02, 1));
        _electro.add(this.load(_currentActivity, R.raw.tr808conga03, 1));

        _preset_lists.add(_electro);
        _PRESET_NAMES_.add("Electro");

        // Fill up Technical Grit presets, Strings, etc...
        _tech_grit.add(this.load(_currentActivity, R.raw.biabkick10, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.biabhardsn2, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.dr660perc32, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.biabkick4, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.dr660snare11, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.biabhat2, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.dr660perc69, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.dr660perc68, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.biabhat3, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.dr660perc72, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.quasia011, 1));
        _tech_grit.add(this.load(_currentActivity, R.raw.quasia013, 1));

        _preset_lists.add(_tech_grit);
        _PRESET_NAMES_.add("Technical Grit");

        // File testing
        String dir_str = _currentActivity.getApplicationInfo().dataDir;
        Log.d("Data Directory: ", dir_str);
        File dir = new File(dir_str);
        File []dir_contents = dir.listFiles();

        for (File f : dir_contents) {
            if (f.isFile()) {
                _PRESET_NAMES_.add(f.getName().replace(".pst", ""));

            }
        }
        
        return (setupPresetKitStrings() && 
                (_preset_lists.size() == _PRESET_NAMES_.size()));
    }

    /**
     * Method that sets up the preset kit name strings
     *
     * @return :
     *         True if number of setups match.
     */
    private boolean setupPresetKitStrings() {
        int listTracker = 0, num_setups = 3;

        _electro_str = new ArrayList<String>();
        _electro_str.add("tr808kick01");
        _electro_str.add("tr808snare01");
        _electro_str.add("tr808clap01");
        _electro_str.add("tr808hatc01");
        _electro_str.add("tr808hato01");
        _electro_str.add("tr808shaker01");
        _tech_grit_str = new ArrayList<String>();
        _tech_grit_str.add("biabkick10");
        _tech_grit_str.add("biabhardsn2");
        _tech_grit_str.add("dr660perc32");
        _tech_grit_str.add("biabkick4");
        _electro_str.add("tr808clave");
        _electro_str.add("tr808cow");
        _electro_str.add("tr808ride04");
        _electro_str.add("tr808conga01");
        _electro_str.add("tr808conga02");
        _electro_str.add("tr808conga03");
        ++listTracker;

        _tech_grit_str.add("dr660snare11");
        _tech_grit_str.add("biabhat2");
        _tech_grit_str.add("dr660perc69");
        _tech_grit_str.add("dr660perc68");
        _tech_grit_str.add("biabhat3");
        _tech_grit_str.add("dr660perc72");
        _tech_grit_str.add("quasia011");
        _tech_grit_str.add("quasia013");
        ++listTracker;

        _chiptune_str = new ArrayList<String>();
        _chiptune_str.add("chipkick2");
        _chiptune_str.add("chipsn5");
        _chiptune_str.add("chipsn6");
        _chiptune_str.add("chipkick1");
        _chiptune_str.add("chiphat6");
        _chiptune_str.add("chiphat8");
        _chiptune_str.add("chipfx1");
        _chiptune_str.add("chipfx3");
        _chiptune_str.add("chipfx5");
        _chiptune_str.add("chipfx6");
        _chiptune_str.add("chipfx7");
        _chiptune_str.add("chipfx10");
        ++listTracker;

        return (listTracker == num_setups);
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
     *                Returns true upon loading a sound to the presets,
     *                false if the index is out of bounds.
     */
    public boolean setPresetSoundIndex(int soundId, int index) {
        if (index >= _selected_preset.size() || index < 0)
            return false;
        else {
            _selected_preset.set(index, this.load(_currentActivity,
                    _samples.get(soundId).get_resource_id(), 1));
            _current_btn_assigned.set(index, _samples.get(soundId)
                    .get_resource_name());
            return true;
        }
    }

    /**
     * Method that sets up the entire kit with a chosen preset.
     *
     * @param userChoiceStr :
     *                      Parameter represents the users choice
     *                      from the menu.
     * @return              :
     *                      Returns true on success, false otherwise.
     */
    public boolean setEntirePresetKit(String userChoiceStr) {
        if (userChoiceStr != null) {
            _current_kit_assigned = userChoiceStr;

            if (_current_kit_assigned.equals("Chiptune"))
                Collections.copy(_current_btn_assigned, _chiptune_str);
            else if (_current_kit_assigned.equals("Electro"))
                Collections.copy(_current_btn_assigned, _electro_str);
            else
                Collections.copy(_current_btn_assigned, _tech_grit_str);

            int position = _PRESET_NAMES_.indexOf(userChoiceStr);
            Collections.copy(_selected_preset, _preset_lists.get(position));
            return true;
        } else {
            return false;
        }
    }


    /**
     * Plays a sound from the sound bank when a pad is pressed,
     * based on the given soundID.
     *
     * @param soundID :
     *                Index of sound in sound bank(_presetSoundIDs).
     */
    public void playSound(int soundID) {
        _streamID = this.play(_selected_preset.get(soundID), _volume, _volume, 1, _loop, _pitch);
    }
}
