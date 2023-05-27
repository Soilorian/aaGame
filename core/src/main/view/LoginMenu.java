package main.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.CharArray;
import main.control.Controller;
import main.control.EntranceController;
import main.model.DataBase;
import main.util.HoverListener;
import main.util.enums.GameText;
import main.util.enums.Messages;

import static com.badlogic.gdx.Gdx.graphics;

public class LoginMenu extends Menu {
    TextButton registerButton, loginButton, guestButton;
    Label errorLabel, usernameLabel, passwordLabel;
    private final Table table, mainTable;
    private final TextField username;
    private final TextField password;


    public LoginMenu(final Controller controller) {
        super(controller);
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);

        table = new Table(controller.getSkin());
        table.setBounds(0, 0, 280, 1200);
        usernameLabel = new Label("username:", controller.getSkin());
        usernameLabel.setColor(Color.BLACK);
        passwordLabel = new Label("password:", controller.getSkin());
        passwordLabel.setColor(Color.BLACK);
        username = new TextField("", controller.getSkin());
        password = new TextField("", controller.getSkin());
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');

        loginButton = new TextButton(GameText.LOGIN.toString(), controller.getSkin(), "small");
        loginButton.addListener(new HoverListener());
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                login();
            }
        });

        guestButton = new TextButton(GameText.GUEST_LOGIN.toString(), controller.getSkin(), "small");
        guestButton.addListener(new HoverListener());
        guestButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                guestLogin();
            }
        });

        registerButton = new TextButton("register", controller.getSkin(), "small");
        registerButton.addListener(new HoverListener());
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                register();
            }
        });
        errorLabel = new Label("", controller.getSkin());
        errorLabel.setColor(Color.RED);
        errorLabel.setWrap(true);

        stage.clear();
        table.add(usernameLabel);
        table.add(username).width(130);
        table.row();
        table.add(passwordLabel);
        table.add(password).width(130);
        table.row();
        table.add(loginButton).colspan(2);
        table.row();
        table.add(registerButton).colspan(2);
        table.row();
        table.add(guestButton).colspan(2);
        table.row();
        table.add(errorLabel).width(300).colspan(2);
        mainTable.setBackground(new TextureRegionDrawable(controller.getBackground()));
        mainTable.add(table);
        mainTable.left();
        stage.addActor(mainTable);
    }

    private void guestLogin() {
        EntranceController.guestLogin();
        controller.setScreen(new MainMenu(controller));
    }

    private void login() {
        Messages message = EntranceController.login(username.getText(), password.getText());
        errorLabel.setText(message.toString());
        if (!message.equals(Messages.SUCCESSFUL)) return;
        controller.setScreen(new MainMenu(controller));
    }

    private void register() {
        Messages message = EntranceController.register(username.getText(), password.getText());
        errorLabel.setText(message.toString());
        if (!message.equals(Messages.SUCCESSFUL)) return;
        controller.setScreen(new MainMenu(controller));
    }

    @Override
    public void show() {
        super.show();
    }

}
