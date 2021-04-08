package view;

import javax.swing.*;
import java.awt.*;

public class SideBarPanel extends JPanel {

    private JButton handOutFlyersButton = new JButton("Hand out flyers");
    private JButton makeAFaceMask = new JButton("Make a face mask");
    private JButton buildARespirator = new JButton("Make a respirator");
    private JButton importDrugs = new JButton("Import drugs");
    private JButton banTraffic = new JButton("Ban traffic");

    private JButton financeResearch = new JButton("Finance research");
    private JButton imposeLockdown = new JButton("Impose a lockdown");
    private JButton distributeFacemasks = new JButton("Distribute facemasks");
    private JButton distributeSanitiser = new JButton("Distribute sanitiser");
    private JButton increaseNumberOfBeds = new JButton("Supply hospitals");
    private JButton developAVaccine = new JButton("Develop a vaccine");


    public SideBarPanel() {
        this.setBackground(Color.DARK_GRAY);
        this.setSize(100, 800);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.handOutFlyersButton.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.makeAFaceMask.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.buildARespirator.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.importDrugs.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.banTraffic.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.financeResearch.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.imposeLockdown.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.distributeFacemasks.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.distributeSanitiser.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.increaseNumberOfBeds.setMaximumSize(new Dimension(this.getWidth()+60, 50));
        this.developAVaccine.setMaximumSize(new Dimension(this.getWidth()+60, 50));

        this.handOutFlyersButton.setToolTipText("+10 cured / -10 infected");
        this.makeAFaceMask.setToolTipText("+100 cured / -100 infected");
        this.buildARespirator.setToolTipText("Low chance of generating a glint of hope.");
        this.importDrugs.setToolTipText("+5 cured/s, -5 infected/s");
        this.banTraffic.setToolTipText("Closes a random route.");
        this.distributeSanitiser.setToolTipText("Halts virus spreading for a short duration");
        this.developAVaccine.setToolTipText("Hmm...?");

        this.add(new JLabel("<html><font color = red>Active abilities: </font></html>"));
        this.add(new JLabel(" "));
        this.add(handOutFlyersButton);
        this.add(makeAFaceMask);
        this.add(buildARespirator);
        this.add(importDrugs);
        this.add(banTraffic);
        this.add(new JLabel(" "));

        this.add(new JLabel("<html><font color = #3366ff>Passive abilites: </font></html>"));
        this.add(new JLabel(" "));
        this.add(financeResearch);
        this.add(imposeLockdown);
        this.add(distributeFacemasks);
        this.add(distributeSanitiser);
        this.add(increaseNumberOfBeds);
        this.add(developAVaccine);

        this.setVisible(true);
    }

    public JButton getHandOutFlyersButton() {
        return handOutFlyersButton;
    }

    public JButton getMakeAFaceMask() {
        return makeAFaceMask;
    }

    public JButton getBuildARespirator() {
        return buildARespirator;
    }

    public JButton getImportDrugs() {
        return importDrugs;
    }

    public JButton getBanTraffic() {
        return banTraffic;
    }

    public JButton getFinanceResearch() {
        return financeResearch;
    }

    public JButton getImposeLockdown() {
        return imposeLockdown;
    }

    public JButton getDistributeFacemasks() {
        return distributeFacemasks;
    }

    public JButton getDistributeSanitiser() {
        return distributeSanitiser;
    }

    public JButton getIncreaseNumberOfBeds() {
        return increaseNumberOfBeds;
    }

    public JButton getDevelopAVaccine() {
        return developAVaccine;
    }
}
