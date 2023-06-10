package main.control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import main.model.DataBase;
import main.model.Player;
import main.model.Settings;
import main.util.GameMusic;
import main.view.GameMenu;
import main.view.LoginMenu;

public class Controller extends Game {
    public static boolean isPersian = false;
    public static Player currentPlayer;
    public static main.model.Game currentGame;
    private final String backgroundA = "pictures/aaBackground.jpg";
    private final String cannonA = "pictures/cannon.jpg";
    private final String blastA = "sounds/cannonBlast.mp3";
    private final String musicA = "musics/Clown.mp3";
    private final String musicA2 = "musics/ezio.mp3";
    private final String skinA = "buttons/glassy/skin/glassy-ui.json";
    private final String grayscaleSkinA = "buttons/glassy-grayscale/skin/glassy-ui.json";
    private final String settingIconA = "pictures/setting.jpg";
    private final String changeIconA = "pictures/change.jpg";
    private final String scoreboardA = "pictures/leaderboard.jpg";
    public SpriteBatch batch;
    public BitmapFont font;
    public Texture backgroundImage;
    public Sound fireSound;
    public Music music;
    public main.model.Game lastSaved;
    public Settings settings;
    public AssetManager manager;
    private String winningBGA = "pictures/win.jpeg";

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    protected static boolean isNotValid(String s) {
        return !s.matches("^\\w{8,}$");
    }

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        settings = new Settings();
        manager = new AssetManager();
        manageAssets();
        music = GameMusic.randomSong();
        music.setLooping(true);
        music.play();
        this.setScreen(new LoginMenu(this));
    }

    public Texture resizer(float width, float height, Texture texture) {
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        Pixmap pixmap200 = texture.getTextureData().consumePixmap();
        Pixmap pixmap100 = new Pixmap((int) width, (int) height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture result = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();
        return result;
    }

    private void manageAssets() {
        manager.load(backgroundA, Texture.class);
        manager.load(cannonA, Texture.class);
        manager.load(settingIconA, Texture.class);
        manager.load(changeIconA, Texture.class);
        manager.load(scoreboardA, Texture.class);
        manager.load(blastA, Sound.class);
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

    public Texture getBackground() {
        return manager.get(backgroundA);
    }

    public Texture getSettingIcon() {
        return manager.get(settingIconA);
    }

    public Music getMusicTheme() {
        if (screen instanceof GameMenu)
            return manager.get(musicA);
        return manager.get(musicA2);
    }

    public Texture getChangeIcon() {
        return manager.get(changeIconA);
    }

    public Texture getScoreboard() {
        return manager.get(scoreboardA);
    }

    public Texture getCannon() {
        return manager.get(cannonA);
    }

    public Music getMusic() {
        return music;
    }



    public GameMusic getSelectedMusic() {
        for (GameMusic value : GameMusic.values()) {
            if (value.getMusic().equals(music))
                return value;
        }
        return null;
    }

    public void setSelectedMusic(GameMusic selected, boolean b) {
        music.pause();
        music = selected.getMusic();
        music.setLooping(b);
        if (!settings.isMute())
            music.play();
    }

    public void pauseMusic() {
        if (music.isPlaying())
            music.pause();
        else if (!settings.isMute())
            music.play();
    }

    public Sound getFireSound() {
        return manager.get(blastA);
    }

    public Texture getLoosingBG() {
        return resizer(600, 600, new Texture("pictures/loose.jpeg"));
    }

    public Texture grayScaler(Texture texture) {

        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        for (int i = 0; i < pixmap.getHeight(); i++) {
            for (int j = 0; j < pixmap.getWidth(); j++) {
                pixmap.setColor(pixmap.getPixel(j, i));

            }
        }
        return texture;
    }

    public Texture getWinningBG() {
        return resizer(600, 600, new Texture("pictures/win.jpeg"));
    }
}