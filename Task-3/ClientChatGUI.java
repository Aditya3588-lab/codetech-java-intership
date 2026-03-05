import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientChatGUI {

    private JFrame frame;
    private JPanel chatPanel;
    private JTextField messageField;
    private JButton sendButton;
    private PrintWriter writer;
    private String username;

    public ClientChatGUI() {

        username = JOptionPane.showInputDialog("Enter your name:");

        frame = new JFrame("Chat - " + username);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(chatPanel);

        messageField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        connectToServer();
    }

    private void sendMessage() {

        String message = messageField.getText().trim();

        if (!message.isEmpty()) {

            writer.println(message);
            addMessage("Me: " + message, true);
            messageField.setText("");
        }
    }

    private void addMessage(String message, boolean isMe) {

        JPanel bubble = new JPanel(new BorderLayout());
        bubble.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        JLabel label = new JLabel("<html>" + message + "</html>");
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));

        if(isMe){
            label.setBackground(new Color(200,255,200));
            bubble.add(label, BorderLayout.EAST);
        }else{
            label.setBackground(new Color(220,220,220));
            bubble.add(label, BorderLayout.WEST);
        }

        chatPanel.add(bubble);
        chatPanel.revalidate();
    }

    private void connectToServer() {

        try {

            Socket socket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(username);

            new Thread(() -> {

                try {

                    String message;

                    while ((message = reader.readLine()) != null) {

                        addMessage(message,false);

                    }

                } catch (IOException e) {

                }

            }).start();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(frame,"Server not running");

        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new ClientChatGUI());

    }
}