package main.util.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ball extends Actor {
    boolean isSet;
    boolean lost;
    boolean win;
    public Ball() {
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (!isSet){
                    moveBy();
                }
            }
        });
    }
}
