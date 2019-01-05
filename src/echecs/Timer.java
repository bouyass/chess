/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lyes 
 * mise en place du compte a rebours de la partie 6 minustes une
 * fois cette durée est écoulée dans la partie prend fin sur une partie nulle
 *
 * cette classe utilise un thread pour effectuer le décompte et affiche une
 * boite de dialogue a la fin de décompte notifier l'utilisateur que la partie
 * est finie sans gagnant
 */
public class Timer {

    boolean finPartie = false;

    public Timer(FenetreJeu fj) {
        Thread t = new Thread() {
            int i = 60;
            String secondes;
            int minutes = 300 / 60;
            String timer = "5:00";
            String min;

            public void run() {
                while (!finPartie) {
                    try {
                        i--;
                        if (i < 0) {
                            minutes--;
                            i = 59;
                        }
                        if (i < 10) {
                            secondes = "0" + String.valueOf(i);
                        } else {
                            secondes = String.valueOf(i);
                        }
                        if (minutes < 10) {
                            min = "0" + String.valueOf(minutes);
                        }
                        timer = String.valueOf(min) + ":" + secondes;
                        fj.dessinerTimer(timer);

                        if (minutes == 0 && i == 0) {
                            timer = "00:00";
                            fj.dessinerTimer(timer);
                            finPartie = true;
                            dialog(fj);
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Partie.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
        t.start();
    }

    void dialog(FenetreJeu fj) {
        String[] choix = {"Rejouer", "Quitter"};
        JOptionPane jop = new JOptionPane();
        int rang = jop.showOptionDialog(null,
                "Partie finie sur un match null",
                "Fin de partie",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[1]);
        if (choix[rang] == "Rejouer") {
            fj.dispose();
            Echecs echec = new Echecs();
        } else {
            System.exit(0);
        }
    }

}
