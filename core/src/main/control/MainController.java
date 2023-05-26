package main.control;

import main.model.DataBase;
import main.model.Game;
import main.util.enums.Messages;

public class MainController extends Controller{
    public static Messages loadGame() {
        Game game = DataBase.getSavedGame();
        if (game == null)
            return Messages.NO_SAVED_GAME;
        currentGame = game;
        return Messages.SUCCESSFUL;
    }
}
