package main.control;

import main.model.DataBase;
import main.model.Player;
import main.util.enums.Messages;

public class EntranceController extends Controller{
    public static Messages register(String username, String password){
        if (isNotValid(username) || isNotValid(password))
            return Messages.NOT_VALID;
        if (DataBase.getPlayerById(username) != null)
            return Messages.CHOSEN_USERNAME;
        Player player = new Player(username, password, DataBase.getRandomTexture());
        DataBase.addPlayer(player);
        Controller.setCurrentPlayer(player);
        return Messages.SUCCESSFUL;
    }

    public static Messages login(String username, String password) {
        if (isNotValid(username) || isNotValid(password))
            return Messages.NOT_VALID;
        Player player = DataBase.getPlayerById(username);
        if (player == null) return Messages.NO_PLAYER;
        if (player.isPasswordWrong(password)) return Messages.WRONG_PASSWORD;
        Controller.setCurrentPlayer(player);
        return Messages.SUCCESSFUL;
    }

    public static void guestLogin() {
        Controller.setCurrentPlayer(DataBase.getPlayerById("_GUEST_"));
    }
}
