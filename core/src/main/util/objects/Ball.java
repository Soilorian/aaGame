package main.util.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;
import main.model.Game;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

@Getter
@Setter

public class Ball {

    private final Game game;
    private TargetObject target = null;
    private boolean lost = false;
    private boolean win = false;
    private int wind;
    private ArrayList<TargetObject> targetObjects;
    private Circle circle;
    private int direction;

    public Ball(final Game game, Circle circle, int id) {
        this((int) game.getWind(), game.targetObjects, game);
        direction = id;
        this.circle = circle;
    }

    public Ball(final Game game, Circle circle) {
        this(game, circle, 1);
    }

    public Ball(int wind, ArrayList<TargetObject> targetObject, final Game game) {
        this.wind = wind;
        this.targetObjects = targetObject;
        this.game = game;
    }

    private Vector2 getDirectionVector2(TargetObject targetObject) {
        Vector2 vector2Des = new Vector2(targetObject.getCircle().x, targetObject.getCircle().y),
                vector2Cur = new Vector2(circle.x, circle.y);
        Vector2 move = vector2Des.sub(vector2Cur);
        move = move.nor();
        return move;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        if (target != null) shapeRenderer.line(new Vector2(circle.x, circle.y), new Vector2( target.getOuterCircle().x,
                target.getOuterCircle().y));
    }

    private Texture getLine() {
        Pixmap pixmap = new Pixmap(972, 1200
                , Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine((int)circle.x,(int)circle.y, (int)target.getCircle().x, (int) target.getCircle().y );
        return new Texture(pixmap);
    }

    public Texture drawingTexture() {
        Pixmap pixmap = new Pixmap((int) circle.radius * 2, (int) circle.radius * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(scaleX((int) circle.x, circle), scaleY((int) circle.y, circle), (int) circle.radius);
        return new Texture(pixmap);
    }


    private int scaleX(int x, Circle outerCircle) {
        return (int) Math.abs(x - outerCircle.x + outerCircle.radius);
    }

    private int scaleY(int y, Circle outerCircle) {
        return (int) Math.abs(y - outerCircle.y + outerCircle.radius);
    }

    public void act(float delta) {
        if (win) {
            if (Ball.this.target != null) {
                Vector2 move = getDirectionVector2(Ball.this.target);
                move.rotateDeg(180);
                moveBy(move.x, move.y);
                if (circle.x > Gdx.graphics.getWidth() || circle.y > Gdx.graphics.getHeight() || circle.x < 0 || circle.y < 0)
                    Ball.this.target = null;
            }
        } else if (lost) {
            if (Ball.this.target != null) {
                Vector2 vector2 = new Vector2(circle.x, circle.y);
                vector2.rotateAroundDeg(new Vector2(Ball.this.target.getCircle().x, Ball.this.target.getCircle().y), 1);
                circle.setX(vector2.x);
                circle.setY(vector2.y);
                if (!vector2.isOnLine(Vector2.Y))
                    Ball.this.target = null;
            }
        } else if (Ball.this.target == null) {
            moveBy(0, 10);
            for (TargetObject targetObject : targetObjects) {
                if (targetObject.isInRange(circle)) {
                    Ball.this.target = targetObject;
                }
                if (Ball.this.target != null) {
                    if (targetObject.addBall(this)) {
                        game.setLost();
                    }
                }
            }
        }
    }

}
