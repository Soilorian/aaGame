package main.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class HoverListener extends ClickListener {
    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (event.getTarget() instanceof TextButton){
            for (Actor child : ((TextButton) event.getTarget()).getChildren()) {
                child.setColor(Color.GREEN);
            }
            event.getTarget().setColor(Color.GREEN);
        }
        else {
            event.getTarget().getParent().setColor(Color.GREEN);
            event.getTarget().setColor(Color.GREEN);
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (event.getTarget() instanceof TextButton){
            for (Actor child : ((TextButton) event.getTarget()).getChildren()) {
                child.setColor(Color.WHITE);
            }
            event.getTarget().setColor(Color.WHITE);
        }
        else {
            event.getTarget().getParent().setColor(Color.WHITE);
            event.getTarget().setColor(Color.WHITE);
        }
    }
}
