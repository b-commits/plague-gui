package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TopPanel extends JPanel {

    private JLabel liveInfectionCount = new JLabel("0");
    private JLabel logo = new JLabel("<html><font size = 70><font color = red>Infected: </font><html>");
    private JLabel liveDate = new JLabel("<html><font size = 70><font color = white>            live count</font></html>");
    private JLabel placeholder = new JLabel("<html><font size = 70><font color = green>Cured: </font><html>");
    private JLabel curedCount = new JLabel("0");
    private JLabel roll = new JLabel("<html><font size = 30><font color =blue>          [respirator roll]           </font></html>");
    public static String htmlFormat = "<html><font size = 70><font color = red>";
    public static String htmlFormatClose = "</font></html>";
    public static String dateHtmlFormat = "<html><font size = 70><font color = white>";

    private boolean isFlowing = true;

    public TopPanel() {
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(1500, 70));
        this.setVisible(true);
        startFlowOfTime();

        this.liveInfectionCount.setForeground(Color.RED);
        this.curedCount.setForeground(Color.GREEN);

        this.add(logo);
        this.add(liveInfectionCount);
        this.add(liveDate);
        this.add(placeholder);
        this.add(curedCount);
        this.add(roll);
    }

    public void startFlowOfTime() {
        new Thread(() -> {
            LocalDate start = LocalDate.of(2020, 1, 1);
            while (isFlowing) {
                try {
                    Thread.sleep(200);
                    liveDate.setText(dateHtmlFormat+start.toString()+htmlFormatClose);
                    start = start.plusDays(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public JLabel getLiveInfectionCount() {
        return liveInfectionCount;
    }

    public JLabel getCuredCount() {
        return curedCount;
    }

    public JLabel getLiveDate() {
        return liveDate;
    }

    public boolean isFlowing() {
        return isFlowing;
    }

    public void setFlowing(boolean flowing) {
        isFlowing = flowing;
    }

    public JLabel getRoll() {
        return roll;
    }
}
