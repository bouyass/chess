/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import javax.swing.JOptionPane;

/**
 *
 * @author lyes
 */
public class Echecs {

    /**
     * @param args the command line arguments
     *
     * cette lance lance la partie en instanciant la classe partie et passant le
     * choix de l'utilisateur
     */
    public static void main(String[] args) {
        // lancement d'une nouvelle partie

        // boite de dialogue permettant a l'utilisateur de choisir contre qui il souhaite jouer
        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
        String[] option = {"contre un joueur", "contre l'ordinateur"};
        int rang = jop.showOptionDialog(null, "Contre ui souhaitez vous jouer", "Jeu d'echec",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        jop2.showMessageDialog(null, "Vous allez jouer " + option[rang], "validation choix", JOptionPane.INFORMATION_MESSAGE);

        if (option[rang] == "contre un joueur") {
            Partie jeu = new Partie("joueur");

        } else {
            //lancer une partie contre l'ordinateur         
            Partie jeu = new Partie("pc");
        }

    }

}
