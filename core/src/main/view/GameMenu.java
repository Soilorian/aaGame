package main.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.IntMap;
import main.control.Controller;
import main.model.Game;
import main.model.Settings;
import main.util.HoverListener;
import main.util.actors.Ball;
import main.util.enums.GameText;

import java.awt.*;

import static com.badlogic.gdx.Gdx.input;

public class GameMenu extends Menu {
    private Settings settings;
    private Game game;
    private boolean duo;
    private final TextButton save, back;
    private final Table table, mainTable;
    private final Label errorLabel;
    final int fireF = controller.settings.getFireFP(), fireS = controller.settings.getFireSP(),
            leftF = controller.settings.getMoveLeftFP(), leftS = controller.settings.getMoveLeftSP(),
            rightF = controller.settings.getMoveRightFP(), rightS = controller.settings.getMoveRightSP(),
            freeze = controller.settings.getFreeze();


    public GameMenu(final Controller controller, boolean duo) {
        //start
        super(controller);
        settings = controller.settings;

        this.duo = duo;
        if (controller.lastSaved != null)
            game = controller.lastSaved;
        else
            game = new Game(settings.getMap(), settings.getInitialBallCount(),
                    settings.getDifficulty());
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);
        table = new Table(controller.getSkin());
        mainTable.setBounds(0, 0, 280, 1200);


        //body
        errorLabel = new Label("", controller.getSkin());
        errorLabel.setColor(Color.RED);
        errorLabel.setWrap(true);

        back = new TextButton(GameText.BACK.toString(), controller.getSkin(), "small");
        back.addListener(new HoverListener());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });



        save = new TextButton(GameText.SAVE.toString(), controller.getSkin(), "small");
        save.addListener(new HoverListener());




        //end
        stage.clear();
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (input.isKeyPressed(fireF)) {
                    fireF();
                } else if (input.isKeyPressed(fireS)){
                    fireS();
                } else if (input.isKeyPressed(leftF)){
                    leftF();
                } else if (input.isKeyPressed(leftS)){
                    leftS();
                } else if (input.isKeyPressed(rightF)){
                    rightF();
                } else if (input.isKeyPressed(rightS)){
                    rightS();
                } else if (input.isKeyPressed(freeze)){
                    freeze();
                }
                return super.keyDown(event, keycode);
            }
        });
        table.add(save).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(back).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(errorLabel).width(300);
        mainTable.setBackground(new TextureRegionDrawable(controller.getBackground()));
        mainTable.add(table);
        mainTable.left();
        stage.addActor(mainTable);
    }

    private void fireF() {
        stage.addActor(new Ball());
    }

    private void back() {
        controller.setScreen(new MainMenu(controller));
    }

}
