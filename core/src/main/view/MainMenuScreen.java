package main.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import main.control.Controller;
import main.util.enums.GameText;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuScreen implements Screen {
    private final Controller controller;
    private final Stage stage;
    private final HashMap<GameText , Button> buttons = new HashMap<>();
    private final ArrayList<Button.ButtonStyle> buttonStyles = new ArrayList<>();
    private TextButton.TextButtonStyle textButtonStyle;
    private final Camera camera;

    public MainMenuScreen(final Controller controller) {
        final Skin skin = new Skin();
        stage = new Stage();
        this.controller = controller;
        Gdx.input.setInputProcessor(stage);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("buttons/glassy/skin/glassy-ui.atlas")));
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = controller.font;
        textButtonStyle.up = skin.getDrawable("button");
        buttonStyles.add(textButtonStyle);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = controller.font;
        textButtonStyle.up = skin.getDrawable("button-down");
        buttonStyles.add(textButtonStyle);
        Button button = new TextButton(GameText.START_GAME.getMessage(), (TextButton.TextButtonStyle) buttonStyles.get(0));
        button.setX(100);
        button.setY(100);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                buttons.get(GameText.START_GAME).setStyle(buttonStyles.get(1));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                buttons.get(GameText.START_GAME).setStyle(buttonStyles.get(0));
            }
        });
        stage.addActor(button);
        buttons.put(GameText.START_GAME, button);
        camera = new OrthographicCamera();
        ((OrthographicCamera) camera).setToOrtho(false, 672, 1200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        controller.batch.setProjectionMatrix(camera.combined);
        controller.batch.begin();
        controller.batch.draw(controller.backgroundImage, 0, 0);
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
