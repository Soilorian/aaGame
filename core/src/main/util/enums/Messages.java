package main.util.enums;

public enum Messages {
    SUCCESSFUL("action was successful!"),
    CHOSEN_USERNAME("this username has already been chosen"),
    WRONG_PASSWORD("wrong password"),

    NO_PLAYER("no user with this username"),
    EMPTY_FIELD("there is an empty field"),
    NOT_VALID("username or password format is not valid"),
    LOGIN_FIRST("please register or login first"),
    NO_SAVED_GAME("no saved game for this account");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
