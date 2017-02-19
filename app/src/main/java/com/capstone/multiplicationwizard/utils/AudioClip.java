package com.capstone.multiplicationwizard.utils;



import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import com.capstone.multiplicationwizard.R;

public class AudioClip {

    private static AudioClip instance;
    private boolean loaded = false;
    private int priority;
    private Context context;
    private SoundPool soundPool;
    private static int clapSoundID;

    public static synchronized AudioClip getInstance(Context androidContext) {
        if (instance == null) {
            instance = new AudioClip(10, AudioManager.STREAM_MUSIC, 0, androidContext);
        }
        return instance;
    }

    private AudioClip(int maxStreams, int streamType, int srcQuality, Context context) {

        this.context = context;

        soundPool = new SoundPool(maxStreams, streamType, srcQuality);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        loadinSounds();

    }

    public int playSound(int soundID, float leftVolume, float rightVolume) {
        int streamID = 0;
        if (loaded) {
            streamID = soundPool.play(soundID, leftVolume, rightVolume, 1, 0, 1f);
            priority = priority++;
        }

        return streamID;
    }

    public int playLoop(int soundID, float leftVolume, float rightVolume) {
        int streamID = 0;
        if (loaded) {
            streamID = soundPool.play(soundID, leftVolume, rightVolume, 1, -1, 1f);
            priority = priority++;
        }
        return streamID;
    }

    public void pauseSound(int streamID) {
        // If the stream is not playing (e.g. is stopped or was previously paused), calling this function will have no effect.
        soundPool.pause(streamID);
    }

    public void stopSound(int streamID) {
        // If the stream is not playing, it will have no effect.
        soundPool.stop(streamID);
    }

    public void autoPause() {
        soundPool.autoPause();
    }

    public void autoResume() {
        soundPool.autoResume();
    }

    private void loadinSounds() {
        clapSoundID = soundPool.load(context, R.raw.clap, 1);
    }

    public static int clapSoundID() {
        return clapSoundID;
    }

}
