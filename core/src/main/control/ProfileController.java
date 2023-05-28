package main.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import lombok.Getter;
import lombok.Setter;
import main.model.DataBase;
import main.util.enums.Difficulty;
import main.util.enums.Messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileController extends Controller {

    @Getter
    @Setter
    private static Difficulty difficulty;

    public static Messages update(String username, String password) {
        if (isNotValid(username) || isNotValid(password) && !username.isEmpty() && !password.isEmpty())
            return Messages.NOT_VALID;
        if (!username.isEmpty())
            currentPlayer.setUsername(username);
        if (!password.isEmpty())
            currentPlayer.setPassword(password);
        return Messages.SUCCESSFUL;
    }

    public static Messages upload(String text) {
        if (text.isEmpty())
            return Messages.NOT_VALID;
        FileHandle file = new FileHandle(text);
        if (!file.exists())
            return Messages.NOT_FOUND;
        file.copyTo(Gdx.files.absolute("C:\\Users\\Notebook\\IdeaProjects\\aaGame\\assets\\profilepictures"));
        String address = turnToInertial(text);
        if (address == null) {
            return Messages.FAIL;
        }
        DataBase.addProfileIcon(address);
        Controller.currentPlayer.setProfileIcon(DataBase.getTextureFromAddress(address));
        return Messages.SUCCESSFUL;
    }

    private static String turnToInertial(String text) {
        Pattern pattern = Pattern.compile("^.*\\\\(?<Name>\\w*.\\w*)$");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()){
            String name = matcher.group("Name");
            return "profilepictures/"+name;
        }
        return null;
    }
}
