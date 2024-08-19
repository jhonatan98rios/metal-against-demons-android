package com.teixeirarios.mad.lib.infra.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

// Music by Karl Casey @ White Bat Audio
// Doom Style Industrial Metal - Torn Flesh  Royalty Free No Copyright Background Music
public class BackgroundSound {
    private static Music backgroundMusic;

    public static void init() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/metal.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
    }

    public static void play() {
        if (backgroundMusic == null) {
            init();
        }
        backgroundMusic.play();
    }

    public static void stop() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public static void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}