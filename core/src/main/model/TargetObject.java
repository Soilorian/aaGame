package main.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class TargetObject {
    private boolean visible = true;
    private final Circle circle;
    private final ArrayList<Circle> circles = new ArrayList<>();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Random random = new Random();

    public TargetObject(Circle circle) {
        this.circle = circle;
        randomBalls();
    }

    private void randomBalls() {
        do {
            float x = random.nextFloat();
            x %= 400;
            x += 436;
            float y = (float) (circle.y + Math.sqrt(Math.pow(circle.radius, 2) - Math.pow(x - circle.x, 2)));
            Circle circle1 = new Circle(x, y, 7);
        } while (circles.size() < 5);
    }

    public ShapeRenderer getShapeRenderer() {
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        for (Circle circle1 : circles) {
            shapeRenderer.circle(circle1.x, circle1.y, circle.radius);
            shapeRenderer.line(circle1.x, circle1.y, circle.x, circle.y);
        }
        shapeRenderer.setColor(Color.BLACK);
        return shapeRenderer;
    }

    public boolean addBall(Circle circle) {
        circles.add(circle);
        return contains(circle);
    }

    private boolean contains(Circle circle) {
        for (Circle circle1 : circles) {
            if (circle1.overlaps(circle)) {
                return false;
            }
        }
        return true;
    }

    public boolean rotate(float speed, float radiusChange) {
        for (Circle circle1 : circles) {
            float radius = circle1.radius;
            if (random.nextBoolean()) radius += radius * radiusChange;
            Vector2 movingTo = new Vector2(circle1.x - circle.x, circle1.y - circle.y).rotateDeg((float) (speed * 0.5));
            circle1.set(circle.x + movingTo.x, circle.y + movingTo.y, radius);
        }
        return checkCollision();
    }

    public boolean checkCollision() {
        for (Circle circle1 : circles) {
            for (Circle circle2 : circles) {
                if (!circle1.equals(circle2) && circle1.overlaps(circle2))
                    return true;
            }
        }
        return false;
    }

    public boolean checkCollision(TargetObject targetObject) {
        for (Circle circle1 : circles) {
            for (Circle circle2 : targetObject.circles) {
                if (circle1.overlaps(circle2))
                    return true;
            }
        }
        return false;
    }

    public void reverseVisibility() {
        visible = !visible;
    }
}
