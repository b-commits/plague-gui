package view;

import javax.swing.*;
import java.io.IOException;

public class MainMenuWindow extends JFrame {

    private JButton newGameButton = new JButton("New game");
    private JButton highScoresButton = new JButton("High Scores");
    private JButton exitButton = new JButton("Close menu window");
    private JButton howToPlayButton = new JButton("How to play [PL]");
    private JComboBox difficultyLevels = new JComboBox(new String[]{"Normal", "Easy", "Hard"});

    public MainMenuWindow() throws IOException {
        this.setResizable(false);
        this.setSize(200, 600);
        this.setTitle("Main Menu");
        JPanel menuPane = new JPanel();
        menuPane.add(new JLabel("PLAGUE GUI"));
        menuPane.add(newGameButton);
        menuPane.add(difficultyLevels);
        menuPane.add(highScoresButton);
        menuPane.add(howToPlayButton);
        menuPane.add(exitButton);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(menuPane);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        newGameButton.addActionListener(a -> SwingUtilities.invokeLater(() -> {
            try {
                dispose();
                new MainWindow(this.difficultyLevels.getSelectedItem().toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }));
        exitButton.addActionListener(a -> dispose());
        howToPlayButton.addActionListener(a -> new HowToPlayWindow());
        highScoresButton.addActionListener(a -> new HighScoresWindow());


    }



}
