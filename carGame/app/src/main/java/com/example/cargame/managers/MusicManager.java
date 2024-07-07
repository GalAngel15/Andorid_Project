package com.example.cargame.managers;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicManager {

    private MediaPlayer mediaPlayer;
    private Context context;
    private int musicResource;
    private Executor executor;

    public MusicManager(Context context, int musicResource) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.musicResource = musicResource;
    }

    public void startMusic(boolean loop) {
        if (mediaPlayer == null) {
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, musicResource);
                mediaPlayer.setLooping(loop);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            });
        } else if (!mediaPlayer.isPlaying()) {
            executor.execute(() -> mediaPlayer.start());
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            executor.execute(()->{
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }

    // Pause the music
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Resume the music
    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // הגדרת עוצמת הקול
    public void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    public void momentMusic(MusicManager music, MusicManager collisionSound) {
        music.setVolume(0.2f);
        collisionSound.startMusic(false);
        new Handler().postDelayed(() -> music.setVolume(1.0f), 300);
    }

    public void releaseExecutor() {
        if (executor != null) {
            ((ExecutorService) executor).shutdown();
        }
    }

}
