package main.control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.view.MainMenuScreen;

public class Controller extends Game {
    public static boolean isPersian = false;
    public SpriteBatch batch;
    public BitmapFont font;
    public Texture backgroundImage;
    public Sound fireSound;
    public Music music;
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        backgroundImage = new Texture("pictures/aaBackground.png");
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\cannonBlast.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("musics\\Clown.mp3"));
        music.setLooping(true);
        music.play();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}