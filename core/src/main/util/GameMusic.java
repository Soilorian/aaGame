package main.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.Random;

public enum GameMusic {
    Clown("musics/Clown.mp3"),
    Ezio("musics/ezio.mp3"),
    DAYS_GONE("musics/daysgone.mp3");
    private final String path;
    private Music music;

    GameMusic(String path) {
        this.path = path;
    }

    public static Music randomSong() {
        return values()[new Random().nextInt(3)].getMusic();
    }

    public com.badlogic.gdx.audio.Music getMusic() {
        if (music == null)
            return music = Gdx.audio.newMusic(Gdx.files.internal(path));
        return music;
    }
}