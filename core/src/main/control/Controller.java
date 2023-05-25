package main.control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import main.model.DataBase;
import main.model.Player;
import main.model.Settings;
import main.view.*;

public class Controller extends Game {
    public static boolean isPersian = false;
    public SpriteBatch batch;
    public BitmapFont font;
    public Texture backgroundImage;
    public Sound fireSound;
    public Music music;
    public static Player currentPlayer;
    public main.model.Game lastSaved;
    public Settings settings;
    public AssetManager manager;
    private final String backgroundA = "pictures/aaBackground.jpg";
    private final String cannonA = "pictures/cannon.jpg";
    private final String blastA = "sounds/cannonBlast.mp3";
    private final String musicA = "musics/Clown.mp3";
    private final String skinA = "buttons/glassy/skin/glassy-ui.json";
    private final String grayscaleSkinA = "buttons/glassy-grayscale/skin/glassy-ui.json";

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        settings = new Settings();
        manager = new AssetManager();
        manageAssets();
        this.setScreen(new LoginMenu(this));
    }

    private void manageAssets() {
        manager.load(backgroundA, Texture.class);
        manager.load(cannonA, Texture.class);
        manager.load(blastA, Sound.class);
        manager.load(musicA, Music.class);
        manager.load(skinA, Skin.class);
        manager.load(grayscaleSkinA, Skin.class);
        DataBase.load();
        manager.finishLoading();
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        DataBase.savePlayers();
        batch.dispose();
        font.dispose();
    }

    public void changeScreen(Screen screen){
        setScreen(screen);
    }

    public boolean loadLastGame() {

        return false;
    }

    public String getBackgroundA() {
        return backgroundA;
    }

    public String getCannonA() {
        return cannonA;
    }

    public String getBlastA() {
        return blastA;
    }

    public String getMusicA() {
        return musicA;
    }

    public String getSkinA() {
        if (settings.isGrayScale()) return grayscaleSkinA;
        return skinA;
    }

    public Skin getSkin() {
        if (settings.isGrayScale()) return manager.get(grayscaleSkinA);
        return manager.get(skinA);
    }

    public static void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public Texture getBackground() {
        return manager.get(backgroundA);
    }
}