package main.util.enums;

public enum Difficulty {
    CHILD_GAME(0.25F),
    EASY(0.5F),
    MEDIUM(1),
    HARD(2),
    SUPER_HARD(4),
    EXTREME(8),
    IMPOSSIBLE(16)
    ;
    private float speed;

    Difficulty(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void reverse() {
        speed *= -1;
    }
}
