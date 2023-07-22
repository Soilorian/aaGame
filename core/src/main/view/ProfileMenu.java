package main.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import main.control.Controller;
import main.control.ProfileController;
import main.model.DataBase;
import main.model.Player;
import main.util.HoverListener;
import main.util.enums.Difficulty;
import main.util.enums.GameMap;
import main.util.enums.GameText;
import main.util.enums.Messages;

import java.util.ArrayList;

public class ProfileMenu extends Menu {
    private final TextButton upload, back;
    private final ImageButton changeButton, profile;
    private final Table table, mainTable, scoreboard;
    private final SelectBox<Difficulty> difficultySelectBox;
    private final TextField username, password;
    private final TextArea fileAddress;
    private final ArrayList<Label> names = new ArrayList<>();
    private final ArrayList<Label> maxScores = new ArrayList<>();
    private final ArrayList<Label> times = new ArrayList<>();
    private final Label difficultyLabel;
    Label successLabel, errorLabel, usernameLabel, passwordLabel, mapLabel;
    private ArrayList<Label> ranks = new ArrayList<>();

    protected ProfileMenu(final Controller controller) {
        //start
        super(controller);
        scoreboard = new Table(controller.getSkin());
        scoreboard.left().bottom();
        table = new Table(controller.getSkin());
        mainTable = new Table(controller.getSkin());
        mainTable.setFillParent(true);
        mainTable.add(table).align(Align.left).expandY();
        mainTable.add(scoreboard).align(Align.bottomLeft);

        //body
        difficultyLabel = new Label(GameText.DIFFICULTY.toString(), controller.getSkin());
        difficultySelectBox = new SelectBox<>(controller.getSkin());
        difficultySelectBox.setItems(Difficulty.values());
        difficultySelectBox.setSelected(ProfileController.getDifficulty());
        difficultySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ProfileController.setDifficulty(difficultySelectBox.getSelected());
                controller.setScreen(new ProfileMenu(controller));
            }
        });


        DataBase.sortPlayers();
        for (int i = 0; i < 10; i++) {
            Label e = new Label(String.valueOf(i + 1), controller.getSkin());
            e.setFontScale(4);
            e.setColor(Color.BLACK);
            ranks.add(e);
            Player player = DataBase.getPlayer(i);
            if (player == null || !player.getDifficulty().equals(difficultySelectBox.getSelected())) {
                names.add(new Label("", controller.getSkin()));
                maxScores.add(new Label("", controller.getSkin()));
                times.add(new Label("", controller.getSkin()));
            } else {
                Label e1 = new Label(player.getUsername(), controller.getSkin());
                e1.setColor(Color.BLACK);
                names.add(e1);
                Label e2 = new Label(String.valueOf(player.getMaxScore()), controller.getSkin());
                e2.setColor(Color.BLACK);
                maxScores.add(e2);
                Label e3 = new Label(String.valueOf(player.getMaxScoreTime()), controller.getSkin());
                e3.setColor(Color.BLACK);
                times.add(e3);
            }
        }

        for (int i = 0; i < 10; i++) {
            scoreboard.add(ranks.get(i)).width(60).height(109);
            scoreboard.add(names.get(i)).width(225);
            scoreboard.add(maxScores.get(i)).width(180);
            scoreboard.add(times.get(i)).width(180);
            scoreboard.row();
        }
        scoreboard.setBackground(new TextureRegionDrawable(controller.getScoreboard()));

        fileAddress = new TextArea("", controller.getSkin());
        upload = new TextButton(GameText.UPLOAD.toString(), controller.getSkin(), "small");
        upload.addListener(new HoverListener());
        upload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                uploadFile();
            }
        });

        profile = new ImageButton(new TextureRegionDrawable(Controller.currentPlayer.getProfileIcon()));
        profile.setSize(100, 100);
        profile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextProfile();
            }
        });


        username = new TextField(Controller.currentPlayer.getUsername(), controller.getSkin());
        usernameLabel = new Label("username:", controller.getSkin());
        usernameLabel.setColor(Color.BLACK);

        passwordLabel = new Label("password:", controller.getSkin());
        passwordLabel.setColor(Color.BLACK);
        password = new TextField(Controller.currentPlayer.getPassword(), controller.getSkin());

        back = new TextButton(GameText.BACK.toString(), controller.getSkin(), "small");
        back.addListener(new HoverListener());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back();
            }
        });

        errorLabel = new Label("", controller.getSkin());
        if (controller.settings.isGrayScale()) {
            errorLabel.setColor(Color.BLACK);
        } else {
            errorLabel.setColor(Color.RED);
        }
        errorLabel.setWrap(true);

        successLabel = new Label("", controller.getSkin());
        if (controller.settings.isGrayScale()) {
            errorLabel.setColor(Color.BLACK);
        } else {
            successLabel.setColor(Color.GREEN);
        }
        errorLabel.setWrap(true);

        changeButton = new ImageButton(new TextureRegionDrawable(controller.getChangeIcon()));
        changeButton.addListener(new ChangeListener() {
            @Override()
            public void changed(ChangeEvent event, Actor actor) {
                changePlayerInfo();
            }
        });


        //end
        stage.clear();
        table.add(profile).colspan(2).pad(10);
        table.row();
        table.add(fileAddress).colspan(2).pad(10);
        table.row();
        table.add(upload).colspan(2).pad(10);
        table.row();
        table.add(usernameLabel).pad(10);
        table.add(username).width(130).pad(10);
        table.row();
        table.add(passwordLabel).pad(10);
        table.add(password).width(130).pad(10);
        table.row();
        table.add(changeButton).colspan(2).pad(10);
        table.row();
        table.add(back).colspan(2).pad(10);
        table.row();
        table.add(errorLabel).colspan(2).pad(10).width(300);
        table.row();
        table.add(successLabel).colspan(2).pad(10).width(300);
        table.row();
        table.add(difficultyLabel, difficultySelectBox);
        mainTable.setBackground(new TextureRegionDrawable(controller.getBackground()));
        stage.addActor(mainTable);
    }

    private void back() {
        controller.setScreen(new MainMenu(controller));
    }

    private void nextProfile() {
        Controller.currentPlayer.setProfileIcon(DataBase.getNextTexture(Controller.currentPlayer.getProfileIconAddress()));
        controller.setScreen(new ProfileMenu(controller));
    }

    private void uploadFile() {
        Messages upload1 = ProfileController.upload(fileAddress.getText());
        if (upload1.equals(Messages.SUCCESSFUL)) {
            successLabel.setText(upload1.toString());
            errorLabel.setText("");
        } else {
            errorLabel.setText(upload1.toString());
            successLabel.setText("");
        }
    }

    private void changePlayerInfo() {
        Messages update = ProfileController.update(username.getText(), password.getText());
        if (update.equals(Messages.SUCCESSFUL)) {
            errorLabel.setText("");
            successLabel.setText(update.toString());
        } else {
            successLabel.setText("");
            errorLabel.setText(update.toString());
        }
    }

    @Override
    public void show() {
        super.show();
    }

}
