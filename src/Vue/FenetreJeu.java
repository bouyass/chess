/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import echecs.Couleur;
import echecs.Echiquier;
import echecs.Partie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author lyes
 */
public class FenetreJeu extends JFrame {

    public Echiquier echiquier;
    final public JLabel[][] labels = new JLabel[8][8];
    //panel stockant l'echiuier
    JPanel panneau = new JPanel(new GridLayout(8, 8));
    //panel stockant la partie status de l'application 
    JPanel statusPanel = new JPanel();
    //creation des couleurs des cases 
    Color couleur = new Color(153, 76, 0);
    Color couleur1 = new Color(255, 255, 204);
    Color couleur2 = new Color(255, 204, 153);
    JLabel afficheTemps = new JLabel("ça va commencer ...");
    public JTextArea ta = new JTextArea();
    Partie partie;
    int pos;

    // constructeur
    public FenetreJeu(Echiquier echiquier, String choix, Partie partie) {
        this.partie = partie;
        this.echiquier = echiquier;
        dessinerStatus();
        dessinerEchiquier();
        panneau.setBounds(0, 0, 600, 600);
        this.getContentPane().setLayout(null);
        this.setSize(920, 635);
        this.setVisible(true);
        EcouterCases(choix);
    }

    // methode dessinant l'echiquier
    public void dessinerEchiquier() {
        //creation des JLabel qui stockeront les cases de l'echiquier
        for (int k = 0; k <= 7; k++) {
            for (int t = 0; t <= 7; t++) {
                labels[k][t] = new JLabel();
            }
        }

        // affiliation des cases de l'echiquier aux JLables
        int compt = 0;
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {

                if (echiquier.cases[i][j].getCouleur() == Couleur.NOIR) {
                    labels[i][j].setOpaque(true);
                    labels[i][j].setBackground(couleur);
                    labels[i][j].setPreferredSize(new Dimension(20, 50));

                    if (echiquier.cases[i][j].piece != null) {
                        ImageIcon ic = new ImageIcon(echiquier.cases[i][j].piece.getIcone());
                        labels[i][j].setIcon(ic);
                        labels[i][j].setVerticalAlignment(JLabel.CENTER);
                        labels[i][j].setVerticalAlignment(JLabel.CENTER);
                    }

                    panneau.add(labels[i][j]);

                } else {
                    labels[i][j].setOpaque(true);
                    labels[i][j].setBackground(couleur1);
                    labels[i][j].setPreferredSize(new Dimension(20, 50));
                    if (echiquier.cases[i][j].piece != null) {
                        labels[i][j].setIcon(new ImageIcon(echiquier.cases[i][j].piece.getIcone()));
                    }
                    panneau.add(labels[i][j]);

                }
            }
        }
        this.getContentPane().add(panneau);
    }

    // methde dessinant la partie status de l'application
    public void dessinerStatus() {

        JScrollPane scroll = new JScrollPane(ta);
        JLabel temps = new JLabel("temps de la pratie");

        JLabel statusLabel = new JLabel("status de la partie");
        ta.setPreferredSize(new Dimension(300, 500));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Serif", Font.BOLD, 16));
        ta.setEditable(false);
        ta.setAutoscrolls(true);
        temps.setLocation(620, 700);
        statusPanel.setBounds(615, 20, 300, 750);
        statusPanel.add(statusLabel);
        statusPanel.add(scroll);
        statusPanel.add(temps);
        statusPanel.add(afficheTemps);
        statusLabel.setIcon(new ImageIcon("/home/lyes/NetBeansProjects/Chess/Icone/info.png"));
        temps.setIcon(new ImageIcon("/home/lyes/NetBeansProjects/Chess/Icone/chrono.png"));

        this.getContentPane().add(statusPanel);
    }

    public void dessinerTimer(String timer) {
        this.afficheTemps.setText(timer);
    }

    public void EcouterCases(String choix) {
        if (choix == "joueur") {
            int i, j;

            this.ta.insert("début de la partie\n\n", pos);
            this.ta.insert("aux blancs de jouer\n\n", pos);
            for (i = 0; i <= 7; i++) {
                for (j = 0; j <= 7; j++) {
                    final int k = i;
                    final int l = j;

                    MouseListener m1 = new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent ee) {
                            // ecouter les clicks des joueurs
                            partie.jouer(k, l);

                        }
                    };
                    this.labels[i][j].addMouseListener(m1);
                }
            }
        } else {
            // partie contre le pc 
        }
    }
}
