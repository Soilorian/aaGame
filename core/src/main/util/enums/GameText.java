package main.util.enums;

import main.control.Controller;

public enum GameText {
    WELCOME("welcome to aa", "به بازی خوش آمدید"),

    START_GAME("start a new game", "بازی جدید شروع کنید"),


    SAVE("save this game", "این بازی را ذخیره کن"),
    SETTINGS("setting", "تنظیمات"),
    NO_SAVED_GAME("you have no saved game", "بازی ذخیره شده ای ندارید"),
    USERNAME("username", "نام کاربری"),
    PASSWORD("password", "رمز عبور"),
    LOGIN("login", "ورود"),
    LOAD("load game", "ادامه ی بازی قبلی"),
    GUEST_LOGIN("login as guest", "به عنوان مهمان وارد شوید"),
    PLAY("play", "بازی کن"),
    DELETE("delete profile", "حذف حساب"),
    LOGOUT("logout", "خروج"),
    CHANGE("change", "تغییر"),
    MUTE("mute", "بی صدا"),
    GRAYSCALE("grayscale", "بی رنگ"),
    BACK("back", "بازگشت"),
    P1FIRE("first player shoot", "پرتاب بازیکن اول"),
    DIFFICULTY("difficulty", "سختی"),
    MAP("map", "نقشه"), P2FIRE("second player shoot", ""),
    P1LEFT("first player left", ""),
    P2LEFT("second player left", ""),
    P1RIGHT("first player right", ""),
    P2RIGHT("second player right", ""),
    FREEZE_BUTTON("freeze", ""),
    DUO("duo", ""),
    UPLOAD("upload", "");

    private final String english;
    private final String persian;

    GameText(String english, String persian) {
        this.english = english;
        this.persian = persian;
    }

    @Override
    public String toString() {
        if (Controller.isPersian) return persian;
        return english;
    }
}
