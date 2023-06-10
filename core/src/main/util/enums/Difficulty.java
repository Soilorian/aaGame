package main.util.enums;

import lombok.Getter;

@Getter
public enum Difficulty {
    CHILD_GAME(0.25F, 1, 32),
    EASY(0.5F, 2, 16),
    MEDIUM(1, 4, 8),
    HARD(2, 8, 4),
    SUPER_HARD(4, 16, 2),
    EXTREME(8, 32, 1),
    IMPOSSIBLE(16, 64, 0.5f)
    ;
    private float speed;
    private final float wind;
    private final float freeze;

    Difficulty(float speed, float wind, float freeze) {
        this.speed = speed;
        this.wind = wind;
        this.freeze = freeze;
    }

    public float getSpeed() {
        return speed;
    }

    public void reverse() {
        speed *= -1;
    }
    public static void reset(){
        for (Difficulty value : values()) {
            if (value.getSpeed() < 0)
                value.reverse();
        }
    }
}
