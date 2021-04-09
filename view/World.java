package view;

import models.Country;
import models.Transit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class World extends JPanel {

    private BufferedImage worldMap;
    private ArrayList<Country> countries;
    private ArrayList<Transit> transit;
    private TopPanel top;
    private SideBarPanel side;
    private int totalCured;
    private int totalInfected;

    private Thread spreadVirus;
    private volatile boolean isSpreading;
    private volatile boolean importIsRunning = true;
    private Thread spreadToCountries;
    private Timer checkStateTimer;

    private boolean isPassive1Unlocked = false; // finance
    private boolean isPassive2Unlocked = false; // lock-down
    private boolean isPassive3Unlocked = false;
    private boolean isPassive4Unlocked = false;

    private boolean isActive2Unlocked = false;  // sew
    private boolean isActive3Unlocked = false;  // resp
    private boolean isActive4Unlocked = false;  // import
    private boolean isActive5Unlocked = false;  // ban

    private int multiplier = 1000;

    public static int tick = 0;
    private boolean calamityGenRunning = true;

    public World(TopPanel top, SideBarPanel side, String difficulty) throws IOException  {
        this.top = top;
        this.side = side;
        setPreferredSize(new Dimension(2000, 993));
        worldMap = ImageIO.read(new File("src/resources/worldMap.png"));

        populateMap();
        spreadToCountries();
        spreadVirus();
        setListeners();
        lockPerks();
        setShortcut();
        loadSoundtrack();

        switch (difficulty) {
            case "Easy":
                generateAGreatCalamity(990);
                multiplier = 1250;
                break;
            case "Hard":
                generateAGreatCalamity(1050);
                multiplier = 500;
                break;
        }

        checkStateTimer = new Timer(10, e -> {
           repaint();
           // FINANCE RESEARCH
           if (totalCured > 25 && totalCured < 100 && !isPassive1Unlocked) {
               side.getFinanceResearch().setEnabled(true);
               isPassive1Unlocked = true;
               JOptionPane.showMessageDialog(null, "Perk unlocked! You can now finance pharma research.");
           }
           // SEW FACE MASKS
           if (totalCured > 100 && totalCured < 500 && !isActive2Unlocked) {
               side.getMakeAFaceMask().setEnabled(true);
               isActive2Unlocked = true;
               JOptionPane.showMessageDialog(null, "Perk unlocked! You have taught yourself to sew facemasks.\n" +
                       "By sewing facemasks, you can help twice the number of people than by spreading leaflets.");
           }
           // LOCKDOWN
           if (totalCured > 1200 && totalCured < 2500 && !isPassive2Unlocked) {
               side.getImposeLockdown().setEnabled(true);
               isPassive2Unlocked = true;
               JOptionPane.showMessageDialog(null, "Perk unlocked! Your actions have inspired the authorities to impose a lock down to help fight the virus.\n" +
                       "Stops infection in a random country on activation.");
           }
           // RESPIRATOR
           if (totalCured > 1500 && totalCured < 4000 && !isActive3Unlocked) {
               side.getBuildARespirator().setEnabled(true);
               isActive3Unlocked = true;
               JOptionPane.showMessageDialog(null, "Perk unlocked! You have learned to build a respirator.\nLow chance of curing a larger number of people on click.");
           }
           // IMPORT DRUGS
           if (totalCured > 2000 && !isActive4Unlocked) {
               side.getImportDrugs().setEnabled(true);
               isActive4Unlocked = true;
               JOptionPane.showMessageDialog(null, "Perk unlocked! Pharma drug trade has increased within all countries.");
           }
           // BAN TRAFFIC
            if (totalCured > 2300 && !isActive5Unlocked) {
                side.getBanTraffic().setEnabled(true);
                isActive5Unlocked = true;
                JOptionPane.showMessageDialog(null, "You've made the authorities realize that they have to close the borders. A random route closes on activation.");
            }
            // SANITISER
            if (totalCured > 3000 && !isPassive3Unlocked) {
                side.getDistributeSanitiser().setEnabled(true);
                isPassive3Unlocked = true;
                JOptionPane.showMessageDialog(null, "You've made the authorities realize that they have to close the borders. A random route closes on activation.");
            }
            // VACCINE
            if (totalCured > 3500 && !isPassive4Unlocked) {
                side.getDevelopAVaccine().setEnabled(true);
                isPassive4Unlocked = true;
                JOptionPane.showMessageDialog(null, "Vaccine development in underway.");
            }
            // GAME_END
            if (totalInfected < 0) {
                checkStateTimer.stop();
                top.setFlowing(false);
                isSpreading = false;
                importIsRunning = false;
                String s = top.getLiveDate().getText().substring(42,52);
                new GameEndWindow(s);
            }
        });
        checkStateTimer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(worldMap, 0, 0, this);
        drawCountries(g);
        drawTransit(g);
        drawVirus(g);
    }

    private void drawCountries(Graphics g) {
        for (Country country : countries) {
            g.setColor(Color.RED);
            g.fillRect(country.getX(), country.getY(), 5, 5);
        }
    }

    private void drawTransit(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke(1));
        for (Transit transit : transit) {
            switch (transit.getType()) {
                case "Bus":
                    g.setColor(Color.GREEN);
                    g.drawLine(transit.getFrom().getX(), transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
                case "Airline":
                    g.setColor(Color.MAGENTA);
                    g.drawLine(transit.getFrom().getX(), transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
                case "Ferry":
                    g.setColor(Color.BLUE);
                    g.drawLine(transit.getFrom().getX(), transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
                case "Train":
                    g.setColor(Color.GRAY);
                    g.drawLine(transit.getFrom().getX(), transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
                case "Stagecoach":
                    g.setColor(Color.PINK);
                    g.drawLine(transit.getFrom().getX(), transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
                case "Supply route":
                    ((Graphics2D) g).setStroke(new BasicStroke(3));
                    g.setColor(Color.ORANGE);
                    g.drawLine(1000, transit.getFrom().getY(), transit.getTo().getX(), transit.getTo().getY());
                    break;
            }
        }
    }

    private void drawVirus(Graphics g) {
        g.setColor(Color.RED);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (Country country : countries) {
            if (country.getInfected() > 10) {
                g.drawOval(country.getX()-22, country.getY()-22, 50, 50);
            }
        }
    }

    private void spreadToCountries() {
        spreadToCountries = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(4000);
                    for (Transit route : transit) {
                        if (route.getFrom().hasVirus() || route.getTo().hasVirus()) {
                            route.getTo().setVirus(true);
                            route.getFrom().setVirus(true);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        spreadToCountries.start();
    }

    private void spreadVirus() {
        totalInfected = 0;
        spreadVirus =  new Thread(() -> {
            try {
                isSpreading = true;
                while (isSpreading) {
                    Thread.sleep(multiplier);
                        for (Country country : countries) {
                            if (country.hasVirus() && !country.isLockdown()) {
                                country.setInfected(country.getInfected() + 10);
                                totalInfected += 10;
                                top.getLiveInfectionCount().setText(String.valueOf(totalInfected));
                            }
                            if (country.getInfected() < 0) {
                                country.setVirus(false);
                            }
                        }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        spreadVirus.start();
    }

    private void setShortcut() {
        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("shift ctrl pressed Q"), "shiftcontrolq");
        this.getActionMap().put("shiftcontrolq", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        new MainMenuWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        });
    }

    private void populateMap() {
        countries = new ArrayList<>();
        transit = new ArrayList<>();
        Country Poland = new Country(920, 198, "Poland", 37_000_000);
        Country China = new Country (1471, 308, "China", 1_400_000_000); China.setVirus(true);
        Country India = new Country(1285, 400, "India", 1_400_000_00);
        Country Japan = new Country (1610, 305, "Japan", 126_000_000);
        Country Russia = new Country(1265, 130, "Russia", 144_000_000);
        Country Korea = new Country (1550, 305, "South Korea", 51_000_000);
        Country Germany = new Country(870, 205, "Germany", 83_000_000);
        Country USA = new Country (235, 285, "USA", 330_000_000);
        Country Canada = new Country (285, 165, "Canada", 37_000_000);
        Country France = new Country (830, 235, "France", 65_000_000);
        Country Brazil = new Country (500, 625, "Brazil", 210_000_000);
        Country United_Kingdom = new Country (810, 192, "UK", 65_000_000);
        Country Turkey = new Country(1010, 290, "Turkey", 82_000_000);
        Country Australia = new Country(1625, 730, "Australia", 25_000_000);
        Country Sweden = new Country(900, 140, "Sweden", 10_000_000);
        Country South_Africa = new Country(950, 765, "South Africa", 54_000_000);
        countries.addAll(Arrays.asList(Poland, China, India, Japan, Russia, Korea,
                                       Germany, USA, Canada, France, Brazil, United_Kingdom,
                                       Turkey, Sweden, South_Africa, Australia));
        transit.add(new Transit(Poland, Germany, "Bus"));
        transit.add(new Transit(China, Japan, "Ferry"));
        transit.add(new Transit(India, China, "Bus"));
        transit.add(new Transit(Russia, China, "Bus"));
        transit.add(new Transit(Korea, Japan, "Ferry"));
        transit.add(new Transit(Canada, USA, "Train"));
        transit.add(new Transit(USA, China, "Airline"));
        transit.add(new Transit(Brazil, Germany, "Airline"));
        transit.add(new Transit(Germany, China, "Airline"));
        transit.add(new Transit(Germany, Turkey, "Airline"));
        transit.add(new Transit(France, Germany, "Train"));
        transit.add(new Transit(Poland, France, "Train"));
        transit.add(new Transit(Poland, Germany, "Train"));
        transit.add(new Transit(United_Kingdom, France, "Bus"));
        transit.add(new Transit(Japan, USA, "Airline"));
        transit.add(new Transit(Canada, Sweden, "Airline"));
        transit.add(new Transit(Sweden, Australia, "Airline"));
        transit.add(new Transit(Australia, South_Africa, "Airline"));
        transit.add(new Transit(Brazil, India, "Airline"));
        transit.add(new Transit(Russia, Germany, "Train"));
        transit.add(new Transit(United_Kingdom, Germany, "Airline"));
        transit.add(new Transit(Germany, Turkey, "Train"));
        transit.add(new Transit(Brazil, USA, "Airline"));
        transit.add(new Transit(South_Africa, USA, "Ferry"));
        transit.add(new Transit(Poland, France, "Stagecoach"));

    }

    private void setListeners() {
        this.side.getHandOutFlyersButton().addActionListener(e -> {
            top.getLiveInfectionCount().setText(String.valueOf(Integer.parseInt(top.getLiveInfectionCount().getText())-10));
            totalInfected = totalInfected-10;
            totalCured = totalCured+10;
            System.out.println(totalInfected+" / "+totalCured);
            int randomizedCountryIndex = (int)(Math.random()*countries.size());
            Country country = countries.get(randomizedCountryIndex);
            country.setInfected(country.getInfected()-10);
            top.getCuredCount().setText(String.valueOf(totalCured));
        });

        this.side.getFinanceResearch().addActionListener(e -> {
            multiplier = 2000;
            side.getFinanceResearch().setEnabled(false);
            JOptionPane.showMessageDialog(null, "Pharma research has been financed. Infection rate across the world decreased by half.");
            side.getFinanceResearch().setBackground(Color.RED);
        });

        this.side.getMakeAFaceMask().addActionListener(e -> {
            top.getLiveInfectionCount().setText(String.valueOf(Integer.parseInt(top.getLiveInfectionCount().getText())-20));
            totalCured = totalCured+20;
            totalInfected = totalInfected-20;
            int randomizedCountryIndex = (int)(Math.random()*countries.size());
            Country country = countries.get(randomizedCountryIndex);
            country.setInfected(country.getInfected()-20);
            top.getCuredCount().setText(String.valueOf(totalCured));
        });

        this.side.getImposeLockdown().addActionListener(e -> {
            int randomIndex = (int)(Math.random()*countries.size());
            Country randomCountry = countries.get(randomIndex);
            randomCountry.setLockdown(true);
            JOptionPane.showMessageDialog(null, "Lockdown imposed by the government of "+randomCountry.getName()+". Virus has stopped spreading within the country's borders.");
            side.getImposeLockdown().setEnabled(false);
            side.getImposeLockdown().setBackground(Color.RED);
        });

        this.side.getBuildARespirator().addActionListener(e -> {
            generateAGlintOfHope(1100);
        });

        this.side.getImportDrugs().addActionListener(e -> {
            side.getImportDrugs().setEnabled(false);
            Country placeholder = new Country(0, 0, "WHO", 50);
            int randomCountryIndex = (int)(Math.random()*countries.size());
            Transit supplyRoute = new Transit(placeholder, countries.get(randomCountryIndex), "Supply route");
            transit.add(supplyRoute);
            JOptionPane.showMessageDialog(null, "A special pharma trade route has been established. WHO deploys additional supply drops to "+countries.get(randomCountryIndex).getName()+". -5 infected/5s.");
            new Thread(() -> {
                while (importIsRunning) {
                    try {
                        Thread.sleep(1000);
                        countries.get(randomCountryIndex).setInfected(countries.get(randomCountryIndex).getInfected() - 5);
                        top.getCuredCount().setText(String.valueOf(Integer.parseInt(top.getCuredCount().getText()) + 5));
                        top.getLiveInfectionCount().setText(String.valueOf(Integer.parseInt(top.getLiveInfectionCount().getText()) - 5));
                        totalInfected = totalInfected - 5;
                        totalCured = totalCured + 5;
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        });

        this.side.getBanTraffic().addActionListener(e -> {
            int randomIndex = (int)(Math.random()*transit.size());
            Transit randomRoute = transit.get(randomIndex);
            if (!randomRoute.getTo().getName().equals("WHO") && !randomRoute.getFrom().getName().equals("WHO")) {
                transit.remove(randomRoute);
                JOptionPane.showMessageDialog(null, "The government of "+randomRoute.getFrom().getName()+" has now ruled that any "+randomRoute.getType()+" traffic to "+randomRoute.getTo().getName()+" shall now be banned. Effective in a few days.");
            } else {
                JOptionPane.showMessageDialog(null, "Traffic ban has been veto'ed. Try again.");
            }
        });

        this.side.getDistributeSanitiser().addActionListener(e -> {
            isSpreading = false;
            Timer t = new Timer(3500, e1 -> {
                tick++;
                if (tick > 5) {
                    isSpreading = true;
                }
            });
            t.start();
        });

        this.side.getDevelopAVaccine().addActionListener(e -> {
            this.top.getLiveInfectionCount().setText("Vaccine");
            this.isSpreading = false;
            this.totalInfected = -50000;
            transit.clear();
        });

    }

    private void lockPerks() {
        // refactor
        side.getBuildARespirator().setEnabled(false);
        side.getBanTraffic().setEnabled(false);
        side.getDevelopAVaccine().setEnabled(false);
        side.getDistributeFacemasks().setEnabled(false);
        side.getDistributeFacemasks().setVisible(false);
        side.getDistributeSanitiser().setVisible(true);
        side.getDistributeSanitiser().setEnabled(false);
        side.getFinanceResearch().setEnabled(false);
        side.getMakeAFaceMask().setEnabled(false);
        side.getImportDrugs().setEnabled(false);
        side.getImposeLockdown().setEnabled(false);
        side.getIncreaseNumberOfBeds().setVisible(false);
    }

    private void generateAGreatCalamity(int chance) {
        new Thread(() -> {
            try {
                Thread.sleep(15_000);
                while (calamityGenRunning) {
                    if ((int)(Math.random()*chance) > 900) {
                        JOptionPane.showMessageDialog(null, "A great calamity ensues! Thousands become infected in the wake of a political rally!\n+10 000 infected.");
                        top.getLiveInfectionCount().setText(String.valueOf(Integer.parseInt(top.getLiveInfectionCount().toString())+10_000));
                        totalInfected += 10_000;
                    }
                    if ((int)(Math.random()*100000) < 5) {
                        JOptionPane.showMessageDialog(null, "A glint of hope! Thousands miraculously recover in the wake of a facemask airdrop!\n-5 000 infected");
                        top.getLiveInfectionCount().setText(String.valueOf(Integer.parseInt(top.getLiveInfectionCount().toString())-10_000));
                        totalCured -= 10_000;
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void generateAGlintOfHope(int chance) {
        if ((int)(Math.random()*chance) >= 999) {
            top.getRoll().setText("<html><font size = 55><font color =green>          [Success!]           </font></html>");
            JOptionPane.showMessageDialog(null, "A glint of hope! Hundreds miraculously recover.\n-200 infected.");
            top.getLiveInfectionCount().setText(String.valueOf(totalCured-200));
            top.getCuredCount().setText(String.valueOf(totalCured+200));
            totalInfected -= 200;
            totalCured += 200;
        } else {
            top.getRoll().setText("<html><font size = 50><font color =red>          [Failure!]           </font></html>");
        }
    }

    public void loadSoundtrack() {
        File music;
        try {
            music = new File("src\\resources\\OST.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
