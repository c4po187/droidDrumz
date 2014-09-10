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
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that deals with all Audio related data.
 * Class inherits from SoundPool.
 * Class implements Serializable so it can be passed
 * as an object instance via Intents.
 */
public class SoundManager extends SoundPool implements Serializable {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/
    
    private float _volume;
    private int _presetSoundIDs[];
    private ArrayList<Sample> _samples;
    private static int _SOUND_BANK_START_INDEX_ = 0x7f040000;
    private static int _SOUND_BANK_END_INDEX_ = 0x7f04003f;
    private Activity _currentActivity;
    // We'll use this to do something interesting later...
    private int _streamID;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public int[] get_presetSoundIDs() {
        return _presetSoundIDs;
    }

    public ArrayList<Sample> get_samples() {
        return _samples;
    }

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
        init();
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Initialise all objects and data within this class.
     */
    private void init() {
        // Set up AudioManager
        AudioManager audioManager = (AudioManager) _currentActivity.getSystemService(
                Context.AUDIO_SERVICE);
        _volume = ((float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) /
                (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        _currentActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Set up Samples
        _samples = new ArrayList<Sample>();

        for (int index = _SOUND_BANK_START_INDEX_; index <= _SOUND_BANK_END_INDEX_; ++index)
            _samples.add(new Sample(_currentActivity, index));

        // Set up preset sounds
        _presetSoundIDs = new int[12];

        _presetSoundIDs[0] = this.load(_currentActivity, 0x7f040011, 1);
        _presetSoundIDs[1] = this.load(_currentActivity, 0x7f040029, 1);
        _presetSoundIDs[2] = this.load(_currentActivity, 0x7f040000, 1);
        _presetSoundIDs[3] = this.load(_currentActivity, 0x7f040009, 1);
        _presetSoundIDs[4] = this.load(_currentActivity, 0x7f04000e, 1);
        _presetSoundIDs[5] = this.load(_currentActivity, 0x7f04000f, 1);
        _presetSoundIDs[6] = this.load(_currentActivity, 0x7f040002, 1);
        _presetSoundIDs[7] = this.load(_currentActivity, 0x7f040027, 1);
        _presetSoundIDs[8] = this.load(_currentActivity, 0x7f040024, 1);
        _presetSoundIDs[9] = this.load(_currentActivity, 0x7f040039, 1);
        _presetSoundIDs[10] = this.load(_currentActivity, 0x7f04003a, 1);
        _presetSoundIDs[11] = this.load(_currentActivity, 0x7f04003b, 1);
    }

    /**
     * Method that changes a soundId in the preset sounds (used by the hit
     * pads), to a given soundId from the sound bank.
     *
     * @param soundId :
     *                Parameter represents the soundId from the sound bank.
     * @param index   :
     *                Parameter represents the position in the array to set.
     * @return
     */
    public boolean setPresetSoundIndex(int soundId, int index) {
        if (index >= _presetSoundIDs.length)
            return false;
        else {
            _presetSoundIDs[index] = _samples.get(index).get_resource_id();
            return true;
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
        _streamID = this.play(_presetSoundIDs[soundID], _volume, _volume, 1, 0, 1.0f);
    }
}
