package main.util.enums;

public enum Messages {
    SUCCESSFUL("action was successful!"),
    CHOSEN_USERNAME("this username has already\nbeen chosen"),
    WRONG_PASSWORD("wrong password"),

    NO_PLAYER("no user with this username"), EMPTY_FIELD("there is an empty field");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
