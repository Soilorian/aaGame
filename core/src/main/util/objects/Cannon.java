package main.util.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.Setter;
import main.model.Game;


@Getter
@Setter
public class Cannon {
    private final Texture texture;
    private final Skin skin;
    private final int shootKey, leftKey, rightKey;
    private final Game game;
    private int ammo;
    private Color color;
    private int x;
    private int y = 20;
    private int id;

    public Cannon(int ammo, Color color, Texture texture, Skin skin, int shootKey, int leftKey, int rightKey,
                  Game game, int id) {
        this.game = game;
        this.ammo = ammo;
        this.color = color;
        this.texture = texture;
        this.skin = skin;
        this.shootKey = shootKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.id = id;
        if (id == 2)
            y = Gdx.graphics.getHeight() - 40;

    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
        if (id == 2)
            batch.draw(new TextureRegion(texture), x, y, x, y, texture.getWidth(), texture.getHeight(), 1, 1,
                    180);
    }
}
