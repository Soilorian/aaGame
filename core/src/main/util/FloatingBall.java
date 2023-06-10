package main.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import main.model.Settings;
import main.view.GameMenu;

public class FloatingBall {
    public static final Color defColor = new Color(0.9f, 0.4f, 0.1f, 1);
    public static final Color p1Color = new Color(0.4f, 0.1f, 0.9f, 1);
    public static final Color p2Color = new Color(0.4f, 0.9f, 0.1f, 1);
    private final Settings gameData;
    private final Batch batch;
    private final BitmapFont font;
    private int pBallIdx;
    private int playerId;
    private Vector2 position;
    private Vector2 velocity;
    private boolean alive = true;

    public FloatingBall(Settings gameData, Batch batch, BitmapFont font, Vector2 position, Vector2 velocity, int playerId,
                        int pBallIdx) {
        this.gameData = gameData;
        this.batch = batch;
        this.font = font;
        this.position = position;
        this.velocity = velocity;
        this.playerId = playerId;
        this.pBallIdx = pBallIdx;
    }

    public static Color getColor(int playerId) {
        switch (playerId) {
            case 0:
                return GameMenu.singleton.transformColor(p1Color);
            case 1:
                return GameMenu.singleton.transformColor(p2Color);
        }
        return GameMenu.singleton.transformColor(defColor);
    }

    public Settings getGameData() {
        return gameData;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void update(float deltaTime) {
        position.mulAdd(velocity, deltaTime);
        velocity.add(-deltaTime * (float) gameData.getDifficulty().getWind() * 70, 0);

        double dst2 = 1.2f * 70;
        dst2 *= dst2;
        if (position.dot(position) <= dst2)
            alive = false;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(position.x, position.y, getBallRadius() + GameMenu.outlineIncrement);
        shapeRenderer.setColor(getColor(playerId));
        shapeRenderer.circle(position.x, position.y, getBallRadius());
        shapeRenderer.end();
        batch.begin();
        font.setColor(Color.BLACK);
        Affine2 translation = new Affine2().translate(position.x, position.y).scale(new Vector2(2, 2));
        batch.setTransformMatrix(new Matrix4().setAsAffine(translation));// SOME MATRIX BS
        font.draw(batch, "" + pBallIdx, -20, 5, 2 * getBallRadius(), Align.center, false);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    private float getBallRadius() {
        return 15;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getpBallIdx() {
        return pBallIdx;
    }

    public void setpBallIdx(int pBallIdx) {
        this.pBallIdx = pBallIdx;
    }
}