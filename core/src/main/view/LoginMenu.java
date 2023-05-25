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
import main.control.Controller;
import main.control.EntranceController;
import main.model.DataBase;
import main.util.HoverListener;
import main.util.enums.GameText;
import main.util.enums.Messages;

import static com.badlogic.gdx.Gdx.graphics;

public class LoginMenu extends Menu {
    TextButton registerButton, loginButton, guestButton;
    Label errorLabel;
    private final Table table;
    private final TextField username;
    private final TextField password;

    public LoginMenu(final Controller controller) {
        super(controller);
        table = new Table(controller.getSkin());
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());
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

        table.add(username);
        table.add(password);
        table.row();
        table.add(loginButton).width(100);
        table.add(registerButton).width(100);
        table.row();
        table.add(guestButton).width(150).colspan(2);
        table.row();
        table.add(errorLabel).colspan(2);
        table.setBackground(new TextureRegionDrawable(controller.getBackground()));
        stage.addActor(table);
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
