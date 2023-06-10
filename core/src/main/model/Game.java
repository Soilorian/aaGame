package main.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.Setter;
import main.control.Controller;
import main.util.objects.Cannon;
import main.util.objects.TargetObject;
import main.util.enums.Difficulty;
import main.util.enums.GameMap;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
public class Game {
    private final Difficulty difficulty;
    private final Controller controller;
    private Cannon cannon2 = null;
    private final Cannon cannon1;
    public ArrayList<TargetObject> targetObjects = new ArrayList<>();
    private float freezeBar = 0, wind = 0;
    private boolean stage1, stage2, stage3, lost, win, duo = false;
    private GameMap gameMap;
    private Random random = new Random();
    private static int prevRatio = 100;
    final float[] add = new float[1];

    public Game(Controller controller){
        this(controller, false);
    }

    public Game(Controller controller, boolean duo) {
        this.duo = duo;
        add[0] = 500;
        Settings settings = controller.settings;
        this.gameMap = settings.getMap();
        this.difficulty = settings.getDifficulty();
        this.controller = controller;
        switch (gameMap) {
            case NORMAL: {
                targetObjects.add(new TargetObject(new Circle(636, 600, 70), this));
                break;
            }
            case DUAL_BALL: {
                targetObjects.add(new TargetObject(new Circle(468, 600, 50), this));
                targetObjects.add(new TargetObject(new Circle(804, 600, 50), this));
                break;
            }
            case TRIPLE_PLAY: {
                targetObjects.add(new TargetObject(new Circle(534, 450, 30), this));
                targetObjects.add(new TargetObject(new Circle(651, 750, 30), this));
                targetObjects.add(new TargetObject(new Circle(738, 450, 30), this));
                break;
            }
        }
        if (duo) {
            cannon2 = new Cannon(settings.getInitialBallCount(), Color.BLACK, controller.getCannon(),
                    controller.getSkin(), settings.getFireSP(), settings.getMoveLeftSP(), settings.getMoveRightSP(),
                    this, 2);
            cannon2.setX(Gdx.graphics.getWidth()/3 * 2);
        }
        cannon1 = new Cannon(settings.getInitialBallCount(), Color.BLACK, controller.getCannon(),
                controller.getSkin(),
                controller.settings.getFireFP(), controller.settings.getMoveLeftFP(),
                controller.settings.getMoveRightFP(), this, 1);
        cannon1.setX(Gdx.graphics.getWidth()/3 * 2);


    }

    public boolean update(float a) {
        for (TargetObject targetObject : targetObjects) {
            float radius = 1;
            if (stage1) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        stageOne();
                    }
                }, 1, 4 + random.nextFloat() + random.nextInt(5));
                stage1 = false;
            }
            if (stage2) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        for (TargetObject object : targetObjects) {
                            object.reverseVisibility();
                        }
                    }
                }, 1, 1);
                stage2 = false;
            }
            if (stage3) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        addWind();
                    }
                }, 1, 4 + random.nextFloat() + random.nextInt(5));
                stage3 = false;
            }
            radius += add[0] / 1000f - 0.5f;
            if (targetObject.rotate(a, radius))
                return true;
            if (add[0] != 500)
                add[0] = 500;
        }

        for (int i = 0; i < targetObjects.size(); i++)
            for (int j = i + 1; j < targetObjects.size(); j++)
                if (targetObjects.get(i).checkCollision(targetObjects.get(j))) return true;
        return false;
    }

    private void addWind() {
        wind += random.nextInt((int) difficulty.getWind()) - difficulty.getWind()/2;
    }

    private void stageOne() {
        difficulty.reverse();
        add[0] = random.nextInt(1000);
    }

    public void setLost() {
        lost = true;
    }

    public float getWind() {
        return wind;
    }

    public void handleStages(int ratio) {
        if (prevRatio == ratio) return;
        else if (prevRatio > 75 && ratio < 75) stage1 =true;
        else if ( prevRatio>50 && ratio < 50) stage2 =true;
        else if (prevRatio > 25 && ratio < 25) stage3 = true;
        prevRatio = ratio;
    }
}
