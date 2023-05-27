package main.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import main.control.Controller;
import main.model.Settings;
import main.util.HoverListener;
import main.util.enums.Difficulty;
import main.util.enums.GameMap;
import main.util.enums.GameText;

import static com.badlogic.gdx.Gdx.input;

public class SettingMenu extends Menu {
    private final Table table, mainTable;
    private final TextButton selectP1Shoot, selectP2Shoot, selectP1Left, selectP2Left, selectP1Right, selectP2Right,
            selectFreeze, back;
    private int keyP1Shoot, keyP2Shoot, keyP1Left, keyP2Left, keyP1Right, keyP2Right, keyFreeze;
    private final SelectBox<GameMap> mapSelectBox;
    private final SelectBox<Difficulty> difficultySelectBox;
    private final CheckBox mute, grayScale;
    private final Slider ballCountSlider;
    private final Label errorLabel, difficultyLabel, mapLabel, ballCountLabel, labelP1Shoot, labelP2Shoot, labelP1Left,
            labelP2Left, labelP1Right, labelP2Right, labelFreeze;

    public SettingMenu(final Controller controller) {
        //start
        super(controller);
        keyP1Shoot = controller.settings.getFireFP();
        keyP2Shoot = controller.settings.getFireSP();
        keyP1Right = controller.settings.getMoveRightFP();
        keyP2Right = controller.settings.getMoveRightSP();
        keyP1Left = controller.settings.getMoveLeftFP();
        keyP2Left = controller.settings.getMoveLeftSP();
        keyFreeze = controller.settings.getFreeze();
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);
        table = new Table(controller.getSkin());
        mainTable.setBounds(0, 0, 280, 1200);


        //body
        selectP1Shoot = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP1Shoot = new Label(GameText.P1FIRE.toString(), controller.getSkin());
        addListeners(selectP1Shoot, 0);

        selectP2Shoot = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP2Shoot = new Label(GameText.P2FIRE.toString(), controller.getSkin());
        addListeners(selectP2Shoot, 1);

        selectP1Right = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP1Right = new Label(GameText.P1RIGHT.toString(), controller.getSkin());
        addListeners(selectP1Right, 2);

        selectP2Right = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP2Right = new Label(GameText.P2RIGHT.toString(), controller.getSkin());
        labelP2Right.setWrap(true);
        addListeners(selectP2Right, 3);

        selectP1Left = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP1Left = new Label(GameText.P1LEFT.toString(), controller.getSkin());
        addListeners(selectP1Left, 4);

        selectP2Left = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelP2Left = new Label(GameText.P2LEFT.toString(), controller.getSkin());
        addListeners(selectP2Left, 5);

        selectFreeze = new TextButton(GameText.CHANGE.toString(), controller.getSkin(), "small");
        labelFreeze = new Label(GameText.FREEZE_BUTTON.toString(), controller.getSkin());
        addListeners(selectFreeze, 6);

        back = new TextButton(GameText.BACK.toString(), controller.getSkin(), "small");
        back.addListener(new HoverListener());
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setScreen( new MainMenu(controller));
            }
        });

        ballCountLabel = new Label("Ball count : " + controller.settings.getInitialBallCount(), controller.getSkin());
        ballCountSlider = new Slider(Settings.MIN_BALL_COUNT, Settings.MAX_BALL_COUNT, 1, false, controller.getSkin());
        ballCountSlider.setValue(controller.settings.getInitialBallCount());
        ballCountSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ballCountLabel.setText("Ball count : " + (int) ballCountSlider.getValue());
            }
        });

        mapSelectBox = new SelectBox<>(controller.getSkin());
        mapSelectBox.setItems(GameMap.values());
        mapSelectBox.setSelected(GameMap.NORMAL);
        mapLabel = new Label(GameText.MAP.toString(), controller.getSkin());

        errorLabel = new Label("", controller.getSkin());
        errorLabel.setColor(Color.RED);
        errorLabel.setWrap(true);

        difficultyLabel = new Label(GameText.DIFFICULTY.toString(), controller.getSkin());
        difficultySelectBox = new SelectBox<>(controller.getSkin());
        difficultySelectBox.setItems(Difficulty.values());
        difficultySelectBox.setSelected(Difficulty.MEDIUM);

        mute = new CheckBox(GameText.MUTE.toString(), controller.getSkin());
        mute.setChecked(controller.settings.isMute());

        grayScale = new CheckBox(GameText.GRAYSCALE.toString(), controller.getSkin());
        grayScale.setChecked(controller.settings.isGrayScale());




        //end
        stage.clear();
        table.add(difficultyLabel).padTop(10).padBottom(10).width(100);
        table.add(difficultySelectBox).padTop(10).padBottom(10).width(200);
        table.row();
        table.add(mapLabel).padTop(10).padBottom(10);
        table.add(mapSelectBox).padTop(10).padBottom(10);
        table.row();
        table.add(mute).padTop(10).padBottom(10);
        table.add(grayScale).padTop(10).padBottom(10);
        table.row();
        table.add(labelP1Shoot).padTop(10).padBottom(10);
        table.add( selectP1Shoot).padTop(10).padBottom(10);
        table.row();
        table.add(labelP1Left).padTop(10).padBottom(10);
        table.add( selectP1Left).padTop(10).padBottom(10);
        table.row();
        table.add(labelP1Right).padTop(10).padBottom(10);
        table.add( selectP1Right).padTop(10).padBottom(10);
        table.row();
        table.add(labelP2Shoot).padTop(10).padBottom(10);
        table.add( selectP2Shoot).padTop(10).padBottom(10);
        table.row();
        table.add(labelP2Left).padTop(10).padBottom(10);
        table.add( selectP2Left).padTop(10).padBottom(10);
        table.row();
        table.add(labelP2Right).width(100);
        table.add( selectP2Right).padTop(10).padBottom(10);
        table.row();
        table.add(labelFreeze, selectFreeze).padTop(10).padBottom(10);
        table.row();
        table.add(back).colspan(2).padTop(10).padBottom(10);
        table.row();
        table.add(errorLabel).colspan(2);
        table.setDebug(true);
        mainTable.setBackground(new TextureRegionDrawable(controller.getBackground()));
        mainTable.add(table);
        mainTable.left();
        stage.addActor(mainTable);
    }

    private void addListeners(TextButton selectP1Right, final int pid) {
        selectP1Right.addListener(new HoverListener());
        selectP1Right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.setInputProcessor(new KeyReader(pid));
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }


    private class KeyReader extends InputAdapter {
        private final int pid;

        private KeyReader(int pid) {
            this.pid = pid;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (pid) {
                case 0:
                    keyP1Shoot = keycode;
                    break;
                case 1:
                    keyP2Shoot = keycode;
                    break;
                case 2:
                    keyP1Right = keycode;
                    break;
                case 3:
                    keyP2Right = keycode;
                    break;
                case 4:
                    keyP1Left = keycode;
                    break;
                case 5:
                    keyP2Left = keycode;
                    break;
                case 6:
                    keyFreeze = keycode;
                    break;
            }
            selectP1Shoot.setText(Input.Keys.toString(keyP1Shoot));
            selectP2Shoot.setText(Input.Keys.toString(keyP2Shoot));
            selectP1Right.setText(Input.Keys.toString(keyP1Right));
            selectP2Right.setText(Input.Keys.toString(keyP2Right));
            selectP1Left.setText(Input.Keys.toString(keyP1Left));
            selectP2Left.setText(Input.Keys.toString(keyP2Left));
            selectFreeze.setText(Input.Keys.toString(keyFreeze));
            input.setInputProcessor(stage);
            return true;
        }
    }
}
