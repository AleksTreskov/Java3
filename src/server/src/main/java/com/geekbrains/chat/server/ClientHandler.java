package com.geekbrains.chat.server;

import jdk.net.Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream((socket.getOutputStream()));
        new Thread(() -> {
            try {
                while (true) {
                    String msg = in.readUTF();

                    if (executeCommand(msg))
                        break;
                }

                while (true) {
                    try {

                        String msg = in.readUTF();

                        if (msg.startsWith("/")) {
                            executeCommand(msg);
                            continue;
                        }
                        server.broadcastMessage(username + ": " + msg);
                    } catch (SocketException e) {
                        System.out.println("Один из клиентов отключился.");
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }

        }).start();


    }

    private boolean executeCommand(String cmd) throws IOException, SQLException {
        if (cmd.startsWith("/login ")) {
            String usernameFromLogin = cmd.split("\\s")[1];
            if (server.isUserOnline(usernameFromLogin)) {
                sendMsg("/login_failed Current nickname has already been occupied");
                return false;
            }
            server.getAuthService().login(usernameFromLogin);
            username = usernameFromLogin;
            sendMsg("/login_ok " + username);
            server.subscribe(this);
            return true;

        }
        if (cmd.startsWith("/who_am_i")) {
            server.whoAmI(this);
        }
        if (cmd.startsWith("/w ")) {
            String[] tokens = cmd.split("\\s", 3);
            server.whisper(this, tokens[1], tokens[2]);
        }
        if (cmd.startsWith("/chnick ")) {
            String[] token = cmd.split("\\s+", 2);
            if (token.length < 2) {
                return false;
            }
            if (token[1].contains(" ")) {
                sendMsg("Ник не может содержать пробелов");
                return false;
            }
            if (server.getAuthService().changeNick(this.username, token[1])) {
                sendMsg("Ваш ник изменен на " + token[1]);
                this.username = token[1];
                server.broadcastClientList();
            } else {
                sendMsg("Не удалось изменить ник. Ник " + token[1] + " уже существует");
            }
        }
        if (cmd.startsWith("/logout")) {
            server.getAuthService().unlogin(this.getUsername());
            try {

                socket.close();
            } catch (EOFException e) {

                server.broadcastMessage(getUsername() + "отключился.");

            }
        }
        return false;
    }

    private void disconnect() {

        server.unsubscribe(this);
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            disconnect();
        }
    }


    public String getUsername() {
        return username;
    }
}

