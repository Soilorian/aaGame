package main.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import main.control.Controller;
import main.model.DataBase;
import main.model.Game;
import main.model.Settings;
import main.util.GameInputListener;
import main.util.GameMusic;
import main.util.HoverListener;
import main.util.Timer;
import main.util.enums.GameText;
import main.util.objects.Ball;
import main.util.objects.Cannon;
import main.util.objects.TargetObject;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;


public class GameMenu extends Menu {
    public static float outlineIncrement;
    public static GameMenu singleton;
    final int fireF = controller.settings.getFireFP(), fireS = controller.settings.getFireSP(),
            leftF = controller.settings.getMoveLeftFP(), leftS = controller.settings.getMoveLeftSP(),
            rightF = controller.settings.getMoveRightFP(), rightS = controller.settings.getMoveRightSP(),
            freezeButton = controller.settings.getFreeze();
    private final Slider freezeSlider;
    private final SpriteBatch batch;
    private final TextButton save, back;
    private final Table table, mainTable;
    private final Label errorLabel;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final BitmapFont font = controller.font;
    private final Random random = new Random();
    private final Timer reverseDirTimer = new Timer(4);
    private final Timer shrinkBallTimer = new Timer(.5f);
    private final Timer visInvisTimer = new Timer(1f);
    private final Timer randomShootDirTimer = new Timer(4);
    private final Array<Ball> floatingBalls;
    private float slowModeTime;
    private Settings settings;
    private Game game;
    private boolean duo;
    private Sound soundEffect = controller.getFireSound();
    private float endGameTime;
    private float planetRotationSpeed;
    private ArrayList<Ball> balls = new ArrayList<>();
    private int ballCount = 0;
    private boolean visInvis = false;
    private float shootDir;
    private float playerPosition;
    private boolean isPaused;
    private boolean gameWon;
    private boolean gameLost;
    private Color bkgColor = Color.GRAY;
    private Label remainingBallLabel, scoreLabel, remainingTimeLabel;
    private float passedTime = 0;
    private Image backgroundImage = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getBackground()));
    private Window window, pauseWindow;
    private TextButton okButton = new TextButton("ok!", controller.getSkin());
    private boolean freeze;


    public GameMenu(final Controller controller, boolean duo) {
        //start
        super(controller);
        stage.clear();
        settings = controller.settings;
        this.duo = duo;
        if (controller.lastSaved != null)
            game = controller.lastSaved;
        else
            game = new Game(controller, duo);
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);
        table = new Table(controller.getSkin());
        mainTable.background(new TextureRegionDrawable(controller.getBackground()));
        mainTable.setFillParent(true);
        mainTable.setBounds(0, 0, 280, 1200);
        if (soundEffect == null) soundEffect = controller.fireSound;
        batch = new SpriteBatch();
        singleton = this;
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());
        ballCount = settings.getInitialBallCount();
        floatingBalls = new Array<>();

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

        int ammo = game.getCannon1().getAmmo();
        if (duo)
            ammo += game.getCannon2().getAmmo();
        remainingBallLabel = new Label("ammo left: " + String.valueOf(ammo), controller.getSkin());
        remainingBallLabel.setPosition(100, 5);


        scoreLabel = new Label(String.valueOf(getScore()), controller.getSkin());
        scoreLabel.setPosition(100, 35);

        remainingTimeLabel = new Label("", controller.getSkin());
        remainingTimeLabel.setPosition(30, 65);

        save = new TextButton(GameText.SAVE.toString(), controller.getSkin(), "small");
        save.addListener(new HoverListener());

        freezeSlider = new Slider(0, 1, 0.1f, true, controller.getSkin());
        freezeSlider.setDisabled(true);
        slowModeTime = settings.getDifficulty().getFreeze();

        //end
        stage.addActor(backgroundImage);
        stage.addActor(remainingBallLabel);
        stage.addActor(remainingTimeLabel);
        stage.addActor(scoreLabel);
        floatingBalls.add(new Ball(game, new Circle(10, 10, 15)));
        stage.addActor(freezeSlider);
        stage.addListener(new GameInputListener(fireF, rightF, leftF, game.getCannon1(), this));
        if (duo) stage.addListener(new GameInputListener(fireS, rightS, leftS, game.getCannon2(), this));

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    if (isPaused)
                        resume();
                    else
                        pause();
                }
                if (keycode == freezeButton) {
                    freeze();
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    private static Color lerp(Color a, Color b, float t) {
        float r = MathUtils.lerp(a.r, b.r, t);
        float g = MathUtils.lerp(a.g, b.g, t);
        float bl = MathUtils.lerp(a.b, b.b, t);
        float al = MathUtils.lerp(a.a, b.a, t);
        return new Color(r, g, bl, al);
    }

    private void freeze() {
        if (freezeSlider.getValue() == 1) {
            freezeSlider.setValue(0);
            freeze = true;
        }
        com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                freeze = false;
            }
        }, settings.getDifficulty().getFreeze());
    }

    private void back() {
        controller.setScreen(new MainMenu(controller));
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
        if (isPaused && !gameWon && !gameLost) return;
        batch.begin();
        if (duo) {
            game.getCannon2().draw(batch);
        }
        game.getCannon1().draw(batch);
        batch.end();
        for (TargetObject targetObject : game.getTargetObjects())
            targetObject.render();
        renderFloatingBalls();
        if (!isPaused) {
            update(deltaTime);
        }
        if (gameLost) {
            gameLostUpdate();
        }
        if (gameWon) {
            gameWonUpdate();
        }
    }

    private void renderFloatingBalls() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        for (Ball floatingBall : floatingBalls) {
            Circle circle = floatingBall.getCircle();
            shapeRenderer.circle(circle.x, circle.y, circle.radius);
        }
        shapeRenderer.end();
    }

    private void update(float deltaTime) {
        passedTime += deltaTime;
        if (ballCount == 0) {
            com.badlogic.gdx.utils.Timer.instance().clear();
            gameWon = true;
        } else {
            int ratio = game.getCannon1().getAmmo() * 100 / settings.getInitialBallCount();
            game.handleStages(ratio);
        }
        float rotationSpeed = game.getDifficulty().getSpeed();
        if (freeze) {
            float effect = 0.5f;
            slowModeTime -= deltaTime;
            rotationSpeed *= effect;
        }
        if (game.update(rotationSpeed)) {
            com.badlogic.gdx.utils.Timer.instance().clear();
            gameLost = true;
        }
        int ammo = game.getCannon1().getAmmo();
        if (duo)
            ammo += game.getCannon2().getAmmo();
        remainingBallLabel.setText(String.valueOf(ammo));
        scoreLabel.setText(String.valueOf(getScore()));
        floatingBallsUpdate();
    }

    private void floatingBallsUpdate() {
        Array.ArrayIterator<Ball> iterator = floatingBalls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            if (ball.getDirection() == 1)
                ball.getCircle().y += 10;
            else
                ball.getCircle().y -= 10;
            ball.getCircle().x += game.getWind();
            for (TargetObject targetObject : game.targetObjects)
                if (targetObject.isInRange(ball.getCircle())) {
                    iterator.remove();
                    targetObject.addBall(ball);
                    if (targetObject.checkCollision()) gameLost = true;
                }
        }
    }

    private void gameWonUpdate() {
        isPaused = true;
        window = new Window("you win!", controller.getSkin());
        window.addActor(okButton);
        window.setWidth(600);
        window.setHeight(600);
        okButton.setPosition((window.getWidth() - okButton.getWidth()) / 2, 10);
        window.setPosition(0, 300);
        window.background(new TextureRegionDrawable(controller.getWinningBG()));
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Controller.currentPlayer.equals(DataBase.getPlayerById("_GUEST_")))
                    controller.setScreen(new ProfileMenu(controller));
                else
                    controller.setScreen(new MainMenu(controller));
            }
        });
        for (TargetObject targetObject : game.getTargetObjects()) {
            for (Ball ball : targetObject.getBalls()) {
                Vector2 nor =
                        new Vector2(ball.getCircle().x - targetObject.getCircle().x,
                                ball.getCircle().y - targetObject.getCircle().y).nor();
                ball.getCircle().x += nor.x;
                ball.getCircle().y += nor.y;
            }
        }
        stage.addActor(window);
    }

    private void gameLostUpdate() {
        isPaused = true;
        window = new Window("you loose!", controller.getSkin());
        window.addActor(okButton);
        window.setWidth(600);
        window.setHeight(600);
        okButton.setPosition((window.getWidth() - okButton.getWidth()) / 2, 10);
        window.setPosition(0, 300);
        window.background(new TextureRegionDrawable(controller.getLoosingBG()));
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Controller.currentPlayer.equals(DataBase.getPlayerById("_GUEST_")))
                    controller.setScreen(new ProfileMenu(controller));
                else
                    controller.setScreen(new MainMenu(controller));
            }
        });
        for (TargetObject targetObject : game.getTargetObjects()) {
            for (Ball ball : targetObject.getBalls()) {
                ball.getCircle().y -= 10;
            }
        }
        stage.addActor(window);
    }

    @Override
    public void dispose() {
        stage.dispose();
        stage = new Stage();
    }

    public Color transformColor(Color in) {
        if (settings.isGrayScale()) {
            float gray = (in.r + in.g + in.b) / 3;
            return new Color(gray, gray, gray, in.a);
        }
        return in;
    }
    private int getScore() {
        int score = 0;
        for (TargetObject targetObject : game.getTargetObjects()) score += targetObject.getBalls().size();
        score *= settings.getDifficulty().getSpeed();
        if (gameWon) score *= 2;
        return score;
    }

    @Override
    public void pause() {
        isPaused = true;
        pauseWindow = new Window("", controller.getSkin());
        Table table = new Table();
        final TextButton playButton;
        if (controller.getMusic().isPlaying())
            playButton = new TextButton("Pause", controller.getSkin(), "small");
        else
            playButton = new TextButton("Play", controller.getSkin(), "small");
        final SelectBox<GameMusic> musicSelectBox = new SelectBox<>(controller.getSkin());
        musicSelectBox.setItems(GameMusic.Clown, GameMusic.Ezio, GameMusic.DAYS_GONE);
        musicSelectBox.setSelected(controller.getSelectedMusic());

        final TextButton restartButton = new TextButton("Restart", controller.getSkin(), "small");
        final TextButton saveButton = new TextButton("Save", controller.getSkin(), "small");
        final TextButton backButton = new TextButton("Back to main menu", controller.getSkin(), "small");

        table.add(new Label("Pause menu", controller.getSkin())).colspan(2).spaceBottom(20);
        table.row();
        table.add(new Label("move left : LEFT Key", controller.getSkin())).colspan(2);
        table.row();
        table.add(new Label("move right : RIGHT Key", controller.getSkin())).colspan(2);
        table.row();
        table.add(new Label("frozen mode : TAB key", controller.getSkin())).colspan(2);
        table.row();
        table.add(new Label("main shoot : " + Input.Keys.toString(settings.getFireFP()) + " key",
                controller.getSkin())).colspan(2);
        table.row();
        table.add(new Label("alt shoot : " + Input.Keys.toString(settings.getFireSP()) + " key",
                controller.getSkin())).colspan(2);
        table.row();
        table.add(playButton);
        table.add(musicSelectBox);
        table.row();
        table.add(saveButton).width(100);
        table.add(restartButton).width(100);
        table.row();
        table.add(backButton).colspan(2);
        pauseWindow.setWidth(972);
        pauseWindow.setHeight(1200);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                save();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setScreen(new MainMenu(controller));
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setScreen(new GameMenu(controller, duo));
            }
        });
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playMusic(playButton);
            }
        });
        musicSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                changeMusic(musicSelectBox);
            }
        });
        pauseWindow.add(table);
        stage.addActor(pauseWindow);
    }

    private void save() {

    }

    private void changeMusic(SelectBox<GameMusic> musicSelectBox) {
        controller.setSelectedMusic(musicSelectBox.getSelected(), true);
    }

    private void playMusic(TextButton button) {
        if (controller.getMusic().isPlaying())
            button.setText("Play");
        else
            button.setText("Pause");
        controller.pauseMusic();
    }

    @Override
    public void resume() {
        isPaused = false;
        stage.getActors().removeValue(pauseWindow, true);
    }

    public void spawnBall(int playerId, Vector2 position) {
        soundEffect.play();
        floatingBalls.add(new Ball(game, new Circle(position.x, position.y, 15), playerId));
        if (freezeSlider.getValue() != 1)
            freezeSlider.setValue(freezeSlider.getValue() + 0.1f);
    }
}
