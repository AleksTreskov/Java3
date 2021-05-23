package com.geekbrains.chat.server;

public class DBAuthService implements AuthService {
    @Override
    public String getNickname(String nickname) {
       return SQLHandler.getNickname(nickname);
    }

    @Override
    public boolean login(String nickname) {
        return SQLHandler.login(nickname);
    }

    @Override
    public boolean changeNick(String oldNickname, String newNickname) {
        return SQLHandler.changeNick(oldNickname,newNickname);
    }

    @Override
    public boolean unlogin(String nickname) {
        return SQLHandler.unlogin(nickname);
    }
}
