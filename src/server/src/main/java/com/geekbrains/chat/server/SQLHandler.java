package com.geekbrains.chat.server;

import java.sql.*;

public class SQLHandler {

    private static Connection connection;
    private static PreparedStatement psGetNickname;
    private static PreparedStatement psLogin;
    private static PreparedStatement psChangeNick;
    private static PreparedStatement psUnlogin;

    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepareAllStatements();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void prepareAllStatements() throws SQLException {
        psGetNickname = connection.prepareStatement("SELECT nickname FROM users WHERE nickname = ?;");
        psLogin = connection.prepareStatement("INSERT INTO users(nickname) VALUES ( ? );");
        psChangeNick = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
        psUnlogin = connection.prepareStatement("DELETE FROM users WHERE nickname = ?;");
    }

    public static String getNickname(String nickname) {
        String nick = null;
        try {
            psGetNickname.setString(1, nickname);
            ResultSet rs = psGetNickname.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }

    public static boolean login(String nickname) {
        try {
            psLogin.setString(1, nickname);
            psLogin.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeNick(String oldNickname, String newNickname) {
        try {
            psChangeNick.setString(1, newNickname);
            psChangeNick.setString(2, oldNickname);
            psChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean unlogin(String nickname){
        try {
            psUnlogin.setString(1,nickname);
            psUnlogin.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public static void disconnect() {
        try {
            psLogin.close();
            psGetNickname.close();
            psChangeNick.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


