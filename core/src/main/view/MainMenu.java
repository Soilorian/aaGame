package main.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import main.Main;
import main.control.Controller;
import main.util.enums.GameText;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenu extends Menu {
    private TextButton loadSavedGame,play, setting, deleteAccount, logout, scoreboard;
    private ImageButton profile;
    private final Camera camera;

    public MainMenu(Controller controller) {
        //start
        super(controller);
        camera = new OrthographicCamera();
        //body
        loadSavedGame = new TextButton(GameText.LOAD.toString(), controller.getSkin());






        //end
        Gdx.input.setInputProcessor(stage);
        ((OrthographicCamera) camera).setToOrtho(false, 972, 1200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.8F, 0.8F, 0.8F, 1);
        controller.batch.setProjectionMatrix(camera.combined);
        controller.batch.begin();
        controller.batch.draw(controller.getBackground(), 300, 0);
        controller.batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
