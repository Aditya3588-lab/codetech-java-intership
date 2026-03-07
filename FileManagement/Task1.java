import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Task1 extends JFrame implements ActionListener {

    private JTextField fileNameField;
    private JTextArea textArea;
    private JButton writeBtn, readBtn, modifyBtn, appendBtn, clearBtn;

    public Task1() {

        setTitle("CODTECH - File Handling Utility");
        setSize(700, 550);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //main panel
        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(new EmptyBorder(15,15,15,15));
        add(mainPanel);

        //Top Panel
        JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));


        JLabel fileLabel=new JLabel("Enter a file name:");
        fileLabel.setFont(new Font("Arial",Font.BOLD,14));

        fileNameField=new JTextField(25);
        fileNameField.setFont(new Font("Arial",Font.PLAIN,14));

        topPanel.add(fileLabel);
        topPanel.add(fileNameField);
        mainPanel.add(topPanel,BorderLayout.NORTH);




     //text area
        textArea = new JTextArea();
       textArea.setFont(new Font("Consolas",Font.PLAIN,15));
       textArea.setLineWrap(true);
       textArea.setWrapStyleWord(true);

       JScrollPane scrollPane=new JScrollPane(textArea);
       mainPanel.add(scrollPane,BorderLayout.CENTER);

       //Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(1,5,10,10));

        writeBtn = new JButton("Write");
        readBtn = new JButton("Read");
        modifyBtn = new JButton("Modify");
        appendBtn = new JButton("Append");
        clearBtn = new JButton("Clear");

        bottomPanel.add(writeBtn);
        bottomPanel.add(readBtn);
        bottomPanel.add(modifyBtn);
        bottomPanel.add(appendBtn);
        bottomPanel.add(clearBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        writeBtn.addActionListener(this);
        readBtn.addActionListener(this);
        modifyBtn.addActionListener(this);
        appendBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        setVisible(true);
    }

    // Common method to validate file name
    private String getValidatedFileName() {

        String fileName = fileNameField.getText().trim();

        if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter file name!");
            return null;
        }

        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }

        return fileName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String fileName = getValidatedFileName();
        if (fileName == null) return;

        String content = textArea.getText();

        try {

            // WRITE
            if (e.getSource() == writeBtn) {

                FileWriter writer = new FileWriter(fileName, false);
                writer.write(content);
                writer.close();

                JOptionPane.showMessageDialog(this, "File Written Successfully!");
            }

            // READ
            else if (e.getSource() == readBtn) {

                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                textArea.setText("");

                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                reader.close();
            }

            // MODIFY
            else if (e.getSource() == modifyBtn) {

                String oldWord = JOptionPane.showInputDialog(this, "Enter word to replace:");
                String newWord = JOptionPane.showInputDialog(this, "Enter new word:");

                if (oldWord != null && newWord != null) {

                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    StringBuilder updatedContent = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        updatedContent.append(line.replace(oldWord, newWord)).append("\n");
                    }
                    reader.close();

                    FileWriter writer = new FileWriter(fileName);
                    writer.write(updatedContent.toString());
                    writer.close();

                    textArea.setText(updatedContent.toString());
                    JOptionPane.showMessageDialog(this, "File Modified Successfully!");
                }
            }

            // APPEND
            else if (e.getSource() == appendBtn) {

                FileWriter writer = new FileWriter(fileName, true);
                writer.write("\n" + content);
                writer.close();

                JOptionPane.showMessageDialog(this, "Content Appended Successfully!");
            }

            // CLEAR
            else if (e.getSource() == clearBtn) {
                textArea.setText("");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Task1();
    }
}
