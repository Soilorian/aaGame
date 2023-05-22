package main.util.enums;

import main.control.Controller;

public enum GameText {
    WELCOME("welcome to aa", "به بازی خوش آمدید"),

    START_GAME("start a new game", "بازی جدید شروع کنید"),


    ;

    private final String english;
    private final String persian;
    GameText(String english, String persian) {
        this.english = english;
        this.persian = persian;
    }

    public String getMessage(){
        if (Controller.isPersian) return persian;
        return english;
    }
}
