package main.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.google.gson.Gson;
import main.util.enums.Difficulty;
import main.util.enums.GameMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
    private final int MAX_BALL_COUNT = 50, MIN_BALL_COUNT = 5;
    private Difficulty difficulty = Difficulty.MEDIUM;
    private GameMap map = GameMap.NORMAL;
    private int initialBallCount = 15;
    private boolean grayScale = false;
    private int fireFP = Input.Keys.SPACE;
    private int moveLeftFP = Input.Keys.LEFT;
    private int moveRightFP = Input.Keys.RIGHT;
    private int fireSP = Input.Keys.ENTER;
    private int moveLeftSP = Input.Keys.A;
    private int moveRightSP = Input.Keys.D;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public int getInitialBallCount() {
        return initialBallCount;
    }

    public void setInitialBallCount(int initialBallCount) {
        this.initialBallCount = initialBallCount;
    }

    public boolean isGrayScale() {
        return grayScale;
    }

    public void setGrayScale(boolean grayScale) {
        this.grayScale = grayScale;
    }

    public int getFireFP() {
        return fireFP;
    }

    public void setFireFP(int fireFP) {
        this.fireFP = fireFP;
    }

    public int getMoveLeftFP() {
        return moveLeftFP;
    }

    public void setMoveLeftFP(int moveLeftFP) {
        this.moveLeftFP = moveLeftFP;
    }

    public int getMoveRightFP() {
        return moveRightFP;
    }

    public void setMoveRightFP(int moveRightFP) {
        this.moveRightFP = moveRightFP;
    }

    public int getFireSP() {
        return fireSP;
    }

    public void setFireSP(int fireSP) {
        this.fireSP = fireSP;
    }

    public int getMoveLeftSP() {
        return moveLeftSP;
    }

    public void setMoveLeftSP(int moveLeftSP) {
        this.moveLeftSP = moveLeftSP;
    }

    public int getMoveRightSP() {
        return moveRightSP;
    }

    public void setMoveRightSP(int moveRightSP) {
        this.moveRightSP = moveRightSP;
    }
}
