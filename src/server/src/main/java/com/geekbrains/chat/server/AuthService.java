package com.geekbrains.chat.server;

public interface AuthService {
    String getNickname(String nickname);
    boolean login(String nickname);
    boolean changeNick(String oldNickname, String newNickname);
    boolean unlogin(String nickname);
}
