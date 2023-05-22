package main.model;

import com.badlogic.gdx.graphics.Texture;

public class Player {
    private Texture profileIcon;
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
