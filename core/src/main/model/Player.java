package main.model;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;
import main.util.enums.Difficulty;

@Getter
@Setter
public class Player {

    private Texture profileIcon;

    private String profileIconAddress;
    private String username;
    private String password;
    private int maxScoreTime = 0;
    private int maxScore = 0;
    private Difficulty difficulty = Difficulty.MEDIUM;

    public void setProfileIcon(Texture profileIcon) {
        this.profileIcon = profileIcon;
        this.profileIconAddress = DataBase.getAddressFromTexture(profileIcon);
    }

    public Player( String username, String password, Texture profileIcon) {
        this.profileIcon = profileIcon;
        this.username = username;
        this.password = password;
        profileIconAddress = DataBase.getAddressFromTexture(profileIcon);
    }

    public Player( String username, String password,String profileIconAddress) {
        profileIcon = DataBase.getTextureFromAddress(profileIconAddress);
        this.profileIconAddress = profileIconAddress;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Texture getProfileIcon() {
        return profileIcon;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileIconAddress() {
        return profileIconAddress;
    }

    public boolean isPasswordWrong(String password) {
        return !this.password.equals(password);
    }

    public void updateMaxScore(int maxScore) {
        this.maxScore = Math.max(maxScore, this.maxScore);
    }

    public int getMaxScoreTime() {
        return maxScoreTime;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
