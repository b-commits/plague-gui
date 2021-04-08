package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HighScoresWindow extends JFrame {

    private JList list;
    private ArrayList<String> highScores = new ArrayList<>();


    public HighScoresWindow() {
        super("High Scores");
        add(new JLabel("<html><font size = 70><font color = red>HIGH SCORES: </html></font>"));
        add(new JLabel("Name / Virus contained at"));
        loadHighscores();
        sortHighScores();
        list = new JList(highScores.toArray());
        list.setVisibleRowCount(20);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setPreferredSize(new Dimension(280, 400));
        this.add(jsp);
        setLayout(new FlowLayout());
        this.setSize(300, 530);
        this.setVisible(true);
    }

    private void loadHighscores() {
        File records = new File("src\\util\\Record");
        try {
            BufferedReader br = new BufferedReader(new FileReader(records));
            String record;
            while ((record = br.readLine()) != null) {
                highScores.add(record);
            }
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sortowanie na podstawie daty
     * @return 0 -- jeżeli pierwsza data powstrzymania wirusa jest wcześniejsza.
     * @return 1 -- jeżeli daty są takie same.
     * @return -1 -- jeżeli druga data jest wcześniejsza.
     */
    private void sortHighScores() {
        highScores.sort((o1, o2) -> {
            String[] tokens1 = o1.split(" ");
            String[] tokens2 = o2.split(" ");
            try {
                LocalDate date = LocalDate.parse(tokens1[1]);
                LocalDate date2 = LocalDate.parse(tokens2[1]);
                if (date.isBefore(date2)) {
                    return -1;
                } else if (date.isAfter(date2)) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {

            }
            return 0;
        });
    }

}
