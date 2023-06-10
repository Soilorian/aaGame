package main.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import main.util.objects.Cannon;
import main.view.GameMenu;

public class GameInputListener extends InputListener {
    final int shoot;
    final int right;
    final int left;
    Cannon cannon;
    GameMenu gameMenu;

    public GameInputListener(final int shoot, int right, int left, Cannon cannon, GameMenu gameMenu) {
        this.shoot = shoot;
        this.right = right;
        this.left = left;
        this.cannon = cannon;
        this.gameMenu = gameMenu;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == shoot) {
            cannon.setAmmo(cannon.getAmmo() - 1);
            gameMenu.spawnBall(cannon.getId(), new Vector2(cannon.getX() + cannon.getTexture().getWidth() / 2f,
                            cannon.getY()));
        } else if (keycode == left){
            cannon.setX(cannon.getX() - 10);
        } else if (keycode == right){
            cannon.setX(cannon.getX() + 10);
        }
        return super.keyDown(event, keycode);
    }
}
