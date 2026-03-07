import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static final int PORT = 1234;
    static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {

        System.out.println("Server Started...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);

                new Thread(handler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {

        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler client){
        clients.remove(client);
    }
}