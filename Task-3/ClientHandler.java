import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket) {

        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {

            username = reader.readLine();

            ChatServer.broadcast("🔔 " + username + " joined the chat");

            String message;

            while ((message = reader.readLine()) != null) {

                String time = LocalTime.now().withNano(0).toString();

                ChatServer.broadcast("[" + time + "] " + username + ": " + message);
            }

        } catch (IOException e) {

        } finally {

            ChatServer.removeClient(this);
            ChatServer.broadcast("❌ " + username + " left the chat");
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}