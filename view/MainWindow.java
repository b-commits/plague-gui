package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    public MainWindow(String difficulty) throws IOException {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setTitle("GUI Game");
        this.setSize(1500, 1000);

        TopPanel top = new TopPanel();
        SideBarPanel side = new SideBarPanel();
        World world = new World(top, side, difficulty);
        ScrollPane sp = new ScrollPane();
        sp.add(world);

        this.add(top, BorderLayout.NORTH);
        this.add(side, BorderLayout.WEST);
        this.add(sp, BorderLayout.CENTER);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
