package main.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import main.Main;
import main.control.Controller;
import main.model.Game;
import main.util.enums.GameText;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMenu implements Screen {
    final Controller controller;
    private Game game;
    private final Stage stage;
    private final Skin skin;
    private final HashMap<GameText, Button> buttons = new HashMap<>();
    private final ArrayList<Button.ButtonStyle> buttonStyles = new ArrayList<>();
    private final Camera camera;


    public GameMenu() {
        //start
        this.controller = Main.controller;
        if (controller.lastSaved != null)
            game = controller.lastSaved;
        stage = new Stage();
        skin = new Skin();
        camera = new OrthographicCamera();

        //body


        //end
        Gdx.input.setInputProcessor(stage);
        ((OrthographicCamera) camera).setToOrtho(false, 972, 1200);
    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}
