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
import java.util.ArrayList;

/**
 * Class that deals with all Audio related data.
 * Class inherits from SoundPool.
 * Class implements Serializable so it can be passed
 * as an object instance via Intents.
 */
public class SoundManager extends SoundPool {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private float _volume;
    private int _loop;
    private float _pitch;
    private int _presetSoundIDs[];
    private ArrayList<Sample> _samples;
    private static int _SOUND_BANK_START_INDEX_ = 0x7f040000;
    private static int _SOUND_BANK_END_INDEX_ = 0x7f040157;
    private Activity _currentActivity;
    private int _streamID;

    /*********************************************************************
     * Getters & Setters *************************************************
     *********************************************************************/

    public ArrayList<Sample> get_samples() {
        return _samples;
    }

    public void set_pitch(float pitch) { _pitch = pitch; }

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
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Initialise all objects and data within this class.
     */
    public void init() {
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
            if (index != 0x7f0400d9)
                _samples.add(new Sample(_currentActivity, index));
        }

        // Set up preset sounds
        _presetSoundIDs = new int[12];

        _presetSoundIDs[0] = this.load(_currentActivity, R.raw.tr808kick01, 1);
        _presetSoundIDs[1] = this.load(_currentActivity, R.raw.tr808snare01, 1);
        _presetSoundIDs[2] = this.load(_currentActivity, R.raw.tr808clap01, 1);
        _presetSoundIDs[3] = this.load(_currentActivity, R.raw.tr808hatc01, 1);
        _presetSoundIDs[4] = this.load(_currentActivity, R.raw.tr808hato01, 1);
        _presetSoundIDs[5] = this.load(_currentActivity, R.raw.tr808shaker01, 1);
        _presetSoundIDs[6] = this.load(_currentActivity, R.raw.tr808clave, 1);
        _presetSoundIDs[7] = this.load(_currentActivity, R.raw.tr808cow, 1);
        _presetSoundIDs[8] = this.load(_currentActivity, R.raw.tr808ride04, 1);
        _presetSoundIDs[9] = this.load(_currentActivity, R.raw.tr808conga01, 1);
        _presetSoundIDs[10] = this.load(_currentActivity, R.raw.tr808conga02, 1);
        _presetSoundIDs[11] = this.load(_currentActivity, R.raw.tr808conga03, 1);
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
        if (index >= _presetSoundIDs.length || index < 0)
            return false;
        else {
            _presetSoundIDs[index] = this.load(_currentActivity,
                    _samples.get(soundId).get_resource_id(), 1);
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
        _streamID = this.play(_presetSoundIDs[soundID], _volume, _volume, 1, _loop, _pitch);
    }
}
