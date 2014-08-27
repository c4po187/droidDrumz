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
    private AudioManager _audioManager;
    private float _volume;
    private int _streamID;
    private int _presetSoundIDs[];

    /**
     * Accessors & Mutators
     */
    public AudioManager getAudioManager() {
        return _audioManager;
    }

    /**
     * Constructor
     * @param maxStreams :
     *                   Maximum number of streams this class
     *                   can handle synchronously.
     * @param streamType :
     *                   AudioManager stream type.
     * @param srcQuality :
     *                   Needed but unused parameter: always
     *                   set to zero.
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
     * @param currentActivity :
     *                        Parameter passed due it holding some vary valuable
     *                        members and methods, of which are needed in order
     *                        to help aid the initialization of this class.
     */
    private void init(Activity currentActivity) {
        // Set up AudioManager
        _audioManager = (AudioManager) currentActivity.getSystemService(
                Context.AUDIO_SERVICE);
        _volume = ((float) _audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) /
                (float) _audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
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
     * Play a sound from the sound bank, based on the given soundID.
     * @param soundID :
     *                Index of sound in sound bank(_presetSoundIDs).
     */
    public void playSound(int soundID) {
        _streamID = this.play(_presetSoundIDs[soundID], _volume, _volume, 1, 0, 1.0f);
    }

}
