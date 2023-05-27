package main.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import main.control.Controller;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public abstract class Menu implements Screen {
    protected final Controller controller;
    protected final Stage stage;

    protected Menu(Controller controller) {
        this.controller = controller;
        stage = new Stage();
        input.setInputProcessor(stage);
        stage.setViewport(new FitViewport(graphics.getWidth(), graphics.getHeight()));
    }

    protected Menu getScreen() {
        return (Menu) controller.getScreen();
    }

    protected void setScreen(Menu menu) {
        controller.setScreen(menu);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
    }
}
