package view;

import javax.swing.*;

public class HowToPlayWindow extends JFrame {
    private JLabel text = new JLabel("<html><font color = green>Zielonia linia</font> - transport autokarem" +
            "<br><font color = blue>Niebieska linia</font> - transport promem" +
            "<br><font color = gray>Szara linia</font> - transport koleją" +
            "<br><font color = red>Czerwona linia</font> - transport samolotem" +
            "<br><font color = #FFC0CB>Różowa linia</font> - transport bryczką :) " +
            "<br>" +
            "<br>Czerwonym kółkiem zaznaczane są kraje, w których istnieje przypadek wirusa." +
            "<br>Skrót klawiszowy <b>CTRL+SHIFT+Q</b> powoduje wywołanie menu głównego."+
            "<br>"+
            "Umiejętności pasywne odblokowywane są po uratowaniu określonej liczby osób i użyte mogą zostać jedynie raz (oprócz Distribute Sanitiser)" +
            "<br>Umiejętności aktywne mogą z reguły (oprócz Import Drugs) być stosowane wielokrotnie i reprezentują doraźne środki walki z epidemią." +
            "<br>Muzyczka dodana pół-żartem, pół-serio :) Na wszelki wypadek przed uruchomieniem proszę troszkę ściszyć dźwięk." +
            "<br>" +
            "<br>Ranking organizowany jest na podstawie daty w grze. Im szybciej (wcześniejsza data) gracz powstrzyma wirusa, tym wyższą pozycję w rankingu zajmie." +
            "<br>Plik zapisywany jest w katalogu util -> Record. Można go podejrzeć w menu High Scores." +
            "<br>" +
            "<br>Poziom łatwy: zmniejszona szansa wygenerowania niekorzystnych zdarzeń losowych. Przyrost wirusa wolniejszy." +
            "<br>Poziom trudny: zwiększona szansa wygenerowania niekorzystnych zdarzeń losowych. Przyrost wirusa szybszy. " +
            "<br>Zdarzenie losowe pozytywne - (Glint of Hope)"+
            "</html>");

    public HowToPlayWindow() {
        this.setResizable(false);
        this.setSize(200, 600);
        this.setTitle("How to play");
        JPanel menuPane = new JPanel();
        menuPane.add(text);
        this.getContentPane().add(menuPane);
        this.pack();
        this.setVisible(true);
    }
}
