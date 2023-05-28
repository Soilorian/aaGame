package main.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import main.util.enums.Difficulty;
import main.util.enums.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final Difficulty difficulty;
    private int freezeBar = 0;
    private boolean stage1, stage2, stage3;
    private Vector2 cannonVector2;
    private Texture cannonImage;
    private GameMap gameMap;
    private ArrayList<TargetObject> targetObjects = new ArrayList<>();

    private ArrayList<Circle> ammo = new ArrayList<>();
    private ArrayList<Circle> ammo2 = new ArrayList<>();
    private Random random = new Random();

    public Game(GameMap gameMap, int initialBall, Difficulty difficulty) {
        this.gameMap = gameMap;
        this.difficulty = difficulty;
        cannonVector2 = new Vector2((float) Gdx.graphics.getWidth() / 2, 0);
        switch (gameMap) {
            case NORMAL: {
                targetObjects.add(new TargetObject(new Circle(336, 900, 70)));
            }
            case DUAL_BALL: {
                targetObjects.add(new TargetObject(new Circle(468, 900, 50)));
                targetObjects.add(new TargetObject(new Circle(804, 900, 50)));
                break;
            }
            case TRIPLE_PLAY: {
                targetObjects.add(new TargetObject(new Circle(534, 600, 40)));
                targetObjects.add(new TargetObject(new Circle(651, 900, 30)));
                targetObjects.add(new TargetObject(new Circle(738, 600, 40)));
            }
        }
    }

    public boolean update() {
        for (TargetObject targetObject : targetObjects) {
            float radius = 5;
            final float[] add = new float[1];
            add[0] = 0;
            if (stage1){
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        difficulty.reverse();
                        add[0] = random.nextInt(1000);
                    }
                }, 4 + random.nextFloat() + random.nextInt(5));
                radius += add[0];
            }
            if (stage2)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        for (TargetObject object : targetObjects) {
                            object.reverseVisibility();
                        }
                    }
                }, 1);

            if (stage3)
                ;
            Timer timer = new Timer();


            if (targetObject.rotate(difficulty.getSpeed(), (bringToRange(radius) - 5)/100))
                return true;
        }
        for (int i = 0; i < targetObjects.size(); i++) for (int j = i + 1; j < targetObjects.size(); j++)
                if (targetObjects.get(i).checkCollision(targetObjects.get(j))) return true;
        return false;
    }

    private float bringToRange(float radius) {
        while (radius > 10)
            radius /= 10;
        return radius;
    }
}
