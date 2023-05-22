package main.model;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class DataBase {
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final Texture background = new Texture("pictures\\aaBackground.jpg");
    private static Sound fireSound;
    private static Music music;


    public static Player getPlayerById(String id){
        for (Player player : players) {
            if (player.getUsername().equals(id))
                return player;
        }
        return null;
    }

    public static boolean addPlayer(Player player){
        return players.add(player);
    }

    public static Sound getFireSound() {
        return fireSound;
    }

    public static void setFireSound(Sound fireSound) {
        DataBase.fireSound = fireSound;
    }

    public static Music getMusic() {
        return music;
    }

    public static void setMusic(Music music) {
        DataBase.music = music;
    }

    public static Texture getBackground() {
        return background;
    }
}
