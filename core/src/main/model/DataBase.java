package main.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.control.Controller;
import main.util.PlayerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class DataBase {
    private static final HashMap<String, Texture> pictures = new HashMap<>();
    private static final ArrayList<Player> players = new ArrayList<>();
    private static Gson gson = null;

    public static void load() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Player.class, new PlayerAdapter());
        builder.setPrettyPrinting();
        gson = builder.create();
        for (int i = 1; i <= 8; i++)
            addProfileIcon("profilepictures/profile" + i + ".jpg");
        loadPlayers();
    }


    public static void loadPlayers() {
        File file = new File("Data/Users.json");
        try {
            FileReader reader = new FileReader(file);
            Player[] players1 = gson.fromJson(reader, Player[].class);
            players.addAll(Arrays.asList(players1));
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePlayers() {
        File file = new File("Data/Users.json");
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            gson.toJson(players, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player getPlayerById(String id) {
        for (Player player : players) {
            if (player.getUsername().equals(id))
                return player;
        }
        return null;
    }

    public static boolean addPlayer(Player player) {
        return players.add(player);
    }

    public static void addProfileIcon(String address) {
        pictures.put(address, new Texture(address));
    }

    public static String getAddressFromTexture(Texture profileIcon) {
        for (String s : pictures.keySet()) {
            if (profileIcon.equals(pictures.get(s)))
                return s;
        }
        return null;
    }

    public static Texture getTextureFromAddress(String address) {
        if (pictures.containsKey(address)) return pictures.get(address);
        else return new Texture(address);
    }

    public static Texture getRandomTexture() {
        Random random = new Random();
        String s = (String) pictures.keySet().toArray()[random.nextInt(pictures.size() - 1)];
        return pictures.get(s);
    }

    public static void removeCurrentUser() {
        if (!Controller.currentPlayer.getUsername().equals("_GUEST_"))
            players.remove(Controller.currentPlayer);
    }

    public static Game getSavedGame() {
        return null;
    }
}
