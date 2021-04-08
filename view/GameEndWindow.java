package view;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameEndWindow extends JFrame {

    private JButton submitHighScore = new JButton("Submit");
    private JLabel endMessage = new JLabel("You won! Enter your name: ");
    private JPanel panel = new JPanel();
    private JTextField enterYourName = new JTextField(10);

    private String endDate;

    public GameEndWindow(String endDate) {
        this.endDate = endDate;
        endMessage.setLocation(50, 50);
        setResizable(false);
        setButtonAction();

        panel.add(endMessage);
        panel.add(enterYourName);
        panel.add(submitHighScore);
        this.add(panel);
        this.setVisible(true);
        setSize(200, 400);
    }

    public void setButtonAction() {
        this.submitHighScore.addActionListener(e -> {
            saveHighScore();
            JOptionPane.showMessageDialog(null, "High score has been saved. Press Ctrl+Shift+Q to bring up the main menu.");
            this.setVisible(false);
        });
    }

    public void saveHighScore() {
        try {
            File records = new File("src\\util\\Record");
            BufferedWriter br = new BufferedWriter(new FileWriter(records, true));
            String record = enterYourName.getText().concat(" ").concat(endDate+"\n");
            br.write(record);
            br.flush();
            System.out.println(record);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
