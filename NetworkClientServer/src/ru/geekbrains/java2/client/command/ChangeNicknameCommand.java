package ru.geekbrains.java2.client.command;

import java.io.Serializable;

public class ChangeNicknameCommand implements Serializable {

    private final String username;
    private final String newNickname;

    public ChangeNicknameCommand(String username, String message) {
        this.username = username;
        this.newNickname = message;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public String getUsername() {
        return username;
    }
}
