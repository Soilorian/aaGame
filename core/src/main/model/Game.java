package main.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.util.enums.GameMap;

import java.util.ArrayList;

public class Game {
    private boolean blackAndWhite = false;
    private Vector2 cannonVector2;
    private Texture cannonImage;
    private Sprite sprite;
    private GameMap gameMap;
    private Circle targetBall;
    private Circle targetBall2;
    private Rectangle targetRectangle;
    ShapeRenderer shapeRenderer;
    private ArrayList<Circle> firedBalls;
    private ArrayList<Circle> ammo;
    public Game(GameMap gameMap){
//        cannonVector2 = new Vector2((float) Gdx.graphics.getWidth() /2, 0);
//        sprite.setScale(4);
        switch (gameMap) {
            case NORMAL:{
                targetBall = new Circle(336, 900, 20);
                break;
            }
            case DUAL_BALL:{
                targetBall = new Circle(168, 900, 15);
                targetBall2 = new Circle(504, 900, 15);
                break;
            }
            case RECTANGLE:{
                targetRectangle = new Rectangle();
                targetRectangle.setX(336);
                targetRectangle.setY(900);
                targetRectangle.setWidth(30);
                targetRectangle.setHeight(20);
                break;
            }
        }
    }

    public void draw(SpriteBatch batch){
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(targetRectangle.x, targetRectangle.y, targetBall.radius);
        shapeRenderer.end();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Circle getTargetBall() {
        return targetBall;
    }

    public Circle getTargetBall2() {
        return targetBall2;
    }

    public Rectangle getTargetRectangle() {
        return targetRectangle;
    }
}
