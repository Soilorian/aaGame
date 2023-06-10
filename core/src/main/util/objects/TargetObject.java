package main.util.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;
import main.model.Game;

import java.util.ArrayList;
import java.util.Random;


@Getter
@Setter
public class TargetObject {
    private boolean visible = true;
    private final Circle outerCircle,circle;
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Random random = new Random();
    private final Game game;

    public TargetObject(Circle circle, Game game) {
        this.circle = circle;
        outerCircle = new Circle(circle.x, circle.y,  circle.radius * 3);
        this.game = game;
        for (int i = 0; i < 5; i++) {
            addBall(randomBall());
        }
    }

    public Ball randomBall() {
        do {
            float x = random.nextFloat();
            x *= outerCircle.radius * 2;
            x += outerCircle.x - outerCircle.radius;
            float y = (float) (outerCircle.y + Math.sqrt(Math.pow(outerCircle.radius, 2) - Math.pow(x - outerCircle.x, 2)));
            Circle circle1 = new Circle(x, y, 15);
            if (contains(circle1))
                continue;
            Ball e = new Ball(game, circle1);
            e.setTarget(this);
            return e;
        } while (true);
    }

    public boolean addBall(Ball ball) {
        balls.add(ball);
        return contains(ball.getCircle());
    }

    private boolean contains(Circle circle) {
        for (Ball ball : balls) {
            if (checkForContact(ball.getCircle(), circle)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForContact(Circle circle, Circle circle1) {
        Vector2 vector2 = new Vector2(circle.x - circle1.x, circle.y - circle1.y);
        return vector2.len() < circle.radius + circle1.radius;
    }

    public boolean rotate(float speed, float radiusChange) {
        for (Ball ball : balls) {
            Circle circle1 = ball.getCircle();
            float radius = circle1.radius;
            if (random.nextBoolean()) radius *= radiusChange;
            Vector2 movingTo = new Vector2(circle1.x - circle.x, circle1.y - circle.y).rotateDeg((float) (speed * 0.5));
            circle1.set(circle.x + movingTo.x, circle.y + movingTo.y, radius);
        }
        return checkCollision();
    }

    public boolean checkCollision() {
        for (Ball ball : balls) {
            for (Ball ball2 : balls) {
                Circle circle1 = ball.getCircle(), circle2 = ball2.getCircle();
                if (!circle1.equals(circle2) && circle1.overlaps(circle2))
                    return true;
            }
        }
        return false;
    }

    public boolean checkCollision(TargetObject targetObject) {
        for (Ball ball : balls) {
            for (Ball ball1 : targetObject.balls) {
                Circle circle1 = ball.getCircle(), circle2 = ball1.getCircle();
                if (circle1.overlaps(circle2))
                    return true;
            }
        }
        return false;
    }

    public void reverseVisibility() {
        visible = !visible;
    }

    public boolean isInRange(Circle circle) {
        return outerCircle.contains(circle.x,circle.y);
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        if (visible) {
            for (Ball ball : balls) {
                Circle circle1 = ball.getCircle();
                shapeRenderer.circle(circle1.x, circle1.y, circle1.radius);
                shapeRenderer.line(circle1.x, circle1.y, circle.x, circle.y);
            }
        }
        shapeRenderer.end();
    }

    private int scaleX(int x){
        return (int) (x - outerCircle.x+outerCircle.radius);
    }
    private int scaleY(int y){
        return (int) (y - outerCircle.y+outerCircle.radius);
    }

    public ArrayList<Ball> generateBalls() {
        ArrayList<Ball> randomBalls = new ArrayList<>();
        for (int i = 0; i < random.nextInt(5) + 5; i++) {
            randomBalls.add(randomBall());
        }
        return randomBalls;
    }

    public void draw(SpriteBatch batch) {

    }
}