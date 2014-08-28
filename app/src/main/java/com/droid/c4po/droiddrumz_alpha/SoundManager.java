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

/**
 * Class that deals with all Audio related data.
 * Class inherits from SoundPool.
 */
public class SoundManager extends SoundPool {

    /**
     * Members
     */
    private float _volume;
    private int _presetSoundIDs[];
    // We'll use this to do something interesting later...
    private int _streamID;

    /**
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
        init(currentActivity);
    }

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Initialise all objects and data within this class.
     *
     * @param currentActivity :
     *                        Parameter passed due it holding some vary valuable
     *                        members and methods, of which are needed in order
     *                        to help aid the initialization of this class.
     */
    private void init(Activity currentActivity) {
        // Set up AudioManager
        AudioManager audioManager = (AudioManager) currentActivity.getSystemService(
                Context.AUDIO_SERVICE);
        _volume = ((float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) /
                (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        currentActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Set up preset sounds
        _presetSoundIDs = new int[12];

        _presetSoundIDs[0] = this.load(currentActivity, R.raw.kick01, 1);
        _presetSoundIDs[1] = this.load(currentActivity, R.raw.snare01, 1);
        _presetSoundIDs[2] = this.load(currentActivity, R.raw.clap01, 1);
        _presetSoundIDs[3] = this.load(currentActivity, R.raw.hat_c01, 1);
        _presetSoundIDs[4] = this.load(currentActivity, R.raw.hat_o01, 1);
        _presetSoundIDs[5] = this.load(currentActivity, R.raw.hat_o02, 1);
        _presetSoundIDs[6] = this.load(currentActivity, R.raw.clave, 1);
        _presetSoundIDs[7] = this.load(currentActivity, R.raw.shaker01, 1);
        _presetSoundIDs[8] = this.load(currentActivity, R.raw.ride04, 1);
        _presetSoundIDs[9] = this.load(currentActivity, R.raw.tom01, 1);
        _presetSoundIDs[10] = this.load(currentActivity, R.raw.tom02, 1);
        _presetSoundIDs[11] = this.load(currentActivity, R.raw.tom03, 1);
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
