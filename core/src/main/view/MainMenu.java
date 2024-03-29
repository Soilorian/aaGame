package main.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import main.control.Controller;
import main.control.MainController;
import main.model.DataBase;
import main.util.HoverListener;
import main.util.enums.GameText;
import main.util.enums.Messages;

public class MainMenu extends Menu {
    private final TextButton duo,loadSavedGame, play, deleteAccount, logout;
    private final ImageButton profile, setting;
    private final Table table, mainTable;
    Label errorLabel;

    public MainMenu(final Controller controller) {
        //start
        super(controller);
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);
        table = new Table(controller.getSkin());
        mainTable.setBounds(0, 0, 280, 1200);


        //body
        errorLabel = new Label("", controller.getSkin());
        errorLabel.setColor(Color.RED);
        errorLabel.setWrap(true);

        loadSavedGame = new TextButton(GameText.LOAD.toString(), controller.getSkin(), "small");
        play = new TextButton(GameText.PLAY.toString(), controller.getSkin(), "small");
        deleteAccount = new TextButton(GameText.DELETE.toString(), controller.getSkin(), "small");
        logout = new TextButton(GameText.LOGOUT.toString(), controller.getSkin(), "small");
        duo = new TextButton(GameText.DUO.toString(), controller.getSkin(), "small");
        setting = new ImageButton(new TextureRegionDrawable(controller.getSettingIcon()));

        profile = new ImageButton(new TextureRegionDrawable(Controller.currentPlayer.getProfileIcon()));
        profile.setWidth(100);
        profile.setHeight(100);

        loadSavedGame.addListener(new HoverListener());
        loadSavedGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadGame();
            }
        });

        play.addListener(new HoverListener());
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startNewGame();
            }
        });

        deleteAccount.addListener(new HoverListener());
        deleteAccount.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deleteProfile();
            }
        });

        logout.addListener(new HoverListener());
        logout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logoff();
            }
        });

        duo.addListener(new HoverListener());
        duo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setScreen(new GameMenu(controller, true));
            }
        });

        profile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                profileMenu();
            }
        });

        setting.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingMenu();
            }
        });


        //end
        stage.clear();
        table.add(profile).width(300);
        table.row();
        table.add(duo).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(loadSavedGame).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(play).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(logout).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(deleteAccount).width(300).padTop(50).padBottom(50);
        table.row();
        table.add(setting);
        table.row();
        table.add(errorLabel).width(300);
        mainTable.setBackground(new TextureRegionDrawable(controller.getBackground()));
        mainTable.add(table);
        mainTable.left();
        stage.addActor(mainTable);
    }

    private void settingMenu() {
        dispose();
        controller.setScreen(new SettingMenu(controller));
    }

    private void profileMenu() {
        if (Controller.currentPlayer.getUsername().equals("_GUEST_"))
            errorLabel.setText(Messages.LOGIN_FIRST.toString());
        else{
            dispose();
            controller.setScreen(new ProfileMenu(controller));
        }
    }

    private void logoff() {
        dispose();
        controller.setScreen(new LoginMenu(controller));
    }

    private void deleteProfile() {
        if (Controller.currentPlayer.getUsername().equals("_GUEST_"))
            errorLabel.setText(Messages.LOGIN_FIRST.toString());
        else {
            DataBase.removeCurrentUser();
            logoff();
        }
    }

    private void startNewGame() {
        dispose();
        controller.setScreen(new GameMenu(controller, false));
    }

    private void loadGame() {
        Messages messages = MainController.loadGame();
        errorLabel.setText(messages.toString());
        if (messages.equals(Messages.SUCCESSFUL))
            startNewGame();
    }

    @Override
    public void show() {
        super.show();
    }
}
