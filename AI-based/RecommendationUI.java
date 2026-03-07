import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RecommendationUI extends JFrame {

    JTextField userInput;
    JTextArea resultArea;

    public RecommendationUI() {

        setTitle("AI Recommendation System");
        setSize(450,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter User ID:");

        userInput = new JTextField(10);

        JButton recommendBtn = new JButton("Get Recommendations");

        resultArea = new JTextArea(12,30);
        resultArea.setEditable(false);

        recommendBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int userId = Integer.parseInt(userInput.getText());

                String result =
                        RecommendationEngine.getRecommendations(userId);

                resultArea.setText(result);
            }
        });

        add(label);
        add(userInput);
        add(recommendBtn);
        add(new JScrollPane(resultArea));
    }

    public static void main(String[] args) {

        new RecommendationUI().setVisible(true);
    }
}