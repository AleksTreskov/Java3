package com.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthService db;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        if (!SQLHandler.connect())
            throw new RuntimeException("Не удалось подключиться к Базе Данных.");
         db=new DBAuthService();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port + "...");

            while (true) {
                System.out.println("Ожидаем подключение клиента");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(socket, this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void subscribe(ClientHandler clientHandler ) {

        clients.add(clientHandler);
        try {
            broadcastMessage("Клиент "+ clientHandler.getUsername()+" подключился.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        broadcastClientList();
    }
    public AuthService getAuthService() {
        return db;
    }
    public void unsubscribe(ClientHandler clientHandler)  {

        clients.remove(clientHandler);

        try {
            broadcastMessage("Клиент "+ clientHandler.getUsername()+" отключился.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        broadcastClientList();
    }

    public void whoAmI(ClientHandler sender) {
        for (ClientHandler cliethandlers : clients
        ) {
            if (db.getNickname(sender.getUsername()).equals(cliethandlers.getUsername())) {
                sender.sendMsg(sender.getUsername() + "\n");
                return;
            }

        }
    }

    public void broadcastMessage(String message) throws IOException {
        for (ClientHandler clientHandler : clients
        ) {
            clientHandler.sendMsg(message);
        }
    }

    public void whisper(ClientHandler sender, String receiver, String msg) {
        for (ClientHandler clientHandler : clients
        ) {
            if (clientHandler.getUsername().equals(receiver)) {
                clientHandler.sendMsg("От "+ sender.getUsername()+" сообщение: "+msg);
                sender.sendMsg("Пользователю: "+clientHandler.getUsername()+" cообщение: "+msg);
            return;}

        }
        sender.sendMsg("Нет такого пользователя"+"\n");
    }

    public boolean isUserOnline(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
public void broadcastClientList(){
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
    for (ClientHandler client:clients
         ) {
        stringBuilder.append(client.getUsername()).append(" ");
    }
    stringBuilder.setLength(stringBuilder.length()-1);
    String clientsList = stringBuilder.toString();
    for (ClientHandler clientHandler:clients
         ) {
        clientHandler.sendMsg(clientsList);

    }
}

}