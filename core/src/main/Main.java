package main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;
import main.control.Controller;

public class Main extends ApplicationAdapter {
    public static Controller controller;

    @Override
    public void create () {
        controller = new Controller();
        controller.create();
    }

    @Override
    public void render() {
        controller.render();
    }

    @Override
    public void dispose () {
        controller.dispose();
    }
}
